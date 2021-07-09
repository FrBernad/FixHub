package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.image.Image;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

public class ImageDto {

    public static UriBuilder getImageUriBuilder(Image image, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().clone().path("images").path(String.valueOf(image.getId()));
    }

}
