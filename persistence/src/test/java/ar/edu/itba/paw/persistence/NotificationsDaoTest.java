package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.NotificationDao;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.notification.Notification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.*;

import static org.junit.Assert.*;

@Rollback
@Transactional
@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:notification-dao-test.sql")
public class NotificationsDaoTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private NotificationDao notificationDao;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert imageSimpleJdbcInsert;

    private static final User MOCKED_CLIENT = Mockito.when(Mockito.mock(User.class).getId()).thenReturn(1L).getMock();

    private static final User MOCKED_CLIENT_NO_NOTS = Mockito.when(Mockito.mock(User.class).getId()).thenReturn(2L).getMock();


    @PersistenceContext
    private EntityManager em;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        imageSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("NOTIFICATIONS");
    }

    @Test
    public void TestGetNotificationsByUserNoNotifications() {
        Collection<Notification> notifications = notificationDao.getNotificationsByUser(MOCKED_CLIENT_NO_NOTS, false, 0, 4);
        assertEquals(0, notifications.size());
    }

    @Test
    public void TestGetNotificationsByUserAllNotifications() {
        final Long[] orderedIds = {2L, 1L};

        Collection<Notification> notfications = notificationDao.getNotificationsByUser(MOCKED_CLIENT, false, 0, 4);

        final Collection<Long> resultIds = new LinkedList<>();
        for (Notification not : notfications) {
            resultIds.add(not.getId());
        }

        assertEquals(2, notfications.size());
        assertTrue(notfications.size() <= 10);

        Assert.assertArrayEquals(orderedIds, resultIds.toArray());
    }

    @Test
    public void TestGetNotificationsByUserNotSeenNotifications() {
        Collection<Notification> notfications = notificationDao.getNotificationsByUser(MOCKED_CLIENT, true, 0, 4);

        assertEquals(1, notfications.size());
        assertTrue(notfications.size() <= 10);
    }

}
