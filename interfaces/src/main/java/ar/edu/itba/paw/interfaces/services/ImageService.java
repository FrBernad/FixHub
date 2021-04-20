package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.ImageDto;
import ar.edu.itba.paw.models.Job;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ImageService {

    List<Image> createImages(List<ImageDto> images);
    Image createImage(ImageDto image);

    Optional<Image> getImageById(Long imageId);
    Collection<Image> getImagesByJobId(long jobId);


    void updateImage(ImageDto image,long imageId);
}
