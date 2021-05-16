package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
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

    static {
        ORDER_OPTIONS.put(OrderOptions.MOST_POPULAR, "avg_rating desc, total_ratings desc, j_id desc");
        ORDER_OPTIONS.put(OrderOptions.LESS_POPULAR, "avg_rating asc, total_ratings desc, j_id desc");
        ORDER_OPTIONS.put(OrderOptions.HIGHER_PRICE, "j_price desc, total_ratings desc, j_id desc");
        ORDER_OPTIONS.put(OrderOptions.LOWER_PRICE, "j_price asc, total_ratings desc, j_id desc");
    }

    @Override
    public Optional<Job> getJobById(long id) {
        return Optional.empty();
    }

    @Override
    public Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, boolean paused, User user, List<Image> images) {
        return null;
    }

    public Collection<JobCategory> getJobsCategories() {
        return categories;
    }

    @Override
    public Collection<Job> getJobsByProviderId(String searchBy, OrderOptions orderOption, Long providerId, int page, int itemsPerPage) {
        return null;
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

        @SuppressWarnings("unchecked")
        final List<Long> filteredIds = ((List<Number>) filteredIdsSelectNativeQuery.getResultList()).stream().map(Number::longValue).collect(Collectors.toList());

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

    @Override
    public Integer getJobsCountByProviderId(String searchBy, Long providerId) {
        return null;
    }

    @Override
    public void updateJob(String jobProvided, String description, BigDecimal price, boolean paused, List<Image> images, long jobId, List<Long> imagesIdDeleted) {

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
//    static {
//        ORDER_OPTIONS.put(OrderOptions.MOST_POPULAR, "avg_rating desc, total_ratings desc, j_id desc");
//        ORDER_OPTIONS.put(OrderOptions.LESS_POPULAR, "avg_rating asc, total_ratings desc, j_id desc");
//        ORDER_OPTIONS.put(OrderOptions.HIGHER_PRICE, "j_price desc, total_ratings desc, j_id desc");
//        ORDER_OPTIONS.put(OrderOptions.LOWER_PRICE, "j_price asc, total_ratings desc, j_id desc");
//    }
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(JobDaoImpl.class);
//
//    private static final ResultSetExtractor<Collection<Job>> JOB_RS_EXTRACTOR = (rs) ->
//    {
//        final Map<Long, Job> jobsMap = new LinkedHashMap<>();
//        long jobId;
//
//        while (rs.next()) {
//
//            jobId = rs.getLong("j_id");
//
//            if (!jobsMap.containsKey(jobId)) {
//                jobsMap.put(jobId,
//                    new Job(rs.getString("j_description"),
//                        rs.getString("j_job_provided"),
//                        rs.getInt("avg_rating"),
//                        rs.getInt("total_ratings"),
//                        JobCategory.valueOf(rs.getString("j_category")),
//                        rs.getLong("j_id"),
//                        rs.getBigDecimal("j_price"),
//                        rs.getBoolean("j_paused"),
//                        new User(rs.getLong("j_provider_id"),
//                            rs.getString("u_password"),
//                            rs.getString("u_name"),
//                            rs.getString("u_surname"),
//                            rs.getString("u_email"),
//                            rs.getString("u_phone_number"),
//                            rs.getString("u_state"),
//                            rs.getString("u_city"),
//                            new HashSet<>(),
//                            rs.getLong("u_profile_picture"),
//                            rs.getLong("u_cover_picture")),
//                        new HashSet<>()));
//            }
//
//            final Job job = jobsMap.get(jobId);
//
//            job.getProvider().addRole(Roles.valueOf(rs.getString("r_role")));
//
//            final Long imageId = rs.getLong("ji_image_id");
//            if (imageId != 0)
//                job.addImageId(imageId);
//
//        }
//
//        return jobsMap.values();
//    };
//
//    @Autowired
//    public JobDaoImpl(final DataSource ds) {
//        jdbcTemplate = new JdbcTemplate(ds);
//        jobSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("JOBS").usingGeneratedKeyColumns("j_id");
//        jobImagesSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("JOB_IMAGE");
//    }
//
//    @Transactional
//    @Override
//    public Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, boolean paused, User provider, List<Image> images) {
//
//        final Map<String, Object> map = new HashMap<>();
//        final int averageRating = 0, totalRatings = 0;
//        map.put("j_provider_id", provider.getId());
//        map.put("j_category", category);
//        map.put("j_description", description);
//        map.put("j_job_provided", jobProvided);
//        map.put("j_price", price);
//        map.put("j_paused", paused);
//        final Number id = jobSimpleJdbcInsert.executeAndReturnKey(map);
//        LOGGER.debug("Created job with id {}", id);
//
//        final Map<String, Object> imageJobMap = new HashMap<>();
//        final Collection<Long> imagesId = new LinkedList<>();
//
//        for (Image image : images) {
//            imageJobMap.put("ji_image_id", image.getImageId());
//            imageJobMap.put("ji_job_id", id);
//            imagesId.add(image.getImageId());
//            jobImagesSimpleJdbcInsert.execute(imageJobMap);
//            LOGGER.debug("Inserted image with id {} to job with id {}", image.getImageId(), id);
//        }
//
//        return new Job(description, jobProvided, averageRating, totalRatings, category, id.longValue(), price, paused, provider, imagesId);
//    }
//
//
//    @Override
//    public Collection<Job> getJobsByCategory(String searchBy, OrderOptions orderOption, JobCategory category, String state, String city, int page, int itemsPerPage) {
//
//        final List<Object> variables = new LinkedList<>();
//
//        final String filterQuery = getCategoryFilterQuery(category, variables);
//
//        final String orderQuery = getOrderQuery(orderOption);
//
//        final String stateAndCityQuery = getStateAndCityQuery(state, city, variables);
//
//        final String searchQuery = getSearchQuery(searchBy, variables);
//
//        final String offsetAndLimitQuery = getOffsetAndLimitQuery(page, itemsPerPage, variables);
//
//        final String jobWhereQuery = String.format(
//            " where j_id in ( " +
//                " select j_id from " +
//                " (select * from JOBS j JOIN USERS u ON j_provider_id = u_id %s ) w0 " +
//                " JOIN " +
//                " (SELECT distinct pl_user_id from cities join provider_location on c_id = pl_city_id %s ) as w1 " +
//                " ON w0.j_provider_id=w1.pl_user_id " +
//                " JOIN " +
//                " (select j_id as job_id, count(r_job_id) as total_ratings,coalesce(avg(r_rating), 0) as avg_rating " +
//                " FROM jobs LEFT OUTER JOIN reviews on j_id = r_job_id group by j_id) jobsStats " +
//                " on job_id=j_id %s %s %s ) ",
//            filterQuery, stateAndCityQuery, searchQuery, orderQuery, offsetAndLimitQuery);
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
//                " on j_id = jobImages.ji_job_id %s ",
//            jobWhereQuery, orderQuery
//        );
//
//        return executeQuery(query, variables);
//    }
//
//    @Override
//    public Integer getJobsCountByCategory(String searchBy, JobCategory category, String state, String city) {
//        List<Object> variables = new LinkedList<>();
//
//        final String filterQuery = getCategoryFilterQuery(category, variables);
//
//        final String stateAndCityQuery = getStateAndCityQuery(state, city, variables);
//
//        final String searchQuery = getSearchQuery(searchBy, variables);
//
//
//        final String query = String.format("select count(distinct j_id) total from " +
//            "(select * from ( (select * from JOBS j JOIN USERS u ON j_provider_id = u_id %s ) as aux0 " +
//            "JOIN (SELECT distinct pl_user_id from " +
//            " cities join provider_location on c_id = provider_location.pl_city_id %s) " +
//            " as aux1 ON aux0.j_provider_id=aux1.pl_user_id ) as aux2 %s) as aux3", filterQuery, stateAndCityQuery, searchQuery);
//        LOGGER.debug("Executing query: {}", query);
//
//        return jdbcTemplate.query(query, variables.toArray(), (rs, rowNum) -> rs.getInt("total")).stream().findFirst().orElse(0);
//
//    }
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
//    @Override
//    public void updateJob(String jobProvided, String description, BigDecimal price, boolean paused, List<Image> images, long jobId, List<Long> imagesIdDeleted) {
//
//        LOGGER.info("Updating job with id {} information", jobId);
//
//        jdbcTemplate.update("UPDATE jobs SET j_job_provided = ?, " +
//            "j_description = ?, j_paused = ?,j_price = ? where j_id = ? ", jobProvided, description, paused, price, jobId);
//
//        final Map<String, Object> imageJobMap = new HashMap<>();
//        final Collection<Long> imagesId = new LinkedList<>();
//
//        for (Long imageId : imagesIdDeleted) {
//            jdbcTemplate.update("DELETE FROM JOB_IMAGE where ji_image_id = ?", imageId);
//        }
//
//        for (Image image : images) {
//            imageJobMap.put("ji_image_id", image.getImageId());
//            imageJobMap.put("ji_job_id", jobId);
//            imagesId.add(image.getImageId());
//            jobImagesSimpleJdbcInsert.execute(imageJobMap);
//        }
//
//    }
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
