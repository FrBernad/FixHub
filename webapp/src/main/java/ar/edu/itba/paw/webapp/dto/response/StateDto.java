package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.location.State;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.stream.Collectors;

@XmlType(name = "")
public class StateDto {

    public static Collection<StateDto> mapStateToDto(Collection<State> states, UriInfo uriInfo) {
        return states.stream().map(state -> new StateDto(state,uriInfo)).collect(Collectors.toList());
    }

    private long id;
    private String citiesUrl;

    private String name;

    public StateDto() {
    }

    public StateDto(State state, UriInfo uriInfo) {
        this.id = state.getId();
        this.name = state.getName();
        this.citiesUrl = uriInfo.getBaseUriBuilder()
            .path("locations")
            .path("states")
            .path(String.valueOf(state.getId()))
            .path("cities")
            .build().toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCitiesUrl() {
        return citiesUrl;
    }

    public void setCitiesUrl(String citiesUrl) {
        this.citiesUrl = citiesUrl;
    }
}
