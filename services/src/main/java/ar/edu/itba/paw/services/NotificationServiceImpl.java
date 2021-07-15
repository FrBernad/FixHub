package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.NotificationDao;
import ar.edu.itba.paw.interfaces.services.NotificationService;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.notification.AuxNotificationDto;
import ar.edu.itba.paw.models.user.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Transactional
    @Override
    public Notification createNotification(User user, AuxNotificationDto notificationDto) {
        LOGGER.debug("Creating notification for user with id {} in service", user.getId());
        return notificationDao.createNotification(user, notificationDto);
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
    public void createNewJobNotification(User user) {

    }

    @Transactional
    @Override
    public void createNewFollowerNotification(User user) {

    }

    @Transactional
    @Override
    public void createNewRequestNotification(User user) {

    }

    @Override
    public void createRequestStatusChange(User user) {

    }
}
