package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.persistance.VerificationTokenDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.models.Roles;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

@org.springframework.stereotype.Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private VerificationTokenDao verificationTokenDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private String appBaseUrl;

    private final Collection<Roles> DEFAULT_ROLES = Collections.unmodifiableCollection(Arrays.asList(Roles.ROLE_USER, Roles.NOT_VALIDATED));

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

    @Override
    public Optional<User> verifyAccount(String token) {
        Optional<VerificationToken> vtoken = verificationTokenDao.getTokenByValue(token);

        if (!vtoken.isPresent()||!vtoken.get().isValid()) {
            return Optional.empty();
        }

        Optional<User> user = userDao.updateRoles(vtoken.get().getUserId(), Roles.NOT_VALIDATED, Roles.VALIDATED);
        verificationTokenDao.removeTokenById(vtoken.get().getId()); //remove always, either token is valid or not
        return user;
    }

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
            emailService.sendMail("verification", "Account Confirmation", mailAttrs);
        } catch (MessagingException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private VerificationToken generateVerificationToken(long userId) {
        String token = UUID.randomUUID().toString();
        return verificationTokenDao.createVerificationToken(userId, token, VerificationToken.generateTokenExpirationDate());
    }

}