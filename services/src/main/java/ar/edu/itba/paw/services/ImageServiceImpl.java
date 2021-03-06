package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.ImageDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.image.NewImageDto;
import ar.edu.itba.paw.models.job.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Transactional
    public Optional<Image> getImageById(Long imageId) {
        LOGGER.debug("Looking for image by id {}", imageId);
        return imageDao.getImageById(imageId);
    }

    @Transactional
    public Collection<Image> getImagesById(Collection<Long> imagesId) {
        LOGGER.debug("Looking for {} image by id", imagesId.size());
        return imageDao.getImagesById(imagesId);
    }

    @Override
    public Optional<Image> getImageByJob(Job job, Long imageId) {
        return job.getImages().stream().filter(image -> image.getId().equals(imageId)).findFirst();
    }

    @Transactional
    public Set<Image> createImages(List<NewImageDto> images) {
        LOGGER.debug("Creating images");
        return imageDao.createImages(images);
    }

    @Transactional
    public Image createImage(NewImageDto image) {
        final Image img = imageDao.createImage(image);
        LOGGER.info("Created image with id {}", img.getId());
        return img;
    }

    @Transactional
    @Override
    public int deleteImageById(long imageId) {
        LOGGER.info("Deleted image with id {}", imageId);
        return imageDao.deleteImageById(imageId);
    }

    @Transactional
    @Override
    public int deleteImagesById(List<Long> imagesId) {
        LOGGER.info("Deleted images");
        return imageDao.deleteImagesById(imagesId);
    }


}
