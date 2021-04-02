package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.JobDao;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategory;
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

    private static final RowMapper<Job> JOB_ROW_MAPPER = (rs, rowNum) ->
            new Job(rs.getString("description"),
                    rs.getString("jobProvided"),
                    rs.getInt("avgRating"),
                    rs.getInt("totalRatings"),
                    JobCategory.valueOf(rs.getString("category")),
                    rs.getLong("jobId"),
                    rs.getBigDecimal("price"),
                    new User(rs.getLong("providerId"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("email"),
                            rs.getString("phoneNumber"),
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
        map.put("providerId", provider.getId());
        map.put("category", category);
        map.put("averageRating", averageRating);
        map.put("description", description);
        map.put("jobProvided", jobProvided);
        map.put("price", price);
        final Number id = simpleJdbcInsert.executeAndReturnKey(map);

        return new Job(description, jobProvided, averageRating, totalRatings, category, id, price, provider);
    }

    @Override
    public Collection<Job> getJobs() {
        return jdbcTemplate.query(
                "select * from ((select * from JOBS j JOIN USERS u ON j.providerId = u.id) " +
                        "as aux (jobid) LEFT OUTER JOIN (select jobidd, count(jobid) as totalRatings,coalesce(avg(rating), 0) as avgrating   " +
                        "from (select id as jobidd from jobs) j " +
                        "LEFT OUTER JOIN reviews r on j.jobidd = r.jobid group by jobidd) " +
                        "r on aux.jobid = r.jobidd)", JOB_ROW_MAPPER);
    }


    //TODO: CAMBIAR A LIKE SI TESTING NO FUNCA
    @Override
    public Collection<Job> getJobsBySearchPhrase(String phrase) {
        return jdbcTemplate.query(
                "select * from ((select * from JOBS j JOIN USERS u ON j.providerId = u.id " +
                        "WHERE to_tsvector('spanish',j.description) @@ plainto_tsquery('spanish',?) " +
                        "OR to_tsvector('spanish',j.jobprovided) @@ plainto_tsquery('spanish',?) " +
                        "OR to_tsvector('spanish',u.name) @@ plainto_tsquery('spanish',?))" +
                        "as aux(jobid) LEFT OUTER JOIN (select jobidd, count(jobid) as totalRatings,coalesce(avg(rating), 0) as avgrating " +
                        "from (select id as jobidd from jobs) j " +
                        "LEFT OUTER JOIN reviews r on j.jobidd = r.jobid group by jobidd) " +
                        "r on aux.jobid = r.jobidd)", new Object[]{phrase, phrase, phrase},
                JOB_ROW_MAPPER);
    }

    @Override
    public Collection<Job> getJobsByCategory(long jobCategory) {
        return jdbcTemplate.query(
                "SELECT * FROM JOBS j JOIN USERS u ON j.providerId = u.id WHERE jobtype = ?"
                , new Object[]{jobCategory},
                JOB_ROW_MAPPER);
    }

    @Override
    public Collection<Job> getJobsOrderByCategory(long categoryId) {
        //TODO: Query
        return null;
    }

    @Override
    public Collection<Job> getJobsOrderByRating() {
        //TODO: Query
        return null;
    }

    @Override
    public Optional<Job> getJobById(long id) {
        return jdbcTemplate.query("select * from ((select * from JOBS j JOIN USERS u ON j.providerId = u.id WHERE j.id = ?) " +
                "as aux (jobid) LEFT OUTER JOIN (select jobidd, count(jobid) as totalRatings,coalesce(avg(rating), 0) as avgrating   " +
                "from (select id as jobidd from jobs) j " +
                "LEFT OUTER JOIN reviews r on j.jobidd = r.jobid group by jobidd) " +
                "r on aux.jobid = r.jobidd)", new Object[]{id}, JOB_ROW_MAPPER).stream().findFirst();
    }

    public Collection<JobCategory> getJobsCategories() {
        return categories;
    }

    @Override
    public Collection<Job> getJobsBySearchCategory(String category) {
        return jdbcTemplate.query(
                "select * from ((select * from JOBS j JOIN USERS u ON j.providerId = u.id " +
                        "WHERE j.category = ?)" +
                        "as aux(jobid) LEFT OUTER JOIN (select jobidd, count(jobid) as totalRatings,coalesce(avg(rating), 0) as avgrating " +
                        "from (select id as jobidd from jobs) j " +
                        "LEFT OUTER JOIN reviews r on j.jobidd = r.jobid group by jobidd) " +
                        "r on aux.jobid = r.jobidd)", new Object[]{category},
                JOB_ROW_MAPPER);
    }


}
