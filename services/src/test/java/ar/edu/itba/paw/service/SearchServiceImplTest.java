//package ar.edu.itba.paw.service;
//
//import ar.edu.itba.paw.interfaces.persistance.JobDao;
//import ar.edu.itba.paw.interfaces.persistance.LocationDao;
//import ar.edu.itba.paw.interfaces.persistance.UserDao;
//import ar.edu.itba.paw.interfaces.services.SearchService;
//import ar.edu.itba.paw.models.*;
//import ar.edu.itba.paw.models.job.Job;
//import ar.edu.itba.paw.models.job.JobCategory;
//import ar.edu.itba.paw.models.job.JobContact;
//import ar.edu.itba.paw.models.user.User;
//import ar.edu.itba.paw.services.SearchServiceImpl;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.math.BigDecimal;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class SearchServiceImplTest {
//
//    @InjectMocks
//    private SearchService searchService = new SearchServiceImpl();
//
//    @Mock
//    private JobDao mockJobDao;
//
//    @Mock
//    private UserDao mockUserDao;
//
//    @Mock
//    private LocationDao mockLocationDao;
//
//    private static final Job JOB = new Job("", "", 5, 5, JobCategory.MECANICO, 1, BigDecimal.valueOf(13), false, null, null);
//    private static final Collection<Job> JOB_COLLECTION = Collections.singletonList(JOB);
//
//    private static final JobContact JOB_CONTACT = new JobContact(null, null, "", null, 1L, "", null);
//    private static final Collection<JobContact> JOB_CONTACT_COLLECTION = Collections.singletonList(JOB_CONTACT);
//
//    private static final User USER = new User(1L, "", "", "", "", "", null, null, null, null, null);
//    private static final Collection<User> USER_COLLECTION = Collections.singletonList(USER);
//
//    private final int DEFAULT_ITEMS_PER_PAGE = 6;
//
//    //GET JOBS BY CATEGORY
//
//    @Test
//    public void testGetJobsByCategoryInvalidOrder() {
//        when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
//            thenReturn(10);
//
//        lenient().when(mockLocationDao.getStateById(anyInt())).thenReturn(Optional.of(new State(1, "")));
//        lenient().when(mockLocationDao.getCityByCityAndStateId(anyInt(), anyInt())).thenReturn(Optional.of(new City(2, "")));
//
//        when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO), any(), any(), eq(0), eq(6)))
//            .thenReturn(JOB_COLLECTION);
//        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
//            "MECANICO", "1", "2", 0, 6);
//
//        assertEquals(JOB_COLLECTION, result.getResults());
//        assertEquals("MOST_POPULAR", result.getOrder());
//    }
//
//    @Test
//    public void testGetJobsByCategoryInvalidPage() {
//        when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
//            thenReturn(10);
//
//        lenient().when(mockLocationDao.getStateById(anyInt())).thenReturn(Optional.of(new State(1, "")));
//        lenient().when(mockLocationDao.getCityByCityAndStateId(anyInt(), anyInt())).thenReturn(Optional.of(new City(2, "")));
//
//        when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO), any(), any(), eq(0), eq(6)))
//            .thenReturn(JOB_COLLECTION);
//
//        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
//            "MECANICO", "1", "2", -10, 6);
//
//        assertEquals(0, result.getPage());
//    }
//
//    @Test
//    public void testGetJobsByCategoryInvalidItemsPerPage() {
//        when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
//            thenReturn(10);
//
//        lenient().when(mockLocationDao.getStateById(anyInt())).thenReturn(Optional.of(new State(1, "")));
//        lenient().when(mockLocationDao.getCityByCityAndStateId(anyInt(), anyInt())).thenReturn(Optional.of(new City(2, "")));
//
//        when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO), any(), any(), eq(0), eq(6)))
//            .thenReturn(JOB_COLLECTION);
//
//        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
//            "MECANICO", "1", "2", 0, -10);
//
//        assertEquals(DEFAULT_ITEMS_PER_PAGE, result.getItemsPerPage());
//    }
//
//    @Test
//    public void testGetJobsByCategoryInvalidState() {
//        when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
//            thenReturn(10);
//
//        lenient().when(mockLocationDao.getStateById(-1)).thenReturn(Optional.empty());
//        lenient().when(mockLocationDao.getCityByCityAndStateId(anyInt(), anyInt())).thenReturn(Optional.of(new City(2, "")));
//
//        when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO), eq(null), eq(null), eq(0), eq(6)))
//            .thenReturn(JOB_COLLECTION);
//
//        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
//            "MECANICO", "invalid", "", 0, 6);
//
//        assertEquals("", result.getState());
//        assertEquals(JOB_COLLECTION, result.getResults());
//    }
//
//    @Test
//    public void testGetJobsByCategoryInvalidCity() {
//        when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
//            thenReturn(10);
//
//        lenient().when(mockLocationDao.getStateById(1)).thenReturn(Optional.of(new State(1, "valid")));
//        lenient().when(mockLocationDao.getCityByCityAndStateId(eq(-1), eq(1))).thenReturn(Optional.empty());
//
//        when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO),
//            eq("1"), eq(null), eq(0), eq(6)))
//            .thenReturn(JOB_COLLECTION);
//
//        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
//            "MECANICO", "1", "invalid", 0, -10);
//
//        assertEquals("", result.getCity());
//        assertEquals("valid", result.getState());
//        assertEquals(JOB_COLLECTION, result.getResults());
//    }
//
//    @Test
//    public void testGetJobsByCategoryInvalidStateValidCity() {
//        when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
//            thenReturn(10);
//
//        lenient().when(mockLocationDao.getStateById(-1)).thenReturn(Optional.empty());
//        lenient().when(mockLocationDao.getCityByCityAndStateId(anyInt(), anyInt())).thenReturn(Optional.of(new City(2, "")));
//
//        when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO), eq(null), eq(null), eq(0), eq(6)))
//            .thenReturn(JOB_COLLECTION);
//
//        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
//            "MECANICO", "invalid", "2", 0, 6);
//
//        assertEquals("", result.getState());
//        assertEquals("", result.getCity());
//        assertEquals(JOB_COLLECTION, result.getResults());
//    }
//
//    @Test
//    public void testGetJobsByCategoryValidStateInvalidCity() {
//        when(mockJobDao.getJobsCountByCategory(any(), any(), any(), any())).
//            thenReturn(10);
//
//        lenient().when(mockLocationDao.getStateById(2)).thenReturn(Optional.of(new State(2, "valid")));
//        lenient().when(mockLocationDao.getCityByCityAndStateId(eq(-1), eq(2))).thenReturn(Optional.empty());
//
//        when(mockJobDao.getJobsByCategory(any(), eq(OrderOptions.MOST_POPULAR), eq(JobCategory.MECANICO),
//            eq("2"), eq(null), eq(0), eq(6)))
//            .thenReturn(JOB_COLLECTION);
//
//        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "asdad",
//            "MECANICO", "2", "invalid", 0, 6);
//
//        assertEquals(result.getCity(), "");
//        assertEquals(result.getState(), "valid");
//        assertEquals(result.getResults(), JOB_COLLECTION);
//    }
//
//
//    //GET JOBS BY PROVIDER ID
//
//    @Test
//    public void testGetJobsByProviderIdInvalidOrder() {
//        when(mockJobDao.getJobsCountByProviderId(any(), any())).
//            thenReturn(10);
//
//        when(mockJobDao.getJobsByProviderId(any(), eq(OrderOptions.MOST_POPULAR), any(), eq(0), eq(6)))
//            .thenReturn(JOB_COLLECTION);
//        final PaginatedSearchResult<Job> result = searchService.getJobsByProviderId("", "asdad",
//            1L, 0, 6);
//
//        assertEquals(JOB_COLLECTION, result.getResults());
//        assertEquals("MOST_POPULAR", result.getOrder());
//    }
//
//    @Test
//    public void testGetJobsByProviderIdInvalidPage() {
//        when(mockJobDao.getJobsCountByProviderId(any(), any())).
//            thenReturn(10);
//
//        when(mockJobDao.getJobsByProviderId(any(), eq(OrderOptions.MOST_POPULAR), any(), eq(0), eq(6)))
//            .thenReturn(JOB_COLLECTION);
//        final PaginatedSearchResult<Job> result = searchService.getJobsByProviderId("", "asdad",
//            1L, -10, 6);
//
//        assertEquals(JOB_COLLECTION, result.getResults());
//        assertEquals(0, result.getPage());
//    }
//
//    @Test
//    public void testGetJobsByProviderIdInvalidItemsPerPage() {
//        when(mockJobDao.getJobsCountByProviderId(any(), any())).
//            thenReturn(10);
//
//        when(mockJobDao.getJobsByProviderId(any(), eq(OrderOptions.MOST_POPULAR), any(), eq(0), eq(DEFAULT_ITEMS_PER_PAGE)))
//            .thenReturn(JOB_COLLECTION);
//        final PaginatedSearchResult<Job> result = searchService.getJobsByProviderId("", "asdad",
//            1L, 0, -20);
//
//        assertEquals(JOB_COLLECTION, result.getResults());
//        assertEquals(DEFAULT_ITEMS_PER_PAGE, result.getItemsPerPage());
//    }
//
//    //GET CLIENTS BY PROVIDER ID
//
//    @Test
//    public void testGetClientsByProviderIdInvalidPage() {
//        when(mockUserDao.getClientsCountByProviderId(any())).
//            thenReturn(10);
//
//        when(mockUserDao.getClientsByProviderId(any(), eq(0), eq(6)))
//            .thenReturn(JOB_CONTACT_COLLECTION);
//
//        final PaginatedSearchResult<JobContact> result = searchService.getClientsByProviderId(1L, -10, 6);
//
//        assertEquals(JOB_CONTACT_COLLECTION, result.getResults());
//        assertEquals(0, result.getPage());
//    }
//
//    @Test
//    public void testGetClientsByProviderIdInvalidItemsPerPage() {
//        when(mockUserDao.getClientsCountByProviderId(any())).
//            thenReturn(10);
//
//        when(mockUserDao.getClientsByProviderId(any(), eq(0), eq(DEFAULT_ITEMS_PER_PAGE)))
//            .thenReturn(JOB_CONTACT_COLLECTION);
//
//        final PaginatedSearchResult<JobContact> result = searchService.getClientsByProviderId(1L, 0, -10);
//
//        assertEquals(JOB_CONTACT_COLLECTION, result.getResults());
//        assertEquals(DEFAULT_ITEMS_PER_PAGE, result.getItemsPerPage());
//    }
//
//    //GET PROVIDERS BY CLIENT ID
//
//    @Test
//    public void testGetProvidersByClientIdInvalidPage() {
//        when(mockUserDao.getProvidersCountByClientId(any())).
//            thenReturn(10);
//
//        when(mockUserDao.getProvidersByClientId(any(), eq(0), eq(6)))
//            .thenReturn(JOB_CONTACT_COLLECTION);
//
//        final PaginatedSearchResult<JobContact> result = searchService.getProvidersByClientId(1L, -10, 6);
//
//        assertEquals(JOB_CONTACT_COLLECTION, result.getResults());
//        assertEquals(0, result.getPage());
//    }
//
//    @Test
//    public void testGetProvidersByClientIdInvalidItemsPerPage() {
//        when(mockUserDao.getProvidersCountByClientId(any())).
//            thenReturn(10);
//
//        when(mockUserDao.getProvidersByClientId(any(), eq(0), eq(DEFAULT_ITEMS_PER_PAGE)))
//            .thenReturn(JOB_CONTACT_COLLECTION);
//
//        final PaginatedSearchResult<JobContact> result = searchService.getProvidersByClientId(1L, 0, -10);
//
//        assertEquals(JOB_CONTACT_COLLECTION, result.getResults());
//        assertEquals(DEFAULT_ITEMS_PER_PAGE, result.getItemsPerPage());
//    }
//
//
//    //GET USER FOLLOWERS
//
//    @Test
//    public void testGetUserFollowersInvalidPage() {
//        when(mockUserDao.getUserFollowersCount(any())).
//            thenReturn(10);
//
//        when(mockUserDao.getUserFollowers(any(), eq(0), eq(6)))
//            .thenReturn(USER_COLLECTION);
//
//        final PaginatedSearchResult<User> result = searchService.getUserFollowers(1L, -10, 6);
//
//        assertEquals(USER_COLLECTION, result.getResults());
//        assertEquals(0, result.getPage());
//    }
//
//    @Test
//    public void testGetUserFollowersInvalidItemsPerPage() {
//        when(mockUserDao.getUserFollowersCount(any())).
//            thenReturn(10);
//
//        when(mockUserDao.getUserFollowers(eq(1L), eq(0), eq(DEFAULT_ITEMS_PER_PAGE)))
//            .thenReturn(USER_COLLECTION);
//
//        final PaginatedSearchResult<User> result = searchService.getUserFollowers(1L, 0, -10);
//
//        assertEquals(USER_COLLECTION, result.getResults());
//        assertEquals(DEFAULT_ITEMS_PER_PAGE, result.getItemsPerPage());
//    }
//
//
//    //GET USER FOLLOWING
//
//    @Test
//    public void testGetUserFollowingInvalidPage() {
//        when(mockUserDao.getUserFollowingCount(any())).
//            thenReturn(10);
//
//        when(mockUserDao.getUserFollowings(any(), eq(0), eq(6)))
//            .thenReturn(USER_COLLECTION);
//
//        final PaginatedSearchResult<User> result = searchService.getUserFollowing(1L, -10, 6);
//
//        assertEquals(USER_COLLECTION, result.getResults());
//        assertEquals(0, result.getPage());
//    }
//
//    @Test
//    public void testGetUserFollowingInvalidItemsPerPage() {
//        when(mockUserDao.getUserFollowingCount(any())).
//            thenReturn(10);
//
//        when(mockUserDao.getUserFollowings(eq(1L), eq(0), eq(DEFAULT_ITEMS_PER_PAGE)))
//            .thenReturn(USER_COLLECTION);
//
//        final PaginatedSearchResult<User> result = searchService.getUserFollowing(1L, 0, -10);
//
//        assertEquals(USER_COLLECTION, result.getResults());
//        assertEquals(DEFAULT_ITEMS_PER_PAGE, result.getItemsPerPage());
//    }
//}
