package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.exceptions.IllegalContactException;
import ar.edu.itba.paw.interfaces.persistance.PasswordResetTokenDao;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.interfaces.persistance.VerificationTokenDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ar.edu.itba.paw.services.UserServiceImpl.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String EMAIL = "email";
    private static final String PHONENUMBER = "phoneNumber";
    private static final String STATE = "state";
    private static final String CITY = "city";
    private static final String TOKEN = "12345";
    private static final String URL = "http:/paw-2021a-06/user/verifyAccount?token=" + TOKEN;
    private static final String VERIFICATION_SUBJECT = "Verify your account";

    private static final User DEFAULT_USER = new User(1L, PASSWORD, NAME, SURNAME,
        EMAIL, PHONENUMBER, STATE, CITY,
        DEFAULT_ROLES, null, null);

    private static final Job DEFAULT_JOB = new Job("Description","JOB",1,2,JobCategory.CARPINTERO,
            1L,new BigDecimal(3000),false,DEFAULT_USER,null);

    private static final ContactDto CONTACT_DTO = new ContactDto(DEFAULT_JOB,3L,DEFAULT_USER,"message",
            STATE,CITY,"STREET","ADRESS","FLOOR","DEPARTMENT");

    private static final LocalDateTime DEFAULT_TIME = LocalDateTime.ofEpochSecond(1619457499,
            0, ZoneOffset.UTC);

    private static final PasswordResetToken PASSWORD_RESET_TOKEN = new PasswordResetToken(1L,"",
            DEFAULT_USER.getId(),LocalDateTime.now().plusDays(4L));

    private static final VerificationToken VTOKEN = new VerificationToken(1L,TOKEN, DEFAULT_USER.getId(),
            LocalDateTime.now().plusDays(4L));

    private static final ContactInfo CONTACT_INFO = new ContactInfo(4L,1L,STATE,CITY,"STREET",
            "ADRESS","FLOOR","DEPARTMENT_NUMBER");


    private static final Map<String, Object> DEFAULT_MAIL_ATTRS = Stream.of(new String[][]{
        {"confirmationURL", URL},
        {"to", DEFAULT_USER.getEmail()},
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    @InjectMocks
    private final UserServiceImpl userService = new UserServiceImpl();

    @Mock
    private UserDao mockUserDao;

    @Mock
    private VerificationTokenDao mockVerificationTokenDao;

    @Mock
    private EmailService mockEmailService;

    @Mock
    private PasswordEncoder mockEncoder;

    @Mock
    private MessageSource mockMessageSource;

    @Mock
    private PasswordResetTokenDao mockPasswordResetTokenDao;

    @Before
    public void setTest() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
    }

    @Test
    public void testCreate() throws DuplicateUserException, MessagingException {
        // 1. Setup!
        when(mockMessageSource.getMessage(anyString(),any(),eq(LocaleContextHolder.getLocale()))).
            thenReturn(VERIFICATION_SUBJECT);
        when(mockVerificationTokenDao.createVerificationToken(eq(DEFAULT_USER.getId()), anyString(), any(LocalDateTime.class)))
            .thenReturn(new VerificationToken(1, TOKEN, DEFAULT_USER.getId(), DEFAULT_TIME));
        when(mockEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
        when(mockUserDao.createUser(Mockito.eq(PASSWORD), Mockito.eq(NAME), Mockito.eq(SURNAME),
            Mockito.eq(EMAIL), Mockito.eq(PHONENUMBER),Mockito.eq(STATE), Mockito.eq(CITY), Mockito.eq(DEFAULT_ROLES))).
            thenReturn(DEFAULT_USER);


        // 2. "ejercito" la class under test
        User maybeUser = userService.createUser(PASSWORD, NAME, SURNAME, EMAIL, PHONENUMBER, STATE, CITY);

        // 3. Asserts!
        verify(mockEmailService, times(1)).sendMail("verification", VERIFICATION_SUBJECT,
            DEFAULT_MAIL_ATTRS, LocaleContextHolder.getLocale());
        assertNotNull(maybeUser);
        assertEquals(DEFAULT_USER,maybeUser);

    }
    @Test(expected = DuplicateUserException.class)
    public void testCreateAlreadyExists() throws DuplicateUserException {

        //Assuming the DEFAULT_USER already exists 
        when(mockEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
        when(mockUserDao.createUser(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.eq(DEFAULT_USER.getEmail()),Mockito.anyString(),
                Mockito.anyString(),Mockito.anyString(),Mockito.any())).thenThrow(new DuplicateUserException());

        userService.createUser(PASSWORD,NAME,SURNAME,DEFAULT_USER.getEmail(),PHONENUMBER,STATE,CITY);

    }

    @Test
    public void getUserByIdTest(){

        //An user that already exists
        when(mockUserDao.getUserById(Mockito.eq(DEFAULT_USER.getId()))).thenReturn(Optional.of(DEFAULT_USER));

        Optional<User> opUser = userService.getUserById(DEFAULT_USER.getId());

        assertNotNull(opUser);
        assertTrue(opUser.isPresent());
        assertEquals(DEFAULT_USER,opUser.get());

    }
    @Test
    public void getUserByWrongIdTest(){

        long id=-1234567890L;
        //An user that does not exists
        when(mockUserDao.getUserById(Mockito.eq(id))).thenReturn(Optional.empty());

        Optional<User> opUser = userService.getUserById(id);

        assertNotNull(opUser);
        assertFalse(opUser.isPresent());

    }

    @Test
    public void getUserByEmailTest(){

        when(mockUserDao.getUserByEmail(Mockito.eq(DEFAULT_USER.getEmail()))).thenReturn(Optional.of(DEFAULT_USER));

        Optional<User> opUser = userService.getUserByEmail(DEFAULT_USER.getEmail());

        assertNotNull(opUser);
        assertTrue(opUser.isPresent());
        assertEquals(DEFAULT_USER,opUser.get());

    }

    @Test
    public void verifyAccountTest(){
        Collection<Roles> newRoles = new LinkedList<>(DEFAULT_USER.getRoles());
        newRoles.remove(Roles.NOT_VERIFIED);
        newRoles.add(Roles.VERIFIED);

        when(mockVerificationTokenDao.getTokenByValue(Mockito.eq(TOKEN))).thenReturn(Optional.of(VTOKEN));
        Mockito.doNothing().when(mockVerificationTokenDao).removeTokenById(Mockito.eq(VTOKEN.getId()));
        when(mockUserDao.updateRoles(Mockito.eq(VTOKEN.getId()), Mockito.eq(Roles.NOT_VERIFIED),Mockito.eq(Roles.VERIFIED))).thenReturn(
                Optional.of(new User(DEFAULT_USER.getId(),DEFAULT_USER.getPassword(),DEFAULT_USER.getName(),
                        DEFAULT_USER.getSurname(),DEFAULT_USER.getEmail(),DEFAULT_USER.getPhoneNumber(),DEFAULT_USER.getState(),
                        DEFAULT_USER.getCity(),newRoles,DEFAULT_USER.getProfileImageId(),DEFAULT_USER.getCoverImageId())));

        Optional<User> opUser = userService.verifyAccount(TOKEN);

        assertNotNull(opUser);
        assertTrue(opUser.isPresent());
        assertTrue(opUser.get().getRoles().contains(Roles.VERIFIED));


    }

    @Test
    public void updatePasswordTest(){

        when(mockPasswordResetTokenDao.getTokenByValue(Mockito.eq(TOKEN))).thenReturn(Optional.of(PASSWORD_RESET_TOKEN));
        Mockito.doNothing().when(mockPasswordResetTokenDao).removeTokenById(Mockito.eq(PASSWORD_RESET_TOKEN.getId()));
        when(mockEncoder.encode(Mockito.eq(PASSWORD))).thenReturn("NEWPASSWORD");
        when(mockUserDao.updatePassword(Mockito.eq(PASSWORD_RESET_TOKEN.getUserId()),Mockito.anyString())).thenReturn(
                Optional.of(new User(DEFAULT_USER.getId(),"NEWPASSWORD",DEFAULT_USER.getName(),
                        DEFAULT_USER.getSurname(),DEFAULT_USER.getEmail(),DEFAULT_USER.getPhoneNumber(),DEFAULT_USER.getState(),
                        DEFAULT_USER.getCity(),DEFAULT_USER.getRoles(),DEFAULT_USER.getProfileImageId(),DEFAULT_USER.getCoverImageId())));
        Optional<User> opUser=userService.updatePassword(TOKEN,PASSWORD);

        assertNotNull(opUser);
        assertTrue(opUser.isPresent());
        assertNotEquals(opUser.get().getPassword(),DEFAULT_USER.getPassword());
        assertNotEquals(opUser.get(),DEFAULT_USER);

    }

    @Test(expected = IllegalContactException.class)
    public void illegalContactTest(){

        //A user is trying to contact himself
        when(mockUserDao.getContactInfoById(Mockito.eq(CONTACT_DTO.getContactInfoId()))).thenReturn(Optional.of(CONTACT_INFO));

        userService.contact(CONTACT_DTO);

    }
    
}
