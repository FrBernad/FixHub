package ar.edu.itba.paw.models;

import java.time.LocalDateTime;

public class VerificationToken {

    private String value;
    private long id;
    private long userId;
    private LocalDateTime expirationDate;

    private static final int TOKEN_DURATION_DAYS = 1;

    public static LocalDateTime generateTokenExpirationDate() {
        return LocalDateTime.now().plusDays(TOKEN_DURATION_DAYS);
    }

    public VerificationToken(long id, String value, long userId, LocalDateTime expirationDate) {
        this.value = value;
        this.id = id;
        this.userId = userId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}
