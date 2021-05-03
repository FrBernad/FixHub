package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.exceptions.UserAlreadyFollowedException;
import ar.edu.itba.paw.interfaces.persistance.FollowsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class FollowsDaoImpl implements FollowsDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert userFollowsSimpleJdbcInsert;

    private static final Logger LOGGER = LoggerFactory.getLogger(FollowsDaoImpl.class);

    @Autowired
    public FollowsDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        userFollowsSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("FOLLOWS");
    }

    @Override
    public void followUser(Long userId, Long userToFollowId) {
        final Map<String, Object> newFollow = new HashMap<>();
        newFollow.put("f_user_id", userId);
        newFollow.put("f_followed_user_id", userToFollowId);
        try {
            userFollowsSimpleJdbcInsert.execute(newFollow);
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyFollowedException();
        }
        LOGGER.info("User {} now follows user {}", userId, userToFollowId);
    }

    @Override
    public void unfollowUser(Long userId, Long userToFollowId) {
        final String query = "DELETE from FOLLOWS where f_user_id = ? and f_followed_user_id = ?";
        LOGGER.debug("Executing query: {}", query);

        jdbcTemplate.update(query, userId, userToFollowId);

        LOGGER.info("User {} unfollowed user {}", userId, userToFollowId);
    }

}
