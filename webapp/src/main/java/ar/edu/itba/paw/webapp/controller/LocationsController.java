package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.StateNotFoundException;
import ar.edu.itba.paw.interfaces.services.LocationService;
import ar.edu.itba.paw.models.location.City;
import ar.edu.itba.paw.models.location.State;
import ar.edu.itba.paw.webapp.dto.response.CityDto;
import ar.edu.itba.paw.webapp.dto.response.StateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.Collection;

@Path("locations")
@Component
public class LocationsController {

    @Autowired
    private LocationService locationService;

    @Context
    UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationsController.class);

    @GET
    @Path("/states")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getStates() {
        LOGGER.info("Accessed /locations/states GET controller");

        Collection<State> states = locationService.getStates();

        Collection<StateDto> stateDtos = StateDto.mapStateToDto(states, uriInfo);

        return Response.ok(new GenericEntity<Collection<StateDto>>(stateDtos) {
        }).build();
    }

    @GET
    @Path("/states/{id}/cities")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getStateCities(@PathParam("id") final long id) {
        LOGGER.info("Accessed /locations/states/{}/cities GET controller", id);

        State state = locationService.getStateById(id).orElseThrow(StateNotFoundException::new);

        Collection<City> cities = locationService.getCitiesByState(state);

        Collection<CityDto> citiesDtos = CityDto.mapCityToDto(cities);

        return Response.ok(new GenericEntity<Collection<CityDto>>(citiesDtos) {
        }).build();
    }

}
