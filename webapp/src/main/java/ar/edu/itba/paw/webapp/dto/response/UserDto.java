package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.user.User;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.stream.Collectors;

@XmlType(name = "")
public class UserDto extends BaseUserDto {

    public static Collection<UserDto> mapUserToDto(Collection<User> users, UriInfo uriInfo, SecurityContext securityContext) {
        return users.stream().map(u -> new UserDto(u, uriInfo, securityContext)).collect(Collectors.toList());
    }

    private boolean followed;
    private boolean following;

    public UserDto() {
        super();
        // Used by Jersey
    }

    public UserDto(User user, UriInfo uriInfo, SecurityContext securityContext) {
        super(user, uriInfo, securityContext);
        System.out.println(securityContext.getUserPrincipal());
        if (securityContext.getUserPrincipal() != null) {
            this.following = user.userIsFollowing(securityContext.getUserPrincipal().getName());
            this.followed = user.userIsFollower(securityContext.getUserPrincipal().getName());
        }

    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }
}
