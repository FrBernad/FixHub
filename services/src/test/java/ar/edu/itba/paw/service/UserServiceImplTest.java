//package ar.edu.itba.paw.service;
//
//import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
//import ar.edu.itba.paw.interfaces.persistance.UserDao;
//import ar.edu.itba.paw.interfaces.persistance.VerificationTokenDao;
//import ar.edu.itba.paw.interfaces.services.EmailService;
//import ar.edu.itba.paw.models.User;
//import ar.edu.itba.paw.models.VerificationToken;
//import ar.edu.itba.paw.services.UserServiceImpl;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.verification.VerificationMode;
//import org.springframework.context.MessageSource;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import javax.mail.MessagingException;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.ZoneOffset;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static ar.edu.itba.paw.services.UserServiceImpl.*;
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//
//@RunWith(MockitoJUnitRunner.class)
//public class UserServiceImplTest {
//
//    private static final String PASSWORD = "password";
//    private static final String NAME = "name";
//    private static final String SURNAME = "surname";
//    private static final String EMAIL = "email";
//    private static final String PHONENUMBER = "phoneNumber";
//    private static final String STATE = "state";
//    private static final String CITY = "city";
//    private static final String TOKEN = "12345";
//    private static final User DEFAULT_USER = new User(1L, PASSWORD, NAME, SURNAME,
//        EMAIL, PHONENUMBER, STATE, CITY,
//        DEFAULT_ROLES, null, null);
//    private static final LocalDateTime DEFAULT_TIME = LocalDateTime.ofEpochSecond(1619457499, 0, ZoneOffset.UTC);
//    private static final String VERIFICATION_SUBJECT = "Verify your account";
//    private static final String URL = "http:/paw-2021a-06/user/verifyAccount?token=" + TOKEN;
//    private static final Map<String, Object> DEFAULT_MAIL_ATTRS = Stream.of(new String[][]{
//        {"confirmationURL", URL},
//        {"to", DEFAULT_USER.getEmail()},
//    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
//
//    @InjectMocks
//    private UserServiceImpl userService = new UserServiceImpl();
//
//    @Mock
//    private UserDao mockUserDao;
//
//    @Mock
//    private VerificationTokenDao mockVerificationTokenDao;
//
//    @Mock
//    private EmailService mockEmailService;
//
//    @Mock
//    private PasswordEncoder mockEncoder;
//
//    @Mock
//    private MessageSource mockMessageSource;
//
//    @Before
//    public void setTest() {
//        LocaleContextHolder.setLocale(Locale.ENGLISH);
//    }
//
//    @Test
//    public void testCreate() throws DuplicateUserException, MessagingException {
//        // 1. Setup!
//        when(mockMessageSource.getMessage(anyString(),any(),eq(LocaleContextHolder.getLocale()))).
//            thenReturn(VERIFICATION_SUBJECT);
//        when(mockVerificationTokenDao.createVerificationToken(eq(DEFAULT_USER.getId()), anyString(), any(LocalDateTime.class)))
//            .thenReturn(new VerificationToken(1, TOKEN, DEFAULT_USER.getId(), DEFAULT_TIME));
//        when(mockEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
//        when(mockUserDao.createUser(PASSWORD, NAME, SURNAME,
//            EMAIL, PHONENUMBER, STATE, CITY, DEFAULT_ROLES)).
//            thenReturn(DEFAULT_USER);
//
//
//        // 2. "ejercito" la class under test
//        User maybeUser = userService.createUser(PASSWORD, NAME, SURNAME, EMAIL, PHONENUMBER, STATE, CITY);
//
//        // 3. Asserts!
//        verify(mockEmailService, times(1)).sendMail("verification", VERIFICATION_SUBJECT,
//            DEFAULT_MAIL_ATTRS, LocaleContextHolder.getLocale());
//        assertNotNull(maybeUser);
//        assertEquals(PASSWORD, maybeUser.getPassword());
//        assertEquals(NAME, maybeUser.getName());
//        assertEquals(SURNAME, maybeUser.getSurname());
//        assertEquals(EMAIL, maybeUser.getEmail());
//        assertEquals(PHONENUMBER, maybeUser.getPhoneNumber());
//        assertEquals(CITY, maybeUser.getCity());
//        assertEquals(STATE, maybeUser.getState());
//
//    }
//
////    @Test
////    public void testCreateEmptyPassword() {
//////        // 1. Setup!
//////        // 2. "ejercito" la class under test
//////        Optional<User> maybeUser = userService.create(USERNAME, "");// 3. Asserts!
//////        Assert.assertNotNull(maybeUser);
//////        Assert.assertFalse(maybeUser.isPresent());
////    }
////
////    @Test
////    public void testCreateAlreadyExists() {
//////        // 1. Setup!
//////        Mockito.when(mockUserDao.findByUsername(Mockito.eq(USERNAME))).thenReturn(Optional.of(new User(1, USERNAME, PASSWORD)));
//////        // 2. "ejercito" la class under test
//////        Optional<User> maybeUser = userService.create(USERNAME, PASSWORD);
//////        // 3. Asserts!
//////        Assert.assertNotNull(maybeUser);
//////        Assert.assertFalse(maybeUser.isPresent());
////    }
//
//
//}
//
//
//
//
//
