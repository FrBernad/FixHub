package ar.edu.itba.paw.models.user.notification;

import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

public class AuxNotificationDto {

    private String title;

    private String body;

    private Long resource;

    private NotificationType type;

    private LocalDateTime date;

    private User user;

    public AuxNotificationDto(String title,
                              String body,
                              Long resource,
                              NotificationType type,
                              LocalDateTime date,
                              User user) {
        this.title = title;
        this.body = body;
        this.resource = resource;
        this.type = type;
        this.date = date;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
