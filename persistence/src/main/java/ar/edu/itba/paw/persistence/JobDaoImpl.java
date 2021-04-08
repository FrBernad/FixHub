package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategory;
import ar.edu.itba.paw.models.OrderOptions;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.*;

@Repository
public class JobDaoImpl implements JobDao {

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private Collection<JobCategory> categories = Arrays.asList(JobCategory.values().clone());

    private final String EMPTY = " ";

    private static final RowMapper<Job> JOB_ROW_MAPPER = (rs, rowNum) ->
            new Job(rs.getString("description"),
                    rs.getString("job_provided"),
                    rs.getInt("avg_rating"),
                    rs.getInt("total_ratings"),
                    JobCategory.valueOf(rs.getString("category")),
                    rs.getLong("job_id"),
                    rs.getBigDecimal("price"),
                    new User(rs.getLong("provider_id"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("email"),
                            rs.getString("phone_number"),
                            rs.getString("state"),
                            rs.getString("city")));

    @Autowired
    public JobDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("JOBS").usingGeneratedKeyColumns("id");
    }

    @Override
    public Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, User provider) {
        Map<String, Object> map = new HashMap<>();
        final int averageRating = 0, totalRatings = 0;
        map.put("provider_id", provider.getId());
        map.put("category", category);
        map.put("description", description);
        map.put("job_provided", jobProvided);
        map.put("price", price);
        final Number id = simpleJdbcInsert.executeAndReturnKey(map);

        return new Job(description, jobProvided, averageRating, totalRatings, category, id, price, provider);
    }

    @Override
    public Collection<Job> getJobsByCategory(String searchBy, OrderOptions orderOptions, JobCategory category) {

        List<Object> variables = new LinkedList<>();

        String filterQuery = EMPTY;
        if (category != null) {
            variables.add(category.name());
            filterQuery = " WHERE j.category = ? ";
        }

        String searchQuery = EMPTY;
        if (searchBy != null) {
            final String searchByLike = String.format("%%%s%%", searchBy);
            variables.add(searchByLike);
            variables.add(searchByLike);
            variables.add(searchByLike);
            searchQuery = " WHERE description LIKE ? OR job_provided LIKE ? OR name LIKE ?";
        }

        String orderQuery = getOrderQuery(orderOptions);

        return createAndExecuteQuery(searchQuery, orderQuery, filterQuery, variables);
    }

    @Override
    public Optional<Job> getJobById(long id) {
        List<Object> variables = new LinkedList<>();
        variables.add(id);

        String filterQuery = " WHERE j.id = ? ";

        return createAndExecuteQuery(EMPTY, EMPTY,filterQuery,variables).stream().findFirst();

    }

    public Collection<JobCategory> getJobsCategories() {
        return categories;
    }

    private Collection<Job> createAndExecuteQuery(String searchQuery, String orderQuery, String filterQuery, List<Object> variables) {

        return jdbcTemplate.query(
                "select * from (" +
                        "(select * from JOBS j JOIN USERS u ON j.provider_id = u.id " + filterQuery +
                        ") as aux(job_id) LEFT OUTER JOIN (select job_idd, count(job_id) as total_ratings,coalesce(avg(rating), 0) as avg_rating " +
                        "from (select id as job_idd from jobs) j " +
                        "LEFT OUTER JOIN reviews r on j.job_idd = r.job_id group by job_idd) " +
                        "r on aux.job_id = r.job_idd) " + searchQuery + orderQuery, variables.toArray(),
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
                return orderQuery + "price desc";

            case LOWER_PRICE:
                return orderQuery + "price asc";

        }
        return null; //never reaches
    }

}
