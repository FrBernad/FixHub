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

    public UserDto() {
        super();
        // Used by Jersey
    }

    public UserDto(User user, UriInfo uriInfo, SecurityContext securityContext) {
        super(user, uriInfo, securityContext);
    }

}
