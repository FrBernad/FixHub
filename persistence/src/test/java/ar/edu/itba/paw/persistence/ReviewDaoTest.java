package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@Transactional
@Sql(scripts = "classpath:review-dao-test.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ReviewDaoTest {

    //PROVIDER data
    private static final String PASSWORD = "password";
    private static final String NAME = "Juan";
    private static final String SURNAME = "Gomez";
    private static final String EMAIL = "juan@yopmail.com";
    private static final String PHONE_NUMBER = "5491187654321";
    private static final String STATE = "Palermo";
    private static final String CITY = "CABA";
    private static final long ID = 1L;


    //REVIEWER data
    private static final String PASSWORD_R = "password";
    private static final String NAME_R = "John";
    private static final String SURNAME_R = "Doe";
    private static final String EMAIL_R = "john@yopmail.com";
    private static final String PHONE_NUMBER_R = "5491112345678";
    private static final String STATE_R = "Buenos Aires";
    private static final String CITY_R = "Banfield";
    private static final long ID_R = 2L;


    //Job data
    private static final String JOB_DESCRIPTION = "El mejor cambio de aceite";
    private static final String JOB_PROVIDED = "Cambio de aceite";
    private static final int AVG_JOB_RATING = 4;
    private static final int TOTAL_JOB_RATINGS = 1;
    private static final JobCategory JOB_CATEGORY = JobCategory.MECANICO;
    private static final BigDecimal PRICE = BigDecimal.valueOf(3500.50);
    private static final boolean PAUSED = false;
    private static final Collection<Long> IMAGES = new LinkedList<>();

    //Review data
    private static final int RATING = 4;
    private static final String REVIEW_DESCRIPTION = "Muy buen trabajo, gracias por todo";
    private static final Timestamp CREATION_DATE = Timestamp.valueOf("2021-04-05 20:26:02");

    private static final int REVIEWS_AMOUNT = 5;

    private static final Collection<Roles> PROVIDER_ROLES = Collections.unmodifiableCollection(Arrays.asList(Roles.USER, Roles.VERIFIED, Roles.PROVIDER));
    private static final Collection<Roles> DEFAULT_ROLES = Collections.unmodifiableCollection(Arrays.asList(Roles.USER, Roles.VERIFIED));


    private static final User PROVIDER = new User(ID, PASSWORD, NAME, SURNAME, EMAIL, PHONE_NUMBER, STATE, CITY, PROVIDER_ROLES, null, null);
    private static final User REVIEWER = new User(ID_R, PASSWORD_R, NAME_R, SURNAME_R, EMAIL_R, PHONE_NUMBER_R, STATE_R, CITY_R, DEFAULT_ROLES, null, null);

    private static final Job JOB = new Job(JOB_DESCRIPTION, JOB_PROVIDED, AVG_JOB_RATING, TOTAL_JOB_RATINGS, JOB_CATEGORY, ID, PRICE, PAUSED, PROVIDER, IMAGES);

    @Autowired
    private DataSource ds;

    @Autowired
    private ReviewDao reviewDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testCreate() {
        final Review review = reviewDao.createReview(REVIEW_DESCRIPTION, JOB, RATING, CREATION_DATE, REVIEWER);
        assertNotNull(review);
        assertEquals(REVIEW_DESCRIPTION, review.getDescription());
        assertEquals(JOB.getId(), review.getJobId());
        assertEquals(RATING, review.getRating());
        assertEquals(CREATION_DATE.toLocalDateTime().toLocalDate(), review.getCreationDate());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "reviews", "r_id = " + review.getId()));
    }

    @Test
    public void testGetReviewsCountByJobId() {
        final int reviewsCount = reviewDao.getReviewsCountByJobId(JOB.getId());
        assertEquals(REVIEWS_AMOUNT, reviewsCount);
    }

    @Test
    public void testGetReviewsByJobId() {
        final Collection<Review> reviews = reviewDao.getReviewsByJobId(JOB.getId(), 0, 5);

        for(Review review : reviews) {
            assertEquals(ID, review.getJobId());
        }

        assertEquals(REVIEWS_AMOUNT, reviews.size());
    }

}
