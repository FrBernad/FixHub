package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

import static ar.edu.itba.paw.models.OrderOptions.*;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private JobDao jobDao;

    @Autowired
    private UserDao userDao;

    private final OrderOptions DEFAULT_ORDER = OrderOptions.valueOf("MOST_POPULAR");

    private final int DEFAULT_ITEMS_PER_PAGE = 6;

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Override
    public PaginatedSearchResult<Job> getJobsByCategory(String searchBy, String orderBy, String filterBy, int page, int itemsPerPage) {
        OrderOptions queryOrderOption;
        if (!OrderOptions.contains(orderBy)) {
            LOGGER.debug("Order option {} not valid, setting default order", orderBy);
            queryOrderOption = DEFAULT_ORDER;
            orderBy = DEFAULT_ORDER.name();
        } else {
            LOGGER.debug("Order option is valid");
            queryOrderOption = valueOf(orderBy);
        }


        JobCategory queryCategoryFilter;
        if (!JobCategory.contains(filterBy)) {
            LOGGER.debug("Filter option {} not valid, defaulting filter to none", filterBy);
            queryCategoryFilter = null;
            filterBy = "";
        } else {
            LOGGER.debug("Filter is valid");
            queryCategoryFilter = JobCategory.valueOf(filterBy);
        }

        String querySearchBy;
        if (searchBy != null && searchBy.equals("")) {
            LOGGER.debug("Search query is empty, setting searchQuery to none");
            querySearchBy = null;
            searchBy = "";
        } else {
            LOGGER.debug("Search query is valid: {}", searchBy);
            querySearchBy = searchBy;
        }

        if (page < 0) {
            LOGGER.debug("Page number {} is invalid, defaulting to 0", page);
            page = 0;
        }

        if (itemsPerPage <= 0) {
            LOGGER.debug("Items per page {} is invalid, defaulting to DEFAULT {}", itemsPerPage, DEFAULT_ITEMS_PER_PAGE);
            itemsPerPage = DEFAULT_ITEMS_PER_PAGE;
        }

        LOGGER.debug("Retrieving total jobs count");
        int totalJobs = jobDao.getJobsCountByCategory(querySearchBy, queryCategoryFilter);
        int totalPages = (int) Math.ceil((float) totalJobs / itemsPerPage);

        if (page >= totalPages) {
            LOGGER.debug("Page number {} is higher than totalPages {}, defaulting to {}", page, totalPages, totalPages - 1);
            page = totalPages - 1;
        }

        LOGGER.debug("Retrieving page {} for jobs by category {}", page, filterBy);
        Collection<Job> jobs = jobDao.getJobsByCategory(querySearchBy, queryOrderOption, queryCategoryFilter, page, itemsPerPage);
        return new PaginatedSearchResult<>(orderBy, filterBy, searchBy, page, itemsPerPage, totalJobs, jobs);
    }

    @Override
    public PaginatedSearchResult<Job> getJobsByProviderId(String searchBy, String orderBy, Long providerId, int page, int itemsPerPage) {
        OrderOptions queryOrderOption;
        if (!OrderOptions.contains(orderBy)) {
            LOGGER.debug("Order option {} not valid, setting default order", orderBy);
            queryOrderOption = DEFAULT_ORDER;
            orderBy = DEFAULT_ORDER.name();
        } else {
            LOGGER.debug("Order option is valid");
            queryOrderOption = valueOf(orderBy);
        }

        String querySearchBy;
        if (searchBy != null && searchBy.equals("")) {
            LOGGER.debug("Search query is empty, setting searchQuery to none");
            querySearchBy = null;
            searchBy = "";
        } else {
            LOGGER.debug("Search query is valid: {}", searchBy);
            querySearchBy = searchBy;
        }


        if (page < 0) {
            LOGGER.debug("Page number {} is invalid, defaulting to 0", page);
            page = 0;
        }

        if (itemsPerPage <= 0) {
            LOGGER.debug("Items per page {} is invalid, defaulting to DEFAULT {}", itemsPerPage, DEFAULT_ITEMS_PER_PAGE);
            itemsPerPage = DEFAULT_ITEMS_PER_PAGE;
        }

        LOGGER.debug("Retrieving total jobs count");
        int totalJobs = jobDao.getJobsCountByProviderId(querySearchBy, providerId);
        int totalPages = (int) Math.ceil((float) totalJobs / itemsPerPage);

        if (page >= totalPages) {
            LOGGER.debug("Page number {} is higher than totalPages {}, defaulting to {}", page, totalPages, totalPages - 1);
            page = totalPages - 1;
        }

        LOGGER.debug("Retrieving page {} for jobs by provider id {}", page, providerId);
        Collection<Job> jobs = jobDao.getJobsByProviderId(querySearchBy, queryOrderOption, providerId, page, itemsPerPage);
        return new PaginatedSearchResult<>(orderBy, providerId.toString(), searchBy, page, itemsPerPage, totalJobs, jobs);
    }

    @Override
    public PaginatedSearchResult<JobContact> getClientsByProviderId(Long providerId, int page, int itemsPerPage) {

        if (page < 0) {
            LOGGER.debug("Page number {} is invalid, defaulting to 0", page);
            page = 0;
        }

        if (itemsPerPage <= 0) {
            LOGGER.debug("Items per page {} is invalid, defaulting to DEFAULT {}", itemsPerPage, DEFAULT_ITEMS_PER_PAGE);
            itemsPerPage = DEFAULT_ITEMS_PER_PAGE;
        }

        LOGGER.debug("Retrieving total clients count");
        int totalJobs = userDao.getClientsCountByProviderId(providerId);
        int totalPages = (int) Math.ceil((float) totalJobs / itemsPerPage);

        if (page >= totalPages) {
            LOGGER.debug("Page number {} is higher than totalPages {}, defaulting to {}", page, totalPages, totalPages - 1);
            page = totalPages - 1;
        }

        LOGGER.debug("Retrieving page {} for contacts by provider id {}", page, providerId);
        Collection<JobContact> contacts = userDao.getClientsByProviderId(providerId, page, itemsPerPage);
        return new PaginatedSearchResult<>("", "", "", page, itemsPerPage, totalJobs, contacts);
    }

    @Override
    public PaginatedSearchResult<JobContact> getProvidersByClientId(Long clientId, int page, int itemsPerPage) {

        if (page < 0) {
            LOGGER.debug("Page number {} is invalid, defaulting to 0", page);
            page = 0;
        }

        if (itemsPerPage <= 0) {
            LOGGER.debug("Items per page {} is invalid, defaulting to DEFAULT {}", itemsPerPage, DEFAULT_ITEMS_PER_PAGE);
            itemsPerPage = DEFAULT_ITEMS_PER_PAGE;
        }

        LOGGER.debug("Retrieving total providers count");
        int totalJobs = userDao.getProvidersCountByClientId(clientId);
        int totalPages = (int) Math.ceil((float) totalJobs / itemsPerPage);

        if (page >= totalPages) {
            LOGGER.debug("Page number {} is higher than totalPages {}, defaulting to {}", page, totalPages, totalPages - 1);
            page = totalPages - 1;
        }

        LOGGER.debug("Retrieving page {} for providers by client id {}", page, clientId);
        Collection<JobContact> contacts = userDao.getProvidersByClientId(clientId, page, itemsPerPage);
        return new PaginatedSearchResult<>("", "", "", page, itemsPerPage, totalJobs, contacts);
    }


    @Override
    public Collection<OrderOptions> getOrderOptions() {
        return Arrays.asList(values());
    }


}
