package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.StateNotFoundException;
import ar.edu.itba.paw.interfaces.services.LocationService;
import ar.edu.itba.paw.models.location.City;
import ar.edu.itba.paw.models.location.State;
import ar.edu.itba.paw.webapp.dto.CityDto;
import ar.edu.itba.paw.webapp.dto.StateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.stream.Collectors;

@Path("locations")
@Component
public class LocationsController {

    @Autowired
    private LocationService locationService;

    @GET
    @Path("/states")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getStates() {

        Collection<State> states = locationService.getStates();

        Collection<StateDto> stateDtos = StateDto.mapStateToDto(states);

        return Response.ok(new GenericEntity<Collection<StateDto>>(stateDtos) {
        }).build();
    }

    @GET
    @Path("/state/{id}/cities")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getStateCities(@PathParam("id") final long id) {

        State state = locationService.getStateById(id).orElseThrow(StateNotFoundException::new);

        Collection<City> cities = locationService.getCitiesByState(state);

        Collection<CityDto> citiesDtos = CityDto.mapCityToDto(cities);

        return Response.ok(new GenericEntity<Collection<CityDto>>(citiesDtos) {
        }).build();
    }

}
