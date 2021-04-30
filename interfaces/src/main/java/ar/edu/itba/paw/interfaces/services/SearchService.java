package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobContact;
import ar.edu.itba.paw.models.OrderOptions;
import ar.edu.itba.paw.models.PaginatedSearchResult;

import java.util.Collection;

public interface SearchService {
    PaginatedSearchResult<Job> getJobsByCategory(String searchBy, String orderBy, String category, String state, String city, int page, int itemsPerPage);

    PaginatedSearchResult<Job> getJobsByProviderId(String searchBy, String orderBy, Long providerId, int page, int itemsPerPage);

    PaginatedSearchResult<JobContact> getClientsByProviderId(Long providerId, int page, int itemsPerPage);

    PaginatedSearchResult<JobContact> getProvidersByClientId(Long clientId, int page, int itemsPerPage);

    Collection<OrderOptions> getOrderOptions();

}
