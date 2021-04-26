package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.models.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@Rollback
@Sql(scripts = "classpath:review-test.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ReviewDaoTest {

    //User data
    private static final String PASSWORD = "password";
    private static final String NAME = "Juan";
    private static final String SURNAME = "Gomez";
    private static final String EMAIL = "juan@yopmail.com";
    private static final String PHONE_NUMBER = "5491187654321";
    private static final String STATE = "Palermo";
    private static final String CITY = "CABA";

    //Job data
    private static final String JOB_DESCRIPTION = "El mejor cambio de aceite";
    private static final String JOB_PROVIDED = "Cambio de aceite";
    private static final int AVG_JOB_RATING = 4;
    private static final int TOTAL_JOB_RATINGS = 1;
    private static final JobCategory JOB_CATEGORY = JobCategory.MECANICO;
    private static final BigDecimal PRICE = BigDecimal.valueOf(3500.50);

    //Review data
    private static final int RATING = 4;
    private static final String REVIEW_DESCRIPTION = "Muy buen trabajo, gracias por todo";
    private static final Timestamp CREATION_DATE = Timestamp.valueOf("2021-04-05 20:26:02");

    private static final long ID = 1;
    private static final int REVIEWS_AMOUNT = 5;

    private static final Collection<Roles> DEFAULT_ROLES = Collections.unmodifiableCollection(Arrays.asList(Roles.USER, Roles.NOT_VERIFIED));

    private static final User user = new User(ID, PASSWORD, NAME, SURNAME, EMAIL, PHONE_NUMBER, STATE, CITY, DEFAULT_ROLES, null, null);
    private static final Job job = new Job(JOB_DESCRIPTION, JOB_PROVIDED, AVG_JOB_RATING, TOTAL_JOB_RATINGS, JOB_CATEGORY, ID, PRICE, user, null);

    @Autowired
    private DataSource ds;

    @Autowired
    private ReviewDao reviewDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() { jdbcTemplate = new JdbcTemplate(ds); }

    @Test
    public void testCreate(){
        final Review review = reviewDao.createReview(REVIEW_DESCRIPTION, job, RATING, CREATION_DATE);

        assertNotNull(review);
        assertEquals(REVIEW_DESCRIPTION, review.getDescription());
        assertEquals(job.getId(), review.getJobId());
        assertEquals(RATING, review.getRating());
        assertEquals(CREATION_DATE.toLocalDateTime().toLocalDate(), review.getCreationDate());

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "reviews", "r_id = " + review.getId()));
    }

    @Test
    public void testGetReviewsByJobId() {
        final Collection<Review> reviews = reviewDao.getReviewsByJobId(job.getId(), 0, REVIEWS_AMOUNT);
        for(Review review : reviews) {
            assertEquals(ID, review.getJobId());
        }
        assertEquals(REVIEWS_AMOUNT, reviews.size());
    }

    @Test
    public void testGetReviewsCountByJobId() {
//        final int reviewsCount = reviewDao.getReviewsCountByJobId(job.getId());
//        assertEquals(reviewsCount, REVIEWS_AMOUNT);
    }
}
