package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.contact.ContactInfo;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.dto.customValidations.ProviderDetailsDto;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlType;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@XmlType(name = "")
public class SessionUserDto extends BaseUserDto {

    private Set<ContactInfoDto> contactInfo;

    public SessionUserDto() {
        super();
    }

    private ProviderDetailsDto providerDetails;

    public SessionUserDto(User user, UriInfo uriInfo, SecurityContext securityContext) {
        super(user, uriInfo, securityContext);
        this.contactInfo = user.getContactInfo().stream().map(ContactInfoDto::new).collect(Collectors.toSet());
        if (user.isProvider()) {
            this.providerDetails = new ProviderDetailsDto(user.getProviderDetails());
        }
    }

    public Set<ContactInfoDto> getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(Set<ContactInfoDto> contactInfo) {
        this.contactInfo = contactInfo;
    }

    public ProviderDetailsDto getProviderDetails() {
        return providerDetails;
    }

    public void setProviderDetails(ProviderDetailsDto providerDetails) {
        this.providerDetails = providerDetails;
    }
}
