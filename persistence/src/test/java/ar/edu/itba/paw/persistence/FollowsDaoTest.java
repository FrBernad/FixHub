package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.exceptions.UserAlreadyFollowedException;
import ar.edu.itba.paw.interfaces.persistance.FollowsDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql(scripts = "classpath:follows-dao-test.sql")
public class FollowsDaoTest {

    private static final long ID1 = 1L;
    private static final long ID2 = 2L;


    @Autowired
    private FollowsDao followsDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test(expected = UserAlreadyFollowedException.class)
    public void followUser() {
        followsDao.followUser(ID1, ID2);
    }

    @Test
    public void testFollowUser() {
        followsDao.unfollowUser(ID1, ID2);
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "follows", "f_user_id = " + ID1));
    }

}
