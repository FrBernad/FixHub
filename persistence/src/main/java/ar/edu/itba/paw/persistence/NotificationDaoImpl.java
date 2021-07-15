package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.NotificationDao;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.notification.AuxNotificationDto;
import ar.edu.itba.paw.models.user.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class NotificationDaoImpl implements NotificationDao {


    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationDaoImpl.class);

    @Override
    public Notification createNotification(User user, AuxNotificationDto notificationDto) {
        Notification notification = new Notification(notificationDto.getTitle(), notificationDto.getBody(),
            notificationDto.getResource(), notificationDto.getDate(),notificationDto.getType(),false,user);
        em.persist(notification);
        LOGGER.info("Created notification with id {} for user with id {}", notification.getId(), user.getId());
        return null;
    }

    @Override
    public Optional<Notification> getNotificationById(Long id) {
        final TypedQuery<Notification> query = em.createQuery("from Notification as n where n.id = :notificationId", Notification.class);
        query.setParameter("notificationId", id);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public int deleteNotificationById(Long id) {
        LOGGER.info("Trying to delete the notification with id {}", id);

        final Query query = em.createQuery("delete from Notification as n where n.id = :notificationId");
        query.setParameter("notificationId", id);
        int aux = query.executeUpdate();

        if (aux == 0)
            LOGGER.warn("Error trying to delete notification with non-existent id {}", id);
        else
            LOGGER.info("The notification with id {} has been deleted successfully", id);

        return aux;
    }

    @Override
    public void markAllNotificationAsSeen(User user) {
        final TypedQuery<Notification> query = em.createQuery("update Notification n set n.seen = :value where n.user.id = :userId", Notification.class);
        query.setParameter("value", true);
        query.setParameter("userId",user.getId());
        int seenNotifications = query.executeUpdate();
        LOGGER.info("{} notification of the user with id {} were marked as seen", seenNotifications,user.getId());
    }
}
