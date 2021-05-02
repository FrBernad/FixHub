package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.itba.paw.interfaces.exceptions.ImageNotFoundException;
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
    private static final Collection<JobCategory> categories = Collections.unmodifiableList(Arrays.asList(JobCategory.values().clone()));

    private static final String EMPTY = " ";
    private static final Logger LOGGER = LoggerFactory.getLogger(JobDaoImpl.class);

    private static final RowMapper<Job> JOB_ROW_MAPPER = (rs, rowNum) ->
        new Job(rs.getString("j_description"),
            rs.getString("j_job_provided"),
            rs.getInt("avg_rating"),
            rs.getInt("total_ratings"),
            JobCategory.valueOf(rs.getString("j_category")),
            rs.getLong("j_id"),
            rs.getBigDecimal("j_price"),
            rs.getBoolean("j_paused"),
            new User(rs.getLong("j_provider_id"),
                rs.getString("u_password"),
                rs.getString("u_name"),
                rs.getString("u_surname"),
                rs.getString("u_email"),
                rs.getString("u_phone_number"),
                rs.getString("u_state"),
                rs.getString("u_city"),
                Collections.emptyList(),
                rs.getLong("u_profile_picture"),
                rs.getLong("u_cover_picture")),
            rs.getLong("ji_image_id"),
            Collections.emptyList());


    private static final ResultSetExtractor<Collection<Long>> JOB_IMAGE_ROW_MAPPER = rs -> {
        List<Long> imageIds = new LinkedList<>();
        while (rs.next()) {
            imageIds.add(rs.getLong("ji_image_id"));
        }
        return imageIds;
    };

    @Autowired
    public JobDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jobSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("JOBS").usingGeneratedKeyColumns("j_id");
        jobImagesSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("JOB_IMAGE");
    }

    @Transactional
    @Override
    public Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, boolean paused, User provider, List<Image> images) {

        Map<String, Object> map = new HashMap<>();
        final int averageRating = 0, totalRatings = 0;
        map.put("j_provider_id", provider.getId());
        map.put("j_category", category);
        map.put("j_description", description);
        map.put("j_job_provided", jobProvided);
        map.put("j_price", price);
        map.put("j_paused", paused);
        final Number id = jobSimpleJdbcInsert.executeAndReturnKey(map);
        LOGGER.debug("Created job with id {}", id);

        Map<String, Object> imageJobMap = new HashMap<>();
        Collection<Long> imagesId = new LinkedList<>();

        for (Image image : images) {
            imageJobMap.put("ji_image_id", image.getImageId());
            imageJobMap.put("ji_job_id", id);
            imagesId.add(image.getImageId());
            jobImagesSimpleJdbcInsert.execute(imageJobMap);
            LOGGER.debug("Inserted image with id {} to job with id {}", image.getImageId(), id);
        }

        return new Job(description, jobProvided, averageRating, totalRatings, category, id.longValue(), price, paused, provider, imagesId.stream().findFirst().orElse(null), imagesId);
    }

    @Override
    public Collection<Job> getJobsByCategory(String searchBy, OrderOptions orderOption, JobCategory category, String state, String city, int page, int itemsPerPage) {

        List<Object> variables = new LinkedList<>();

        String filterQuery = EMPTY;
        if (category != null) {
            variables.add(category.name());

            filterQuery = " WHERE j_category = ? ";
        }

        String orderQuery = getOrderQuery(orderOption);

        String stateQuery = EMPTY;
        if (state != null) {
            stateQuery = " WHERE c_state_id = ? ";
            variables.add(Integer.valueOf(state));
        }

        String cityQuery = EMPTY;
        if (city != null) {
            cityQuery = " AND c_id = ? ";
            variables.add(Integer.valueOf(city));
        }

        String searchQuery = EMPTY;
        if (searchBy != null) {
            searchBy = String.format("%%%s%%", searchBy.replace("%", "\\%").replace("_", "\\_").toLowerCase());
            variables.add(searchBy);
            variables.add(searchBy);
            variables.add(searchBy);
            searchQuery = " WHERE LOWER(j_description) LIKE ? OR LOWER(j_job_provided) LIKE ? OR LOWER(u_name) LIKE ?";
        }

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

        return createAndExecuteQuery(searchQuery, orderQuery, filterQuery, stateQuery, cityQuery, offset, limit, variables);
    }

    @Override
    public Integer getJobsCountByCategory(String searchBy, JobCategory category, String state, String city) {
        List<Object> variables = new LinkedList<>();

        String filterQuery = EMPTY;
        if (category != null) {
            variables.add(category.name());

            filterQuery = " WHERE j_category = ? ";
        }

        String stateQuery = EMPTY;
        if (state != null) {
            stateQuery = " WHERE c_state_id = ? ";
            variables.add(Integer.valueOf(state));
        }

        String cityQuery = EMPTY;
        if (city != null) {
            cityQuery = " AND c_id = ? ";
            variables.add(Integer.valueOf(city));
        }

        String searchQuery = EMPTY;
        if (searchBy != null) {
            searchBy = String.format("%%%s%%", searchBy.replace("%", "\\%").replace("_", "\\_").toLowerCase());
            variables.add(searchBy);
            variables.add(searchBy);
            variables.add(searchBy);
            searchQuery = " WHERE LOWER(j_description) LIKE ? OR LOWER(j_job_provided) LIKE ? OR LOWER(u_name) LIKE ?";
        }

        final String query = String.format("select count(distinct j_id) total from " +
            "(select * from ( (select * from JOBS j JOIN USERS u ON j_provider_id = u_id %s ) as aux0 " +
            "JOIN (SELECT distinct ul_user_id from " +
            " cities join user_location on c_id = user_location.ul_city_id %s %s) " +
            " as aux1 ON aux0.j_provider_id=aux1.ul_user_id ) as aux2 %s) as aux3", filterQuery, stateQuery, cityQuery, searchQuery);
        LOGGER.debug("Executing query: {}", query);

        return jdbcTemplate.query(query, variables.toArray(), (rs, rowNum) -> rs.getInt("total")).stream().findFirst().orElse(0);

    }

    @Override
    public Integer getJobsCountByProviderId(String searchBy, Long providerId) {
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

        final String query = " select count(*) total from (select * from JOBS j JOIN USERS u ON j_provider_id = u_id" + filterQuery + " ) as aux" + searchQuery;
        LOGGER.debug("Executing query: {}", query);

        return jdbcTemplate.query(query, variables.toArray(), (rs, rowNum) -> rs.getInt("total")).stream().findFirst().orElse(0);

    }

    @Override
    public void updateJob(String jobProvided, String description, BigDecimal price, boolean paused,List<Image> images, long jobId,List<Long> imagesIdDeleted) {

        LOGGER.info("Updating job with id {} information",jobId);

        jdbcTemplate.update("UPDATE jobs SET j_job_provided = ?, " +
            "j_description = ?, j_paused = ?,j_price = ? where j_id = ? ", jobProvided, description, paused, price, jobId);

        Map<String, Object> imageJobMap = new HashMap<>();
        Collection<Long> imagesId = new LinkedList<>();

        for(Long imageId: imagesIdDeleted){
            jdbcTemplate.update("DELETE FROM JOB_IMAGE where ji_image_id = ?",new Object[]{imageId});
        }

        for (Image image : images) {
            imageJobMap.put("ji_image_id", image.getImageId());
            imageJobMap.put("ji_job_id", jobId);
            imagesId.add(image.getImageId());
            jobImagesSimpleJdbcInsert.execute(imageJobMap);
        }

    }


    @Override
    public Optional<Job> getJobById(long id) {
        List<Object> variables = new LinkedList<>();
        variables.add(id);

        String filterQuery = " WHERE j_id = ? ";

        return createAndExecuteQuery(EMPTY, EMPTY, filterQuery, EMPTY, EMPTY, EMPTY, EMPTY, variables).stream().findFirst();
    }

    public Collection<JobCategory> getJobsCategories() {
        return categories;
    }

    @Override
    public Collection<Long> getImagesIdsByJobId(Long jobId) {
        final String query = "SELECT * FROM JOB_IMAGE where ji_job_id = ? ORDER BY ji_image_id";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(query, new Object[]{jobId}, JOB_IMAGE_ROW_MAPPER);
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

        return createAndExecuteQuery(searchQuery, orderQuery, filterQuery, EMPTY, EMPTY, offset, limit, variables);
    }

    private Collection<Job> createAndExecuteQuery(String searchQuery, String orderQuery, String filterQuery, String stateQuery, String cityQuery, String offset, String limit, List<Object> variables) {
        final String query = "select * from ( ( select * from" +
            "((select * from JOBS j JOIN USERS u ON j_provider_id = u_id " + filterQuery +
            ") aux3 JOIN (SELECT distinct ul_user_id from " +
            " cities join user_location on c_id = user_location.ul_city_id " + stateQuery + cityQuery + ") " +
            " as aux0 ON aux3.j_provider_id=aux0.ul_user_id ) as aux1 " +
            " JOIN " +
            "(select j_id as job_id, count(r_job_id) as total_ratings,coalesce(avg(r_rating), 0) as avg_rating " +
            "FROM jobs LEFT OUTER JOIN reviews on j_id = r_job_id group by j_id) aux2" +
            " on aux1.j_id = aux2.job_id) aux3 LEFT OUTER JOIN (select ji_job_id, min(ji_image_id) as ji_image_id from job_image group by ji_job_id) aux4 on aux3.j_id = aux4.ji_job_id)" + searchQuery + orderQuery + offset + limit;

        LOGGER.debug("Executing query: {}", query);

        return jdbcTemplate.query(query, variables.toArray(), JOB_ROW_MAPPER);
    }

    private String getOrderQuery(OrderOptions orderOption) {
        String orderQuery = " ORDER BY ";
        switch (orderOption) {
            case MOST_POPULAR:
                return orderQuery + " avg_rating desc, total_ratings desc, j_id desc ";

            case LESS_POPULAR:
                return orderQuery + " avg_rating asc, total_ratings desc, j_id desc ";

            case HIGHER_PRICE:
                return orderQuery + " j_price desc, total_ratings desc, j_id desc ";

            case LOWER_PRICE:
                return orderQuery + " j_price asc, total_ratings desc, j_id desc ";

        }
        return null; //never reaches
    }

    //FIXME:CORRESPONDE LANZAR EXCEPCION?
    @Override
    public int deleteImageById(long imageId, long jobId){
        int res = jdbcTemplate.update("DELETE FROM JOB_IMAGE WHERE ji_job_id = ? AND ji_image_id = ?",new Object[]{jobId,imageId});
        if(res == 0 ){
            LOGGER.error("Error, trying to delete a non-existent image with id {}",imageId);
            throw new ImageNotFoundException();
        }
        return res;
    }

}
