package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategory;
import ar.edu.itba.paw.models.OrderOptions;
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

    @Override
    public Collection<Job> getJobs(String searchQuery, String orderBy, String filterBy) {
        OrderOptions orderOption;
        if (!OrderOptions.contains(orderBy)) {
            orderOption = defaultOrder;
        } else {
            orderOption = valueOf(orderBy);
        }

        JobCategory categoryFilter;
        if (!JobCategory.contains(filterBy)) {
            categoryFilter = null;
        } else {
            categoryFilter = JobCategory.valueOf(filterBy);
        }

        return jobDao.getJobs(searchQuery, orderOption, categoryFilter);
    }

    @Override
    public Collection<OrderOptions> getOrderOptions() {
        return Arrays.asList(values());
    }

}
