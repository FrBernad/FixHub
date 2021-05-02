package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.exceptions.ContactInfoNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.models.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

//@Sql(scripts = "classpath:user-test.sql")//sirve para partir siempre de una base de datos conocida. Se ejecuta antes de cada test
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class UserDaoTest {

    private static final String PASSWORD = "password";
    private static final String NAME = "Juan";
    private static final String SURNAME = "Gomez";
    private static final String EMAIL = "juan@yopmail.com";
    private static final String PHONENUMBER = "5491187654321";
    private static final String STATE = "Palermo";
    private static final String CITY = "CABA";

    private static final Collection<Roles> PROVIDER_VERIFIED_ROLES = Collections.unmodifiableCollection(Arrays.asList(Roles.PROVIDER, Roles.VERIFIED, Roles.USER));
    private static final Collection<Roles> CLIENT_VERIFIED_ROLES = Collections.unmodifiableCollection(Arrays.asList(Roles.VERIFIED, Roles.USER));

    private static final User USER = new User(1L, "password", "Ignacio", "Lopez", "ignacio@yopmail.com", "5491112345678", "Caballito", "CABA", PROVIDER_VERIFIED_ROLES, 0L, 0L);
    private static final Job JOB = new Job("'Limpieza total'", "Limpieza de filtros", 0, 0, JobCategory.MECANICO, 1, BigDecimal.valueOf(300), false, USER, 0L,new ArrayList<>());
    private static final UserStats USER_STATS = new UserStats(6, 3, 4);
    private static final UserInfo USER_INFO = new UserInfo("Gonzalo", "Martinez", "Burzaco", "Buenos Aires", "5491143218765");

    private static final User CLIENT = new User(3L, "password", "Pedro", "Romero", "pedro@yopmail.com", "5491109876543", "Once", "CABA", CLIENT_VERIFIED_ROLES, 0L, 0L);
    private static final ContactDto CONTACT_DTO = new ContactDto(JOB, -1L, CLIENT, "message", "Adrogue", "Buenos Aires", "Nother", "123", "21", "A");

    @Autowired
    private DataSource ds;
    @Autowired
    private UserDao userDao;

    private JdbcTemplate jdbcTemplate;

    private static final Collection<Roles> DEFAULT_ROLES = Collections.unmodifiableCollection(Arrays.asList(Roles.USER, Roles.NOT_VERIFIED));

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testCreateUser() throws DuplicateUserException {
        final User user = userDao.createUser(PASSWORD, NAME, SURNAME, EMAIL, PHONENUMBER, STATE, CITY, DEFAULT_ROLES);
        assertNotNull(user);
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(NAME, user.getName());
        assertEquals(SURNAME, user.getSurname());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PHONENUMBER, user.getPhoneNumber());
        assertEquals(CITY, user.getCity());
        assertEquals(STATE, user.getState());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", "u_id = " + user.getId()));
    }

    @Test
    public void testGetUserById() {
        final Optional<User> user = userDao.getUserById(USER.getId());
        assertTrue(user.isPresent());
        assertEquals(user.get(), USER);

        final Optional<User> userNotPresent = userDao.getUserById(50);
        assertFalse(userNotPresent.isPresent());
    }

    @Test
    public void testGetUserByEmail() {
        final Optional<User> user = userDao.getUserByEmail(USER.getEmail());
        assertTrue(user.isPresent());
        assertEquals(user.get(), USER);

        final Optional<User> userNotPresent = userDao.getUserByEmail("gonzalo@yopmail.com");
        assertFalse(userNotPresent.isPresent());
    }

    @Test
    public void updatePassword() {
        final String newPassword = "newPassword";
        userDao.updatePassword(USER.getId(), newPassword);
        String query = "u_id = " + USER.getId() + " and u_password = '" + newPassword + "'";
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", query));
    }


    @Test
    public void getUserStatsById() {
        Optional<UserStats> userStats = userDao.getUserStatsById(USER.getId());
        assertTrue(userStats.isPresent());
        assertEquals(userStats.get().getJobsCount(), USER_STATS.getJobsCount());
        assertEquals(userStats.get().getAvgRating(), USER_STATS.getAvgRating());
        assertEquals(userStats.get().getReviewCount(), USER_STATS.getReviewCount());
    }


    @Test
    public void updateUserInfo() {
        userDao.updateUserInfo(USER_INFO, USER);
        String query = new StringBuilder("u_id = ").append(USER.getId())
            .append(" and u_name = '").append(USER_INFO.getName()).append("'")
            .append(" and u_surname = '").append(USER_INFO.getSurname()).append("'")
            .append(" and u_city = '").append(USER_INFO.getCity()).append("'")
            .append(" and u_state = '").append(USER_INFO.getState()).append("'")
            .append(" and u_phone_number = '").append(USER_INFO.getPhoneNumber()).append("'").toString();
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", query));

    }

    @Test
    public void addContactInfo() {
        userDao.addContactInfo(CONTACT_DTO);

        String query = new StringBuilder("ci_user_id = ").append(CLIENT.getId())
            .append(" and ci_city = '").append(CONTACT_DTO.getCity()).append("'")
            .append(" and ci_state = '").append(CONTACT_DTO.getState()).append("'")
            .append(" and ci_street = '").append(CONTACT_DTO.getStreet()).append("'")
            .append(" and ci_address_number = '").append(CONTACT_DTO.getAddressNumber()).append("'")
            .append(" and ci_floor = '").append(CONTACT_DTO.getFloor()).append("'")
            .append(" and ci_department_number = '").append(CONTACT_DTO.getDepartmentNumber()).append("'").toString();

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "contact_info", query));

    }

    @Test
    public void getContactInfo() {
        Collection<ContactInfo> contactInfo = userDao.getContactInfo(CLIENT);
        Assert.assertEquals(contactInfo.size(), 2);
    }


    @Test
    public void getClientsByProviderId() {
        Collection<JobContact> jobContacts = userDao.getClientsByProviderId(1L, 0, 4);
        assertEquals(1, jobContacts.size());
        assertTrue(jobContacts.size() <= 4);

        jobContacts = userDao.getClientsByProviderId(99L, 0, 4);
        assertEquals(0, jobContacts.size());
    }

    @Test
    public void getClientsCountByProviderId() {
        int count = userDao.getClientsCountByProviderId(1L);
        assertEquals(1, count);

        count = userDao.getClientsCountByProviderId(99L);
        assertEquals(0, count);

    }

    @Test
    public void getProvidersByClientId() {
        Collection<JobContact> providers = userDao.getProvidersByClientId(3L, 0, 10);
        assertEquals(1, providers.size());
        assertTrue(providers.size() <= 10);

        providers = userDao.getProvidersByClientId(99L, 0, 10);
        assertEquals(0, providers.size());
    }

    @Test
    public void getProvidersCountByClientId() {
        int count = userDao.getProvidersCountByClientId(3L);
        assertEquals(1, count);

        count = userDao.getProvidersCountByClientId(99L);
        assertEquals(0, count);

    }

    @Test
    public void getLocationByProviderId() {
        ProviderLocation providerLocation = userDao.getLocationByProviderId(1L);
        City[] cities = providerLocation.getCities().toArray(new City[0]);
        for (int i = 0; i < 3; i++) {
            assertEquals(cities[i].getId(), i+1);
        }
        assertEquals(3, cities.length);

        providerLocation = userDao.getLocationByProviderId(99L);
        assertNull(providerLocation);

    }

    @Test
    public void getScheduleByUserId() {
        UserSchedule userSchedule = userDao.getScheduleByUserId(1L).orElseThrow(UserNotFoundException::new);
        assertEquals(userSchedule.getStartTime(), "11:00");
        assertEquals(userSchedule.getEndTime(), "18:00");
    }

    @Test
    public void getContactInfoById() {
        ContactInfo contactInfo = userDao.getContactInfoById(1L).orElseThrow(ContactInfoNotFoundException::new);
        assertEquals(contactInfo.getUserId().longValue(), 3L);
    }


}

