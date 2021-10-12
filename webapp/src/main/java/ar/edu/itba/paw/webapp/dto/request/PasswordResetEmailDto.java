package ar.edu.itba.paw.webapp.dto.request;

public class PasswordResetEmailDto {
    String email;

    public PasswordResetEmailDto() {
    }

    public PasswordResetEmailDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
