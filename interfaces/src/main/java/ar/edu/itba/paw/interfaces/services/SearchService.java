package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.OrderOptions;
import ar.edu.itba.paw.models.SearchResult;

import java.util.Collection;

public interface SearchService {
        SearchResult getJobsByCategory(String searchQuery, String orderBy, String filterBy);

        Collection<OrderOptions> getOrderOptions();
}
