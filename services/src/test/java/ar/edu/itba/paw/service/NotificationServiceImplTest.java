package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistance.NotificationDao;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.notification.Notification;
import ar.edu.itba.paw.models.user.notification.NotificationType;
import ar.edu.itba.paw.services.NotificationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceImplTest {

    private static final Long FOLLOWER_RESOURCE = 2L;
    private static final Long JOB_CONTACT_RESOURCE=3L;


    @Mock
    private User DEFAULT_USER;

    @Mock
    private User DEFAULT_USER2;

    @Mock
    private JobContact JOB_CONTACT;

    private static final LocalDateTime CREATION_DATE= LocalDateTime.of(2021,7,17,15,0);


    @InjectMocks
    private final NotificationServiceImpl notificationService = new NotificationServiceImpl();

    @Mock
    private NotificationDao mockNotificationDao;

    @Test
    public void testCreateNewFollowerNotification() {
        Notification notification = new Notification(FOLLOWER_RESOURCE,
            CREATION_DATE,NotificationType.NEW_FOLLOWER,false,DEFAULT_USER);

        lenient().when(mockNotificationDao.createNotification
            (eq(DEFAULT_USER),
                eq(FOLLOWER_RESOURCE),
                eq(NotificationType.NEW_FOLLOWER),
                any())).thenReturn(notification);

        when(DEFAULT_USER2.getId()).thenReturn(FOLLOWER_RESOURCE);

        notificationService.createNewFollowerNotification(DEFAULT_USER,DEFAULT_USER2);
        verify(mockNotificationDao).createNotification(eq(DEFAULT_USER),eq(FOLLOWER_RESOURCE),eq(NotificationType.NEW_FOLLOWER),any());

    }

    @Test
    public void testCreateNewRequestNotification() {
        Notification notification = new Notification(JOB_CONTACT_RESOURCE,
            CREATION_DATE,NotificationType.JOB_REQUEST,false,DEFAULT_USER);

        lenient().when(mockNotificationDao.createNotification
            (eq(DEFAULT_USER),
                eq(JOB_CONTACT_RESOURCE),
                eq(NotificationType.JOB_REQUEST),
                any())).thenReturn(notification);

        when(JOB_CONTACT.getId()).thenReturn(JOB_CONTACT_RESOURCE);

        notificationService.createNewRequestNotification(DEFAULT_USER,JOB_CONTACT);
        verify(mockNotificationDao).createNotification(eq(DEFAULT_USER),eq(JOB_CONTACT_RESOURCE),eq(NotificationType.JOB_REQUEST),any());

    }

    @Test
    public void testCreateRequestStatusChangeForProvider() {
        Notification notification = new Notification(JOB_CONTACT_RESOURCE,
            CREATION_DATE,NotificationType.REQUEST_STATUS_CHANGE_PROVIDER,false,DEFAULT_USER);

        lenient().when(mockNotificationDao.createNotification
            (eq(DEFAULT_USER),
                eq(JOB_CONTACT_RESOURCE),
                eq(NotificationType.REQUEST_STATUS_CHANGE_PROVIDER),
                any())).thenReturn(notification);

        when(JOB_CONTACT.getId()).thenReturn(JOB_CONTACT_RESOURCE);

        notificationService.createRequestStatusChangeForProvider(DEFAULT_USER,JOB_CONTACT);

        verify(mockNotificationDao).createNotification(eq(DEFAULT_USER),eq(JOB_CONTACT_RESOURCE),
            eq(NotificationType.REQUEST_STATUS_CHANGE_PROVIDER),
            any());

    }

    @Test
    public void testGetUnseenNotificationsCount() {
        notificationService.getUnseenNotificationsCount(DEFAULT_USER);
        verify(mockNotificationDao).getNotificationCountByUser(DEFAULT_USER,true);

    }

    @Test
    public void testMarkAllNotificationsAsSeen() {
        notificationService.markAllNotificationsAsSeen(DEFAULT_USER);
        verify(mockNotificationDao).markAllNotificationAsSeen(DEFAULT_USER);


    }

    @Test
    public void testCreateRequestStatusChangeAcceptForUser() {
        Notification notification = new Notification(JOB_CONTACT_RESOURCE,
            CREATION_DATE,NotificationType.REQUEST_STATUS_CHANGE_USER_ACCEPTED,false,DEFAULT_USER);

        lenient().when(mockNotificationDao.createNotification
            (eq(DEFAULT_USER),
                eq(JOB_CONTACT_RESOURCE),
                eq(NotificationType.REQUEST_STATUS_CHANGE_USER_ACCEPTED),
                any())).thenReturn(notification);

        when(JOB_CONTACT.getId()).thenReturn(JOB_CONTACT_RESOURCE);

        notificationService.createRequestStatusChangeAcceptForUser(DEFAULT_USER,JOB_CONTACT);

        verify(mockNotificationDao).createNotification(eq(DEFAULT_USER),eq(JOB_CONTACT_RESOURCE),
            eq(NotificationType.REQUEST_STATUS_CHANGE_USER_ACCEPTED),
            any());

    }

    @Test
    public void testCreateRequestStatusRejectChangeForUser() {
        Notification notification = new Notification(JOB_CONTACT_RESOURCE,
            CREATION_DATE,NotificationType.REQUEST_STATUS_CHANGE_USER_REJECTED,false,DEFAULT_USER);

        lenient().when(mockNotificationDao.createNotification
            (eq(DEFAULT_USER),
                eq(JOB_CONTACT_RESOURCE),
                eq(NotificationType.REQUEST_STATUS_CHANGE_USER_REJECTED),
                any())).thenReturn(notification);

        when(JOB_CONTACT.getId()).thenReturn(JOB_CONTACT_RESOURCE);

        notificationService.createRequestStatusRejectChangeForUser(DEFAULT_USER,JOB_CONTACT);

        verify(mockNotificationDao).createNotification(eq(DEFAULT_USER),eq(JOB_CONTACT_RESOURCE),
            eq(NotificationType.REQUEST_STATUS_CHANGE_USER_REJECTED),
            any());

    }

    @Test
    public void testCreateRequestStatusFinishedChangeForUser() {
        Notification notification = new Notification(JOB_CONTACT_RESOURCE,
            CREATION_DATE,NotificationType.REQUEST_STATUS_CHANGE_USER_FINISHED,false,DEFAULT_USER);

        lenient().when(mockNotificationDao.createNotification
            (eq(DEFAULT_USER),
                eq(JOB_CONTACT_RESOURCE),
                eq(NotificationType.REQUEST_STATUS_CHANGE_USER_FINISHED),
                any())).thenReturn(notification);

        when(JOB_CONTACT.getId()).thenReturn(JOB_CONTACT_RESOURCE);

        notificationService.createRequestStatusFinishedChangeForUser(DEFAULT_USER,JOB_CONTACT);

        verify(mockNotificationDao).createNotification(eq(DEFAULT_USER),eq(JOB_CONTACT_RESOURCE),
            eq(NotificationType.REQUEST_STATUS_CHANGE_USER_FINISHED),
            any());

    }

}
