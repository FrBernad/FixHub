package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.models.*;
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
import java.util.*;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private static final ResultSetExtractor<Collection<Review>> REVIEW_RS_EXTRACTOR = (rs) ->
    {
        final Map<Long, Review> reviewsMap = new HashMap<>();

        long reviewId;

        while (rs.next()) {

            reviewId = rs.getLong("r_id");

            if (!reviewsMap.containsKey(reviewId)) {
                reviewsMap.put(reviewId,
                    new Review(rs.getLong("r_id"),
                        rs.getString("r_description"),
                        rs.getLong("r_job_id"),
                        rs.getInt("r_rating"),
                        rs.getTimestamp("r_creation_date").toLocalDateTime().toLocalDate(),
                        new User(
                            rs.getLong("u_id"),
                            rs.getString("u_password"),
                            rs.getString("u_name"),
                            rs.getString("u_surname"),
                            rs.getString("u_email"),
                            rs.getString("u_phone_number"),
                            rs.getString("u_state"),
                            rs.getString("u_city"),
                            new HashSet<>(),
                            rs.getLong("u_profile_picture"),
                            rs.getLong("u_cover_picture"))));
            }

            reviewsMap.get(reviewId).getReviewer().addRole(Roles.valueOf(rs.getString("r_role")));
        }

        return reviewsMap.values();
    };


    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewDaoImpl.class);

    @Autowired
    public ReviewDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("REVIEWS").usingGeneratedKeyColumns("r_id");
    }

    @Override
    public Collection<Review> getReviewsByJobId(long jobId, int page, int itemsPerPage) {
        final List<Object> variables = new LinkedList<>();
        variables.add(jobId);
        final String offsetAndLimitQuery = getOffsetAndLimitQuery(page, itemsPerPage, variables);

        final String USER_WHERE_IDS_QUERY =
            " where r_id in (select r_id FROM " +
                " REVIEWS where r_job_id = ? " +
                " order by r.r_creation_date desc, r_id desc " + offsetAndLimitQuery + " ) ";

        final String query =
            " SELECT * " +
                " FROM " +
                " (SELECT * FROM REVIEWS r " + USER_WHERE_IDS_QUERY + " ) r " +
                " JOIN " +
                " USERS u " +
                " on r_reviewer_id = u.u_id " +
                " JOIN " +
                " ROLES " +
                " on roles.r_user_id = u.u_id " +
                " order by r.r_creation_date desc, r.r_id desc ";


        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query(
            query, variables.toArray(), REVIEW_RS_EXTRACTOR
        );
    }

    @Override
    public Review createReview(String description, Job job, int rating, Timestamp creationDate, User user) {
        final Map<String, Object> map = new HashMap<>();
        map.put("r_description", description);
        map.put("r_job_id", job.getId());
        map.put("r_rating", rating);
        map.put("r_reviewer_id", user.getId());
        map.put("r_creation_date", creationDate);
        final Number id = simpleJdbcInsert.executeAndReturnKey(map);
        LOGGER.debug("Created review for job {} with id {} by userId {}", job.getId(), id, user.getId());
        return new Review(id, description, job.getId(), rating, creationDate.toLocalDateTime().toLocalDate(), user);
    }

    @Override
    public int getReviewsCountByJobId(long jobId) {
        final String query = "SELECT count(*) as total FROM REVIEWS r WHERE r_job_id = ?";
        LOGGER.debug("Executing query: {}", query);
        return jdbcTemplate.query("SELECT count(*) as total FROM REVIEWS r WHERE r_job_id = ?", new Object[]{jobId},
            (rs, rowNum) -> rs.getInt("total")
        ).stream().findFirst().orElse(0);
    }

    private String getOffsetAndLimitQuery(int page, int itemsPerPage, List<Object> variables) {
        final StringBuilder offsetAndLimitQuery = new StringBuilder();
        if (page > 0) {
            offsetAndLimitQuery.append(" OFFSET ? ");
            variables.add(page * itemsPerPage);
        }
        if (itemsPerPage > 0) {
            offsetAndLimitQuery.append(" LIMIT ? ");
            variables.add(itemsPerPage);
        }
        return offsetAndLimitQuery.toString();
    }
}
