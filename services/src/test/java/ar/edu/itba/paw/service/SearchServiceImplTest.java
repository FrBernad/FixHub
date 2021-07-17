package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistance.LocationDao;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.location.City;
import ar.edu.itba.paw.models.location.State;
import ar.edu.itba.paw.models.pagination.PaginatedSearchResult;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.notification.Notification;
import ar.edu.itba.paw.services.SearchServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static ar.edu.itba.paw.models.user.Roles.PROVIDER;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchServiceImplTest {

    @InjectMocks
    private final SearchService searchService = new SearchServiceImpl();


    @Mock
    private LocationDao mockLocationDao;

    @Mock
    private User user;

    private static final State STATE = new State("STATE");
    private static final City CITY = new City(STATE, "CITY");
    private static final User USER = new User("", "", "", "", "", "", CITY.getName(), Collections.singleton(PROVIDER));

    //GET JOBS BY CATEGORY

    @Test
    public void testGetJobsByCategoryInvalidOrder() {

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "INVALID_ORDER",
            "MECANICO", "1", "2", 0, 6);

        assertNull(result);
    }

    @Test
    public void testGetJobsByCategoryInvalidPage() {

        lenient().when(mockLocationDao.getStateById(anyLong())).thenReturn(Optional.of(STATE));
        lenient().when(mockLocationDao.getCityByCityIdAndState(anyLong(), any(State.class))).thenReturn(Optional.of(CITY));

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "MOST_POPULAR",
            "MECANICO", "1", "2", -10, 6);

        assertNull(result);
    }

    @Test
    public void testGetJobsByCategoryInvalidItemsPerPage() {

        lenient().when(mockLocationDao.getStateById(anyInt())).thenReturn(Optional.of(STATE));
        lenient().when(mockLocationDao.getCityByCityIdAndState(anyLong(), any(State.class))).thenReturn(Optional.of(CITY));

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "MOST_POPULAR",
            "MECANICO", "1", "2", 0, -10);

        assertNull(result);
    }

    @Test
    public void testGetJobsByCategoryInvalidState() {

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "MOST_POPULAR",
            "MECANICO", "invalid", "", 0, 6);

        assertNull(result);
    }

    @Test
    public void testGetJobsByCategoryInvalidCity() {

        lenient().when(mockLocationDao.getStateById(1)).thenReturn(Optional.of(STATE));

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "MOST_POPULAR",
            "MECANICO", "1", "invalid", 0, -10);

        assertNull(result);
    }

    @Test
    public void testGetJobsByCategoryValidStateInvalidCity() {

        lenient().when(mockLocationDao.getStateById(2)).thenReturn(Optional.of(STATE));

        final PaginatedSearchResult<Job> result = searchService.getJobsByCategory("", "MOST_POPULAR",
            "MECANICO", "2", "invalid", 0, 6);

        assertNull(result);
    }


    //GET JOBS BY PROVIDER ID

    @Test
    public void testGetJobsByProviderInvalidOrder() {

        final PaginatedSearchResult<Job> result = searchService.getJobsByProvider("", "INVALID_ORDER",
            user, 0, 6);

        assertNull(result);
    }

    @Test
    public void testGetJobsByProviderInvalidPage() {

        final PaginatedSearchResult<Job> result = searchService.getJobsByProvider("", "MOST_POPULAR",
            user, -10, 6);

        assertNull(result);
    }

    @Test
    public void testGetJobsByProviderInvalidItemsPerPage() {

        final PaginatedSearchResult<Job> result = searchService.getJobsByProvider("", "NEWEST",
            user, 0, -20);

        assertNull(result);
    }

    //GET CLIENTS BY PROVIDER ID

    @Test
    public void testGetClientsByProviderInvalidPage() {

        final PaginatedSearchResult<JobContact> result = searchService.getClientsByProvider(USER, "PENDING", "NEWEST", -10, 6);

        assertNull(result);
    }

    @Test
    public void testGetClientsByProviderInvalidItemsPerPage() {

        final PaginatedSearchResult<JobContact> result = searchService.getClientsByProvider(USER, "PENDING", "NEWEST", 10, -1);

        assertNull(result);
    }

//    //GET PROVIDERS BY CLIENT ID

    @Test
    public void testGetProvidersByClientInvalidPage() {

        final PaginatedSearchResult<JobContact> result = searchService.getProvidersByClient(USER, "PENDING", "NEWEST", -10, 6);

        assertNull(result);
    }

    @Test
    public void testGetProvidersByClientInvalidItemsPerPage() {

        final PaginatedSearchResult<JobContact> result = searchService.getProvidersByClient(USER, "PENDING", "NEWEST", 10, -10);

        assertNull(result);
    }

    //
//    //GET USER FOLLOWERS
//
    @Test
    public void testGetUserFollowersInvalidPage() {

        final PaginatedSearchResult<User> result = searchService.getUserFollowers(USER, -10, 6);

        assertNull(result);
    }

    @Test
    public void testGetUserFollowersInvalidItemsPerPage() {

        final PaginatedSearchResult<User> result = searchService.getUserFollowers(USER, 0, -10);

        assertNull(result);
    }


    //GET USER FOLLOWING

    @Test
    public void testGetUserFollowingInvalidPage() {

        final PaginatedSearchResult<User> result = searchService.getUserFollowing(USER, -10, 6);

        assertNull(result);
    }

    @Test
    public void testGetUserFollowingInvalidItemsPerPage() {

        final PaginatedSearchResult<User> result = searchService.getUserFollowing(USER, 0, -10);

        assertNull(result);
    }

    //GET USER NOTIFICATIONS

    @Test
    public void testGetUserNotificationsInvalidPage() {

        final PaginatedSearchResult<Notification> result = searchService.getNotificationsByUser(USER, true, -10, 6);

        assertNull(result);
    }

    @Test
    public void testGetUserNotificationsInvalidItemsPerPage() {

        final PaginatedSearchResult<Notification> result = searchService.getNotificationsByUser(USER, true, 0, -10);

        assertNull(result);
    }
}
