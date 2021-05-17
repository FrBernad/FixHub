package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.job.Review;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.provider.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JobDaoImpl implements JobDao {

    @PersistenceContext
    private EntityManager em;

    private static final Collection<JobCategory> categories = Collections.unmodifiableList(Arrays.asList(JobCategory.values().clone()));

    private static final Map<OrderOptions, String> ORDER_OPTIONS = new EnumMap<>(OrderOptions.class);

    private static final Logger LOGGER = LoggerFactory.getLogger(JobDaoImpl.class);

    static {
        ORDER_OPTIONS.put(OrderOptions.MOST_POPULAR, "avg_rating desc, total_ratings desc, j_id desc");
        ORDER_OPTIONS.put(OrderOptions.LESS_POPULAR, "avg_rating asc, total_ratings desc, j_id desc");
        ORDER_OPTIONS.put(OrderOptions.HIGHER_PRICE, "j_price desc, total_ratings desc, j_id desc");
        ORDER_OPTIONS.put(OrderOptions.LOWER_PRICE, "j_price asc, total_ratings desc, j_id desc");
    }

   @Override
    public Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, boolean paused, Provider provider, Set<Image> images) {
        final int averageRating = 0;
        final Long totalRatings = 0L;
        Job job = new Job(description,jobProvided,averageRating,totalRatings,category,price,paused,provider,images);
        em.persist(job);
        LOGGER.debug("Created job with id {}", job.getId());
        return job;
    }

    @Override
    public Optional<Job> getJobById(long id) {
        final TypedQuery<Job> query = em.createQuery("from Job as j where j.id = :id",Job.class);
        query.setParameter("id",id);
        return query.getResultList().stream().findFirst();
    }

    public Collection<JobCategory> getJobsCategories() {
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
                " ON w0.j_provider_id=w1.pl_user_id " +
                " JOIN " +
                " (select j_id as job_id, count(r_job_id) as total_ratings,coalesce(avg(r_rating), 0) as avg_rating " +
                " FROM jobs LEFT OUTER JOIN reviews on j_id = r_job_id group by j_id) jobsStats " +
                " on job_id=j_id %s %s %s "
            , filterQuery, searchQuery, orderQuery, offsetAndLimitQuery);

        Query filteredIdsSelectNativeQuery = em.createNativeQuery(filteredIdsSelectQuery);

        setQueryVariables(filteredIdsSelectNativeQuery, variables);

        @SuppressWarnings("unchecked") final List<Long> filteredIds = ((List<Number>) filteredIdsSelectNativeQuery.getResultList()).stream().map(Number::longValue).collect(Collectors.toList());

        if (filteredIds.isEmpty())
            return Collections.emptyList();

        return em.createQuery("from Job where id IN :filteredIds", Job.class)
            .setParameter("filteredIds", filteredIds)
            .getResultList();

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
                " ON w0.j_provider_id=w1.pc_provider_id " +
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

        return em.createQuery("from Job where id IN :filteredIds", Job.class)
            .setParameter("filteredIds", filteredIds)
            .getResultList();
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
                " as aux1 ON aux0.j_provider_id=aux1.pc_provider_id ) as aux2 %s) as aux3 ",
            filterQuery, stateAndCityQuery, searchQuery);

        Query nativeQuery = em.createNativeQuery(query);

        setQueryVariables(nativeQuery, variables);

        return ((BigInteger) nativeQuery.getSingleResult()).intValue();
    }

    private String getOrderQuery(OrderOptions orderOption) {
        return String.format(" ORDER BY %s ", ORDER_OPTIONS.get(orderOption));
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


//
//    @Override
//    public Integer getJobsCountByProviderId(String searchBy, Long providerId) {
//        final List<Object> variables = new LinkedList<>();
//
//        final String filterQuery = " WHERE j_provider_id = ? ";
//        variables.add(providerId);
//
//        final String searchQuery = getSearchQuery(searchBy, variables);
//
//        final String query = " select count(*) total from (select * from JOBS j JOIN USERS u ON j_provider_id = u_id" + filterQuery + " ) as aux" + searchQuery;
//        LOGGER.debug("Executing query: {}", query);
//
//        return jdbcTemplate.query(query, variables.toArray(), (rs, rowNum) -> rs.getInt("total")).stream().findFirst().orElse(0);
//
//    }
//

//
//
//    @Override
//    public Optional<Job> getJobById(long id) {
//        final List<Object> variables = new LinkedList<>();
//        variables.add(id);
//
//        final String filterQuery = " WHERE j_id = ? ";
//
//        final String JOBS_WHERE_ID_QUERY = String.format(
//            " where j_id in ( " +
//                " select j_id from " +
//                " (select * from JOBS j JOIN USERS u ON j_provider_id = u_id %s ) w0 " +
//                " JOIN " +
//                " (SELECT distinct pl_user_id from cities join provider_location on c_id = pl_city_id ) as w1 " +
//                " ON w0.j_provider_id=w1.pl_user_id " +
//                " JOIN " +
//                " (select j_id as job_id, count(r_job_id) as total_ratings,coalesce(avg(r_rating), 0) as avg_rating " +
//                " FROM jobs LEFT OUTER JOIN reviews on j_id = r_job_id group by j_id) jobsStats " +
//                " on job_id=j_id ) ",
//            filterQuery
//        );
//
//        final String query = String.format(
//            " select * from " +
//                " (select * from JOBS %s ) selectedJobs " +
//                " JOIN " +
//                " USERS on selectedJobs.j_provider_id=users.u_id " +
//                " JOIN " +
//                " ROLES on users.u_id = roles.r_user_id " +
//                " JOIN " +
//                " (select j_id as job_id, count(r_job_id) as total_ratings,coalesce(avg(r_rating), 0) as avg_rating " +
//                " FROM jobs LEFT OUTER JOIN reviews on j_id = r_job_id group by j_id) as jobsStats " +
//                " on j_id = jobsStats.job_id " +
//                " LEFT OUTER JOIN " +
//                " (select ji_job_id, ji_image_id from job_image) jobImages " +
//                " on j_id = jobImages.ji_job_id ", JOBS_WHERE_ID_QUERY
//        );
//
//        return executeQuery(query, variables).stream().findFirst();
//    }
//

//
//    @Override
//    public Collection<Job> getJobsByProviderId(String searchBy, OrderOptions orderOption, Long providerId, int page, int itemsPerPage) {
//        final List<Object> variables = new LinkedList<>();
//
//        final String filterQuery = " WHERE j_provider_id = ? ";
//        variables.add(providerId);
//
//        final String searchQuery = getSearchQuery(searchBy, variables);
//
//        final String orderQuery = getOrderQuery(orderOption);
//
//        final String offsetAndLimitQuery = getOffsetAndLimitQuery(page, itemsPerPage, variables);
//
//        final String JOBS_WHERE_ID_QUERY = String.format(
//            " where j_id in ( " +
//                " select j_id from " +
//                " (select * from JOBS j JOIN USERS u ON j_provider_id = u_id %s ) w0 " +
//                " JOIN " +
//                " (SELECT distinct pl_user_id from cities join provider_location on c_id = pl_city_id ) as w1 " +
//                " ON w0.j_provider_id=w1.pl_user_id " +
//                " JOIN " +
//                " (select j_id as job_id, count(r_job_id) as total_ratings,coalesce(avg(r_rating), 0) as avg_rating " +
//                " FROM jobs LEFT OUTER JOIN reviews on j_id = r_job_id group by j_id) jobsStats " +
//                " on job_id=j_id %s %s %s ) "
//            , filterQuery, searchQuery, orderQuery, offsetAndLimitQuery);
//
//        final String query = String.format(
//            " select * from " +
//                " (select * from JOBS %s ) selectedJobs " +
//                " JOIN " +
//                " USERS on selectedJobs.j_provider_id=users.u_id " +
//                " JOIN " +
//                " ROLES on users.u_id = roles.r_user_id " +
//                " JOIN " +
//                " (select j_id as job_id, count(r_job_id) as total_ratings,coalesce(avg(r_rating), 0) as avg_rating " +
//                " FROM jobs LEFT OUTER JOIN reviews on j_id = r_job_id group by j_id) as jobsStats " +
//                " on j_id = jobsStats.job_id " +
//                " LEFT OUTER JOIN " +
//                " (select ji_job_id, ji_image_id from job_image) jobImages " +
//                " on j_id = jobImages.ji_job_id %s "
//                ,JOBS_WHERE_ID_QUERY,orderQuery);
//
//        return executeQuery(query, variables);
//    }

//    private Collection<Job> executeQuery(String query, List<Object> variables) {
//        LOGGER.debug("Executing query: {}", query);
//
//        if (variables != null) {
//            return jdbcTemplate.query(query, variables.toArray(), JOB_RS_EXTRACTOR);
//        }
//
//        return jdbcTemplate.query(query, JOB_RS_EXTRACTOR);
//    }

}
