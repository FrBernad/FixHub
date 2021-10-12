package ar.edu.itba.paw.webapp.dto.request;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class PasswordResetDto {

    @NotEmpty
    @Size(min = 6)
    private String password;

    @NotEmpty
    private String token;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}