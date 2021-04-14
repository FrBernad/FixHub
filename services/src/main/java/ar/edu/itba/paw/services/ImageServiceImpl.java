package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.ImageDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.ImageDto;
import ar.edu.itba.paw.models.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    public Optional<Image> getImageById(Long imageId){
        return imageDao.getImageById(imageId);
    }

    public List<Image> createImages(List<ImageDto> images){
        return imageDao.createImages(images);
    }
    public Image createImage(ImageDto image){
        return imageDao.createImage(image);
    }
}
