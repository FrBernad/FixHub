package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.ContactInfoNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.exceptions.IllegalContactException;
import ar.edu.itba.paw.interfaces.persistance.LocationDao;
import ar.edu.itba.paw.interfaces.persistance.PasswordResetTokenDao;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.interfaces.persistance.VerificationTokenDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.contact.ContactDto;
import ar.edu.itba.paw.models.contact.ContactInfo;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.image.ImageDto;
import ar.edu.itba.paw.models.location.City;
import ar.edu.itba.paw.models.location.State;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.token.PasswordResetToken;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private EmailService emailService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public final static Set<Roles> DEFAULT_ROLES = new HashSet<>(Arrays.asList(Roles.USER, Roles.NOT_VERIFIED));

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Optional<User> getUserById(long id) {
        LOGGER.debug("Retrieving user with id {}", id);
        return userDao.getUserById(id);
    }

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
        emailService.sendVerificationEmail(user, token);
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

        LOGGER.debug("Validating user with id {}", vtoken.getUser().getId());

        final User user = vtoken.getUser();

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

        emailService.sendVerificationEmail(user, token);
    }

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
    public void generateNewPassword(User user) {
        LOGGER.debug("Removing password reset token for user {}", user.getId());
        final Optional<PasswordResetToken> tokenOpt = passwordResetTokenDao.getTokenByUser(user);

        tokenOpt.ifPresent(passwordResetToken -> passwordResetTokenDao.removeToken(passwordResetToken));

        final PasswordResetToken token = generatePasswordResetToken(user);
        LOGGER.info("created password reset token for user {}", user.getId());

        emailService.sendPasswordResetEmail(user, token);
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
        LOGGER.debug("Removing password reset token with id {}", prtoken.getId());
        passwordResetTokenDao.removeToken(prtoken); //remove always, either token is valid or not
        if (!prtoken.isValid()) {
            LOGGER.warn("Token {} has expired", token);
            return Optional.empty();
        }

        final User user = prtoken.getUser();
        LOGGER.debug("Updating user password for user {}", user.getId());

        user.setPassword(passwordEncoder.encode(password));
        return Optional.of(user);
    }


    @Override
    @Transactional
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
    public void updateCoverImage(ImageDto imageDto, User user) {
        Image image = user.getCoverImage();
        LOGGER.debug("Updating user {} cover image", user.getEmail());
        if (image == null) {
            user.setCoverImage(imageService.createImage(imageDto));
        } else
            user.getCoverImage().setData(imageDto.getData());

    }

    @Override
    @Transactional
    public void updateProfileImage(ImageDto imageDto, User user) {
        Image image = user.getProfileImage();
        LOGGER.debug("Updating user {} profile image", user.getEmail());
        if (image == null)
            user.setProfileImage(imageService.createImage(imageDto));
        else
            user.getProfileImage().setData(imageDto.getData());
    }

    @Override
    @Transactional
    public void updateProviderInfo(User user, List<Long> citiesId, String startTime, String endTime) {
        final Collection<City> cities = locationDao.getCitiesById(citiesId);
        final State state = cities.stream().findFirst().get().getState();
        final Location location = user.getProviderDetails().getLocation();
        location.setCities(new HashSet<>(cities));
        location.setState(state);

        final Schedule schedule = user.getProviderDetails().getSchedule();
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);

        LOGGER.info("Update provider info with id {}", user.getId());
    }


    @Transactional
    @Override
    public void makeProvider(User user, List<Long> citiesId, String startTime, String endTime) {

        final Collection<City> cities = locationDao.getCitiesById(citiesId);
        final State state = cities.stream().findFirst().get().getState();
        final Location location = new Location(user, new HashSet<>(cities), state);
        final Schedule schedule = new Schedule(user, startTime, endTime);

        userDao.persistProviderDetails(location, schedule);

        final ProviderDetails providerDetails = new ProviderDetails(location, schedule);

        user.addRole(Roles.PROVIDER);
        user.setProviderDetails(providerDetails);

        LOGGER.info("User {} is now provider", user.getId());
        emailService.sendProviderNotificationEmail(user);
    }

    @Transactional
    @Override
    public void contact(ContactDto contactDto, User user, User provider) throws IllegalContactException {
        ContactInfo contactInfo;

        if (contactDto.getContactInfoId() == -1) {
            LOGGER.debug("Adding new contact info");
            contactInfo = userDao.addContactInfo(contactDto);
        } else {
            LOGGER.debug("Retrieving used contact info");
            contactInfo = userDao.getContactInfoById(contactDto.getContactInfoId()).orElseThrow(ContactInfoNotFoundException::new);
        }
        if (provider.getId().equals(user.getId()))
            throw new IllegalContactException();

        LOGGER.debug("Adding new client to provider {}", provider.getId());
        final JobContact jobContact = userDao.createJobContact(user, provider, contactInfo, contactDto.getMessage(), LocalDateTime.now(), contactDto.getJob());
        provider.getProviderDetails().getContacts().add(jobContact);

        emailService.sendJobRequestEmail(contactDto);
        emailService.sendJobRequestConfirmationEmail(contactDto);

    }

    @Override
    public boolean hasContactJobProvided(User provider, User user) {
        return userDao.hasContactJobProvided(provider, user);
    }

    @Transactional
    @Override
    public void followUser(User user, User follower) {
        LOGGER.debug("Adding user {} to user {} followers", follower, user);
        user.getFollowing().add(follower);
        follower.getFollowers().add(user);
    }

    @Transactional
    @Override
    public void unfollowUser(User user, User follower) {
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

}