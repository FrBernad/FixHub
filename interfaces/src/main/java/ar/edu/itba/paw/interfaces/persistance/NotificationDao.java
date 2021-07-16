package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.notification.Notification;
import ar.edu.itba.paw.models.user.notification.NotificationType;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface NotificationDao {

    Notification createNotification(User user, Long resource, NotificationType type, LocalDateTime date);

    Optional<Notification> getNotificationById(Long id);

    int deleteNotificationById(Long id);

    int getNotificationCountByUser(User user, boolean onlyNew);

    Collection<Notification> getNotificationsByUser(User user, boolean onlyNew, int page, int pageSize);

    void markAllNotificationAsSeen(User user);
}
