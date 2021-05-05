package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.ImageDto;

import java.util.*;

public interface ImageService {

    List<Image> createImages(List<ImageDto> images);
    Image createImage(ImageDto image);

    Collection<String> getContentTypes();


    Optional<Image> getImageById(Long imageId);
    Collection<Image> getImagesByJobId(long jobId);


    void updateImage(ImageDto image,long imageId);

    int deleteImageById(long imageId);

    int deleteImagesById(List<Long> imagesId);

}
