package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.user.User;

import java.util.Collection;

public interface SearchService {
    PaginatedSearchResult<Job> getJobsByCategory(String searchBy, String orderBy, String category, String state, String city, int page, int itemsPerPage);

    PaginatedSearchResult<Job> getJobsByProvider(String searchBy, String orderBy, User provider, int page, int itemsPerPage);

    PaginatedSearchResult<JobContact> getClientsByProvider(User provider, int page, int itemsPerPage);

    PaginatedSearchResult<JobContact> getProvidersByClient(User client, int page, int itemsPerPage);

    Collection<OrderOptions> getOrderOptions();

    PaginatedSearchResult<User> getUserFollowers(Long userId, Integer page, Integer itemsPerPage);

    PaginatedSearchResult<User> getUserFollowing(Long userId, Integer page, Integer itemsPerPage);

}
