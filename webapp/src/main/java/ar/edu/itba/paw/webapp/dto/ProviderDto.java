package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.dto.customValidations.ProviderDetailsDto;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

public class ProviderDto extends  UserDto{

    private ProviderDetailsDto providerDetails;

    public ProviderDto(){
    }

    public ProviderDto(User user, UriInfo uriInfo, SecurityContext securityContext) {
        super(user, uriInfo, securityContext);
        this.providerDetails = new ProviderDetailsDto(user.getProviderDetails());
    }


    public ProviderDetailsDto getProviderDetails() {
        return providerDetails;
    }

    public void setProviderDetails(ProviderDetailsDto providerDetails) {
        this.providerDetails = providerDetails;
    }

}
