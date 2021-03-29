package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ReviewDao;
import ar.edu.itba.paw.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<Review> REVIEW_ROW_MAPPER = (rs, rowNum) ->
            new Review(rs.getLong("id"),
                    rs.getString("description"),
                    rs.getLong("jobId"),
                    rs.getInt("rating"),
                    rs.getTimestamp("creationDate"));

    @Autowired
    public ReviewDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("REVIEWS").usingGeneratedKeyColumns("id");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS " +
                "REVIEWS(" +
                "id SERIAL," +
                "description TEXT," +
                "jobId BIGINT," +
                "rating INT," +
                "creationDate TIMESTAMP," +
                "FOREIGN KEY(jobId) REFERENCES JOBS(id)," +
                "PRIMARY KEY(id))");
    }

    @Override
    public Collection<Review> getReviewsByJobId(long jobId) {
        return jdbcTemplate.query(
                "SELECT * FROM REVIEWS r WHERE r.jobId = ?", new Object[]{jobId}, REVIEW_ROW_MAPPER
        );
    }

    @Override
    public Review createReview(String description, long jobId, int rating, Timestamp creationDate) {
        Map<String,Object> map = new HashMap<>();
        map.put("description",description);
        map.put("jobId",jobId);
        map.put("rating",rating);
        map.put("creationDate",creationDate);
        final Number id = simpleJdbcInsert.executeAndReturnKey(map);
        return new Review(id, description, jobId, rating, creationDate);
    }
}
