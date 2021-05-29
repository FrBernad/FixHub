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

    //PROVIDER data
    private static final Long ID = 1L;

    //REVIEWER data
    private static final Long ID_R = 2L;

    //JOB date
    private static final Long JOB_ID = 1L;

    //Review data
    private static final int RATING = 4;
    private static final String REVIEW_DESCRIPTION = "Muy buen trabajo, gracias por todo";
    private static final Timestamp CREATION_DATE = Timestamp.valueOf("2021-04-05 20:26:02");

    private static final int REVIEWS_AMOUNT = 5;

    @Autowired
    private DataSource ds;

    @Autowired
    private ReviewDao reviewDao;

    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager em;

    private static final Job MOCKJOB = Mockito.when(Mockito.mock(Job.class).getId()).thenReturn(ID).getMock();

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testCreate() {

        JdbcTestUtils.deleteFromTables(jdbcTemplate,"reviews");

        Job j = em.find(Job.class,JOB_ID);
        User u = em.find(User.class,ID_R);

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

        final int reviewsCount = reviewDao.getReviewsCountByJob(MOCKJOB);
        assertEquals(REVIEWS_AMOUNT, reviewsCount);
    }

    @Test
    public void testGetReviewsByJob() {
        final Collection<Review> reviews = reviewDao.getReviewsByJob(MOCKJOB, 0, 5);

        for(Review review : reviews) {
            assertEquals(ID, review.getJob().getId());
            assertEquals(ID_R,review.getReviewer().getId());
        }
        assertEquals(REVIEWS_AMOUNT, reviews.size());
    }

}
