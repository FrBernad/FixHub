package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.NotificationDao;
import ar.edu.itba.paw.interfaces.services.NotificationService;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.notification.Notification;
import ar.edu.itba.paw.models.user.notification.NotificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private Notification createNotification(User user, Long resource, NotificationType type, LocalDateTime date) {
        LOGGER.debug("Creating notification for user with id {} in service", user.getId());
        return notificationDao.createNotification(user, resource, type, date);
    }

    @Transactional
    @Override
    public Optional<Notification> getNotificationById(Long id) {
        LOGGER.debug("Looking for notification with id {} in service", id);
        return notificationDao.getNotificationById(id);
    }

    @Transactional
    @Override
    public int deleteNotificationById(Long id) {
        LOGGER.debug("Deleting notification with id {} in service", id);
        return notificationDao.deleteNotificationById(id);
    }

    @Transactional
    @Override
    public void markNotificationAsSeen(Notification notification) {
        LOGGER.debug("Mark notification as seen with id {} in service", notification.getId());
        notification.setSeen(true);
    }

    @Transactional
    @Override
    public void markAllNotificationsAsSeen(User user) {
        LOGGER.debug("Mark all notification as seen of the user with id {} in service", user.getId());
        notificationDao.markAllNotificationAsSeen(user);
    }


    @Transactional
    @Override
    public Notification createNewFollowerNotification(User user, User resource) {
        LOGGER.debug("Creating a new follower notification for user with id {} for the resource with id {}", user.getId(), resource.getId());
        return createNotification(user, resource.getId(), NotificationType.NEW_FOLLOWER, LocalDateTime.now());

    }

    @Transactional
    @Override
    public Notification createNewRequestNotification(User user, JobContact resource) {
        LOGGER.debug("Creating a new request notification for user with id {} for the job contact with id {}", user.getId(), resource.getId());
        return createNotification(user, resource.getId(), NotificationType.JOB_REQUEST, LocalDateTime.now());

    }

    @Transactional
    @Override
    public Notification createRequestStatusChangeAcceptForUser(User user, JobContact jc) {
        LOGGER.debug("Creating a new request status change to accept notification for user with id {} " +
            "for the job contact with id {} ", user.getId(), jc.getId());
        return createNotification(user, jc.getId(), NotificationType.REQUEST_STATUS_CHANGE_USER_ACCEPTED, LocalDateTime.now());
    }

    @Transactional
    @Override
    public Notification createRequestStatusRejectChangeForUser(User user, JobContact jc) {
        LOGGER.debug("Creating a new request status change to reject notification for user with id {} " +
            "for the job contact with id {}", user.getId(), jc.getId());
        return createNotification(user, jc.getId(), NotificationType.REQUEST_STATUS_CHANGE_USER_REJECTED, LocalDateTime.now());
    }


    @Transactional
    @Override
    public Notification createRequestStatusFinishedChangeForUser(User user, JobContact jc) {
        LOGGER.debug("Creating a new request status change to finished notification for user with id {} " +
            "for the job contact with id {}", user.getId(), jc.getId());
        return createNotification(user, jc.getId(), NotificationType.REQUEST_STATUS_CHANGE_USER_FINISHED, LocalDateTime.now());
    }

    @Transactional
    @Override
    public Notification createRequestStatusChangeForProvider(User provider, JobContact resource) {
        LOGGER.debug("Creating a new request status notification for user with id {} for the job contact with id {}",
            provider.getId(), resource.getId());
        return createNotification(provider, resource.getId(), NotificationType.REQUEST_STATUS_CHANGE_PROVIDER, LocalDateTime.now());
    }

    @Transactional
    @Override
    public int getUnseenNotificationsCount(User user) {
        return notificationDao.getNotificationCountByUser(user, true);
    }
}

