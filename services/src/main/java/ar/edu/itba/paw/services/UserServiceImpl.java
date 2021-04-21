package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.ContactInfoNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.persistance.PasswordResetTokenDao;
import ar.edu.itba.paw.interfaces.persistance.VerificationTokenDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.interfaces.services.UserService;
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

    private final Locale locale = LocaleContextHolder.getLocale();

    private final Collection<Roles> DEFAULT_ROLES = Collections.unmodifiableCollection(Arrays.asList(Roles.USER, Roles.NOT_VERIFIED));

    public Optional<User> getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Transactional
    @Override
    public User createUser(String password, String name, String surname, String email, String phoneNumber, String state, String city) throws DuplicateUserException {
        User user = userDao.createUser(passwordEncoder.encode(password), name, surname, email, phoneNumber, state, city, DEFAULT_ROLES);
        VerificationToken token = generateVerificationToken(user.getId());
        sendVerificationToken(user, token);
        return user;
    }

    @Transactional
    @Override
    public Optional<User> verifyAccount(String token) {
        Optional<VerificationToken> vtokenOpt = verificationTokenDao.getTokenByValue(token);

        if (!vtokenOpt.isPresent())
            return Optional.empty();

        VerificationToken vtoken = vtokenOpt.get();
        verificationTokenDao.removeTokenById(vtoken.getId());//remove always, either token is valid or not

        if (!vtoken.isValid())
            return Optional.empty();

        return userDao.updateRoles(vtoken.getUserId(), Roles.NOT_VERIFIED, Roles.VERIFIED);
    }

    @Transactional
    @Override
    public void resendVerificationToken(User user) {
        verificationTokenDao.removeTokenByUserId(user.getId());
        VerificationToken token = generateVerificationToken(user.getId());
        sendVerificationToken(user, token);
    }

    private void sendVerificationToken(User user, VerificationToken token) {
        try {
            String url = new URL("http", appBaseUrl, 8080, "/user/verifyAccount?token=" + token.getValue()).toString();
            Map<String, Object> mailAttrs = new HashMap<>();
            mailAttrs.put("confirmationURL", url);
            mailAttrs.put("to", user.getEmail());

            emailService.sendMail("verification", messageSource.getMessage("email.verifyAccount", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private VerificationToken generateVerificationToken(long userId) {
        String token = UUID.randomUUID().toString();
        return verificationTokenDao.createVerificationToken(userId, token, VerificationToken.generateTokenExpirationDate());
    }

    public boolean validatePasswordReset(String token) {
        Optional<PasswordResetToken> prtokenOpt = passwordResetTokenDao.getTokenByValue(token);

        if (!prtokenOpt.isPresent() || !prtokenOpt.get().isValid())
            return false;

        return true;
    }


    @Transactional
    public void generateNewPassword(User user) {
        passwordResetTokenDao.removeTokenByUserId(user.getId());
        PasswordResetToken token = generatePasswordResetToken(user.getId());
        sendPasswordResetToken(user, token);
    }

    @Transactional
    public Optional<User> updatePassword(String token, String password) {
        Optional<PasswordResetToken> prtokenOpt = passwordResetTokenDao.getTokenByValue(token);

        if (!prtokenOpt.isPresent()) {
            return Optional.empty();
        }

        PasswordResetToken prtoken = prtokenOpt.get();
        passwordResetTokenDao.removeTokenById(prtoken.getId()); //remove always, either token is valid or not
        if (!prtoken.isValid()) {
            return Optional.empty();
        }

        return userDao.updatePassword(prtoken.getUserId(), passwordEncoder.encode(password));
    }

    @Override
    public Optional<UserStats> getUserStatsById(long id) {
        return userDao.getUserStatsById(id);
    }

    @Override
    public void updateUserInfo(UserInfo userInfo, User user) {

        userDao.updateUserInfo(userInfo,user);
    }

    @Override
    public void updateCoverImage(ImageDto imageDto,User user){
        Long imageId = user.getCoverImageId();
        if(imageId == 0){
            imageId = imageService.createImage(imageDto).getImageId();
            userDao.updateCoverImage(imageId,user);
        }
        else
            imageService.updateImage(imageDto,user.getProfileImageId());
    }

    @Override
    public void updateProfileImage(ImageDto imageDto,User user){
        Long imageId = user.getProfileImageId();
        if(imageId == 0){
            imageId = imageService.createImage(imageDto).getImageId();
            userDao.updateProfileImage(imageId,user);
        }
        else
            imageService.updateImage(imageDto,user.getProfileImageId());
    }

    @Override
    @Transactional
    public void makeProvider(Long userId, List<Long> citiesId, String startTime, String endTime) {
        userDao.addRole(userId, Roles.PROVIDER);
        userDao.addSchedule(userId, startTime, endTime);
        userDao.addLocation(userId, citiesId);
    }

    private void sendPasswordResetToken(User user, PasswordResetToken token) {
        try {
            String url = new URL("http", appBaseUrl, 8080, "/user/resetPassword?token=" + token.getValue()).toString();
            Map<String, Object> mailAttrs = new HashMap<>();
            mailAttrs.put("confirmationURL", url);
            mailAttrs.put("to", user.getEmail());
            emailService.sendMail("passwordReset", messageSource.getMessage("email.resetPassword", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException | MalformedURLException e) {
            e.printStackTrace();
        }
    }


    private PasswordResetToken generatePasswordResetToken(long userId) {
        String token = UUID.randomUUID().toString();
        return passwordResetTokenDao.createToken(userId, token, VerificationToken.generateTokenExpirationDate());
    }

    @Override
    public Collection<ContactInfo> getContactInfo(User user) {
        return userDao.getContactInfo(user);
    }


    @Override
    @Transactional
    public void contact(ContactDto contactDto) {
        ContactInfo contactInfo;
        if (contactDto.getContactInfoId() == -1)
            contactInfo = userDao.addContactInfo(contactDto);
        else
            contactInfo = userDao.getContactInfoById(contactDto.getContactInfoId()).orElseThrow(ContactInfoNotFoundException::new);

        userDao.addClient(contactDto,contactInfo.getContactInfoId(), Timestamp.valueOf(LocalDateTime.now()));
    }


    @Override
    public ProviderLocation getLocationByProviderId(Long providerId){
        return userDao.getLocationByProviderId(providerId);
    }


}