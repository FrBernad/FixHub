package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.models.JobCategory;
import ar.edu.itba.paw.models.OrderOptions;
import ar.edu.itba.paw.models.SearchResult;
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
    public SearchResult getJobsByCategory(String searchBy, String orderBy, String filterBy) {
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
        if (searchBy!=null && searchBy.equals("")) {
            querySearchBy = null;
            searchBy = "";
        }else{
            querySearchBy = searchBy;
        }

        return new SearchResult(orderBy,filterBy,searchBy,jobDao.getJobsByCategory(querySearchBy, queryOrderOption, queryCategoryFilter));
    }

    @Override
    public Collection<OrderOptions> getOrderOptions() {
        return Arrays.asList(values());
    }

}
