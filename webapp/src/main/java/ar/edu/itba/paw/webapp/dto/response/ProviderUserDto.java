package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.dto.customValidations.ProviderDetailsDto;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "")
public class ProviderUserDto extends BaseUserDto {

    private ProviderDetailsDto providerDetails;

    public ProviderUserDto() {
        super();
    }

    public ProviderUserDto(User user, UriInfo uriInfo, SecurityContext securityContext) {
        super(user, uriInfo, securityContext);
        this.providerDetails = new ProviderDetailsDto(user.getProviderDetails(), uriInfo);
    }

    public ProviderDetailsDto getProviderDetails() {
        return providerDetails;
    }

    public void setProviderDetails(ProviderDetailsDto providerDetails) {
        this.providerDetails = providerDetails;
    }

}
