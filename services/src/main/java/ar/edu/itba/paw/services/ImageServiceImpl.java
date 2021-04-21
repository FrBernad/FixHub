package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.ImageDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.ImageDto;
import ar.edu.itba.paw.models.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    private final  Collection<String> CONTENT_TYPES = Collections.unmodifiableCollection(Arrays.asList("image/png", "image/jpeg", "image/gif"));


    public Optional<Image> getImageById(Long imageId){
        return imageDao.getImageById(imageId);
    }

    public List<Image> createImages(List<ImageDto> images){
        return imageDao.createImages(images);
    }
    public Image createImage(ImageDto image){
        return imageDao.createImage(image);
    }

    public Collection<Image> getImagesByJobId(long jobId){
        return imageDao.getImagesByJobId(jobId);
    }

    @Override
    public void updateImage(ImageDto image,long imageId) {
        imageDao.updateImage(image,imageId);
    }

    @Override
    public Collection<String> getContentTypes(){
        return CONTENT_TYPES;
    }
}
