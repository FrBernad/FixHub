package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.exceptions.IllegalContactException;
import ar.edu.itba.paw.interfaces.persistance.LocationDao;
import ar.edu.itba.paw.interfaces.persistance.PasswordResetTokenDao;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.interfaces.persistance.VerificationTokenDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.contact.ContactDto;
import ar.edu.itba.paw.models.contact.ContactInfo;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.image.ImageDto;
import ar.edu.itba.paw.models.location.City;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.token.PasswordResetToken;
import ar.edu.itba.paw.models.token.VerificationToken;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.UserInfo;
import ar.edu.itba.paw.models.user.provider.Location;
import ar.edu.itba.paw.models.user.provider.ProviderDetails;
import ar.edu.itba.paw.models.user.provider.Schedule;
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
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private static final String START_TIME = "11:00";
    private static final String END_TIME = "13:00";

    private static final User DEFAULT_USER = new User(PASSWORD, NAME, SURNAME, EMAIL,
        PHONENUMBER, STATE, CITY, DEFAULT_ROLES);

    private static final Job DEFAULT_JOB = new Job("Description", "JOB", 1,
        2L, JobCategory.CARPINTERO, new BigDecimal(3000), false, DEFAULT_USER, null);


    private static final ContactDto CONTACT_DTO = new ContactDto(DEFAULT_JOB, 3L,
        DEFAULT_USER, "message", STATE, CITY, "STREET", "ADRESS",
        "FLOOR", "DEPARTMENT");

    private static final LocalDateTime DEFAULT_TIME = LocalDateTime.ofEpochSecond(1619457499,
        0, ZoneOffset.UTC);

    private static final User USER_TASKER = new User(PASSWORD, NAME, SURNAME, EMAIL,
        PHONENUMBER, STATE, CITY, DEFAULT_ROLES);

    private static final ContactInfo CONTACT_INFO = new ContactInfo(USER_TASKER, STATE, CITY, "STREET",
        "ADRESS", "FLOOR", "DEPARTMENT_NUMBER");


    private static final Map<String, Object> DEFAULT_MAIL_ATTRS = Stream.of(new String[][]{
        {"confirmationURL", URL},
        {"to", DEFAULT_USER.getEmail()},
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    private static final ImageDto COVER_IMAGE = new ImageDto(
        new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, "image/jpeg");

    private static final ImageDto PROFILE_IMAGE = new ImageDto(
        new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, "image/jpeg");

    private static final Image coverImage = new Image(4L, COVER_IMAGE.getData(), COVER_IMAGE.getMimeType());
    private static final Image profileImage = new Image(5L, PROFILE_IMAGE.getData(), PROFILE_IMAGE.getMimeType());

    @InjectMocks
    private final UserServiceImpl userService = new UserServiceImpl();

    @Mock
    private User mockUser;

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

    @Mock
    private ImageService mockImageService;

    @Mock
    private LocationDao mockLocationDao;

    @Before
    public void setTest() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
    }

    @Test
    public void testCreate() throws DuplicateUserException, MessagingException {
        VerificationToken token = new VerificationToken(TOKEN,DEFAULT_USER,DEFAULT_TIME);
        lenient().when(mockMessageSource.getMessage(anyString(), any(), eq(LocaleContextHolder.getLocale()))).
            thenReturn(VERIFICATION_SUBJECT);
        when(mockVerificationTokenDao.createVerificationToken(eq(DEFAULT_USER), anyString(), any(LocalDateTime.class)))
            .thenReturn(token);
        when(mockEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
        when(mockUserDao.createUser(Mockito.eq(PASSWORD), Mockito.eq(NAME), Mockito.eq(SURNAME),
            Mockito.eq(EMAIL), Mockito.eq(PHONENUMBER), Mockito.eq(STATE), Mockito.eq(CITY), Mockito.eq(DEFAULT_ROLES))).
            thenReturn(DEFAULT_USER);


        User maybeUser = userService.createUser(PASSWORD, NAME, SURNAME, EMAIL, PHONENUMBER, STATE, CITY);

        assertNotNull(maybeUser);
        assertEquals(DEFAULT_USER, maybeUser);
        verify(mockUserDao).createUser(PASSWORD, NAME, SURNAME, EMAIL, PHONENUMBER, STATE, CITY, DEFAULT_ROLES);

        verify(mockEmailService, times(1)).sendVerificationEmail(maybeUser,token);

    }

    @Test(expected = DuplicateUserException.class)
    public void testCreateAlreadyExists() throws DuplicateUserException {

        //Assuming the DEFAULT_USER already exists
        when(mockEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
        when(mockUserDao.createUser(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.eq(DEFAULT_USER.getEmail()), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyString(), Mockito.any())).thenThrow(new DuplicateUserException());

        userService.createUser(PASSWORD, NAME, SURNAME, DEFAULT_USER.getEmail(), PHONENUMBER, STATE, CITY);

    }

    @Test
    public void testVerifyAccount() {
        VerificationToken vToken = new VerificationToken(TOKEN, mockUser, LocalDateTime.now().plusDays(4L));
        when(mockVerificationTokenDao.getTokenByValue(Mockito.eq(TOKEN))).thenReturn(Optional.of(vToken));
        Mockito.doNothing().when(mockVerificationTokenDao).removeToken(Mockito.eq(vToken));

        userService.verifyAccount(TOKEN);
        verify(mockUser).removeRole(Roles.NOT_VERIFIED);
        verify(mockUser).addRole(Roles.VERIFIED);
    }

    @Test
    public void testUpdatePassword() {

        PasswordResetToken passwordResetToken = new PasswordResetToken(TOKEN, mockUser, LocalDateTime.now().plusDays(4L));

        when(mockPasswordResetTokenDao.getTokenByValue(Mockito.eq(TOKEN))).thenReturn(Optional.of(passwordResetToken));
        Mockito.doNothing().when(mockPasswordResetTokenDao).removeToken(Mockito.eq(passwordResetToken));

        when(mockEncoder.encode(Mockito.eq(PASSWORD))).thenReturn("NEWPASSWORD");

        Optional<User> opUser = userService.updatePassword(TOKEN, PASSWORD);

        verify(mockUser).setPassword("NEWPASSWORD");
        assertNotNull(opUser);
        assertTrue(opUser.isPresent());
        assertNotEquals(opUser.get().getPassword(), DEFAULT_USER.getPassword());
        assertNotEquals(opUser.get(), DEFAULT_USER);

    }

    @Test
    public void testUpdateUserInfo() {
        when(mockUser.getEmail()).thenReturn(EMAIL);

        userService.updateUserInfo(new UserInfo(NAME, SURNAME, CITY, STATE, PHONENUMBER), mockUser);

        verify(mockUser).setName(NAME);
        verify(mockUser).setSurname(SURNAME);
        verify(mockUser).setCity(CITY);
        verify(mockUser).setState(STATE);
        verify(mockUser).setPhoneNumber(PHONENUMBER);

    }

    @Test
    public void testUpdateCoverImageNull() {
        when(mockUser.getCoverImage()).thenReturn(null);
        when(mockImageService.createImage(COVER_IMAGE)).thenReturn(coverImage);
        userService.updateCoverImage(COVER_IMAGE, mockUser);
        verify(mockUser).setCoverImage(coverImage);
    }

    @Test
    public void testUpdateCoverImageNotNull() {
        Image mockCoverImage = Mockito.mock(Image.class);
        when(mockUser.getCoverImage()).thenReturn(mockCoverImage);
        userService.updateCoverImage(COVER_IMAGE, mockUser);
        verify(mockCoverImage).setData(COVER_IMAGE.getData());
    }

    @Test
    public void testUpdateProfileImageNull() {
        when(mockUser.getProfileImage()).thenReturn(null);
        when(mockImageService.createImage(PROFILE_IMAGE)).thenReturn(profileImage);
        userService.updateProfileImage(PROFILE_IMAGE, mockUser);
        verify(mockUser).setProfileImage(profileImage);
    }

    @Test
    public void testUpdateProfileImageNotNull() {
        Image mockProfileImage = Mockito.mock(Image.class);
        when(mockUser.getProfileImage()).thenReturn(mockProfileImage);
        userService.updateProfileImage(PROFILE_IMAGE, mockUser);
        verify(mockProfileImage).setData(PROFILE_IMAGE.getData());
    }

    @Test
    public void testUpdateProviderInfo() {
        Location location = Mockito.mock(Location.class);
        Schedule schedule = Mockito.mock(Schedule.class);
        ProviderDetails providerDetails = Mockito.mock(ProviderDetails.class);
        City city = Mockito.mock(City.class);

        when(city.getState()).thenReturn(null);

        when(mockLocationDao.getCitiesById(null)).thenReturn(Collections.singletonList(city));
        when(mockUser.getProviderDetails()).thenReturn(providerDetails);
        when(providerDetails.getLocation()).thenReturn(location);
        when(providerDetails.getSchedule()).thenReturn(schedule);
        when(mockUser.getId()).thenReturn(1L);

        userService.updateProviderInfo(mockUser, null, START_TIME, END_TIME);

        verify(location).setCities(any());
        verify(location).setState(any());

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String startTimeString = "11:00";
        String endTimeString = "13:00";
        long startTimeInMs;
        long endTimeInMs;

        try {
            startTimeInMs = sdf.parse(startTimeString).getTime();
        } catch (ParseException e) {
            return;
        }

        try {
            endTimeInMs = sdf.parse(endTimeString).getTime();
        } catch (ParseException e) {
            return;
        }

        verify(schedule).setStartTime(new Time(startTimeInMs));
        verify(schedule).setEndTime(new Time(endTimeInMs));

    }

    @Test
    public void testMakeProvider() {
        City city = Mockito.mock(City.class);

        when(city.getState()).thenReturn(null);
        when(mockLocationDao.getCitiesById(null)).thenReturn(Collections.singletonList(city));
        when(mockUser.getId()).thenReturn(1L);

        userService.makeProvider(mockUser, null, START_TIME, END_TIME);

        verify(mockUser).addRole(Roles.PROVIDER);
        verify(mockUser).setProviderDetails(any());
    }

    @Test
    public void testGetUserByEmail() {

        when(mockUserDao.getUserByEmail(Mockito.eq(DEFAULT_USER.getEmail()))).thenReturn(Optional.of(DEFAULT_USER));

        Optional<User> opUser = userService.getUserByEmail(DEFAULT_USER.getEmail());

        verify(mockUserDao).getUserByEmail(EMAIL);

        assertNotNull(opUser);
        assertTrue(opUser.isPresent());
        assertEquals(DEFAULT_USER, opUser.get());

    }


    @Test(expected = IllegalContactException.class)
    public void testIllegalContact() {

        when(mockUserDao.getContactInfoById(Mockito.eq(CONTACT_DTO.getContactInfoId()))).thenReturn(Optional.of(CONTACT_INFO));

        userService.contact(CONTACT_DTO, mockUser, mockUser);
    }

}
