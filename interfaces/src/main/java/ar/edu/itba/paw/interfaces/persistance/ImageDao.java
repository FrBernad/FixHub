package ar.edu.itba.paw.interfaces.persistance;


import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.ImageDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ImageDao {

    Set<Image> createImages(List<ImageDto> images);
    Image createImage(ImageDto image);
    Optional<Image> getImageById(Long imageId);
    Collection<Image> getImagesById(Collection<Long> imagesId);
    int deleteImageById(long imageId);
    int deleteImagesById(List<Long> imagesId);
}
