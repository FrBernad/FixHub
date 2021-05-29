package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.Review;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.services.ReviewServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.HashSet;

import static ar.edu.itba.paw.services.UserServiceImpl.DEFAULT_ROLES;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceImplTest {

    //Reviewer Info
    private static final String NAME = "Matias";
    private static final String SURNAME = "Lopez";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String PHONENUMBER = "phoneNumber";
    private static final String STATE = "state";
    private static final String CITY = "city";


    //Review Info
    private static final String REVIEW_DESCRIPTION = "Excelente Servicio";
    private static final int RATING = 4;
    private static final Timestamp CREATIONDATE = Timestamp.valueOf("2021-04-05 20:26:02");

    private static final User USER = new User(PASSWORD, NAME, SURNAME, EMAIL, PHONENUMBER, STATE, CITY, DEFAULT_ROLES);

    @InjectMocks
    private final ReviewService reviewService = new ReviewServiceImpl();

    @Mock
    private ReviewDao reviewDao;

    @Mock
    private User user;

    @Mock
    private Job job;

    @Mock
    private UserService userService;

    @Test
    public void createReviewTest() {
        when(user.getId()).thenReturn(1L);
        when(job.getJobProvided()).thenReturn("");
        when(job.getId()).thenReturn(1L);
        when(job.getReviews()).thenReturn(new HashSet<>());
        when(userService.hasContactJobProvided(Mockito.eq(job.getProvider()), Mockito.eq(USER))).thenReturn(Boolean.TRUE);

        when(reviewDao.createReview(Mockito.eq(REVIEW_DESCRIPTION), Mockito.eq(job), Mockito.eq(RATING)
            , Mockito.any(), Mockito.eq(USER))).thenReturn(
            new Review(REVIEW_DESCRIPTION, job, RATING, CREATIONDATE.toLocalDateTime().toLocalDate(), USER));

        reviewService.createReview(REVIEW_DESCRIPTION, job, RATING, USER);
        Mockito.verify(reviewDao).createReview(Mockito.eq(REVIEW_DESCRIPTION), Mockito.eq(job), Mockito.eq(RATING),
            Mockito.any(), Mockito.eq(USER));

    }

}
