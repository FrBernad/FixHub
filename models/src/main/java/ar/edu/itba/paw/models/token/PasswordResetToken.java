package ar.edu.itba.paw.models.token;

import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "password_reset_tokens_prt_id_seq")
    @SequenceGenerator(sequenceName = "password_reset_tokens_prt_id_seq", name = "password_reset_tokens_prt_id_seq", allocationSize = 1)
    @Column(name = "prt_id")
    private long id;

    @OneToOne
    @JoinColumn(name = "prt_user_id")
    private User user;

    @Column(name = "prt_token", nullable = false)
    private String value;

    @Column(name = "prt_expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    private static final int TOKEN_DURATION_DAYS = 1;

    /* default */
    protected PasswordResetToken() {
        // Just for Hibernate
    }

    public static LocalDateTime generateTokenExpirationDate() {
        return LocalDateTime.now().plusDays(TOKEN_DURATION_DAYS);
    }

    public PasswordResetToken(String value, User user, LocalDateTime expirationDate) {
        this.value = value;
        this.user = user;
        this.expirationDate = expirationDate;
    }

    public boolean isValid() {
        return expirationDate.compareTo(LocalDateTime.now()) > 0;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}
