package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

import static ar.edu.itba.paw.models.OrderOptions.*;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private JobDao jobDao;

    private final OrderOptions defaultOrder = OrderOptions.valueOf("MOST_POPULAR");

    private final int DEFAULT_ITEMS_PER_PAGE = 6;

    @Override
    public PaginatedSearchResult<Job> getJobsByCategory(String searchBy, String orderBy, String filterBy, int page, int itemsPerPage) {
        OrderOptions queryOrderOption;
        if (!OrderOptions.contains(orderBy)) {
            queryOrderOption = defaultOrder;
            orderBy = defaultOrder.name();
        } else {
            queryOrderOption = valueOf(orderBy);
        }


        JobCategory queryCategoryFilter;
        if (!JobCategory.contains(filterBy)) {
            queryCategoryFilter = null;
            filterBy = "";
        } else {
            queryCategoryFilter = JobCategory.valueOf(filterBy);
        }

        String querySearchBy;
        if (searchBy != null && searchBy.equals("")) {
            querySearchBy = null;
            searchBy = "";
        } else {
            querySearchBy = searchBy;
        }


        if (page < 0) {
            page = 0;
        }

        int totalJobs = jobDao.getJobsCountByCategory(querySearchBy, queryOrderOption, queryCategoryFilter);
        int totalPages = (int) Math.ceil((float) totalJobs / DEFAULT_ITEMS_PER_PAGE);

        if (page >= totalPages) {
            page = totalPages - 1;
        }

        if (itemsPerPage <= 0) {
            itemsPerPage = DEFAULT_ITEMS_PER_PAGE;
        }

        Collection<Job> jobs = jobDao.getJobsByCategory(querySearchBy, queryOrderOption, queryCategoryFilter, page, itemsPerPage);
        return new PaginatedSearchResult<>(orderBy, filterBy, searchBy, page, itemsPerPage, totalJobs, jobs);
    }

    @Override
    public PaginatedSearchResult<Job> getJobsByProviderId(String searchBy, String orderBy, Long providerId, int page, int itemsPerPage) {
        OrderOptions queryOrderOption;
        if (!OrderOptions.contains(orderBy)) {
            queryOrderOption = defaultOrder;
            orderBy = defaultOrder.name();
        } else {
            queryOrderOption = valueOf(orderBy);
        }

        String querySearchBy;
        if (searchBy != null && searchBy.equals("")) {
            querySearchBy = null;
            searchBy = "";
        } else {
            querySearchBy = searchBy;
        }


        if (page < 0) {
            page = 0;
        }

        int totalJobs = jobDao.getJobsCountByProviderId(querySearchBy, queryOrderOption, providerId);
        int totalPages = (int) Math.ceil((float) totalJobs / DEFAULT_ITEMS_PER_PAGE);

        if (page >= totalPages) {
            page = totalPages - 1;
        }

        if (itemsPerPage <= 0) {
            itemsPerPage = DEFAULT_ITEMS_PER_PAGE;
        }

        Collection<Job> jobs = jobDao.getJobsByProviderId(querySearchBy, queryOrderOption, providerId, page, itemsPerPage);
        return new PaginatedSearchResult<>(orderBy, providerId.toString(), searchBy, page, itemsPerPage, totalJobs, jobs);
    }

    @Override
    public Collection<OrderOptions> getOrderOptions() {
        return Arrays.asList(values());
    }

}
