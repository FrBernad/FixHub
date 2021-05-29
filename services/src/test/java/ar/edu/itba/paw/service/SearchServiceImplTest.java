package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.interfaces.persistance.LocationDao;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.services.SearchServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static ar.edu.itba.paw.models.user.Roles.PROVIDER;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchServiceImplTest {

    @InjectMocks
    private final SearchService searchService = new SearchServiceImpl();

    @Mock
    private JobDao mockJobDao;

    @Mock
    private UserDao mockUserDao;

    @Mock
    private LocationDao mockLocationDao;

    @Mock
    private User user;

    private static final State STATE = new State("STATE");
    private static final City CITY = new City(STATE,"CITY");
    private static final User USER = new User("", "", "", "", "", "", CITY.getName(), Collections.singleton(PROVIDER));

    private static final Job JOB = new Job("", "", 5, 5L, JobCategory.MECANICO, BigDecimal.valueOf(13), false, USER, null);
    private static final Collection<Job> JOB_COLLECTION = Collections.singletonList(JOB);

    private static final JobContact JOB_CONTACT = new JobContact(USER, null, null, null, null, null);
    private static final Collection<JobContact> JOB_CONTACT_COLLECTION = Collections.singletonList(JOB_CONTACT);

    private static final Collection<User> USER_COLLECTION = Collections.singletonList(USER);

    private final int DEFAULT_ITEMS_PER_PAGE = 6;

    //GET JOBS BY CATEGORY

    @Test
    public void testGetJobsByCategoryInvalidOrder() {
            when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
            thenReturn(10);

        lenient().when(mockLocationDao.getStateById(anyInt())).thenReturn(Optional.of(STATE));
        lenient().when(mockLocationDao.getCityByCityIdAndState(anyLong(), any(State.class))).thenReturn(Optional.of(CITY));

        when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO), any(), any(), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);
        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
            "MECANICO", "1", "2", 0, 6);

        assertEquals(JOB_COLLECTION, result.getResults());
        assertEquals("MOST_POPULAR", result.getOrder());
    }

    @Test
    public void testGetJobsByCategoryInvalidPage() {
        when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
            thenReturn(10);

        lenient().when(mockLocationDao.getStateById(anyInt())).thenReturn(Optional.of(STATE));
        lenient().when(mockLocationDao.getCityByCityIdAndState(anyLong(), any(State.class))).thenReturn(Optional.of(CITY));

         when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO), any(), any(), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
            "MECANICO", "1", "2", -10, 6);

        assertEquals(0, result.getPage());
    }

    @Test
    public void testGetJobsByCategoryInvalidItemsPerPage() {
         lenient().when(mockJobDao.getJobsCountByCategory(any(String.class), any(JobCategory.class), any(String.class), any(String.class))).
            thenReturn(10);

        lenient().when(mockLocationDao.getStateById(anyInt())).thenReturn(Optional.of(STATE));
        lenient().when(mockLocationDao.getCityByCityIdAndState(anyLong(), any(State.class))).thenReturn(Optional.of(CITY));

         lenient().when(mockJobDao.getJobsByCategory(any(String.class), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO), any(String.class), any(String.class), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
            "MECANICO", "1", "2", 0, -10);

        assertEquals(DEFAULT_ITEMS_PER_PAGE, result.getItemsPerPage());
    }

    @Test
    public void testGetJobsByCategoryInvalidState() {
        when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
            thenReturn(10);

        lenient().when(mockLocationDao.getStateById(-1)).thenReturn(Optional.empty());
        lenient().when(mockLocationDao.getCityByCityIdAndState(anyInt(), any(State.class))).thenReturn(Optional.of(CITY));

        when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO), eq(null), eq(null), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
            "MECANICO", "invalid", "", 0, 6);

        assertEquals("", result.getState());
        assertEquals(JOB_COLLECTION, result.getResults());
    }

    @Test
    public void testGetJobsByCategoryInvalidCity() {
        when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
            thenReturn(10);

        lenient().when(mockLocationDao.getStateById(1)).thenReturn(Optional.of(STATE));
        lenient().when(mockLocationDao.getCityByCityIdAndState(eq(-1), eq(STATE))).thenReturn(Optional.empty());

        when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO),
            eq("1"), eq(null), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
            "MECANICO", "1", "invalid", 0, -10);

        assertEquals("", result.getCity());
        assertEquals(STATE.getName(), result.getState());
        assertEquals(JOB_COLLECTION, result.getResults());
    }

    @Test
    public void testGetJobsByCategoryInvalidStateValidCity() {
        when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
            thenReturn(10);

        lenient().when(mockLocationDao.getStateById(-1)).thenReturn(Optional.empty());
        lenient().when(mockLocationDao.getCityByCityIdAndState(anyInt(), any())).thenReturn(Optional.of(new City(STATE, "")));

        when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO), eq(null), eq(null), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
            "MECANICO", "invalid", "2", 0, 6);

        assertEquals("", result.getState());
        assertEquals("", result.getCity());
        assertEquals(JOB_COLLECTION, result.getResults());
    }

    @Test
    public void testGetJobsByCategoryValidStateInvalidCity() {
        when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
            thenReturn(10);
        lenient().when(mockLocationDao.getStateById(2)).thenReturn(Optional.of(STATE));
        lenient().when(mockLocationDao.getCityByCityIdAndState(eq(-1), eq(STATE))).thenReturn(Optional.empty());

        when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO),
            eq("2"), eq(null), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
            "MECANICO", "2", "invalid", 0, 6);

        assertEquals(result.getCity(), "");
        assertEquals(result.getState(), STATE.getName());
        assertEquals(result.getResults(), JOB_COLLECTION);
    }


    //GET JOBS BY PROVIDER ID

    @Test
    public void testGetJobsByProviderInvalidOrder() {
        when(mockJobDao.getJobsCountByProvider(any(), any())).
            thenReturn(10);
        when(user.getId()).thenReturn(new Long(1));

        when(mockJobDao.getJobsByProvider(any(), eq(OrderOptions.MOST_POPULAR), eq(user), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);
        final PaginatedSearchResult<Job> result = searchService.getJobsByProvider("", "asdad",
            user, 0, 6);

        assertEquals(JOB_COLLECTION, result.getResults());
        assertEquals("MOST_POPULAR", result.getOrder());
    }

    @Test
    public void testGetJobsByProviderIdInvalidPage() {
        when(mockJobDao.getJobsCountByProvider(any(), any())).
            thenReturn(10);

        when(mockJobDao.getJobsByProvider(any(), eq(OrderOptions.MOST_POPULAR), any(), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);
        final PaginatedSearchResult<Job> result = searchService.getJobsByProvider("", "asdad",
            user, -10, 6);

        assertEquals(JOB_COLLECTION, result.getResults());
        assertEquals(0, result.getPage());
    }

    @Test
    public void testGetJobsByProviderIdInvalidItemsPerPage() {
        when(mockJobDao.getJobsCountByProvider(any(), any())).
            thenReturn(10);
        when(user.getId()).thenReturn(new Long(1));

        when(mockJobDao.getJobsByProvider(any(), eq(OrderOptions.MOST_POPULAR), any(), eq(0), eq(DEFAULT_ITEMS_PER_PAGE)))
            .thenReturn(JOB_COLLECTION);
        final PaginatedSearchResult<Job> result = searchService.getJobsByProvider("", "asdad",
            user, 0, -20);

        assertEquals(JOB_COLLECTION, result.getResults());
        assertEquals(DEFAULT_ITEMS_PER_PAGE, result.getItemsPerPage());
    }

    //GET CLIENTS BY PROVIDER ID

    @Test
    public void testGetClientsByProviderIdInvalidPage() {
        when(mockUserDao.getClientsCountByProvider(any())).
            thenReturn(10);

        when(mockUserDao.getClientsByProvider(any(), eq(0), eq(6)))
            .thenReturn(JOB_CONTACT_COLLECTION);

        final PaginatedSearchResult<JobContact> result = searchService.getClientsByProvider(USER, -10, 6);

        assertEquals(JOB_CONTACT_COLLECTION, result.getResults());
        assertEquals(0, result.getPage());
    }

    @Test
    public void testGetClientsByProviderIdInvalidItemsPerPage() {
        when(mockUserDao.getClientsCountByProvider(any())).
            thenReturn(10);

        when(mockUserDao.getClientsByProvider(any(), eq(0), eq(DEFAULT_ITEMS_PER_PAGE)))
            .thenReturn(JOB_CONTACT_COLLECTION);

        final PaginatedSearchResult<JobContact> result = searchService.getClientsByProvider(USER, 0, -10);

        assertEquals(JOB_CONTACT_COLLECTION, result.getResults());
        assertEquals(DEFAULT_ITEMS_PER_PAGE, result.getItemsPerPage());
    }

//    //GET PROVIDERS BY CLIENT ID

    @Test
    public void testGetProvidersByClientIdInvalidPage() {
        when(mockUserDao.getProvidersCountByClient(any())).
            thenReturn(10);

        when(mockUserDao.getProvidersByClient(any(), eq(0), eq(6)))
            .thenReturn(JOB_CONTACT_COLLECTION);

        final PaginatedSearchResult<JobContact> result = searchService.getProvidersByClient(USER, -10, 6);

        assertEquals(JOB_CONTACT_COLLECTION, result.getResults());
        assertEquals(0, result.getPage());
    }

    @Test
    public void testGetProvidersByClientIdInvalidItemsPerPage() {
        when(mockUserDao.getProvidersCountByClient(any())).
            thenReturn(10);

        when(mockUserDao.getProvidersByClient(any(), eq(0), eq(DEFAULT_ITEMS_PER_PAGE)))
            .thenReturn(JOB_CONTACT_COLLECTION);

        final PaginatedSearchResult<JobContact> result = searchService.getProvidersByClient(USER, 0, -10);

        assertEquals(JOB_CONTACT_COLLECTION, result.getResults());
        assertEquals(DEFAULT_ITEMS_PER_PAGE, result.getItemsPerPage());
    }

//
//    //GET USER FOLLOWERS
//
    @Test
    public void testGetUserFollowersInvalidPage() {
        when(mockUserDao.getUserFollowersCount(any())).
            thenReturn(10);

        when(mockUserDao.getUserFollowers(any(), eq(0), eq(6)))
            .thenReturn(USER_COLLECTION);

        final PaginatedSearchResult<User> result = searchService.getUserFollowers(USER, -10, 6);

        assertEquals(USER_COLLECTION, result.getResults());
        assertEquals(0, result.getPage());
    }

    @Test
    public void testGetUserFollowersInvalidItemsPerPage() {
        when(mockUserDao.getUserFollowersCount(any())).
            thenReturn(10);

        when(mockUserDao.getUserFollowers(any(), eq(0), eq(DEFAULT_ITEMS_PER_PAGE)))
            .thenReturn(USER_COLLECTION);

        final PaginatedSearchResult<User> result = searchService.getUserFollowers(USER, 0, -10);

        assertEquals(USER_COLLECTION, result.getResults());
        assertEquals(DEFAULT_ITEMS_PER_PAGE, result.getItemsPerPage());
    }


    //GET USER FOLLOWING

    @Test
    public void testGetUserFollowingInvalidPage() {
        when(mockUserDao.getUserFollowingCount(any())).
            thenReturn(10);

        when(mockUserDao.getUserFollowings(any(), eq(0), eq(6)))
            .thenReturn(USER_COLLECTION);

        final PaginatedSearchResult<User> result = searchService.getUserFollowing(USER, -10, 6);

        assertEquals(USER_COLLECTION, result.getResults());
        assertEquals(0, result.getPage());
    }

    @Test
    public void testGetUserFollowingInvalidItemsPerPage() {
        when(mockUserDao.getUserFollowingCount(any())).
            thenReturn(10);

        when(mockUserDao.getUserFollowings(any(), eq(0), eq(DEFAULT_ITEMS_PER_PAGE)))
            .thenReturn(USER_COLLECTION);

        final PaginatedSearchResult<User> result = searchService.getUserFollowing(USER, 0, -10);

        assertEquals(USER_COLLECTION, result.getResults());
        assertEquals(DEFAULT_ITEMS_PER_PAGE, result.getItemsPerPage());
    }
}
