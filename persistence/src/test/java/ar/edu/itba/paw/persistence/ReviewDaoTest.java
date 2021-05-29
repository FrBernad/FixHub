package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.Review;
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
import java.sql.Timestamp;
import java.util.*;

import static org.junit.Assert.*;

@Transactional
@Sql(scripts = "classpath:review-dao-test.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ReviewDaoTest {

    //Provider data
    private static final Long U_ID = 1L;

    //Reviewer data
    private static final Long R_ID = 2L;

    //JOB data
    private static final Long JOB_ID = 1L;

    //Review data
    private static final int RATING = 4;
    private static final String REVIEW_DESCRIPTION = "Muy buen trabajo, gracias por todo";
    private static final Timestamp CREATION_DATE = Timestamp.valueOf("2021-04-05 20:26:02");

    @Autowired
    private DataSource ds;

    @Autowired
    private ReviewDao reviewDao;

    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager em;

    private static final Job MOCK_JOB = Mockito.when(Mockito.mock(Job.class).getId()).thenReturn(1L).getMock();
    private static final Job MOCK_JOB_NO_REVIEWS = Mockito.when(Mockito.mock(Job.class).getId()).thenReturn(2L).getMock();

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testCreate() {

        JdbcTestUtils.deleteFromTables(jdbcTemplate, "reviews");

        Job j = em.find(Job.class, JOB_ID);
        User u = em.find(User.class, U_ID);

        final Review review = reviewDao.createReview(REVIEW_DESCRIPTION, j, RATING, CREATION_DATE, u);

        em.flush();

        assertNotNull(review);
        assertEquals(REVIEW_DESCRIPTION, review.getDescription());
        assertEquals(JOB_ID, review.getJob().getId());
        assertEquals(RATING, review.getRating());
        assertEquals(CREATION_DATE.toLocalDateTime().toLocalDate(), review.getCreationDate());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "reviews", "r_id = " + review.getId()));
    }

    @Test
    public void testGetReviewsCountByJob() {
        final int reviewsCount = reviewDao.getReviewsCountByJob(MOCK_JOB);
        assertEquals(5, reviewsCount);
    }

    @Test
    public void testGetReviewsCountByJobNoReviews() {
        final int reviewsCount = reviewDao.getReviewsCountByJob(MOCK_JOB_NO_REVIEWS);
        assertEquals(0, reviewsCount);
    }

    @Test
    public void testGetReviewsByJob() {
        final Long[] reviewsIds = {5L, 4L, 3L, 2L, 1L};

        final Collection<Review> reviews = reviewDao.getReviewsByJob(MOCK_JOB, 0, 5);
        assertTrue(reviews.size() <= 5);
        assertEquals(5, reviews.size());

        final Collection<Long> resultIds = new LinkedList<>();
        for (Review review : reviews) {
            resultIds.add(review.getId());
            assertEquals(JOB_ID, review.getJob().getId());
            assertEquals(R_ID, review.getReviewer().getId());
        }

        assertArrayEquals(reviewsIds, resultIds.toArray());
    }


    @Test
    public void testGetReviewsByJobNoReviews() {
        final Collection<Review> reviews = reviewDao.getReviewsByJob(MOCK_JOB_NO_REVIEWS, 0, 5);
        assertTrue(reviews.size() <= 5);
        assertEquals(0, reviews.size());
    }

}
