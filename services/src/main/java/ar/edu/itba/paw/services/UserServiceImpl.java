package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.ContactInfoNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.exceptions.IllegalContactException;
import ar.edu.itba.paw.interfaces.exceptions.IllegalOperationException;
import ar.edu.itba.paw.interfaces.persistance.*;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.NotificationService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.contact.AuxContactDto;
import ar.edu.itba.paw.models.contact.ContactInfo;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.image.NewImageDto;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.location.City;
import ar.edu.itba.paw.models.location.State;
import ar.edu.itba.paw.models.token.PasswordResetToken;
import ar.edu.itba.paw.models.token.SessionRefreshToken;
import ar.edu.itba.paw.models.token.VerificationToken;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.UserInfo;
import ar.edu.itba.paw.models.user.provider.Location;
import ar.edu.itba.paw.models.user.provider.ProviderDetails;
import ar.edu.itba.paw.models.user.provider.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;

@org.springframework.stereotype.Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private VerificationTokenDao verificationTokenDao;

    @Autowired
    private PasswordResetTokenDao passwordResetTokenDao;

    @Autowired
    private SessionRefreshTokenDao sessionRefreshTokenDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NotificationService notificationService;

    public final static Set<Roles> DEFAULT_ROLES = new HashSet<>(Arrays.asList(Roles.USER, Roles.NOT_VERIFIED));

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Transactional
    @Override
    public Optional<User> getUserById(long id) {
        LOGGER.debug("Retrieving user with id {}", id);
        return userDao.getUserById(id);
    }

    @Transactional
    @Override
    public Optional<User> getUserByEmail(String email) {
        LOGGER.debug("Retrieving user with email {}", email);

        return userDao.getUserByEmail(email);
    }

    @Transactional
    @Override
    public User createUser(String password, String name, String surname, String email, String phoneNumber, String state, String city) throws DuplicateUserException {
        LOGGER.debug("Creating user with email {}", email);
        final User user = userDao.createUser(passwordEncoder.encode(password), name, surname, email, phoneNumber, state, city, DEFAULT_ROLES);
        LOGGER.debug("Created user with id {}", user.getId());
        final VerificationToken token = generateVerificationToken(user);
        LOGGER.debug("Created verification token with id {}", token.getId());
        emailService.sendVerificationEmail(user, token, LocaleContextHolder.getLocale());
        return user;
    }

    @Transactional
    @Override
    public Optional<User> verifyAccount(String token) {
        final Optional<VerificationToken> vtokenOpt = verificationTokenDao.getTokenByValue(token);

        if (!vtokenOpt.isPresent()) {
            LOGGER.warn("No verification token with value {}", token);
            return Optional.empty();
        }

        final VerificationToken vtoken = vtokenOpt.get();
        verificationTokenDao.removeToken(vtoken);//remove always, either token is valid or not
        LOGGER.debug("Removed token with id {}", vtoken.getId());

        if (!vtoken.isValid()) {
            LOGGER.warn("Token with value {} is invalid", token);
            return Optional.empty();
        }

        final User user = vtoken.getUser();

        LOGGER.debug("Validating user with id {}", vtoken.getUser().getId());

        user.removeRole(Roles.NOT_VERIFIED);
        user.addRole(Roles.VERIFIED);

        return Optional.of(user);
    }

    @Transactional
    @Override
    public void resendVerificationToken(User user) {
        LOGGER.debug("Removing token for user with id {}", user.getId());
        final Optional<VerificationToken> vttokenOpt = verificationTokenDao.getTokenByUser(user);

        vttokenOpt.ifPresent(verificationToken -> verificationTokenDao.removeToken(verificationToken));

        final VerificationToken token = generateVerificationToken(user);
        LOGGER.debug("Created token with id {}", token.getId());

        emailService.sendVerificationEmail(user, token, LocaleContextHolder.getLocale());
    }

    @Transactional
    @Override
    public boolean validatePasswordReset(String token) {
        final Optional<PasswordResetToken> prtokenOpt = passwordResetTokenDao.getTokenByValue(token);

        if (!prtokenOpt.isPresent() || !prtokenOpt.get().isValid()) {
            LOGGER.info("token {} is not valid", token);
            return false;
        }

        LOGGER.info("token {} is valid", token);
        return true;
    }

    @Transactional
    @Override
    public SessionRefreshToken getSessionRefreshToken(User user) {
        LOGGER.debug("Retrieving session refresh token for user");
        final Optional<SessionRefreshToken> tokenOpt = sessionRefreshTokenDao.getTokenByUser(user);

        if (tokenOpt.isPresent()) {
            final SessionRefreshToken sessionRefreshToken = tokenOpt.get();
            if (!sessionRefreshToken.isValid()) {
                sessionRefreshToken.refresh();
            }
            return sessionRefreshToken;
        }

        return generateSessionRefreshToken(user);
    }

    @Transactional
    @Override
    public void deleteSessionRefreshToken(User user) {
        LOGGER.debug("Deleting session refresh token for user");
        final Optional<SessionRefreshToken> optToken = sessionRefreshTokenDao.getTokenByUser(user);
        optToken.ifPresent(sessionRefreshToken -> sessionRefreshTokenDao.removeToken(sessionRefreshToken));
    }

    @Transactional
    @Override
    public Optional<User> getUserByRefreshToken(String token) {
        LOGGER.debug("Retrieving user for token with value {}", token);
        return sessionRefreshTokenDao.getTokenByValue(token).map(SessionRefreshToken::getUser);
    }

    @Transactional
    @Override
    public void generateNewPassword(User user) {
        LOGGER.debug("Removing password reset token for user {}", user.getId());
        final Optional<PasswordResetToken> tokenOpt = passwordResetTokenDao.getTokenByUser(user);

        tokenOpt.ifPresent(passwordResetToken -> passwordResetTokenDao.removeToken(passwordResetToken));

        final PasswordResetToken token = generatePasswordResetToken(user);
        LOGGER.info("created password reset token for user {}", user.getId());

        emailService.sendPasswordResetEmail(user, token, LocaleContextHolder.getLocale());
    }

    @Transactional
    @Override
    public Optional<User> updatePassword(String token, String password) {
        final Optional<PasswordResetToken> prtokenOpt = passwordResetTokenDao.getTokenByValue(token);

        if (!prtokenOpt.isPresent()) {
            LOGGER.warn("Token {} is not valid", token);
            return Optional.empty();
        }

        final PasswordResetToken prtoken = prtokenOpt.get();
        final User user = prtoken.getUser();

        LOGGER.debug("Removing password reset token with id {}", prtoken.getId());
        passwordResetTokenDao.removeToken(prtoken); //remove always, either token is valid or not
        if (!prtoken.isValid()) {
            LOGGER.warn("Token {} has expired", token);
            return Optional.empty();
        }

        LOGGER.debug("Updating user password for user {}", user.getId());

        user.setPassword(passwordEncoder.encode(password));
        return Optional.of(user);
    }


    @Transactional
    @Override
    public void updateUserInfo(UserInfo userInfo, User user) {
        LOGGER.debug("Updating user info for user {}", user.getEmail());
        user.setName(userInfo.getName());
        user.setSurname(userInfo.getSurname());
        user.setCity(userInfo.getCity());
        user.setState(userInfo.getState());
        user.setPhoneNumber(userInfo.getPhoneNumber());
    }

    @Transactional
    @Override
    public void updateCoverImage(NewImageDto newImageDto, User user) {
        Image image = user.getCoverImage();
        LOGGER.debug("Updating user {} cover image", user.getEmail());
        user.setCoverImage(imageService.createImage(newImageDto));
    }

    @Transactional
    @Override
    public void updateProfileImage(NewImageDto newImageDto, User user) {
        Image image = user.getProfileImage();
        LOGGER.debug("Updating user {} profile image", user.getEmail());
        user.setProfileImage(imageService.createImage(newImageDto));
    }

    @Transactional
    @Override
    public void updateProviderInfo(User user, List<Long> citiesId, String startTime, String endTime) {
        final Collection<City> cities = locationDao.getCitiesById(citiesId);
        final State state = cities.stream().findFirst().get().getState();
        final Location location = user.getProviderDetails().getLocation();
        location.setCities(new HashSet<>(cities));
        location.setState(state);

        final Schedule schedule = user.getProviderDetails().getSchedule();
        final LocalTime localStartTime;
        final LocalTime localEndTime;

        if ((localStartTime = parseTime(startTime)) == null) {
            return;

        }

        if ((localEndTime = parseTime(endTime)) == null) {
            return;
        }

        schedule.setStartTime(localStartTime);
        schedule.setEndTime(localEndTime);

        LOGGER.info("Update provider info with id {}", user.getId());
    }


    @Transactional
    @Override
    public void makeProvider(User user, List<Long> citiesId, String startTime, String endTime) {
        LOGGER.debug("Making user a provider");
        final Collection<City> cities = locationDao.getCitiesById(citiesId);
        final State state = cities.stream().findFirst().get().getState();
        final Location location = new Location(user, new HashSet<>(cities), state);
        final LocalTime localStartTime;
        final LocalTime localEndTime;

        if ((localStartTime = parseTime(startTime)) == null)
            return;

        if ((localEndTime = parseTime(endTime)) == null)
            return;


        final Schedule schedule = new Schedule(user, localStartTime, localEndTime);

        userDao.persistProviderDetails(location, schedule);

        final ProviderDetails providerDetails = new ProviderDetails(location, schedule);

        user.addRole(Roles.PROVIDER);
        user.setProviderDetails(providerDetails);

        LOGGER.info("User {} is now provider", user.getId());
        emailService.sendProviderNotificationEmail(user, LocaleContextHolder.getLocale());
    }

    @Transactional
    @Override
    public JobContact contact(AuxContactDto auxContactDto, User user, User provider) throws IllegalContactException {
        ContactInfo contactInfo;

        if (auxContactDto.getContactInfoId() == -1) {
            LOGGER.debug("Adding new contact info");
            contactInfo = userDao.addContactInfo(auxContactDto);
        } else {
            LOGGER.debug("Retrieving used contact info");
            contactInfo = userDao.getContactInfoById(auxContactDto.getContactInfoId()).orElseThrow(ContactInfoNotFoundException::new);
        }
        if (provider.getId().equals(user.getId()))
            throw new IllegalContactException();

        LOGGER.debug("Adding new client to provider {}", provider.getId());
        final JobContact jobContact = userDao.createJobContact(user, provider, contactInfo, auxContactDto.getMessage(), LocalDateTime.now(), auxContactDto.getJob());
        provider.getProviderDetails().getContacts().add(jobContact);

        notificationService.createNewRequestNotification(provider, jobContact);

        emailService.sendJobRequestEmail(jobContact, LocaleContextHolder.getLocale());
        emailService.sendJobRequestConfirmationEmail(jobContact, LocaleContextHolder.getLocale());

        return jobContact;
    }

    @Transactional
    @Override
    public boolean hasContactJobProvided(User provider, User user, Job job) {
        LOGGER.debug("Retrieving user has contact job provided");
        return userDao.hasContactJobProvided(provider, user, job);
    }

    @Transactional
    @Override
    public void followUser(User user, User follower) {
        if (user.getId().equals(follower.getId())) {
            throw new IllegalOperationException();
        }
        LOGGER.debug("Adding user {} to user {} followers", follower, user);
        user.getFollowing().add(follower);
        follower.getFollowers().add(user);
        notificationService.createNewFollowerNotification(follower, user);
    }

    @Transactional
    @Override
    public boolean isUserFollowing(User user, User follower) {
        return user.userIsFollowing(follower.getEmail());
    }

    @Transactional
    @Override
    public void unfollowUser(User user, User follower) {
        if (user.getId().equals(follower.getId())) {
            throw new IllegalOperationException();
        }

        LOGGER.debug("Removing user {} to user {} followers", follower, user);
        user.getFollowing().remove(follower);
        follower.getFollowers().remove(user);
    }

    private PasswordResetToken generatePasswordResetToken(User user) {
        final String token = UUID.randomUUID().toString();
        return passwordResetTokenDao.createToken(user, token, VerificationToken.generateTokenExpirationDate());
    }

    private VerificationToken generateVerificationToken(User user) {
        final String token = UUID.randomUUID().toString();
        return verificationTokenDao.createVerificationToken(user, token, VerificationToken.generateTokenExpirationDate());
    }

    private SessionRefreshToken generateSessionRefreshToken(User user) {
        return sessionRefreshTokenDao.createToken(user,
            SessionRefreshToken.generateSessionToken(),
            SessionRefreshToken.generateTokenExpirationDate()
        );
    }

    private LocalTime parseTime(String time) {
        LocalTime localTime;

        try {
            localTime = LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            return null;
        }

        return localTime;
    }


}