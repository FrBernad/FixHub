package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.models.Job;
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
                    rs.getTimestamp("creationDate").toLocalDateTime().toLocalDate());

    @Autowired
    public ReviewDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("REVIEWS").usingGeneratedKeyColumns("id");
    }

    @Override
    public Collection<Review> getReviewsByJobId(Job job) {
        return jdbcTemplate.query(
                "SELECT * FROM REVIEWS r WHERE r.jobId = ?", new Object[]{job.getId()}, REVIEW_ROW_MAPPER
        );
    }

    @Override
    public Review createReview(String description, Job job, int rating, Timestamp creationDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("description", description);
        map.put("jobId", job.getId());
        map.put("rating", rating);
        map.put("creationDate", creationDate);
        final Number id = simpleJdbcInsert.executeAndReturnKey(map);
        return new Review(id, description, job.getId(), rating, creationDate.toLocalDateTime().toLocalDate());
    }

    @Override
    public int getReviewsCountByJobId(Job job) {
        return jdbcTemplate.query(
                "SELECT count(*) as total FROM REVIEWS r WHERE r.jobId = ?", new Object[]{job.getId()},
                rs -> {
                    return rs.getInt("total");
                }
        );
    }
}
