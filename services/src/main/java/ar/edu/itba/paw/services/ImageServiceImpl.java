package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.ImageDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.ImageDto;
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

    private static final Collection<String> CONTENT_TYPE_NO_GIF = Collections.unmodifiableCollection(Arrays.asList("image/png", "image/jpeg"));
    private static final Collection<String> CONTENT_TYPE_GIF = Collections.unmodifiableCollection(Arrays.asList("image/png", "image/jpeg", "image/gif"));

    public Optional<Image> getImageById(Long imageId) {
        LOGGER.debug("Looking for image by id {}", imageId);
        return imageDao.getImageById(imageId);
    }

    public Collection<Image> getImagesById(Collection<Long> imagesId){
        LOGGER.debug("Looking for {} image by id", imagesId.size());
        return imageDao.getImagesById(imagesId);
    }


    public Set<Image> createImages(List<ImageDto> images) {
        LOGGER.debug("Creating {} images", images.size());
        return imageDao.createImages(images);
    }

    public Image createImage(ImageDto image) {
        final Image img = imageDao.createImage(image);
        LOGGER.info("Created image with id {}", img.getImageId());
        return img;
    }


    @Override
    public Collection<String> getContentTypesGIF() {
        LOGGER.debug("Retrieving content types with gif");
        return CONTENT_TYPE_GIF;
    }

    @Override
    public Collection<String> getContentTypesNoGIF() {
        LOGGER.debug("Retrieving content types no gif");
        return CONTENT_TYPE_NO_GIF;
    }

    @Override
    public int deleteImageById(long imageId){
        return imageDao.deleteImageById(imageId);
    }

    @Override
    public int deleteImagesById(List<Long> imagesId){
        return imageDao.deleteImagesById(imagesId);
    }


}
