package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.ImageDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.ImageDto;
import ar.edu.itba.paw.models.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

    private static final Collection<String> CONTENT_TYPES = Collections.unmodifiableCollection(Arrays.asList("image/png", "image/jpeg", "image/gif"));

    public Optional<Image> getImageById(Long imageId) {
        LOGGER.debug("Looking for image by id {}", imageId);
        return imageDao.getImageById(imageId);
    }

    public List<Image> createImages(List<ImageDto> images) {
        LOGGER.debug("Creating {} images", images.size());
        return imageDao.createImages(images);
    }

    public Image createImage(ImageDto image) {
        Image img = imageDao.createImage(image);
        LOGGER.info("Created image with id {}", img.getImageId());
        return img;
    }

    public Collection<Image> getImagesByJobId(long jobId) {
        LOGGER.debug("Retrieving images for job {}", jobId);
        return imageDao.getImagesByJobId(jobId);
    }

    @Override
    public void updateImage(ImageDto image, long imageId) {
        LOGGER.debug("Updating image with id {}", imageId);
        imageDao.updateImage(image, imageId);
    }

    @Override
    public Collection<String> getContentTypes() {
        LOGGER.debug("Retrieving content types");
        return CONTENT_TYPES;
    }

    @Override
    public int deleteImageById(long imageId){
        return imageDao.deleteImageById(imageId);
    }

}
