package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.notification.Notification;

import java.util.Optional;

public interface NotificationService {

    Notification createNewFollowerNotification(User user, User resource);

    Notification createNewRequestNotification(User user, JobContact resource);

    Notification createRequestStatusChangeForProvider(User provider, JobContact resource);

    int getUnseenNotificationsCount(User user);

    Optional<Notification> getNotificationById(Long id);

    int deleteNotificationById(Long id);

    void markNotificationAsSeen(Notification notification);

    void markAllNotificationsAsSeen(User user);

    Notification createRequestStatusChangeAcceptForUser(User user, JobContact jc);

    Notification createRequestStatusRejectChangeForUser(User user, JobContact jc);

    Notification createRequestStatusFinishedChangeForUser(User user, JobContact jc);
}
