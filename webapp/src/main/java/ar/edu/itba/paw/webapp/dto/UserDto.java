package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.user.User;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class UserDto {

    private long id;
    private String username;

    public UserDto() {
        // Used by Jersey
    }

    public UserDto(User user, UriInfo uriInfo) {
        this.id = user.getId();
        this.username = user.getName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
