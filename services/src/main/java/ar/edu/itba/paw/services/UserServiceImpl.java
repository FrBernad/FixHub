package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.ContactInfoNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.exceptions.IllegalContactException;
import ar.edu.itba.paw.interfaces.persistance.PasswordResetTokenDao;
import ar.edu.itba.paw.interfaces.persistance.VerificationTokenDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.token.PasswordResetToken;
import ar.edu.itba.paw.models.token.VerificationToken;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.provider.Location;
import ar.edu.itba.paw.models.user.provider.Schedule;
import ar.edu.itba.paw.models.user.provider.Stats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@org.springframework.stereotype.Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

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

    @Autowired
    private String appBaseUrl;

    @Autowired
    private MessageSource messageSource;

    public final static Set<Roles> DEFAULT_ROLES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(Roles.USER, Roles.NOT_VERIFIED)));

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
        sendVerificationToken(user, token);
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
        verificationTokenDao.removeTokenByUserId(user.getId());
        final VerificationToken token = generateVerificationToken(user);
        LOGGER.debug("Created token with id {}", token.getId());
        sendVerificationToken(user, token);
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
        passwordResetTokenDao.removeTokenByUserId(user.getId());
        final PasswordResetToken token = generatePasswordResetToken(user.getId());
        LOGGER.info("created password reset token for user {}", user.getId());
        sendPasswordResetToken(user, token);
    }

    @Transactional
    @Override
    public Optional<User> updatePassword(String token, String password) {
//        final Optional<PasswordResetToken> prtokenOpt = passwordResetTokenDao.getTokenByValue(token);
//
//        if (!prtokenOpt.isPresent()) {
//            LOGGER.warn("Token {} is not valid", token);
//            return Optional.empty();
//        }
//
//        final PasswordResetToken prtoken = prtokenOpt.get();
//        LOGGER.debug("Removing password reset token with id {}", prtoken.getId());
//        passwordResetTokenDao.removeToken(prtoken.getId()); //remove always, either token is valid or not
//        if (!prtoken.isValid()) {
//            LOGGER.warn("Token {} has expired", token);
//            return Optional.empty();
//        }
//
//        LOGGER.debug("Updating user password for user {}", prtoken.getUserId());
//        return userDao.updatePassword(prtoken.getUserId(), passwordEncoder.encode(password));
        return Optional.empty();
    }

    @Override
    public Optional<Stats> getUserStatsById(long id) {
        return userDao.getUserStatsById(id);
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
        if (image == null) {
            user.setProfileImage(imageService.createImage(imageDto));
        } else
            user.getProfileImage().setData(imageDto.getData());
    }

    @Transactional
    @Override
    public void makeProvider(User user, List<Long> citiesId, String startTime, String endTime) {
        userDao.addRole(user.getId(), Roles.PROVIDER);
        LOGGER.info("User {} is now provider", user.getId());
        userDao.addSchedule(user.getId(), startTime, endTime);
        userDao.addLocation(user.getId(), citiesId);
        sendProviderNotificationEmail(user);
    }

    @Override
    public Collection<ContactInfo> getContactInfo(User user) {
        return user.getContactInfo();
    }

    @Override
    public void contact(ContactDto contactDto) throws IllegalContactException {
        ContactInfo contactInfo;

        if (contactDto.getContactInfoId() == -1) {
            LOGGER.debug("Adding new contact info");
            contactInfo = userDao.addContactInfo(contactDto);
        } else {
            LOGGER.debug("Retrieving used contact info");
            contactInfo = userDao.getContactInfoById(contactDto.getContactInfoId()).orElseThrow(ContactInfoNotFoundException::new);
        }
        if (contactDto.getJob().getProvider().getId().equals(contactDto.getUser().getId()))
            throw new IllegalContactException();

        sendJobRequestEmail(contactDto);
        sendJobRequestConfirmationEmail(contactDto);

        LOGGER.debug("Adding new client to provider {}", contactDto.getJob().getProvider().getId());
        userDao.addClient(contactDto, contactInfo.getContactInfoId(), Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
    public Location getLocationByProviderId(Long providerId) {
        return userDao.getLocationByProviderId(providerId);
    }

    @Override
    public boolean hasContactJobProvided(Job job, User user) {
        return userDao.hasContactJobProvided(job, user);
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

    @Override
    public Optional<Schedule> getScheduleByUserId(long userId) {
        return userDao.getScheduleByUserId(userId);
    }


    private void sendJobRequestEmail(ContactDto contactDto) {
        final Map<String, Object> mailAttrs = new HashMap<>();

        final String address = String.format("%s, %s, %s %s, %s %s", contactDto.getState(), contactDto.getCity(),
            contactDto.getStreet(), contactDto.getAddressNumber(), contactDto.getFloor(), contactDto.getDepartmentNumber());

        mailAttrs.put("to", contactDto.getJob().getProvider().getEmail());
        mailAttrs.put("providerJob", contactDto.getJob().getJobProvided());
        mailAttrs.put("providerName", contactDto.getJob().getProvider().getName());
        mailAttrs.put("name", contactDto.getUser().getName());
        mailAttrs.put("surname", contactDto.getUser().getSurname());
        mailAttrs.put("email", contactDto.getUser().getEmail());
        mailAttrs.put("address", address);
        mailAttrs.put("phoneNumber", contactDto.getUser().getPhoneNumber());
        mailAttrs.put("message", contactDto.getMessage());

        try {
            emailService.sendMail("jobRequest", messageSource.getMessage("email.jobRequest", new Object[]{}, LocaleContextHolder.getLocale()), mailAttrs, LocaleContextHolder.getLocale());
        } catch (MessagingException e) {
            LOGGER.warn("Error, Job request mail not sent");
        }
    }

    private void sendJobRequestConfirmationEmail(ContactDto contactDto) {
        final Map<String, Object> mailAttrs = new HashMap<>();

        mailAttrs.put("to", contactDto.getUser().getEmail());
        mailAttrs.put("providerJob", contactDto.getJob().getJobProvided());
        mailAttrs.put("providerName", contactDto.getJob().getProvider().getName());
        mailAttrs.put("name", contactDto.getUser().getName());

        try {
            emailService.sendMail("jobRequestConfirmation", messageSource.getMessage("email.jobRequestConfirmation", new Object[]{}, LocaleContextHolder.getLocale()), mailAttrs, LocaleContextHolder.getLocale());
        } catch (MessagingException e) {
            LOGGER.warn("Error, Job request confirmation mail not sent");
        }
    }

    private void sendProviderNotificationEmail(User user) {
        try {
            final Locale locale = LocaleContextHolder.getLocale();
            final Map<String, Object> mailAttrs = new HashMap<>();
            mailAttrs.put("to", user.getEmail());
            mailAttrs.put("name", user.getName());
            emailService.sendMail("providerNotification", messageSource.getMessage("email.providerNotification", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException e) {
            LOGGER.warn("Error, provider notification mail not sent");
        }
    }

    private void sendPasswordResetToken(User user, PasswordResetToken token) {
        try {
            final Locale locale = LocaleContextHolder.getLocale();
            final String url = new URL("http", appBaseUrl, "/paw-2021a-06/user/resetPassword?token=" + token.getValue()).toString();
            Map<String, Object> mailAttrs = new HashMap<>();
            mailAttrs.put("confirmationURL", url);
            mailAttrs.put("to", user.getEmail());
            emailService.sendMail("passwordReset", messageSource.getMessage("email.resetPassword", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Error, user password reset mail not sent");
        }
    }

    private PasswordResetToken generatePasswordResetToken(long userId) {
        final String token = UUID.randomUUID().toString();
        return passwordResetTokenDao.createToken(userId, token, VerificationToken.generateTokenExpirationDate());
    }

    private void sendVerificationToken(User user, VerificationToken token) {
        try {
            final Locale locale = LocaleContextHolder.getLocale();
            final String url = new URL("http", appBaseUrl, "/paw-2021a-06/user/verifyAccount?token=" + token.getValue()).toString();
            final Map<String, Object> mailAttrs = new HashMap<>();
            mailAttrs.put("confirmationURL", url);
            mailAttrs.put("to", user.getEmail());

            emailService.sendMail("verification", messageSource.getMessage("email.verifyAccount", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Error, user verification mail not sent");
        }
    }

    private VerificationToken generateVerificationToken(User user) {
        final String token = UUID.randomUUID().toString();
        return verificationTokenDao.createVerificationToken(user, token, VerificationToken.generateTokenExpirationDate());
    }

}