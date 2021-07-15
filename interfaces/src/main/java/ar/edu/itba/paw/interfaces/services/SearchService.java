package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.pagination.OrderOptions;
import ar.edu.itba.paw.models.pagination.PaginatedSearchResult;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.notification.Notification;

import java.util.Collection;
import java.util.Optional;

public interface SearchService {
    PaginatedSearchResult<Job> getJobsByCategory(String searchBy, String orderBy, String category, String state, String city, int page, int pageSize);

    PaginatedSearchResult<Job> getJobsByProvider(String searchBy, String orderBy, User provider, int page, int pageSize);

    PaginatedSearchResult<JobContact> getClientsByProvider(User provider, String state, String orderBy, int page, int pageSize);

    PaginatedSearchResult<JobContact> getProvidersByClient(User client, int page, int pageSize);

    PaginatedSearchResult<User> getUserFollowers(User user, Integer page, Integer pageSize);

    PaginatedSearchResult<User> getUserFollowing(User user, Integer page, Integer pageSize);

    PaginatedSearchResult<Notification> getNotificationsByUser(User user, Integer page, Integer pageSize);


}
