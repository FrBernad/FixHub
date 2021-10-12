package ar.edu.itba.paw.webapp.dto.request;

import org.hibernate.validator.constraints.NotEmpty;

public class TokenDto {

    @NotEmpty
    String token;

    public TokenDto() {
    }

    public TokenDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
