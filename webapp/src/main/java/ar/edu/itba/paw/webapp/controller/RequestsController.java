package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.IllegalOperationException;
import ar.edu.itba.paw.interfaces.exceptions.NoContactFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.dto.response.JobContactDto;
import ar.edu.itba.paw.webapp.dto.response.SearchOptionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.Collection;

@Path("/requests")
public class RequestsController {

    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    @Context
    private SecurityContext securityContext;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestsController.class);

    @GET
    @Path("/searchOptions")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getRequestsSearchOptions(
    ) {
        LOGGER.info("Accessed /requests/searchOptions GET controller");
        final Collection<SearchOptionDto> searchOptions = new ArrayList<>();
        searchOptions.add(new SearchOptionDto("status", jobService.getJobsRequestsStatus()));
        searchOptions.add(new SearchOptionDto("order", jobService.getJobsRequestsOrder()));

        return Response.ok(new GenericEntity<Collection<SearchOptionDto>>(searchOptions) {
        }).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getReceivedRequest(@PathParam("id") final long id) {

        LOGGER.info("Accessed /requests/{} GET controller", id);

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);

        final JobContact jobContact = jobService.getContactById(id).orElseThrow(NoContactFoundException::new);

        if (!user.getId().equals(jobContact.getProvider().getId()) || !user.getId().equals(jobContact.getUser().getId())) {
            throw new ForbiddenException();
        }

        final JobContactDto jobContactDto = new JobContactDto(jobContact, uriInfo);

        return Response.ok(jobContactDto).build();
    }

}
