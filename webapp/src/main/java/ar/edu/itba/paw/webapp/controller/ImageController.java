package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.image.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

@Path("images")
@Component
public class ImageController {

    @Autowired
    private ImageService imageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    @GET
    @Path("/{id}")
    @Produces({"image/*", javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response getJobImage(@PathParam("id") long id, @Context Request request) {
        LOGGER.info("Accessed images/{} GET controller", id);
        Image img = imageService.getImageById(id).orElseThrow(ImageNotFoundException::new);
        LOGGER.info("Response image with id {}", id);
//        Fixme: si no existe
        final EntityTag eTag = new EntityTag(String.valueOf(img.getId()));

        final CacheControl cacheControl = new CacheControl();
        cacheControl.setNoCache(true);

        Response.ResponseBuilder responseBuilder = request.evaluatePreconditions(eTag);

        if (responseBuilder == null) {
            final byte[] jobImage = img.getData();
            responseBuilder = Response.ok(jobImage).type(img.getMimeType()).tag(eTag);
        }

        return responseBuilder.cacheControl(cacheControl).build();
    }

}
