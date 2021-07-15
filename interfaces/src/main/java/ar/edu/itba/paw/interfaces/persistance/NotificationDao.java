package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.notification.AuxNotificationDto;
import ar.edu.itba.paw.models.user.notification.Notification;

import java.util.Optional;

public interface NotificationDao {

    Notification createNotification(User user, AuxNotificationDto notificationDto);
    Optional<Notification> getNotificationsByUser(User user);
    Optional<Notification> getNotificationById(Long id);
    int deleteNotificationById(Long id);
    void markNotificationAsSeen(long id);
    void markAllNotificationAsSeen(User user);
}
