package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.models.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.*;

import static ar.edu.itba.paw.models.JobCategory.*;
import static ar.edu.itba.paw.models.OrderOptions.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:job-dao-test.sql")
@Transactional
public class JobDaoTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private JobDao jobDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    //GET JOBS BY CATEGORY

    //GET COUNTS
    @Test
    public void testGetJobsCountByCategoryNoSearchQuery() {
        int count = jobDao.getJobsCountByCategory(null, ELECTRICISTA, null, null);
        assertEquals(3, count);
    }

    @Test
    public void testGetJobsCountByCategoryTrickySearchQuery() {
        int count = jobDao.getJobsCountByCategory("%", ELECTRICISTA, null, null);
        assertEquals(0, count);
    }

    @Test
    public void testGetJobsCountByCategorySearchQuery() {
        int count = jobDao.getJobsCountByCategory("errores", ELECTRICISTA, null, null);
        assertEquals(1, count);
    }

    @Test
    public void testGetJobsCountByCategorySearchQueryCityAndState() {
        int count = jobDao.getJobsCountByCategory("errores", ELECTRICISTA, "2", "1");
        assertEquals(1, count);
    }


    //GET JOBS
    @Test
    public void testGetJobsByCategoryIdSeachQuery() {

        Collection<Job> jobs = jobDao.
            getJobsByCategory("tot", MOST_POPULAR, MECANICO,
                null, null, 0, 4);

        assertEquals(3, jobs.size());
        assertTrue(jobs.size() <= 4);
        for (Job job : jobs) {
            long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, 1L);
        }

    }

    @Test
    public void testGetJobsByCategoryHigherPrice() {
        final Long[] orderedIds = {1L, 3L, 2L};

        Collection<Job> jobs = jobDao.getJobsByCategory(null, HIGHER_PRICE,
            MECANICO, null, null, 0, 4);

        assertEquals(3, jobs.size());
        assertTrue(jobs.size() <= 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (Job job : jobs) {
            resultIds.add(job.getId());
            long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, 1L);
        }
        Assert.assertArrayEquals(orderedIds, resultIds.toArray());
    }

    @Test
    public void testGetJobsByCategoryLowerPrice() {
        final Long[] orderedIds = {2L, 3L, 1L};

        Collection<Job> jobs = jobDao.getJobsByCategory(null, LOWER_PRICE,
            MECANICO, null, null, 0, 4);

        assertEquals(3, jobs.size());
        assertTrue(jobs.size() <= 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (Job job : jobs) {
            resultIds.add(job.getId());
            long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, 1L);
        }
        Assert.assertArrayEquals(orderedIds, resultIds.toArray());
    }


    @Test
    public void testGetJobsByCategoryMostPopular() {
        final Long[] orderedIds = {1L, 2L, 3L};

        Collection<Job> jobs = jobDao.getJobsByCategory(null, MOST_POPULAR,
            MECANICO, null, null, 0, 4);

        assertEquals(3, jobs.size());
        assertTrue(jobs.size() <= 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (Job job : jobs) {
            resultIds.add(job.getId());
            long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, 1L);
        }
        Assert.assertArrayEquals(orderedIds, resultIds.toArray());
    }

    @Test
    public void testGetJobsByCategoryLessPopular() {
        final Long[] orderedIds = {3L, 2L, 1L};

        Collection<Job> jobs = jobDao.getJobsByCategory(null, LESS_POPULAR,
            MECANICO, null, null, 0, 4);

        assertEquals(3, jobs.size());
        assertTrue(jobs.size() <= 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (Job job : jobs) {
            resultIds.add(job.getId());
            long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, 1L);
        }
        Assert.assertArrayEquals(orderedIds, resultIds.toArray());
    }

    @Test
    public void testGetJobsByCategoryOrderAndStringQuery() {
        final Long[] orderedIds = {6L, 4L, 5L};

        Collection<Job> jobs = jobDao.getJobsByCategory("Soluciono", HIGHER_PRICE,
            ELECTRICISTA, null, null, 0, 4);

        assertEquals(3, jobs.size());
        assertTrue(jobs.size() <= 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (Job job : jobs) {
            resultIds.add(job.getId());
            long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, 1L);
        }
        Assert.assertArrayEquals(orderedIds, resultIds.toArray());
    }


    @Test
    public void testGetJobsByCategoryOrderAndStringQueryAndStateCity() {
        final Long[] orderedIds = {6L, 4L, 5L};

        Collection<Job> jobs = jobDao.getJobsByCategory("Soluciono", HIGHER_PRICE,
            ELECTRICISTA, "2", "1", 0, 4);

        assertEquals(3, jobs.size());
        assertTrue(jobs.size() <= 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (Job job : jobs) {
            resultIds.add(job.getId());
            long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, 1L);
        }
        Assert.assertArrayEquals(orderedIds, resultIds.toArray());
    }


    @Test
    public void testGetJobsByCategoryOrderAndStateCity() {
        final Long[] orderedIds = {4L, 6L, 5L};

        Collection<Job> jobs = jobDao.getJobsByCategory(null, MOST_POPULAR,
            ELECTRICISTA, "2", "1", 0, 4);

        assertEquals(3, jobs.size());
        assertTrue(jobs.size() <= 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (Job job : jobs) {
            resultIds.add(job.getId());
            long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, 1L);
        }
        Assert.assertArrayEquals(orderedIds, resultIds.toArray());
    }

    //GET JOBS BY PROVIDER ID

    //GET COUNTS
    @Test
    public void testGetJobsCountByProviderIdTrickSearchQuery() {
        int count = jobDao.getJobsCountByProviderId("%", 1L);
        assertEquals(0, count);
    }

    @Test
    public void testGetJobsCountByProviderIdSearchQuery() {
        int count = jobDao.getJobsCountByProviderId("techo", 1L);
        assertEquals(3, count);
    }

    @Test
    public void testGetJobsCountByProviderIdNoSearchQuery() {
        int count = jobDao.getJobsCountByProviderId(null, 1L);
        assertEquals(9, count);
    }

    //GET JOBS
    @Test
    public void testGetJobsByProviderIdSeachQuery() {

        Collection<Job> jobs = jobDao.
            getJobsByProviderId("limpie", MOST_POPULAR,
                1L, 0, 4);

        assertEquals(3, jobs.size());
        assertTrue(jobs.size() <= 4);
        for (Job job : jobs) {
            long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, 1L);
        }

    }

    @Test
    public void testGetJobsByProviderHigherPrice() {
        final Long[] orderedIds = {9L, 7L, 8L, 6L};

        Collection<Job> jobs = jobDao.getJobsByProviderId(null, HIGHER_PRICE, 1L, 0, 4);

        assertEquals(4, jobs.size());
        assertTrue(jobs.size() <= 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (Job job : jobs) {
            resultIds.add(job.getId());
            long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, 1L);
        }
        Assert.assertArrayEquals(orderedIds, resultIds.toArray());
    }

    @Test
    public void testGetJobsByProviderLowerPrice() {
        final Long[] orderedIds = {2L, 3L, 1L, 4L};

        Collection<Job> jobs = jobDao.getJobsByProviderId(null, LOWER_PRICE, 1L, 0, 4);

        assertEquals(4, jobs.size());
        assertTrue(jobs.size() <= 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (Job job : jobs) {
            resultIds.add(job.getId());
            long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, 1L);
        }
        Assert.assertArrayEquals(orderedIds, resultIds.toArray());
    }


    @Test
    public void testGetJobsByProviderMostPopular() {
        final Long[] orderedIds = {1L, 2L, 3L, 4L};

        Collection<Job> jobs = jobDao.getJobsByProviderId(null, MOST_POPULAR, 1L, 0, 4);

        assertEquals(4, jobs.size());
        assertTrue(jobs.size() <= 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (Job job : jobs) {
            resultIds.add(job.getId());
            long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, 1L);
        }
        Assert.assertArrayEquals(orderedIds, resultIds.toArray());
    }

    @Test
    public void testGetJobsByProviderLessPopular() {
        final Long[] orderedIds = {9L, 8L, 7L, 6L};

        Collection<Job> jobs = jobDao.getJobsByProviderId(null, LESS_POPULAR, 1L, 0, 4);

        assertEquals(4, jobs.size());
        assertTrue(jobs.size() <= 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (Job job : jobs) {
            resultIds.add(job.getId());
            long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, 1L);
        }
        Assert.assertArrayEquals(orderedIds, resultIds.toArray());
    }

    @Test
    public void testGetJobsByProviderOrderAndStringQuery() {
        final Long[] orderedIds = {6L, 4L, 5L};

        Collection<Job> jobs = jobDao.getJobsByProviderId("Soluciono", HIGHER_PRICE, 1L, 0, 4);

        assertEquals(3, jobs.size());
        assertTrue(jobs.size() <= 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (Job job : jobs) {
            resultIds.add(job.getId());
            long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, 1L);
        }
        Assert.assertArrayEquals(orderedIds, resultIds.toArray());
    }

    @Test
    public void testGetJobById() {
        Optional<Job> job = jobDao.getJobById(1);

        assertTrue(job.isPresent());
        assertEquals(1, job.get().getId());

        job = jobDao.getJobById(-1);

        assertFalse(job.isPresent());
    }

}