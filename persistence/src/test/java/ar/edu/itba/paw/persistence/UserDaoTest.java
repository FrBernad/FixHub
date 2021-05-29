package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.exceptions.ContactInfoNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.provider.Stats;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql(scripts = "classpath:user-dao-test.sql")
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

    private static final User USER = new User("password", "Ignacio", "Lopez", "ignacio@yopmail.com", "5491112345678", "CABA", "Caballito", new HashSet<>(PROVIDER_VERIFIED_ROLES));
    private static final Job JOB = new Job("Limpieza total", "Limpieza de filtros", 0, 0L, JobCategory.MECANICO, BigDecimal.valueOf(300), false, USER, new HashSet<>());
    private static final Stats USER_STATS = new Stats(6, 3, 4);
    private static final UserInfo USER_INFO = new UserInfo("Gonzalo", "Martinez", "Burzaco", "Buenos Aires", "5491143218765");

    private static final User CLIENT = new User("password", "Pedro", "Romero", "pedro@yopmail.com", "5491109876543", "Once", "CABA", new HashSet<>(CLIENT_VERIFIED_ROLES));
    private static final User MOCKED_CLIENT = Mockito.when(Mockito.mock(User.class).getId()).thenReturn(3L).getMock();
    private static final User MOCKED_PROVIDER = Mockito.when(Mockito.mock(User.class).getId()).thenReturn(1L).getMock();

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
    public void testGetUserById() {

        User user = Mockito.mock(User.class);
        when(user.getId()).thenReturn(1L);

        final Optional<User> mayBeUser = userDao.getUserById(user.getId());

        assertTrue(mayBeUser.isPresent());
        assertEquals(mayBeUser.get().getId(), user.getId());
    }


    @Test
    public void testGetUserByIdNotFound() {
        final Optional<User> user = userDao.getUserById(50);
        assertFalse(user.isPresent());
    }

    @Test
    public void testGetUserByEmail() {
        User user = Mockito.mock(User.class);
        when(user.getId()).thenReturn(1L);
        when(user.getEmail()).thenReturn("ignacio@yopmail.com");

        final Optional<User> mayBeUser = userDao.getUserByEmail(user.getEmail());

        assertTrue(mayBeUser.isPresent());
        assertEquals(mayBeUser.get().getId(), user.getId());

    }

    @Test
    public void testGetUserByEmailNotFound() {
        final Optional<User> user = userDao.getUserByEmail("gonzalo@yopmail.com");
        assertFalse(user.isPresent());
    }

    @Test
    public void testCreateUser() throws DuplicateUserException {
        final User user = userDao.createUser(PASSWORD, NAME, SURNAME, EMAIL, PHONENUMBER, STATE, CITY, new HashSet<>(DEFAULT_ROLES));
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

    @Test(expected = DuplicateUserException.class)
    public void testCreateDuplicateUser() throws DuplicateUserException {
        userDao.createUser(USER.getPassword(), USER.getName(), USER.getSurname(),
            USER.getEmail(), USER.getPhoneNumber(), USER.getState(), USER.getCity(), new HashSet<>(DEFAULT_ROLES));
    }

    @Test
    public void addContactInfo() {
        userDao.addContactInfo(CONTACT_DTO);

        String query = String.format("ci_user_id = %s and ci_city = '%s' and ci_state = '%s' and ci_street = '%s' and " +
                "ci_address_number = '%s' and ci_floor = '%s' and ci_department_number = '%s' ",
            CLIENT.getId(),
            CONTACT_DTO.getCity(),
            CONTACT_DTO.getState(),
            CONTACT_DTO.getStreet(),
            CONTACT_DTO.getAddressNumber(),
            CONTACT_DTO.getFloor(),
            CONTACT_DTO.getDepartmentNumber());

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "contact_info", query));

    }

    @Test
    public void createJobContact() {


    }

    @Test
    public void getContactInfoById() {
        ContactInfo contactInfo = userDao.getContactInfoById(1L).orElseThrow(ContactInfoNotFoundException::new);
        assertEquals(contactInfo.getUser().getId().longValue(), 3L);
    }

    //GET CLIENTS AND PROVIDERS
    @Test
    public void getClientsByProvider() {
        Collection<JobContact> jobContacts = userDao.getClientsByProvider(MOCKED_PROVIDER, 0, 4);
        assertEquals(1, jobContacts.size());
        assertTrue(jobContacts.size() <= 4);
    }

    @Test
    public void getClientsByProviderNoClients() {
        Collection<JobContact> jobContacts = userDao.getClientsByProvider(MOCKED_CLIENT, 0, 4);
        assertEquals(0, jobContacts.size());
    }

    @Test
    public void getProvidersByClient() {
        Collection<JobContact> providers = userDao.getProvidersByClient(MOCKED_CLIENT, 0, 10);
        assertEquals(1, providers.size());
        assertTrue(providers.size() <= 10);

        providers = userDao.getProvidersByClient(MOCKED_PROVIDER, 0, 10);
        assertEquals(0, providers.size());
    }

    @Test
    public void getProvidersByClientNoProviders() {
        Collection<JobContact> providers = userDao.getProvidersByClient(MOCKED_PROVIDER, 0, 10);
        assertEquals(0, providers.size());
    }

    //GET CLIENTS AND PROVIDERS COUNTS
    @Test
    public void getClientsCountByProvider() {
        int count = userDao.getClientsCountByProvider(MOCKED_PROVIDER);
        assertEquals(1, count);
    }

    @Test
    public void getClientsCountByProviderNoClients() {
        int count = userDao.getClientsCountByProvider(MOCKED_CLIENT);
        assertEquals(0, count);
    }

    @Test
    public void getProvidersCountByClient() {
        int count = userDao.getProvidersCountByClient(MOCKED_CLIENT);
        assertEquals(1, count);

        count = userDao.getProvidersCountByClient(MOCKED_PROVIDER);
        assertEquals(0, count);
    }

    @Test
    public void getProvidersCountByClientNoProviders() {
        int count = userDao.getProvidersCountByClient(MOCKED_PROVIDER);
        assertEquals(0, count);
    }

    //GET FOLLOW
    @Test
    public void getUserFollowers() {
        final Long[] followersIds = {3L, 2L};

        Collection<User> followers = userDao.getUserFollowers(MOCKED_PROVIDER, 0, 4);
        assertEquals(2, followers.size());
        assertTrue(followers.size() <= 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (User follower : followers) {
            resultIds.add(follower.getId());
        }

        assertArrayEquals(followersIds, resultIds.toArray());
    }

    @Test
    public void getUserFollowersNoFollowers() {
        Collection<User> followers = userDao.getUserFollowers(MOCKED_CLIENT, 0, 4);
        assertEquals(0, followers.size());
        assertTrue(followers.size() <= 4);
    }

    @Test
    public void getUserFollowings() {
        final Long[] followingIds = {2L};

        Collection<User> following = userDao.getUserFollowings(MOCKED_PROVIDER, 0, 4);
        assertEquals(1, following.size());
        assertTrue(following.size() <= 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (User user : following) {
            resultIds.add(user.getId());
        }

        assertArrayEquals(followingIds, resultIds.toArray());
    }

    //GET FOLLOW COUNTS

    @Test
    public void getUserFollowersCount() {
        int count = userDao.getUserFollowersCount(MOCKED_PROVIDER);
        assertEquals(2, count);
    }

    @Test
    public void getUserFollowersCountNoFollowers() {
        int count = userDao.getUserFollowersCount(MOCKED_CLIENT);
        assertEquals(0, count);
    }

    @Test
    public void getUserFollowingsCount() {
        int count = userDao.getUserFollowingCount(MOCKED_PROVIDER);
        assertEquals(1, count);
    }


    @Test
    public void persistProviderDetails() {
//        void persistProviderDetails(Location location, Schedule schedule);

    }


    @Test
    public void hasContactJobProvided() {
//        boolean hasContactJobProvided(User provider, User user);

    }


}

