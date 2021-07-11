package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.LocationDao;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.models.job.JobStatus;
import ar.edu.itba.paw.models.location.City;
import ar.edu.itba.paw.models.location.State;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.pagination.OrderOptions;
import ar.edu.itba.paw.models.pagination.PaginatedSearchResult;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static ar.edu.itba.paw.models.pagination.OrderOptions.*;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private JobDao jobDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LocationDao locationDao;

    private final int MAX_SEARCH_LENGTH = 50;

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Override
    public PaginatedSearchResult<Job> getJobsByCategory(String searchBy, String orderBy, String category, String state, String city, int page, int pageSize) {

        //ORDER
        OrderOptions queryOrderOption;
        if (!OrderOptions.contains(orderBy)) {
            LOGGER.debug("Order option {} not valid", orderBy);
            return null;
        }
        LOGGER.debug("Order option is valid");
        queryOrderOption = valueOf(orderBy);

        //CATEGORY
        JobCategory queryCategoryFilter;
        if (category != null) {
            if (!JobCategory.contains(category)) {
                LOGGER.debug("Filter option {} not valid", category);
                return null;
            }

            LOGGER.debug("Filter is valid");
            queryCategoryFilter = JobCategory.valueOf(category);
        } else {
            queryCategoryFilter = null;
        }

        //QUERY
        String querySearchBy;
        if (!searchBy.equals("")) {
            if (searchBy.length() > MAX_SEARCH_LENGTH) {
                LOGGER.debug("Search query is empty");
                return null;
            }
            LOGGER.debug("Search query is valid: {}", searchBy);
            querySearchBy = searchBy;
        } else {
            querySearchBy = null;
        }

        String queryState;
        String queryCity;
        if (state == null) {
            if (city != null && !city.equals("")) {
                LOGGER.debug("State empty but city provided");
                return null;
            }
            queryState = null;
            queryCity = null;
        } else {
            long stateId;
            State stateObj;
            try {
                stateId = Long.parseLong(state);
            } catch (NumberFormatException ignored) {
                LOGGER.debug("Invalid state id");
                return null;
            }
            Optional<State> stateOpt = locationDao.getStateById(stateId);
            if (stateOpt.isPresent()) {
                stateObj = stateOpt.get();
                queryState = String.valueOf(stateId);
            } else {
                LOGGER.debug("Invalid state id");
                return null;
            }
            if (city == null || city.equals("")) {
                LOGGER.debug("City query is empty, setting searchQuery to none");
                queryCity = null;
            } else {
                long cityId;
                try {
                    cityId = Long.parseLong(city);
                } catch (NumberFormatException ignored) {
                    LOGGER.debug("Invalid city id");
                    return null;
                }
                Optional<City> cityOtp = locationDao.getCityByCityIdAndState(cityId, stateObj);
                if (cityOtp.isPresent()) {
                    queryCity = String.valueOf(cityId);
                } else {
                    LOGGER.debug("Invalid city id");
                    return null;
                }
            }
        }

        if (page < 0) {
            LOGGER.debug("Page number {} is invalid", page);
            return null;
        }

        if (pageSize <= 0) {
            LOGGER.debug("Items per page {} is invalid", pageSize);
            return null;
        }

        LOGGER.debug("Retrieving total jobs count");
        final int totalJobs = jobDao.getJobsCountByCategory(querySearchBy, queryCategoryFilter, queryState, queryCity);
        final int totalPages = (int) Math.ceil((float) totalJobs / pageSize);

        if (totalPages == 0 || page >= totalPages) {
            LOGGER.debug("Page number {} is higher than totalPages {} or there is no jobs", page, totalPages);
            return new PaginatedSearchResult<>(0, pageSize, 0, Collections.emptyList());
        }

        LOGGER.debug("Retrieving page {} for jobs by category {}", page, category);
        final Collection<Job> jobs = jobDao.getJobsByCategory(querySearchBy, queryOrderOption, queryCategoryFilter, queryState, queryCity, page, pageSize);

        return new PaginatedSearchResult<>(page, pageSize, totalJobs, jobs);
    }

    @Override
    public PaginatedSearchResult<Job> getJobsByProvider(String searchBy, String orderBy, User provider, int page, int pageSize) {

        //ORDER
        OrderOptions queryOrderOption;
        if (!OrderOptions.contains(orderBy)) {
            LOGGER.debug("Order option {} not valid", orderBy);
            return null;
        }
        LOGGER.debug("Order option is valid");
        queryOrderOption = valueOf(orderBy);

        //QUERY
        String querySearchBy;
        if (!searchBy.equals("")) {
            if (searchBy.length() > MAX_SEARCH_LENGTH) {
                LOGGER.debug("Search query is empty");
                return null;
            }
            LOGGER.debug("Search query is valid: {}", searchBy);
            querySearchBy = searchBy;
        } else {
            querySearchBy = null;
        }

        if (page < 0) {
            LOGGER.debug("Page number {} is invalid", page);
            return null;
        }

        if (pageSize <= 0) {
            LOGGER.debug("Items per page {} is invalid", pageSize);
            return null;
        }

        LOGGER.debug("Retrieving total jobs count");
        final int totalJobs = jobDao.getJobsCountByProvider(provider, querySearchBy);
        final int totalPages = (int) Math.ceil((float) totalJobs / pageSize);

        if (totalPages == 0 || page >= totalPages) {
            LOGGER.debug("Page number {} is higher than totalPages {} or there is no jobs", page, totalPages);
            return new PaginatedSearchResult<>(0, pageSize, 0, Collections.emptyList());
        }

        LOGGER.debug("Retrieving page {} for jobs by provider id {}", page, provider.getId());

        final Collection<Job> jobs = jobDao.getJobsByProvider(querySearchBy, queryOrderOption, provider, page, pageSize);
        return new PaginatedSearchResult<>(page, pageSize, totalJobs, jobs);
    }

    @Override
    public PaginatedSearchResult<JobContact> getClientsByProvider(User provider, String status, int page, int pageSize) {

        //STATUS
        JobStatus statusOption;
        if (status != null) {
            if (!JobStatus.contains(status)) {
                LOGGER.debug("Status option {} not valid", status);
                return null;
            }
            LOGGER.debug("Status option is valid");
            statusOption = JobStatus.valueOf(status);
        } else {
            statusOption = null;
        }

        if (page < 0) {
            LOGGER.debug("Page number {} is invalid", page);
            return null;
        }

        if (pageSize <= 0) {
            LOGGER.debug("Items per page {} is invalid", pageSize);
            return null;
        }

        LOGGER.debug("Retrieving total clients count");
        final int totalContacts = userDao.getClientsCountByProvider(provider, statusOption);
        final int totalPages = (int) Math.ceil((float) totalContacts / pageSize);

        if (totalPages == 0 || page >= totalPages) {
            LOGGER.debug("Page number {} is higher than totalPages {} or there is no jobs", page, totalPages);
            return new PaginatedSearchResult<>(0, pageSize, 0, Collections.emptyList());
        }

        LOGGER.debug("Retrieving page {} for contacts by provider id {}", page, provider.getId());
        final Collection<JobContact> contacts = userDao.getClientsByProvider(provider, statusOption, page, pageSize);

        return new PaginatedSearchResult<>(page, pageSize, totalContacts, contacts);
    }


    @Override
    public PaginatedSearchResult<JobContact> getProvidersByClient(User client, int page, int pageSize) {
        if (page < 0) {
            LOGGER.debug("Page number {} is invalid", page);
            return null;
        }

        if (pageSize <= 0) {
            LOGGER.debug("Items per page {} is invalid", pageSize);
            return null;
        }

        LOGGER.debug("Retrieving total providers count");
        final int totalContacts = userDao.getProvidersCountByClient(client);
        final int totalPages = (int) Math.ceil((float) totalContacts / pageSize);

        if (totalPages == 0 || page >= totalPages) {
            LOGGER.debug("Page number {} is higher than totalPages {} or there is no jobs", page, totalPages);
            return new PaginatedSearchResult<>(0, pageSize, 0, Collections.emptyList());
        }

        LOGGER.debug("Retrieving page {} for providers by client id {}", page, client.getId());
        final Collection<JobContact> contacts = userDao.getProvidersByClient(client, page, pageSize);

        return new PaginatedSearchResult<>(page, pageSize, totalContacts, contacts);
    }

    @Override
    public PaginatedSearchResult<User> getUserFollowers(User user, Integer page, Integer pageSize) {

        if (page < 0) {
            LOGGER.debug("Page number {} is invalid", page);
            return null;
        }

        if (pageSize <= 0) {
            LOGGER.debug("Items per page {} is invalid", pageSize);
            return null;
        }

        LOGGER.debug("Retrieving total followers count");
        final int totalUsers = userDao.getUserFollowersCount(user);
        final int totalPages = (int) Math.ceil((float) totalUsers / pageSize);

        if (totalPages == 0 || page >= totalPages) {
            LOGGER.debug("Page number {} is higher than totalPages {} or there is no jobs", page, totalPages);
            return new PaginatedSearchResult<>(0, pageSize, 0, Collections.emptyList());
        }

        LOGGER.debug("Retrieving page {} for user followers with id {}", page, user.getId());

        final Collection<User> users = userDao.getUserFollowers(user, page, pageSize);

        return new PaginatedSearchResult<>(page, pageSize, totalUsers, users);

    }

    @Override
    public PaginatedSearchResult<User> getUserFollowing(User user, Integer page, Integer pageSize) {

        if (page < 0) {
            LOGGER.debug("Page number {} is invalid", page);
            return null;
        }

        if (pageSize <= 0) {
            LOGGER.debug("Items per page {} is invalid", pageSize);
            return null;
        }

        LOGGER.debug("Retrieving total following count");
        final int totalUsers = userDao.getUserFollowingCount(user);
        final int totalPages = (int) Math.ceil((float) totalUsers / pageSize);

        if (totalPages == 0 || page >= totalPages) {
            LOGGER.debug("Page number {} is higher than totalPages {} or there is no jobs", page, totalPages);
            return new PaginatedSearchResult<>(0, pageSize, 0, Collections.emptyList());
        }

        LOGGER.debug("Retrieving page {} for user following with id {}", page, user.getId());
        final Collection<User> users = userDao.getUserFollowings(user, page, pageSize);
        return new PaginatedSearchResult<>(page, pageSize, totalUsers, users);
    }

}
