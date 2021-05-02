//package ar.edu.itba.paw.service;
//
//import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
//import ar.edu.itba.paw.interfaces.services.ReviewService;
//import ar.edu.itba.paw.interfaces.services.UserService;
//import ar.edu.itba.paw.models.*;
//import ar.edu.itba.paw.services.ReviewServiceImpl;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//
//import static ar.edu.itba.paw.models.JobCategory.TECHISTA;
//import static ar.edu.itba.paw.services.UserServiceImpl.DEFAULT_ROLES;
//import static org.junit.Assert.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ReviewServiceImpltest {
//
//    //Reviewer Info
//    private static final long USER_ID = 1;
//    private static final String NAME = "Matias";
//    private static final String SURNAME = "Lopez";
//    private static final long PROFILE_IMAGE_ID = 1;
//    private static final String PASSWORD = "password";
//    private static final String EMAIL = "email";
//    private static final String PHONENUMBER = "phoneNumber";
//    private static final String STATE = "state";
//    private static final String CITY = "city";
//
//
//    //Review Info
//    private static final Number REVIEW_ID = 1;
//    private static final String REVIEW_DESCRIPTION = "Excelente Servicio";
//    private static final Number JOB_REVIEW_ID = 1;
//    private static final int RATING = 4;
//    private static final Timestamp CREATIONDATE=Timestamp.valueOf("2021-04-05 20:26:02");
//
//
//    //Job info
//    private static final String DESCRIPTION = "arreglo techos y los dejo como nuevos, años de experiencia";
//    private static final String JOB_PROVIDED = "arreglo techos";
//    private static final JobCategory CATEGORY = TECHISTA;
//    private static final BigDecimal PRICE = BigDecimal.valueOf(3000);
//    private static final boolean PAUSED = false;
//    private static final int AVG_RATINGS = 0;
//    private static final long TOTAL_RATINGS = 0;
//    private static final long JOB_ID = 1;
//
//
//
//    private static final User USER =new User(USER_ID,PASSWORD,NAME,SURNAME,EMAIL,PHONENUMBER,STATE,CITY,DEFAULT_ROLES,null,null);
//    private static final Job AUX_JOB=new Job(DESCRIPTION,JOB_PROVIDED,AVG_RATINGS,TOTAL_RATINGS,CATEGORY, JOB_ID,PRICE,PAUSED,USER,null,null);
//    private ReviewUser reviewUser=new ReviewUser(USER_ID,NAME,SURNAME,PROFILE_IMAGE_ID);
//
//    @InjectMocks
//    private ReviewService reviewService=new ReviewServiceImpl();
//
//    @Mock
//    private ReviewDao reviewDao;
//
//    @Mock
//    private UserService userService;
//
//    @Test
//    public void createReviewTest(){
//
//
//
//        Mockito.when(userService.hasContactJobProvided(Mockito.eq(AUX_JOB),Mockito.eq(USER))).thenReturn(Boolean.TRUE);
//
//        Mockito.when(reviewDao.createReview(Mockito.eq(REVIEW_DESCRIPTION),Mockito.eq(AUX_JOB),Mockito.eq(RATING)
//                ,Mockito.any(),Mockito.eq(USER))).thenReturn(
//                    new Review(REVIEW_ID,REVIEW_DESCRIPTION,JOB_REVIEW_ID,RATING,CREATIONDATE.toLocalDateTime().toLocalDate(),reviewUser));
//
//        Review review= reviewService.createReview(REVIEW_DESCRIPTION,AUX_JOB,RATING,USER);
//
//        assertNotNull(review);
//        assertEquals(review.getReviewer(),reviewUser);
//        assertEquals(review.getDescription(),REVIEW_DESCRIPTION);
//        assertEquals(review.getId(),REVIEW_ID);
//        assertEquals(review.getJobId(),JOB_REVIEW_ID);
//        assertEquals(review.getRating(),RATING);
//
//
//    }
//
//
//}
