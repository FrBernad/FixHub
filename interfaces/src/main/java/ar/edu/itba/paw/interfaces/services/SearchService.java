package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.OrderOptions;
import ar.edu.itba.paw.models.PaginatedSearchResult;

import java.util.Collection;

public interface SearchService {
    PaginatedSearchResult<Job> getJobsByCategory(String searchBy, String orderBy, String filterBy, int page, int itemsPerPage);

    Collection<OrderOptions> getOrderOptions();
}
