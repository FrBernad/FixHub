package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.pagination.OrderOptions;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JobDaoImpl implements JobDao {

    @PersistenceContext
    private EntityManager em;

    private static final Collection<JobCategory> categories = Collections.unmodifiableList(Arrays.asList(JobCategory.values().clone()));

    private static final Map<OrderOptions, String> SQL_ORDER_OPTIONS = new EnumMap<>(OrderOptions.class);
    private static final Map<OrderOptions, String> HQL_ORDER_OPTIONS = new EnumMap<>(OrderOptions.class);

    private static final Logger LOGGER = LoggerFactory.getLogger(JobDaoImpl.class);

    static {
        SQL_ORDER_OPTIONS.put(OrderOptions.MOST_POPULAR, "avg_rating desc, total_ratings desc, j_id desc");
        SQL_ORDER_OPTIONS.put(OrderOptions.LESS_POPULAR, "avg_rating asc, total_ratings desc, j_id desc");
        SQL_ORDER_OPTIONS.put(OrderOptions.HIGHER_PRICE, "j_price desc, total_ratings desc, j_id desc");
        SQL_ORDER_OPTIONS.put(OrderOptions.LOWER_PRICE, "j_price asc, total_ratings desc, j_id desc");
    }

    static {
        HQL_ORDER_OPTIONS.put(OrderOptions.MOST_POPULAR, "avg_rating desc, total_ratings desc, job.id desc");
        HQL_ORDER_OPTIONS.put(OrderOptions.LESS_POPULAR, "avg_rating asc, total_ratings desc, job.id desc");
        HQL_ORDER_OPTIONS.put(OrderOptions.HIGHER_PRICE, "job.price desc, total_ratings desc, job.id desc");
        HQL_ORDER_OPTIONS.put(OrderOptions.LOWER_PRICE, "job.price asc, total_ratings desc, job.id desc");
    }

    @Override
    public Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, boolean paused, User provider, Set<Image> images) {
        final int averageRating = 0;
        final Long totalRatings = 0L;
        Job job = new Job(description, jobProvided, averageRating, totalRatings, category, price, paused, provider, images);
        em.persist(job);
        LOGGER.info("Created job with id {}", job.getId());
        return job;
    }

    @Override
    public Optional<Job> getJobById(long id) {
        final String hqlQuery =
            " SELECT job, count(reviews) as total_ratings, coalesce(avg(reviews.rating),0) as avg_rating " +
                " FROM Job job " +
                " LEFT OUTER JOIN " +
                " job.reviews reviews WHERE job.id = :id " +
                " GROUP BY job.id ";

        final Collection<Object[]> hqlQueryResult = em.createQuery(hqlQuery, Object[].class)
            .setParameter("id", id)
            .getResultList();

        LOGGER.info("Retrieved job with id {}", id);

        return hqlQueryResult
            .stream()
            .map(objArray -> {
                final Job job = (Job) objArray[0];
                job.setTotalRatings((Long) objArray[1]);
                job.setAverageRating(((Double) objArray[2]).intValue());
                return job;
            }).findFirst();
    }

    @Override
    public Optional<JobContact> getContactById(long id) {
        LOGGER.debug("Retrieving contact with id {}", id);
        return Optional.ofNullable(em.find(JobContact.class, id));
    }

    public Collection<JobCategory> getJobsCategories() {
        LOGGER.debug("Retrieving job categories");
        return categories;
    }

    @Override
    public Collection<Job> getJobsByProvider(String searchBy, OrderOptions orderOption, User provider, int page, int itemsPerPage) {
        final List<Object> variables = new LinkedList<>();

        final String filterQuery = " WHERE j_provider_id = ? ";
        variables.add(provider.getId());

        final String searchQuery = getSearchQuery(searchBy, variables);

        final String orderQuery = getOrderQuery(orderOption);

        final String offsetAndLimitQuery = getOffsetAndLimitQuery(page, itemsPerPage, variables);

        final String filteredIdsSelectQuery = String.format(
            " select j_id from " +
                " (select * from JOBS j JOIN USERS u ON j_provider_id = u_id %s ) w0 " +
                " JOIN " +
                " (SELECT distinct pc_provider_id from cities join provider_cities on c_id = pc_city_id ) as w1 " +
                " ON w0.u_location_id=w1.pc_provider_id " +
                " JOIN " +
                " (select j_id as job_id, count(r_job_id) as total_ratings,coalesce(avg(r_rating), 0) as avg_rating " +
                " FROM jobs LEFT OUTER JOIN reviews on j_id = r_job_id group by j_id) jobsStats " +
                " on job_id=j_id %s %s %s "
            , filterQuery, searchQuery, orderQuery, offsetAndLimitQuery);

        Query filteredIdsSelectNativeQuery = em.createNativeQuery(filteredIdsSelectQuery);

        setQueryVariables(filteredIdsSelectNativeQuery, variables);

        @SuppressWarnings("unchecked") final List<Long> filteredIds = ((List<Number>) filteredIdsSelectNativeQuery.getResultList()).stream().map(Number::longValue).collect(Collectors.toList());

        if (filteredIds.isEmpty()) {
            return Collections.emptyList();
        }

        final String hqlOrderQuery = getHQLOrderQuery(orderOption);

        final String hqlQuery = String.format(
            " SELECT job, count(reviews) as total_ratings, coalesce(avg(reviews.rating),0) as avg_rating " +
                " FROM Job job " +
                " LEFT OUTER JOIN " +
                " job.reviews reviews WHERE job.id IN :filteredIds " +
                " GROUP BY job.id %s ", hqlOrderQuery);

        final Collection<Object[]> hqlQueryResult = em.createQuery(hqlQuery, Object[].class)
            .setParameter("filteredIds", filteredIds)
            .getResultList();

        LOGGER.debug("Retrieved jobs by provider with id {}", provider.getId());

        return hqlQueryResult
            .stream()
            .map(objArray -> {
                final Job job = (Job) objArray[0];
                job.setTotalRatings((Long) objArray[1]);
                job.setAverageRating(((Double) objArray[2]).intValue());
                return job;
            }).collect(Collectors.toList());

    }


    @Override
    public Integer getJobsCountByProvider(User provider, String searchBy) {

        final List<Object> variables = new LinkedList<>();

        final String filterQuery = " WHERE j_provider_id = ? ";
        variables.add(provider.getId());

        final String searchQuery = getSearchQuery(searchBy, variables);

        final String query =
            " select count(*) total " +
                " from (select * from JOBS j JOIN USERS u ON j_provider_id = u_id" + filterQuery + " ) as aux" + searchQuery;

        LOGGER.debug("Executing query: {}", query);

        Query nativeQuery = em.createNativeQuery(query);

        setQueryVariables(nativeQuery, variables);

        LOGGER.debug("Retrieved jobs count by provider with id {}", provider.getId());

        return ((BigInteger) nativeQuery.getSingleResult()).intValue();
    }

    @Override
    public Collection<Job> getJobsByCategory(String searchBy, OrderOptions orderOption, JobCategory category, String state, String city, int page, int itemsPerPage) {

        final List<Object> variables = new LinkedList<>();

        final String filterQuery = getCategoryFilterQuery(category, variables);

        final String orderQuery = getOrderQuery(orderOption);

        final String stateAndCityQuery = getStateAndCityQuery(state, city, variables);

        final String searchQuery = getSearchQuery(searchBy, variables);

        final String offsetAndLimitQuery = getOffsetAndLimitQuery(page, itemsPerPage, variables);

        final String filteredIdsSelectQuery = String.format(
            " select j_id from " +
                " (select * from JOBS j JOIN USERS u ON j_provider_id = u_id %s ) w0 " +
                " JOIN " +
                " (SELECT distinct pc_provider_id from cities join provider_cities on c_id = pc_city_id %s ) as w1 " +
                " ON w0.u_location_id=w1.pc_provider_id " +
                " JOIN " +
                " (select j_id as job_id, count(r_job_id) as total_ratings,coalesce(avg(r_rating), 0) as avg_rating " +
                " FROM jobs LEFT OUTER JOIN reviews on j_id = r_job_id group by j_id) jobsStats " +
                " on job_id=j_id %s %s %s ",
            filterQuery, stateAndCityQuery, searchQuery, orderQuery, offsetAndLimitQuery);

        Query filteredIdsSelectNativeQuery = em.createNativeQuery(filteredIdsSelectQuery);

        setQueryVariables(filteredIdsSelectNativeQuery, variables);

        @SuppressWarnings("unchecked") final List<Long> filteredIds = ((List<Number>) filteredIdsSelectNativeQuery.getResultList()).stream().map(Number::longValue).collect(Collectors.toList());

        if (filteredIds.isEmpty())
            return Collections.emptyList();

        final String hqlOrderQuery = getHQLOrderQuery(orderOption);
        final String hqlQuery = String.format(
            " SELECT job, count(reviews) as total_ratings, coalesce(avg(reviews.rating),0) as avg_rating " +
                " FROM Job job " +
                " LEFT OUTER JOIN " +
                " job.reviews reviews WHERE job.id IN :filteredIds " +
                " GROUP BY job.id %s ", hqlOrderQuery);

        final Collection<Object[]> hqlQueryResult = em.createQuery(hqlQuery, Object[].class)
            .setParameter("filteredIds", filteredIds)
            .getResultList();

        LOGGER.debug("Retrieved jobs by category {}", category.toString());

        return hqlQueryResult
            .stream()
            .map(objArray -> {
                final Job job = (Job) objArray[0];
                job.setTotalRatings((Long) objArray[1]);
                job.setAverageRating(((Double) objArray[2]).intValue());
                return job;
            }).collect(Collectors.toList());
    }


    @Override
    public Integer getJobsCountByCategory(String searchBy, JobCategory category, String state, String city) {
        List<Object> variables = new LinkedList<>();

        final String filterQuery = getCategoryFilterQuery(category, variables);

        final String stateAndCityQuery = getStateAndCityQuery(state, city, variables);

        final String searchQuery = getSearchQuery(searchBy, variables);

        final String query = String.format("select count(distinct j_id) total from " +
                "(select * from ( ( select * from JOBS j JOIN USERS u ON j_provider_id = u_id %s ) as aux0 " +
                " JOIN (SELECT distinct pc_provider_id from " +
                " cities join provider_cities on c_id = pc_city_id %s) " +
                " as aux1 ON aux0.u_location_id=aux1.pc_provider_id ) as aux2 %s) as aux3 ",
            filterQuery, stateAndCityQuery, searchQuery);

        Query nativeQuery = em.createNativeQuery(query);

        setQueryVariables(nativeQuery, variables);

        LOGGER.debug("Retrieved jobs count by category {}", category.toString());

        return ((BigInteger) nativeQuery.getSingleResult()).intValue();
    }

    private String getOrderQuery(OrderOptions orderOption) {
        return String.format(" ORDER BY %s ", SQL_ORDER_OPTIONS.get(orderOption));
    }

    private String getHQLOrderQuery(OrderOptions orderOption) {
        return String.format(" ORDER BY %s ", HQL_ORDER_OPTIONS.get(orderOption));
    }

    private String getCategoryFilterQuery(JobCategory category, List<Object> variables) {
        final StringBuilder filterQuery = new StringBuilder();
        if (category != null) {
            variables.add(category.name());
            filterQuery.append(" WHERE j_category = ? ");
        }
        return filterQuery.toString();
    }

    private String getStateAndCityQuery(String state, String city, List<Object> variables) {
        final StringBuilder stateAndCityQuery = new StringBuilder();
        if (state != null) {
            stateAndCityQuery.append(" WHERE c_state_id = ? ");
            variables.add(Integer.valueOf(state));
        }
        if (city != null) {
            stateAndCityQuery.append(" AND c_id = ? ");
            variables.add(Integer.valueOf(city));
        }
        return stateAndCityQuery.toString();
    }

    private String getSearchQuery(String searchBy, List<Object> variables) {
        final StringBuilder searchQuery = new StringBuilder();
        if (searchBy != null) {
            searchBy = String.format("%%%s%%", searchBy.replace("%", "\\%").replace("_", "\\_").toLowerCase());
            variables.add(searchBy);
            variables.add(searchBy);
            variables.add(searchBy);
            searchQuery.append(" WHERE LOWER(j_description) LIKE ? OR LOWER(j_job_provided) LIKE ? OR LOWER(u_name) LIKE ? ");
        }
        return searchQuery.toString();
    }

    private String getOffsetAndLimitQuery(int page, int itemsPerPage, List<Object> variables) {
        final StringBuilder offsetAndLimitQuery = new StringBuilder();
        if (page > 0) {
            offsetAndLimitQuery.append(" OFFSET ? ");
            variables.add(page * itemsPerPage);
        }
        if (itemsPerPage > 0) {
            offsetAndLimitQuery.append(" LIMIT ? ");
            variables.add(itemsPerPage);
        }
        return offsetAndLimitQuery.toString();
    }

    private void setQueryVariables(Query query, Collection<Object> variables) {
        int i = 1;
        for (Object variable : variables) {
            query.setParameter(i, variable);
            i++;
        }
    }


}
