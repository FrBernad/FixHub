package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.ReviewUser;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        new Review(rs.getLong("r_id"),
            rs.getString("r_description"),
            rs.getLong("r_job_id"),
            rs.getInt("r_rating"),
            rs.getTimestamp("r_creation_date").toLocalDateTime().toLocalDate(),
            new ReviewUser(rs.getLong("r_reviewer_id"),rs.getString("u_name"),rs.getString("u_surname"),rs.getLong("u_profile_picture")));

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewDaoImpl.class);

    @Autowired
    public ReviewDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("REVIEWS").usingGeneratedKeyColumns("r_id");
    }

    @Override
    public Collection<Review> getReviewsByJobId(long jobId, int page, int itemsPerPage) {
        final String query = "SELECT * FROM REVIEWS r JOIN USERS u on r_reviewer_id = u_id WHERE r_job_id = ? OFFSET ? LIMIT ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(
            query, new Object[]{jobId, page * itemsPerPage, itemsPerPage}, REVIEW_ROW_MAPPER
        );
    }

    @Override
    public Review createReview(String description, Job job, int rating, Timestamp creationDate, User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("r_description", description);
        map.put("r_job_id", job.getId());
        map.put("r_rating", rating);
        map.put("r_reviewer_id",user.getId());
        map.put("r_creation_date", creationDate);
        final Number id = simpleJdbcInsert.executeAndReturnKey(map);
        LOGGER.debug("Created review for job {} with id {} by userId {}", job.getId(), id,user.getId());
        return new Review(id, description, job.getId(), rating, creationDate.toLocalDateTime().toLocalDate(), new ReviewUser(user.getId(),user.getName(), user.getSurname(), user.getProfileImageId()));
    }

    @Override
    public int getReviewsCountByJobId(long jobId) {
        final String query = "SELECT count(*) as total FROM REVIEWS r WHERE r_job_id = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query("SELECT count(*) as total FROM REVIEWS r WHERE r_job_id = ?", new Object[]{jobId},
            (rs, rowNum) -> rs.getInt("total")
        ).stream().findFirst().orElse(0);
    }
}
