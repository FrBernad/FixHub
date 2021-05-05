package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    @RequestMapping(path = "jobs/images/{imageId}",
        produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE},
        method = RequestMethod.GET)
    @ResponseBody
    public byte[] getJobImage(@PathVariable("imageId") long imageId) {
        LOGGER.info("Accessed jobs/images/{} GET controller", imageId);
        Image image = imageService.getImageById(imageId).orElseThrow(ImageNotFoundException::new);
        return image.getData();
    }

    @RequestMapping(path = "/user/images/profile/{imageId}",
        produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE},
        method = RequestMethod.GET)
    @ResponseBody
    public byte[] getProfileImage(@PathVariable("imageId") long imageId) {
        LOGGER.info("Accessed /user/images/profile/{} GET controller", imageId);
        Image image = imageService.getImageById(imageId).orElseThrow(ImageNotFoundException::new);
        return image.getData();
    }
}
