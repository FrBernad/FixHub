package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.models.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.*;

import static ar.edu.itba.paw.models.JobCategory.*;
import static ar.edu.itba.paw.models.OrderOptions.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class JobDaoTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private JobDao jobDao;

    private JdbcTemplate jdbcTemplate;

    public final static Collection<Roles> DEFAULT_ROLES = Collections.
        unmodifiableCollection(
            Arrays.asList(Roles.USER, Roles.NOT_VERIFIED));

    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String EMAIL = "email";
    private static final String PHONENUMBER = "phoneNumber";
    private static final String STATE = "state";
    private static final String CITY = "city";
    private static final User DEFAULT_USER = new User(1L, PASSWORD, NAME, SURNAME,
        EMAIL, PHONENUMBER, STATE, CITY,
        DEFAULT_ROLES, null, null);

    private static final List<Image> IMAGES = Collections.emptyList();

    private static final String DESCRIPTION = "arreglo techos y los dejo como nuevos, años de experiencia";
    private static final String JOB_PROVIDED = "arreglo techos";
    private static final JobCategory CATEGORY = TECHISTA;
    private static final BigDecimal PRICE = BigDecimal.valueOf(3000);

    private static final String SEARCH_QUERY = "techo";
    private static final String TRICKY_SEARCH_QUERY = "%";
    private static final OrderOptions ORDER_OPTION = LESS_POPULAR;
    private static final JobCategory JOB_CATEGORY = TECHISTA;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Rollback
    @Test
    public void createJob() {
        Job job = jobDao.createJob(JOB_PROVIDED, CATEGORY, DESCRIPTION, PRICE, DEFAULT_USER, IMAGES);

        assertNotNull(job);
        assertEquals(DESCRIPTION, job.getDescription());
        assertEquals(JOB_PROVIDED, job.getJobProvided());
        assertEquals(0, job.getAverageRating());
        assertEquals(0, job.getTotalRatings());
        assertEquals(DEFAULT_USER, job.getProvider());
        assertEquals(PRICE, job.getPrice());
        assertEquals(CATEGORY, job.getCategory());
    }

    @Test
    public void getJobsCountByProviderId() {
        int count = jobDao.getJobsCountByProviderId(SEARCH_QUERY, 1L);
        assertEquals(1, count);

        count = jobDao.getJobsCountByProviderId(TRICKY_SEARCH_QUERY, 1L);
        assertEquals(0, count);

        count = jobDao.getJobsCountByProviderId(null, 1L);
        assertEquals(26, count);

        count = jobDao.getJobsCountByProviderId(null, 2L);
        assertEquals(0, count);
    }

    @Test
    public void getJobsCountByCategory() {
        int count = jobDao.getJobsCountByCategory("tejas", TECHISTA);
        assertEquals(1, count);

        count = jobDao.getJobsCountByCategory(TRICKY_SEARCH_QUERY, CATEGORY);
        assertEquals(0, count);

        count = jobDao.getJobsCountByCategory(null, MECANICO);
        assertEquals(6, count);

        count = jobDao.getJobsCountByCategory("asdadasd", MECANICO);
        assertEquals(0, count);

        count = jobDao.getJobsCountByCategory(null, null);
        assertEquals(26, count);
    }


    @Test
    public void getJobsByProviderId() {
        Collection<Job> jobs = jobDao.
            getJobsByProviderId(SEARCH_QUERY, ORDER_OPTION,
                1L, 0, 4);

        assertEquals(1, jobs.size());
        assertTrue(jobs.size() <= 4);
        for (Job job : jobs) {
            long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, 1L);
        }

        jobs = jobDao.
            getJobsByProviderId(TRICKY_SEARCH_QUERY, ORDER_OPTION,
                1L, 0, 4);

        assertEquals(0, jobs.size());
    }

    @Test
    public void getJobsByCategory() {
        //LESS POP
        Collection<Job> jobs = jobDao.
            getJobsByCategory(SEARCH_QUERY, LESS_POPULAR, CATEGORY, 0, 4);

        assertEquals(0, jobs.size());
        assertTrue(jobs.size() <= 4);
        for (Job job : jobs) {
            JobCategory jobCategory = job.getCategory();
            assertEquals(jobCategory, CATEGORY);
        }

        assertTrue(isSorted(jobs, Comparator.comparingInt(Job::getAverageRating)));

        //MOST POP
        jobs = jobDao.
            getJobsByCategory(SEARCH_QUERY, MOST_POPULAR, CATEGORY, 0, 4);

        assertTrue(jobs.size() <= 4);

        assertTrue(isSorted(jobs, Comparator.comparingInt(Job::getAverageRating).reversed()));

        //HIGHER PRICE
        jobs = jobDao.
            getJobsByCategory(SEARCH_QUERY, HIGHER_PRICE, CATEGORY, 0, 4);

        assertTrue(jobs.size() <= 4);

        assertTrue(isSorted(jobs, (o1, o2) -> o2.getPrice().intValue() - o1.getPrice().intValue()));

        //LOWER PRICE
        jobs = jobDao.
            getJobsByCategory(SEARCH_QUERY, LOWER_PRICE, CATEGORY, 0, 4);

        assertTrue(jobs.size() <= 4);

        assertTrue(isSorted(jobs, Comparator.comparingInt(o -> o.getPrice().intValue())));

        //% QUERY
        jobs = jobDao.
            getJobsByCategory(TRICKY_SEARCH_QUERY, ORDER_OPTION, CATEGORY, 0, 4);

        assertEquals(0, jobs.size());


        //% NO SEARCH NO CATEGORY
        jobs = jobDao.
            getJobsByCategory(null, ORDER_OPTION, null, 0, 4);

        assertTrue(jobs.size() <= 4);
    }

    @Test
    public void getJobById() {
        Optional<Job> job = jobDao.getJobById(1);

        assertTrue(job.isPresent());
        assertEquals(1, job.get().getId());

        job = jobDao.getJobById(-1);

        assertFalse(job.isPresent());
    }


    private boolean isSorted(Collection<Job> jobs, Comparator<Job> jobComparator) {
        if (jobs.isEmpty() || jobs.size() == 1) {
            return true;
        }

        Iterator<Job> iter = jobs.iterator();
        Job current, previous = iter.next();
        while (iter.hasNext()) {
            current = iter.next();
            if (jobComparator.compare(previous, current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }

}
