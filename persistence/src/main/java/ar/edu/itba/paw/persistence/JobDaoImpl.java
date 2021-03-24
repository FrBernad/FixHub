package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.JobDao;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JobDaoImpl implements JobDao {

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<Job> JOB_ROW_MAPPER = (rs, rowNum) ->
            new Job(rs.getString("description"),
                    rs.getInt("averageRating"),
                    rs.getInt("serviceType"),
                    rs.getLong("id"),
                    new User(rs.getLong("providerId"),
                            rs.getString("name"),
                            rs.getString("password")));

    @Autowired
    public JobDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("JOBS").usingGeneratedKeyColumns("id");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS " +
                "JOBS(" +
                "id SERIAL," +
                "description TEXT," +
                "averageRating INT," +
                "serviceType INT," +
                "providerId BIGINT," +
                "FOREIGN KEY(providerId) REFERENCES USERS(id)," +
                "PRIMARY KEY(id))");
    }

    @Override
    public Job createJob(String description, int averageRating, int serviceType, User provider) {
        Map<String,Object> map = new HashMap<>();
        map.put("providerId",provider.getId());
        map.put("serviceType",serviceType);
        map.put("averageRating",averageRating);
        map.put("description",description);
        final Number id = simpleJdbcInsert.executeAndReturnKey(map);
        return new Job(description,averageRating,serviceType,id,provider);
    }

    @Override
    public Collection<Job> getJobs() {
        return jdbcTemplate.query(
                "SELECT * FROM JOBS j JOIN USERS u ON j.providerId = u.id",
                JOB_ROW_MAPPER);
    }

    @Override
    public Optional<Job> getJobById(long id) {
        return jdbcTemplate.query("SELECT * FROM JOBS WHERE id = ?", new Object[]{id}, JOB_ROW_MAPPER).stream().findFirst();
    }

}
