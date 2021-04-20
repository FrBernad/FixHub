package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.*;

@Repository
public class JobDaoImpl implements JobDao {

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jobSimpleJdbcInsert;
    private SimpleJdbcInsert jobImagesSimpleJdbcInsert;
    private Collection<JobCategory> categories = Collections.unmodifiableList(Arrays.asList(JobCategory.values().clone()));

    private final String EMPTY = " ";

    private static final ResultSetExtractor<Collection<Job>> JOB_ROW_MAPPER = rs -> {
        Map<Long, Job> jobsMap = new LinkedHashMap<>();

        long jobId;

        while (rs.next()) {

            jobId = rs.getLong("j_id");

            if (!jobsMap.containsKey(jobId)) {
                jobsMap.put(jobId,
                    new Job(rs.getString("j_description"),
                        rs.getString("j_job_provided"),
                        rs.getInt("avg_rating"),
                        rs.getInt("total_ratings"),
                        JobCategory.valueOf(rs.getString("j_category")),
                        rs.getLong("j_id"),
                        rs.getBigDecimal("j_price"),
                        new User(rs.getLong("j_provider_id"),
                            rs.getString("u_password"),
                            rs.getString("u_name"),
                            rs.getString("u_surname"),
                            rs.getString("u_email"),
                            rs.getString("u_phone_number"),
                            rs.getString("u_state"),
                            rs.getString("u_city"),
                            Collections.emptyList(),
                            rs.getLong("u_profile_picture")),
                        new ArrayList<>()));
            }

            if (rs.getObject("ji_image_id") != null) {
                jobsMap.get(jobId).addImageId(rs.getLong("ji_image_id"));
            }
        }

        return jobsMap.values();
    };


    private static final ResultSetExtractor<Collection<Long>> JOB_IMAGE_ROW_MAPPER = rs -> {
        List<Long> imageIds = new LinkedList<>();
        while (rs.next()) {
            imageIds.add(rs.getLong("ji_image_id"));
        }
        return imageIds;
    };

//    FIXME: VER Q ONDA EL ARRAY LIST

    @Autowired
    public JobDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jobSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("JOBS").usingGeneratedKeyColumns("j_id");
        jobImagesSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("JOB_IMAGE");
    }

    @Transactional
    @Override
    public Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, User provider, List<Image> images) {

        Map<String, Object> map = new HashMap<>();
        final int averageRating = 0, totalRatings = 0;
        map.put("j_provider_id", provider.getId());
        map.put("j_category", category);
        map.put("j_description", description);
        map.put("j_job_provided", jobProvided);
        map.put("j_price", price);
        final Number id = jobSimpleJdbcInsert.executeAndReturnKey(map);

        Map<String, Object> imageJobMap = new HashMap<>();
        Collection<Long> imagesId = new LinkedList<>();

        for (Image image : images) {
            imageJobMap.put("ji_image_id", image.getImageId());
            imageJobMap.put("ji_job_id", id);
            imagesId.add(image.getImageId());
            jobImagesSimpleJdbcInsert.execute(imageJobMap);
        }

        return new Job(description, jobProvided, averageRating, totalRatings, category, id.longValue(), price, provider, imagesId);
    }

    @Override
    public Collection<Job> getJobsByCategory(String searchBy, OrderOptions orderOption, JobCategory category, int page, int itemsPerPage) {

        List<Object> variables = new LinkedList<>();

        String filterQuery = EMPTY;
        if (category != null) {
            variables.add(category.name());

            filterQuery = " WHERE j_category = ? ";
        }

        String searchQuery = EMPTY;
        if (searchBy != null) {
            searchBy = String.format("%%%s%%", searchBy.replace("%", "\\%").replace("_", "\\_").toLowerCase());
            variables.add(searchBy);
            variables.add(searchBy);
            variables.add(searchBy);
            searchQuery = " WHERE LOWER(j_description) LIKE ? OR LOWER(j_job_provided) LIKE ? OR LOWER(u_name) LIKE ?";
        }

        String orderQuery = getOrderQuery(orderOption);

        String offset = EMPTY;
        if (page > 0) {
            offset = " OFFSET ? ";
            variables.add(page * itemsPerPage);
        }


        String limit = EMPTY;
        if (itemsPerPage > 0) {
            limit = " LIMIT ? ";
            variables.add(itemsPerPage);
        }

        return createAndExecuteQuery(searchQuery, orderQuery, filterQuery, limit, offset, variables);
    }

    @Override
    public Integer getJobsCountByCategory(String searchBy, OrderOptions orderOption, JobCategory category) {
        List<Object> variables = new LinkedList<>();

        String filterQuery = EMPTY;
        if (category != null) {
            variables.add(category.name());

            filterQuery = " WHERE j_category = ? ";
        }

        String searchQuery = EMPTY;
        if (searchBy != null) {
            searchBy = String.format("%%%s%%", searchBy.replace("%", "\\%").replace("_", "\\_").toLowerCase());
            variables.add(searchBy);
            variables.add(searchBy);
            variables.add(searchBy);
            searchQuery = " WHERE LOWER(j_description) LIKE ? OR LOWER(j_job_provided) LIKE ? OR LOWER(u_name) LIKE ?";
        }

        return jdbcTemplate.query(
            "select count(*) total from " +
                "(select * from JOBS j JOIN USERS u ON j_provider_id = u_id " + filterQuery + ") as aux"
                + searchQuery, variables.toArray(), (rs, rowNum) -> rs.getInt("total")).stream().findFirst().orElse(0);

    }

    @Override
    public Integer getJobsCountByProviderId(String searchBy, OrderOptions orderOption, Long providerId) {
        List<Object> variables = new LinkedList<>();

        String filterQuery = " WHERE j_provider_id = ? ";
        variables.add(providerId);

        String searchQuery = EMPTY;
        if (searchBy != null) {
            searchBy = String.format("%%%s%%", searchBy.replace("%", "\\%").replace("_", "\\_").toLowerCase());
            variables.add(searchBy);
            variables.add(searchBy);
            variables.add(searchBy);
            searchQuery = " WHERE LOWER(j_description) LIKE ? OR LOWER(j_job_provided) LIKE ? OR LOWER(u_name) LIKE ?";
        }

        return jdbcTemplate.query(
            "select count(*) total from " +
                "(select * from JOBS j JOIN USERS u ON j_provider_id = u_id " + filterQuery + ") as aux"
                + searchQuery, variables.toArray(), (rs, rowNum) -> rs.getInt("total")).stream().findFirst().orElse(0);

    }

    @Override
    public Optional<Job> getJobById(long id) {
        List<Object> variables = new LinkedList<>();
        variables.add(id);

        String filterQuery = " WHERE j_id = ? ";

        return createAndExecuteQuery(EMPTY, EMPTY, filterQuery, EMPTY, EMPTY, variables).stream().findFirst();

    }

    public Collection<JobCategory> getJobsCategories() {
        return categories;
    }

    @Override
    public Collection<Long> getImagesIdsByJobId(Long jobId) {
        return jdbcTemplate.query("SELECT * FROM JOB_IMAGE where ji_job_id = ? ORDER BY ji_image_id", new Object[]{jobId}, JOB_IMAGE_ROW_MAPPER);
    }

    @Override
    public Collection<Job> getJobsByProviderId(String searchBy, OrderOptions orderOption, Long providerId, int page, int itemsPerPage) {
        List<Object> variables = new LinkedList<>();

        String filterQuery = " WHERE j_provider_id = ? ";
        variables.add(providerId);

        String searchQuery = EMPTY;
        if (searchBy != null) {
            searchBy = String.format("%%%s%%", searchBy.replace("%", "\\%").replace("_", "\\_").toLowerCase());
            variables.add(searchBy);
            variables.add(searchBy);
            searchQuery = " WHERE LOWER(j_description) LIKE ? OR LOWER(j_job_provided) LIKE ? ";
        }

        String orderQuery = getOrderQuery(orderOption);

        String offset = EMPTY;
        if (page > 0) {
            offset = " OFFSET ? ";
            variables.add(page * itemsPerPage);
        }


        String limit = EMPTY;
        if (itemsPerPage > 0) {
            limit = " LIMIT ? ";
            variables.add(itemsPerPage);
        }

        return createAndExecuteQuery(searchQuery, orderQuery, filterQuery, offset, limit, variables);
    }

    private Collection<Job> createAndExecuteQuery(String searchQuery, String orderQuery, String filterQuery, String offset, String limit, List<Object> variables) {

        return jdbcTemplate.query(
            "select * from ( ( select * from" +
                "(select * from JOBS j JOIN USERS u ON j_provider_id = u_id " + filterQuery + ") aux1" +
                " JOIN " +
                "(select j_id as job_id, count(r_job_id) as total_ratings,coalesce(avg(r_rating), 0) as avg_rating " +
                "FROM jobs LEFT OUTER JOIN reviews on j_id = r_job_id group by j_id) aux2" +
                " on aux1.j_id = aux2.job_id) aux3 LEFT OUTER JOIN job_image on aux3.j_id = ji_job_id)" + searchQuery + orderQuery + offset + limit, variables.toArray(),
            JOB_ROW_MAPPER);
    }

    private String getOrderQuery(OrderOptions orderOption) {
        String orderQuery = " ORDER BY ";
        switch (orderOption) {
            case MOST_POPULAR:
                return orderQuery + "avg_rating desc";

            case LESS_POPULAR:
                return orderQuery + "avg_rating asc";

            case HIGHER_PRICE:
                return orderQuery + "j_price desc";

            case LOWER_PRICE:
                return orderQuery + "j_price asc";

        }
        return null; //never reaches
    }


}
