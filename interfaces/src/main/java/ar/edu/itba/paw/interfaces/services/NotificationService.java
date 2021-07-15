package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.notification.AuxNotificationDto;
import ar.edu.itba.paw.models.user.notification.Notification;

import java.util.Optional;

public interface NotificationService {

    Notification createNotification(User user, AuxNotificationDto notificationDto);
    Optional<Notification> getNotificationById(Long id);
    Optional<Notification> getNotificationByUser(User user);
    int deleteNotificationById(Long id);
    void markNotificationAsSeen(long id);
    void markAllNotificationsAsSeen(User user);
}
