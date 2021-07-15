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
import javax.ws.rs.core.Response;

@Path("images")
@Component
public class ImageController {

    @Autowired
    private ImageService imageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    @GET
    @Path("/{imageId}")
    @Produces({"image/*", javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response getJobImage(@PathParam("imageId") long imageId) {
        LOGGER.info("Accessed images/{} GET controller", imageId);
        Image image = imageService.getImageById(imageId).orElseThrow(ImageNotFoundException::new);
        LOGGER.info("Response image with id {}", imageId);
        return Response.ok(image.getData()).type(image.getMimeType()).build();
    }

}
