package ar.edu.itba.paw.models.user.notification;

import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notifications_n_id_seq")
    @SequenceGenerator(sequenceName = "notifications_n_id_seq", name = "notifications_n_id_seq", allocationSize = 1)
    @Column(name="n_id")
    private Long id;

    @Column(name="n_resource",length=100)
    private Long resource;

    @Column(name="n_type", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name="n_date",nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="n_user_id",nullable = false)
    private User user;

    @Column(name="n_seen",nullable = false)
    private boolean seen;

    public Notification() {
        //Just for Hibernate
    }

    public Notification(Long resource,
                        LocalDateTime date,
                        NotificationType type,
                        boolean seen,
                        User user) {

        this.resource = resource;
        this.type=type;
        this.date = date;
        this.user = user;
        this.seen = seen;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
