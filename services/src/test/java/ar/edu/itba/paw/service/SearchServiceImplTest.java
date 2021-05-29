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
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchServiceImplTest {

    @InjectMocks
    private SearchService searchService = new SearchServiceImpl();

    @Mock
    private JobDao mockJobDao;

    @Mock
    private UserDao mockUserDao;

    @Mock
    private LocationDao mockLocationDao;

    private static final Job JOB = Mockito.lenient().when(Mockito.mock(Job.class).getId()).thenReturn(1L).getMock();
    private static final Collection<Job> JOB_COLLECTION = Collections.singletonList(JOB);

    private static final JobContact JOB_CONTACT = Mockito.lenient().when(Mockito.mock(JobContact.class).getId()).thenReturn(1L).getMock();
    private static final Collection<JobContact> JOB_CONTACT_COLLECTION = Collections.singletonList(JOB_CONTACT);

    private static final User USER = Mockito.lenient().when(Mockito.mock(User.class).getId()).thenReturn(1L).getMock();

    private static final Collection<User> USER_COLLECTION = Collections.singletonList(USER);

    private static final int DEFAULT_ITEMS_PER_PAGE = 6;

    private static final State STATE = Mockito.lenient().when(Mockito.mock(State.class).getId()).thenReturn(1L).getMock();
    private static final City CITY = Mockito.lenient().when(Mockito.mock(City.class).getId()).thenReturn(2L).getMock();

    //GET JOBS BY CATEGORY

    @Test
    public void testGetJobsByCategoryInvalidOrder() {
        when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
            thenReturn(10);

        lenient().when(mockLocationDao.getStateById(anyInt())).thenReturn(Optional.of(STATE));
        lenient().when(mockLocationDao.getCityByCityIdAndState(anyInt(), eq(STATE))).thenReturn(Optional.of(CITY));

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
        lenient().when(mockLocationDao.getCityByCityIdAndState(anyInt(), eq(STATE))).thenReturn(Optional.of(CITY));

        when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO), any(), any(), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
            "MECANICO", "1", "2", -10, 6);

        assertEquals(0, result.getPage());
    }

    @Test
    public void testGetJobsByCategoryInvalidItemsPerPage() {
        when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
            thenReturn(10);

        lenient().when(mockLocationDao.getStateById(anyInt())).thenReturn(Optional.of(STATE));
        lenient().when(mockLocationDao.getCityByCityIdAndState(anyInt(), eq(STATE))).thenReturn(Optional.of(CITY));

        when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO), any(), any(), eq(0), eq(6)))
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
        lenient().when(mockLocationDao.getCityByCityIdAndState(anyInt(), any())).thenReturn(Optional.of(CITY));

        when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO), eq(null), eq(null), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
            "MECANICO", "invalid", "", 0, 6);

        assertEquals("", result.getState());
        assertEquals(JOB_COLLECTION, result.getResults());
    }

    @Test
    public void testGetJobsByCategoryInvalidCity() {
        when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).thenReturn(10);

        lenient().when(mockLocationDao.getStateById(1)).thenReturn(Optional.of(STATE));
        lenient().when(mockLocationDao.getCityByCityIdAndState(eq(-1), eq(STATE))).thenReturn(Optional.empty());
        lenient().when(STATE.getName()).thenReturn("valid");

        when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO),
            eq("1"), eq(null), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
            "MECANICO", "1", "invalid", 0, -10);

        assertEquals("", result.getCity());
        assertEquals("valid", result.getState());
        assertEquals(JOB_COLLECTION, result.getResults());
    }

    @Test
    public void testGetJobsByCategoryInvalidStateValidCity() {
        when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
            thenReturn(10);

        lenient().when(mockLocationDao.getStateById(-1)).thenReturn(Optional.empty());
        lenient().when(mockLocationDao.getCityByCityIdAndState(anyInt(), any())).thenReturn(Optional.of(CITY));

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
        lenient().when(STATE.getName()).thenReturn("valid");
        lenient().when(mockLocationDao.getCityByCityIdAndState(eq(-1), eq(STATE))).thenReturn(Optional.empty());

        when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO),
            eq("2"), eq(null), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
            "MECANICO", "2", "invalid", 0, 6);

        assertEquals(result.getCity(), "");
        assertEquals(result.getState(), "valid");
        assertEquals(result.getResults(), JOB_COLLECTION);
    }


    //GET JOBS BY PROVIDER ID

    @Test
    public void testGetJobsByProviderInvalidOrder() {
        when(mockJobDao.getJobsCountByProvider(any(), any())).
            thenReturn(10);

        when(mockJobDao.getJobsByProvider(any(), eq(OrderOptions.MOST_POPULAR), any(), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);
        final PaginatedSearchResult<Job> result = searchService.getJobsByProvider("", "asdad",
            USER, 0, 6);

        assertEquals(JOB_COLLECTION, result.getResults());
        assertEquals("MOST_POPULAR", result.getOrder());
    }

    @Test
    public void testGetJobsByProviderInvalidPage() {
        when(mockJobDao.getJobsCountByProvider(any(), any())).
            thenReturn(10);

        when(mockJobDao.getJobsByProvider(any(), eq(OrderOptions.MOST_POPULAR), any(), eq(0), eq(6)))
            .thenReturn(JOB_COLLECTION);
        final PaginatedSearchResult<Job> result = searchService.getJobsByProvider("", "asdad",
            USER, -10, 6);

        assertEquals(JOB_COLLECTION, result.getResults());
        assertEquals(0, result.getPage());
    }

    @Test
    public void testGetJobsByProviderInvalidItemsPerPage() {
        when(mockJobDao.getJobsCountByProvider(any(), any())).
            thenReturn(10);

        when(mockJobDao.getJobsByProvider(any(), eq(OrderOptions.MOST_POPULAR), any(), eq(0), eq(DEFAULT_ITEMS_PER_PAGE)))
            .thenReturn(JOB_COLLECTION);
        final PaginatedSearchResult<Job> result = searchService.getJobsByProvider("", "asdad",
            USER, 0, -20);

        assertEquals(JOB_COLLECTION, result.getResults());
        assertEquals(DEFAULT_ITEMS_PER_PAGE, result.getItemsPerPage());
    }

    //GET CLIENTS BY PROVIDER ID

    @Test
    public void testGetClientsByProviderInvalidPage() {
        when(mockUserDao.getClientsCountByProvider(any())).
            thenReturn(10);

        when(mockUserDao.getClientsByProvider(any(), eq(0), eq(6)))
            .thenReturn(JOB_CONTACT_COLLECTION);

        final PaginatedSearchResult<JobContact> result = searchService.getClientsByProvider(USER, -10, 6);

        assertEquals(JOB_CONTACT_COLLECTION, result.getResults());
        assertEquals(0, result.getPage());
    }

    @Test
    public void testGetClientsByProviderInvalidItemsPerPage() {
        when(mockUserDao.getClientsCountByProvider(any())).
            thenReturn(10);

        when(mockUserDao.getClientsByProvider(any(), eq(0), eq(DEFAULT_ITEMS_PER_PAGE)))
            .thenReturn(JOB_CONTACT_COLLECTION);

        final PaginatedSearchResult<JobContact> result = searchService.getClientsByProvider(USER, 0, -10);

        assertEquals(JOB_CONTACT_COLLECTION, result.getResults());
        assertEquals(DEFAULT_ITEMS_PER_PAGE, result.getItemsPerPage());
    }

    //GET PROVIDERS BY CLIENT ID

    @Test
    public void testGetProvidersByClientInvalidPage() {
        when(mockUserDao.getProvidersCountByClient(any())).
            thenReturn(10);

        when(mockUserDao.getProvidersByClient(any(), eq(0), eq(6)))
            .thenReturn(JOB_CONTACT_COLLECTION);

        final PaginatedSearchResult<JobContact> result = searchService.getProvidersByClient(USER, -10, 6);

        assertEquals(JOB_CONTACT_COLLECTION, result.getResults());
        assertEquals(0, result.getPage());
    }

    @Test
    public void testGetProvidersByClientInvalidItemsPerPage() {
        when(mockUserDao.getProvidersCountByClient(any())).
            thenReturn(10);

        when(mockUserDao.getProvidersByClient(any(), eq(0), eq(DEFAULT_ITEMS_PER_PAGE)))
            .thenReturn(JOB_CONTACT_COLLECTION);

        final PaginatedSearchResult<JobContact> result = searchService.getProvidersByClient(USER, 0, -10);

        assertEquals(JOB_CONTACT_COLLECTION, result.getResults());
        assertEquals(DEFAULT_ITEMS_PER_PAGE, result.getItemsPerPage());
    }


    //GET USER FOLLOWERS

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

        when(mockUserDao.getUserFollowers(eq(1L), eq(0), eq(DEFAULT_ITEMS_PER_PAGE)))
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

        when(mockUserDao.getUserFollowings(eq(1L), eq(0), eq(DEFAULT_ITEMS_PER_PAGE)))
            .thenReturn(USER_COLLECTION);

        final PaginatedSearchResult<User> result = searchService.getUserFollowing(USER, 0, -10);

        assertEquals(USER_COLLECTION, result.getResults());
        assertEquals(DEFAULT_ITEMS_PER_PAGE, result.getItemsPerPage());
    }
}
