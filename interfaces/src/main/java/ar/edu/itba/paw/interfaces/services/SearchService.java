package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.OrderOptions;

import java.util.Collection;

public interface SearchService {
        Collection<Job> getJobs(String searchQuery, String orderBy, String filterBy);

        Collection<OrderOptions> getOrderOptions();
}
