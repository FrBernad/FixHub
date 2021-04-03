package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.exceptions.ReviewException;
import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    public Collection<Review> getReviewsByJobId(long jobId) {
        return jdbcTemplate.query(
                "SELECT * FROM REVIEWS r WHERE r.jobId = ?", new Object[]{jobId}, REVIEW_ROW_MAPPER
        );
    }

    @Override
    public Review createReview(String description, long jobId, int rating, Timestamp creationDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("description", description);
        map.put("jobId", jobId);
        map.put("rating", rating);
        map.put("creationDate", creationDate);
        final Number id;
        try{
             id = simpleJdbcInsert.executeAndReturnKey(map);
        }catch(DataIntegrityViolationException e){
            throw new ReviewException();
        }
        return new Review(id, description, jobId, rating, creationDate.toLocalDateTime().toLocalDate());
    }

    @Override
    public int getReviewsCountByJobId(long jobId) {
        return jdbcTemplate.query(
                "SELECT count(*) as total FROM REVIEWS r WHERE r.jobId = ?", new Object[]{jobId},
                rs -> {
                    return rs.getInt("total");
                }
        );
    }
}
