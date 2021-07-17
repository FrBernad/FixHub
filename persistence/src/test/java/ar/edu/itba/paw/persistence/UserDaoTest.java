package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.exceptions.ContactInfoNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.models.contact.AuxContactDto;
import ar.edu.itba.paw.models.contact.ContactInfo;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.time.LocalDateTime;
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

    private static final User MOCKED_CLIENT = Mockito.when(Mockito.mock(User.class).getId()).thenReturn(3L).getMock();
    private static final User MOCKED_CLIENT_NO_CONTACT = Mockito.when(Mockito.mock(User.class).getId()).thenReturn(2L).getMock();
    private static final User MOCKED_PROVIDER = Mockito.when(Mockito.mock(User.class).getId()).thenReturn(1L).getMock();
    private static final Job MOCKED_JOB = Mockito.when(Mockito.mock(Job.class).getId()).thenReturn(1L).getMock();
    private static final ContactInfo MOCKED_CONTACT_INFO = Mockito.when(Mockito.mock(ContactInfo.class).getId()).thenReturn(1L).getMock();


    @Autowired
    private DataSource ds;

    @Autowired
    private UserDao userDao;

    @PersistenceContext
    private EntityManager em;

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
        userDao.createUser("password", "Ignacio", "Lopez", "ignacio@yopmail.com",
            "5491112345678", "CABA", "Caballito",
            new HashSet<>(PROVIDER_VERIFIED_ROLES));
    }

    @Test
    public void testAddContactInfo() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "contact");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "contact_info");

        final User user = em.find(User.class, MOCKED_CLIENT.getId());
        final Job job = em.find(Job.class, MOCKED_JOB.getId());

        final AuxContactDto contactDto = new AuxContactDto(job, -1L, user, "message",
            "Adrogue", "Buenos Aires", "Nother", "123",
            "21", "A");
        ContactInfo contactInfo = userDao.addContactInfo(contactDto);

        String query = String.format("ci_user_id = %s and ci_city = '%s' and ci_state = '%s' and ci_street = '%s' and " +
                "ci_address_number = '%s' and ci_floor = '%s' and ci_department_number = '%s' ",
            MOCKED_CLIENT.getId(), contactInfo.getCity(), contactInfo.getState(), contactInfo.getStreet(),
            contactInfo.getAddressNumber(), contactInfo.getFloor(), contactInfo.getDepartmentNumber());

        em.flush();

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "contact_info", query));
    }

    @Test
    public void testCreateJobContact() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "contact");

        final User user = em.find(User.class, MOCKED_CLIENT.getId());
        final User provider = em.find(User.class, MOCKED_PROVIDER.getId());
        final Job job = em.find(Job.class, MOCKED_JOB.getId());
        final ContactInfo contactInfo = em.find(ContactInfo.class, MOCKED_CONTACT_INFO.getId());

        JobContact jobContact = userDao.createJobContact(user, provider, contactInfo, "Te necesito rápido",
            LocalDateTime.of(2021, 5, 29, 12, 30), job);

        String query = String.format("c_id = %s and c_message = '%s' and c_contact_info = %s and c_job_id = %s and c_provider_id = %s and c_user_id = %s ",
            jobContact.getId(),jobContact.getMessage(),jobContact.getContactInfo().getId()
            ,jobContact.getJob().getId(),jobContact.getProvider().getId(),jobContact.getUser().getId());

        em.flush();

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "contact", query));

    }

    @Test
    public void testGetContactInfoById() {
        ContactInfo contactInfo = userDao.getContactInfoById(1L).orElseThrow(ContactInfoNotFoundException::new);
        assertEquals(contactInfo.getUser().getId().longValue(), 3L);
    }

    //GET CLIENTS AND PROVIDERS
//    @Test
//    public void testGetClientsByProvider() {
//        Collection<JobContact> jobContacts = userDao.getClientsByProvider(MOCKED_PROVIDER, 0, 4);
//        assertEquals(1, jobContacts.size());
//        assertTrue(jobContacts.size() <= 4);
//    }
//
//    @Test
//    public void testGetClientsByProviderNoClients() {
//        Collection<JobContact> jobContacts = userDao.getClientsByProvider(MOCKED_CLIENT, 0, 4);
//        assertEquals(0, jobContacts.size());
//    }
//
//    @Test
//    public void testGetProvidersByClient() {
//        Collection<JobContact> providers = userDao.getProvidersByClient(MOCKED_CLIENT, 0, 10);
//        assertEquals(1, providers.size());
//        assertTrue(providers.size() <= 10);
//
//        providers = userDao.getProvidersByClient(MOCKED_PROVIDER, 0, 10);
//        assertEquals(0, providers.size());
//    }
//
//    @Test
//    public void testGetProvidersByClientNoProviders() {
//        Collection<JobContact> providers = userDao.getProvidersByClient(MOCKED_PROVIDER, 0, 10);
//        assertEquals(0, providers.size());
//    }
//
//    //GET CLIENTS AND PROVIDERS COUNTS
//    @Test
//    public void testGetClientsCountByProvider() {
//        int count = userDao.getClientsCountByProvider(MOCKED_PROVIDER);
//        assertEquals(1, count);
//    }
//
//    @Test
//    public void testGetClientsCountByProviderNoClients() {
//        int count = userDao.getClientsCountByProvider(MOCKED_CLIENT);
//        assertEquals(0, count);
//    }
//
//    @Test
//    public void testGetProvidersCountByClient() {
//        int count = userDao.getProvidersCountByClient(MOCKED_CLIENT);
//        assertEquals(1, count);
//
//        count = userDao.getProvidersCountByClient(MOCKED_PROVIDER);
//        assertEquals(0, count);
//    }
//
//    @Test
//    public void testGetProvidersCountByClientNoProviders() {
//        int count = userDao.getProvidersCountByClient(MOCKED_PROVIDER);
//        assertEquals(0, count);
//    }

    //GET FOLLOW
    @Test
    public void testGetUserFollowers() {
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
    public void testGetUserFollowersNoFollowers() {
        Collection<User> followers = userDao.getUserFollowers(MOCKED_CLIENT, 0, 4);
        assertEquals(0, followers.size());
        assertTrue(followers.size() <= 4);
    }

    @Test
    public void testGetUserFollowings() {
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
    public void testGetUserFollowersCount() {
        int count = userDao.getUserFollowersCount(MOCKED_PROVIDER);
        assertEquals(2, count);
    }

    @Test
    public void testGetUserFollowersCountNoFollowers() {
        int count = userDao.getUserFollowersCount(MOCKED_CLIENT);
        assertEquals(0, count);
    }

    @Test
    public void testGetUserFollowingsCount() {
        int count = userDao.getUserFollowingCount(MOCKED_PROVIDER);
        assertEquals(1, count);
    }

    @Test
    public void testHasContactJobProvided() {
        assertTrue(userDao.hasContactJobProvided(MOCKED_PROVIDER,MOCKED_CLIENT,MOCKED_JOB));
    }

    @Test
    public void testHasContactJobProvidedFalse() {
        assertFalse(userDao.hasContactJobProvided(MOCKED_CLIENT_NO_CONTACT,MOCKED_PROVIDER,MOCKED_JOB));
    }


}

