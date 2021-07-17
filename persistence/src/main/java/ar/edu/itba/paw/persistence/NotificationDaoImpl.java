package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.NotificationDao;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.notification.Notification;
import ar.edu.itba.paw.models.user.notification.NotificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class NotificationDaoImpl implements NotificationDao {


    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationDaoImpl.class);

    @Override
    public Notification createNotification(User user, Long resource, NotificationType type, LocalDateTime date) {
        Notification notification = new Notification(
            resource, date, type, false, user);
        em.persist(notification);
        LOGGER.info("Created notification with id {} for user with id {}", notification.getId(), user.getId());
        return notification;
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
    public int getNotificationCountByUser(User user, boolean onlyNew) {
        final String filterQuery = getNotificationFilterQuery(onlyNew);

        final String query = "SELECT count(n_id) total FROM NOTIFICATIONS WHERE n_user_id = ? " + filterQuery;

        Query nativeQuery = em.createNativeQuery(query);

        nativeQuery.setParameter(1, user.getId());

        return ((BigInteger) nativeQuery.getSingleResult()).intValue();
    }

    @Override
    public Collection<Notification> getNotificationsByUser(User user, boolean onlyNew, int page, int pageSize) {
        final List<Object> variables = new LinkedList<>();

        variables.add(user.getId());

        final String offsetAndLimitQuery = getOffsetAndLimitQuery(page, pageSize, variables);

        final String filterQuery = getNotificationFilterQuery(onlyNew);

        final String filteredIdsSelectQuery =
            " select n_id " +
                " from NOTIFICATIONS " +
                " WHERE n_user_id = ? " + filterQuery +
                " order by n_seen asc, n_date desc, n_id desc " + offsetAndLimitQuery;

        Query filteredIdsSelectNativeQuery = em.createNativeQuery(filteredIdsSelectQuery);

        setQueryVariables(filteredIdsSelectNativeQuery, variables);

        @SuppressWarnings("unchecked") final List<Long> filteredIds = ((List<Number>) filteredIdsSelectNativeQuery.getResultList()).stream().map(Number::longValue).collect(Collectors.toList());

        if (filteredIds.isEmpty())
            return Collections.emptyList();


        return em.createQuery("from Notification n where id IN :filteredIds order by n.seen asc, n.date desc, n.id desc", Notification.class)
            .setParameter("filteredIds", filteredIds)
            .getResultList();
    }

    @Override
    public void markAllNotificationAsSeen(User user) {
        final Query query = em.createQuery("update Notification n set n.seen = :value where n.user.id = :userId");
        query.setParameter("value", true);
        query.setParameter("userId", user.getId());
        int seenNotifications = query.executeUpdate();
        LOGGER.info("{} notification of the user with id {} were marked as seen", seenNotifications, user.getId());
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

    private void setQueryVariables(Query query, Collection<Object> variables) {
        int i = 1;
        for (Object variable : variables) {
            query.setParameter(i, variable);
            i++;
        }
    }

    String getNotificationFilterQuery(boolean onlyNew) {
        final StringBuilder filterQuery = new StringBuilder();
        if (onlyNew) {
            filterQuery.append(" AND n_seen is false ");
        }
        return filterQuery.toString();
    }


}

