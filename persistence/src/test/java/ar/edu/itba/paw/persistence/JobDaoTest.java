package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import static ar.edu.itba.paw.models.job.JobCategory.ELECTRICISTA;
import static ar.edu.itba.paw.models.job.JobCategory.MECANICO;
import static ar.edu.itba.paw.models.pagination.OrderOptions.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:job-dao-test.sql")
@Transactional
public class JobDaoTest {

    @Autowired
    private JobDao jobDao;

    static User provider = Mockito.when(Mockito.mock(User.class).getId()).thenReturn(1L).getMock();

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
    public void testGetJobsByCategoryIdSearchQuery() {

        Collection<Job> jobs = jobDao.
            getJobsByCategory("tot", MOST_POPULAR, MECANICO,
                null, null, 0, 4);

        assertEquals(3, jobs.size());
        assertTrue(jobs.size() <= 4);
        for (Job job : jobs) {
            Long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, provider.getId());
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
            Long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, provider.getId());
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
            Long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, provider.getId());
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
            Long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, provider.getId());
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
            Long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, provider.getId());
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
            Long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, provider.getId());
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
            Long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, provider.getId());
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
            Long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, provider.getId());
        }
        Assert.assertArrayEquals(orderedIds, resultIds.toArray());
    }

    //GET JOBS BY PROVIDER ID

    //GET COUNTS
    @Test
    public void testGetJobsCountByProviderTrickSearchQuery() {
        int count = jobDao.getJobsCountByProvider(provider,"%");
        assertEquals(0, count);
    }

    @Test
    public void testGetJobsCountByProviderSearchQuery() {
        int count = jobDao.getJobsCountByProvider(provider,"techo");
        assertEquals(3, count);
    }

    @Test
    public void testGetJobsCountByProviderNoSearchQuery() {
        int count = jobDao.getJobsCountByProvider(provider,null);
        assertEquals(9, count);
    }

    //GET JOBS
    @Test
    public void testGetJobsByProviderSeachQuery() {

        Collection<Job> jobs = jobDao.
            getJobsByProvider("limpie", MOST_POPULAR,
                provider, 0, 4);

        assertEquals(3, jobs.size());
        assertTrue(jobs.size() <= 4);
        for (Job job : jobs) {
            Long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, provider.getId());
        }

    }

    @Test
    public void testGetJobsByProviderHigherPrice() {
        final Long[] orderedIds = {9L, 7L, 8L, 6L};

        Collection<Job> jobs = jobDao.getJobsByProvider(null, HIGHER_PRICE, provider, 0, 4);

        assertEquals(4, jobs.size());
        assertTrue(jobs.size() <= 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (Job job : jobs) {
            resultIds.add(job.getId());
            Long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, provider.getId());
        }
        Assert.assertArrayEquals(orderedIds, resultIds.toArray());
    }

    @Test
    public void testGetJobsByProviderLowerPrice() {
        final Long[] orderedIds = {2L, 3L, 1L, 4L};

        Collection<Job> jobs = jobDao.getJobsByProvider(null, LOWER_PRICE, provider, 0, 4);

        assertEquals(4, jobs.size());
        assertTrue(jobs.size() <= 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (Job job : jobs) {
            resultIds.add(job.getId());
            Long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, provider.getId());
        }
        Assert.assertArrayEquals(orderedIds, resultIds.toArray());
    }


    @Test
    public void testGetJobsByProviderMostPopular() {
        final Long[] orderedIds = {1L, 2L, 3L, 4L};

        Collection<Job> jobs = jobDao.getJobsByProvider(null, MOST_POPULAR, provider, 0, 4);

        assertEquals(4, jobs.size());
        assertTrue(jobs.size() <= 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (Job job : jobs) {
            resultIds.add(job.getId());
            Long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, provider.getId());
        }
        Assert.assertArrayEquals(orderedIds, resultIds.toArray());
    }

    @Test
    public void testGetJobsByProviderLessPopular() {
        final Long[] orderedIds = {9L, 8L, 7L, 6L};

        Collection<Job> jobs = jobDao.getJobsByProvider(null, LESS_POPULAR, provider, 0, 4);

        assertEquals(4, jobs.size());
        assertTrue(jobs.size() <= 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (Job job : jobs) {
            resultIds.add(job.getId());
            Long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, provider.getId());
        }
        Assert.assertArrayEquals(orderedIds, resultIds.toArray());
    }

    @Test
    public void testGetJobsByProviderOrderAndStringQuery() {
        final Long[] orderedIds = {6L, 4L, 5L};

        Collection<Job> jobs = jobDao.getJobsByProvider("Soluciono", HIGHER_PRICE, provider, 0, 4);

        assertEquals(3, jobs.size());
        assertTrue(jobs.size() <= 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (Job job : jobs) {
            resultIds.add(job.getId());
            Long jobProviderId = job.getProvider().getId();
            assertEquals(jobProviderId, provider.getId());
        }
        Assert.assertArrayEquals(orderedIds, resultIds.toArray());
    }

    @Test
    public void testGetJobById() {
        Optional<Job> job = jobDao.getJobById(1);

        assertTrue(job.isPresent());
        assertEquals(Long.valueOf(1), job.get().getId());

        job = jobDao.getJobById(-1);

        assertFalse(job.isPresent());
    }

}