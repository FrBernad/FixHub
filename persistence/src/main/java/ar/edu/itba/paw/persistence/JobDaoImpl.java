package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.JobDao;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class JobDaoImpl implements JobDao {

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<Job> JOB_ROW_MAPPER = (rs, rowNum) ->
            new Job(rs.getString("description"),
                    rs.getString("jobProvided"),
                    rs.getInt("averageRating"),
                    rs.getInt("jobType"),
                    rs.getLong("id"),
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
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS " +
                "JOBS(" +
                "id SERIAL," +
                "description TEXT," +
                "averageRating INT," +
                "jobType INT," +
                "jobProvided TEXT,"+
                "providerId BIGINT," +
                "FOREIGN KEY(providerId) REFERENCES USERS(id)," +
                "PRIMARY KEY(id))");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS " +
                "JOB_TYPE(" +
                "id SERIAL," +
                "name TEXT," +
                "PRIMARY KEY(id))");
    }

    @Override

    public Job createJob(String jobProvided, String jobType, String description, User provider){
        Map<String,Object> map = new HashMap<>();
        final int averageRating = 0;
        map.put("providerId",provider.getId());
        map.put("jobType",jobType);
        map.put("averageRating",averageRating);
        map.put("description",description);
        map.put("jobProvided",jobProvided);
        final Number id = simpleJdbcInsert.executeAndReturnKey(map);
        int jobAux =1;
        return new Job(description,jobProvided,averageRating,jobAux,id,provider);
    }

    @Override
    public Collection<Job> getJobs() {
        return jdbcTemplate.query(
                "SELECT * FROM JOBS j JOIN USERS u ON j.providerId = u.id",
                JOB_ROW_MAPPER);
    }

    @Override
    public Optional<Job> getJobById(long id) {
        return jdbcTemplate.query("SELECT * FROM JOBS j JOIN USERS u ON j.providerId = u.id " +
                "WHERE j.id = ?", new Object[]{id}, JOB_ROW_MAPPER).stream().findFirst();
    }

}
