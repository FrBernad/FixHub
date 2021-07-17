package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.interfaces.persistance.LocationDao;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.models.job.JobStatus;
import ar.edu.itba.paw.models.location.City;
import ar.edu.itba.paw.models.location.State;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.pagination.OrderOptions;
import ar.edu.itba.paw.models.pagination.PaginatedSearchResult;
import ar.edu.itba.paw.models.pagination.StatusOrderOptions;
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
    private static final City CITY = new City(STATE, "CITY");
    private static final User USER = new User("", "", "", "", "", "", CITY.getName(), Collections.singleton(PROVIDER));

    private static final Job JOB = new Job("", "", 5, 5L, JobCategory.MECANICO, BigDecimal.valueOf(13), false, USER, null);
    private static final Collection<Job> JOB_COLLECTION = Collections.singletonList(JOB);

    private static final JobContact JOB_CONTACT = new JobContact(USER, null, null, null, null, null, JobStatus.IN_PROGRESS);
    private static final Collection<JobContact> JOB_CONTACT_COLLECTION = Collections.singletonList(JOB_CONTACT);

    private static final Collection<User> USER_COLLECTION = Collections.singletonList(USER);

    private final int DEFAULT_ITEMS_PER_PAGE = 6;

    //GET JOBS BY CATEGORY

    @Test
    public void testGetJobsByCategoryInvalidOrder() {
        lenient().when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
            thenReturn(10);

        lenient().when(mockLocationDao.getStateById(anyInt())).thenReturn(Optional.of(STATE));
        lenient().when(mockLocationDao.getCityByCityIdAndState(anyLong(), any(State.class))).thenReturn(Optional.of(CITY));

        lenient().when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO), any(), any(), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);
        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
            "MECANICO", "1", "2", 0, 6);

        assertNull(result);
    }

    @Test
    public void testGetJobsByCategoryInvalidPage() {
        lenient().when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
            thenReturn(10);

        lenient().when(mockLocationDao.getStateById(anyInt())).thenReturn(Optional.of(STATE));
        lenient().when(mockLocationDao.getCityByCityIdAndState(anyLong(), any(State.class))).thenReturn(Optional.of(CITY));

        lenient().when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO), any(), any(), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
            "MECANICO", "1", "2", -10, 6);

        assertNull(result);
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

        assertNull(result);
    }

    @Test
    public void testGetJobsByCategoryInvalidState() {
        lenient().when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
            thenReturn(10);

        lenient().when(mockLocationDao.getStateById(-1)).thenReturn(Optional.empty());
        lenient().when(mockLocationDao.getCityByCityIdAndState(anyInt(), any(State.class))).thenReturn(Optional.of(CITY));

        lenient().when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO), eq(null), eq(null), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
            "MECANICO", "invalid", "", 0, 6);

        assertNull(result);
    }

    @Test
    public void testGetJobsByCategoryInvalidCity() {
        lenient().when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
            thenReturn(10);

        lenient().when(mockLocationDao.getStateById(1)).thenReturn(Optional.of(STATE));
        lenient().when(mockLocationDao.getCityByCityIdAndState(eq(-1), eq(STATE))).thenReturn(Optional.empty());

        lenient().when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO),
            eq("1"), eq(null), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
            "MECANICO", "1", "invalid", 0, -10);

        assertNull(result);
    }

    @Test
    public void testGetJobsByCategoryInvalidStateValidCity() {
        lenient().when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
            thenReturn(10);

        lenient().when(mockLocationDao.getStateById(-1)).thenReturn(Optional.empty());
        lenient().when(mockLocationDao.getCityByCityIdAndState(anyInt(), any())).thenReturn(Optional.of(new City(STATE, "")));

        lenient().when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO), eq(null), eq(null), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
            "MECANICO", "invalid", "2", 0, 6);

        assertNull(result);
    }

    @Test
    public void testGetJobsByCategoryValidStateInvalidCity() {
        lenient().when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
            thenReturn(10);
        lenient().when(mockLocationDao.getStateById(2)).thenReturn(Optional.of(STATE));
        lenient().when(mockLocationDao.getCityByCityIdAndState(eq(-1), eq(STATE))).thenReturn(Optional.empty());

        lenient().when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO),
            eq("2"), eq(null), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
            "MECANICO", "2", "invalid", 0, 6);

        assertNull(result);
    }


    //GET JOBS BY PROVIDER ID

    @Test
    public void testGetJobsByProviderInvalidOrder() {
        lenient().when(mockJobDao.getJobsCountByProvider(any(), any())).
            thenReturn(10);
        lenient().when(user.getId()).thenReturn(new Long(1));

        lenient().when(mockJobDao.getJobsByProvider(any(), eq(OrderOptions.MOST_POPULAR), eq(user), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);
        final PaginatedSearchResult<Job> result = searchService.getJobsByProvider("", "asdad",
            user, 0, 6);

        assertNull(result);
    }

    @Test
    public void testGetJobsByProviderIdInvalidPage() {
        lenient().when(mockJobDao.getJobsCountByProvider(any(), any())).
            thenReturn(10);

        lenient().when(mockJobDao.getJobsByProvider(any(), eq(OrderOptions.MOST_POPULAR), any(), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);
        final PaginatedSearchResult<Job> result = searchService.getJobsByProvider("", "asdad",
            user, -10, 6);

        assertNull(result);
    }

    @Test
    public void testGetJobsByProviderIdInvalidItemsPerPage() {
        lenient().when(mockJobDao.getJobsCountByProvider(any(), any())).
            thenReturn(10);
        lenient().when(user.getId()).thenReturn(new Long(1));

        lenient().when(mockJobDao.getJobsByProvider(any(), eq(OrderOptions.MOST_POPULAR), any(), eq(0), eq(DEFAULT_ITEMS_PER_PAGE)))
            .thenReturn(JOB_COLLECTION);
        final PaginatedSearchResult<Job> result = searchService.getJobsByProvider("", "asdad",
            user, 0, -20);

        assertNull(result);
    }

    //GET CLIENTS BY PROVIDER ID

    @Test
    public void testGetClientsByProviderIdInvalidPage() {
        lenient().when(mockUserDao.getClientsCountByProvider(any(), eq(null))).
            thenReturn(10);

        lenient().when(mockUserDao.getClientsByProvider(any(), eq(null), eq(StatusOrderOptions.NEWEST), eq(0), eq(6)))
            .thenReturn(JOB_CONTACT_COLLECTION);

        final PaginatedSearchResult<JobContact> result = searchService.getClientsByProvider(USER, null, "NEWEST", -10, 6);

        assertNull(result);
    }

    @Test
    public void testGetClientsByProviderIdInvalidItemsPerPage() {
        lenient().when(mockUserDao.getClientsCountByProvider(any(), eq(null))).
            thenReturn(10);

        lenient().when(mockUserDao.getClientsByProvider(any(), eq(null), eq(StatusOrderOptions.NEWEST), eq(0), eq(6)))
            .thenReturn(JOB_CONTACT_COLLECTION);

        final PaginatedSearchResult<JobContact> result = searchService.getClientsByProvider(USER, null, "NEWEST", 0, -10);

        assertNull(result);
    }

//    //GET PROVIDERS BY CLIENT ID

    @Test
    public void testGetProvidersByClientIdInvalidPage() {
        lenient().when(mockUserDao.getClientsCountByProvider(any(), eq(null))).
            thenReturn(10);

        lenient().when(mockUserDao.getProvidersByClient(any(), eq(null), eq(StatusOrderOptions.NEWEST), eq(0), eq(6)))
            .thenReturn(JOB_CONTACT_COLLECTION);

        final PaginatedSearchResult<JobContact> result = searchService.getProvidersByClient(USER, null, "NEWEST", -10, 6);

        assertNull(result);
    }

    @Test
    public void testGetProvidersByClientIdInvalidItemsPerPage() {
        lenient().when(mockUserDao.getProvidersCountByClient(any(), eq(null))).
            thenReturn(10);

        lenient().when(mockUserDao.getProvidersByClient(any(), eq(null), eq(StatusOrderOptions.NEWEST), eq(0), eq(6)))
            .thenReturn(JOB_CONTACT_COLLECTION);

        final PaginatedSearchResult<JobContact> result = searchService.getProvidersByClient(USER, null, "NEWEST", 0, -10);

        assertNull(result);
    }

    //
//    //GET USER FOLLOWERS
//
    @Test
    public void testGetUserFollowersInvalidPage() {
        lenient().when(mockUserDao.getUserFollowersCount(any())).
            thenReturn(10);

        lenient().when(mockUserDao.getUserFollowers(any(), eq(0), eq(6)))
            .thenReturn(USER_COLLECTION);

        final PaginatedSearchResult<User> result = searchService.getUserFollowers(USER, -10, 6);

        assertNull(result);
    }

    @Test
    public void testGetUserFollowersInvalidItemsPerPage() {
        lenient().when(mockUserDao.getUserFollowersCount(any())).
            thenReturn(10);

        lenient().when(mockUserDao.getUserFollowers(any(), eq(0), eq(DEFAULT_ITEMS_PER_PAGE)))
            .thenReturn(USER_COLLECTION);

        final PaginatedSearchResult<User> result = searchService.getUserFollowers(USER, 0, -10);


        assertNull(result);
    }


    //GET USER FOLLOWING

    @Test
    public void testGetUserFollowingInvalidPage() {
        lenient().when(mockUserDao.getUserFollowingCount(any())).
            thenReturn(10);

        lenient().when(mockUserDao.getUserFollowings(any(), eq(0), eq(6)))
            .thenReturn(USER_COLLECTION);

        final PaginatedSearchResult<User> result = searchService.getUserFollowing(USER, -10, 6);

        assertNull(result);
    }

    @Test
    public void testGetUserFollowingInvalidItemsPerPage() {
        lenient().when(mockUserDao.getUserFollowingCount(any())).
            thenReturn(10);

        lenient().when(mockUserDao.getUserFollowings(any(), eq(0), eq(DEFAULT_ITEMS_PER_PAGE)))
            .thenReturn(USER_COLLECTION);

        final PaginatedSearchResult<User> result = searchService.getUserFollowing(USER, 0, -10);

        assertNull(result);
    }
}
