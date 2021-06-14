package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.image.ImageDto;

import java.util.*;

public interface ImageService {

    Set<Image> createImages(List<ImageDto> images);
    Image createImage(ImageDto image);

    Optional<Image> getImageById(Long imageId);
    Collection<Image> getImagesById(Collection<Long> imagesId);

    int deleteImageById(long imageId);

    int deleteImagesById(List<Long> imagesId);

}
