package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.pagination.PaginatedSearchResult;
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static ar.edu.itba.paw.services.UserServiceImpl.DEFAULT_ROLES;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

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

    private static final Review REVIEW = new Review("", null, 5, CREATIONDATE.toLocalDateTime().toLocalDate(), USER);
    private static final Collection<Review> REVIEW_COLLECTION = Collections.singletonList(REVIEW);

    private static final int DEFAULT_ITEMS_PER_PAGE = 6;

    @InjectMocks
    private final ReviewService reviewService = new ReviewServiceImpl();

    @Mock
    private ReviewDao mockReviewDao;

    @Mock
    private User mockUser;

    @Mock
    private Job mockJob;

    @Mock
    private UserService mockUserService;

    @Test
    public void testCreateReview() {
        lenient().when(mockUser.getId()).thenReturn(1L);
        lenient().when(mockJob.getJobProvided()).thenReturn("");
        lenient().when(mockJob.getId()).thenReturn(1L);
        lenient().when(mockJob.getReviews()).thenReturn(new HashSet<>());
        lenient().when(mockUserService.hasContactJobProvided(Mockito.eq(mockJob.getProvider()), Mockito.eq(USER))).thenReturn(Boolean.TRUE);

        lenient().when(mockReviewDao.createReview(Mockito.eq(REVIEW_DESCRIPTION), Mockito.eq(mockJob), Mockito.eq(RATING)
            , Mockito.any(), Mockito.eq(USER))).thenReturn(
            new Review(REVIEW_DESCRIPTION, mockJob, RATING, CREATIONDATE.toLocalDateTime().toLocalDate(), USER));

        reviewService.createReview(REVIEW_DESCRIPTION, mockJob, RATING, USER);
        Mockito.verify(mockReviewDao).createReview(Mockito.eq(REVIEW_DESCRIPTION), Mockito.eq(mockJob), Mockito.eq(RATING),
            Mockito.any(), Mockito.eq(USER));
    }

    @Test
    public void testGetReviewsByJobInvalidPage() {
        lenient().when(mockReviewDao.getReviewsCountByJob(any())).thenReturn(10);

        lenient().when(mockReviewDao.getReviewsByJob(any(), eq(0), eq(6))).thenReturn(REVIEW_COLLECTION);

        final PaginatedSearchResult<Review> result = reviewService.getReviewsByJob(mockJob, -10, 6);

        assertEquals(0, result.getPage());
    }

    @Test
    public void testGetReviewsByJobInvalidItemsPerPage() {
        lenient().when(mockReviewDao.getReviewsCountByJob(any())).thenReturn(10);

        lenient().when(mockReviewDao.getReviewsByJob(any(), eq(0), eq(6))).thenReturn(REVIEW_COLLECTION);

        final PaginatedSearchResult<Review> result = reviewService.getReviewsByJob(mockJob, 0, -10);

        assertEquals(0, result.getPage());

        assertEquals(DEFAULT_ITEMS_PER_PAGE, result.getItemsPerPage());
    }


}
