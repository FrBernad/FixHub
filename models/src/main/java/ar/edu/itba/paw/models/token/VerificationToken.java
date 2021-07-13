package ar.edu.itba.paw.models.token;

import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "verification_tokens")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "verification_tokens_vt_id_seq")
    @SequenceGenerator(sequenceName = "verification_tokens_vt_id_seq", name = "verification_tokens_vt_id_seq", allocationSize = 1)
    @Column(name = "vt_id")
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vt_user_id")
    private User user;

    @Column(name = "vt_token", nullable = false)
    private String value;

    @Column(name = "vt_expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    private static final int TOKEN_DURATION_DAYS = 1;

    public static LocalDateTime generateTokenExpirationDate() {
        return LocalDateTime.now().plusDays(TOKEN_DURATION_DAYS);
    }

    /* default */
    protected VerificationToken() {
        // Just for Hibernate
    }

    public VerificationToken(String value, User user, LocalDateTime expirationDate) {
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
