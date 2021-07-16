package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.user.notification.Notification;
import ar.edu.itba.paw.models.user.notification.NotificationType;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

public class NotificationDto {

    public static Collection<NotificationDto> MapNotificationToDto(Collection<Notification> notifications, UriInfo uriInfo, SecurityContext securityContext) {
        return notifications.stream().map(n -> new NotificationDto(n, uriInfo, securityContext)).collect(Collectors.toList());
    }

    private Long id;
    private Long resource;
    private NotificationType type;
    private LocalDateTime date;
    private UserDto user;
    private boolean seen;


    public NotificationDto() {
    }

    public NotificationDto(Notification notification, UriInfo uriInfo, SecurityContext securityContext) {

        this.id = notification.getId();
        this.resource = notification.getResource();
        this.type = notification.getType();
        this.date = notification.getDate();
        this.user = new UserDto(notification.getUser(), uriInfo, securityContext);
        this.seen = notification.isSeen();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResource() {
        return resource;
    }

    public void setResource(Long resource) {
        this.resource = resource;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
