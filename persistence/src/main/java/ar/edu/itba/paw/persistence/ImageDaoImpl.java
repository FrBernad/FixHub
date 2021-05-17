package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.ImageDao;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.ImageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class ImageDaoImpl implements ImageDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageDaoImpl.class);

    @Override
    public Set<Image> createImages(List<ImageDto> imageDtoList) {
        final Set<Image> images = new LinkedHashSet<>();
        Image aux;
        for(ImageDto image: imageDtoList){
            aux = createImage(image);
            LOGGER.info("Image with id {} has been created succesfully",aux.getImageId());
            images.add(aux);
        }
        return images;
    }

    @Override
    public Image createImage(ImageDto image) {
        LOGGER.info("Trying to create a new image");
        Image img = new Image(image.getData(),image.getMimeType());
        em.persist(img);
        return img;
    }

    @Override
    public Optional<Image> getImageById(Long imageId) {
        final TypedQuery<Image> query = em.createQuery("from Image as i where i.imageId = :imageId",Image.class);
        query.setParameter("imageId",imageId);
        return query.getResultList().stream().findFirst();
    }

    public Collection<Image> getImagesById(Collection<Long> imagesId){
        final Collection<Image> images = new LinkedList<>();
        Optional<Image> image;
        for(Long imageId: imagesId){
            image = getImageById(imageId);
            image.ifPresent(images::add);
        }
        return images;
    }

    @Override
    public int deleteImageById(long imageId) {
        LOGGER.info("Trying to deleted the image with id {}",imageId);

        final TypedQuery<Image> query = em.createQuery("delete from Image as i where i.imageId = :imageId",Image.class);
        query.setParameter("imageId",imageId);
        int aux = query.executeUpdate();
        if(aux == 0 )
            LOGGER.warn("Error trying to delete an image with non-existent id {}",imageId);
        else
            LOGGER.info("The image with id {} has been deleted successfully",imageId);

        return query.executeUpdate();
    }

    @Override
    public int deleteImagesById(List<Long> imagesId) {
        int res = 0;
        for(Long imageId: imagesId){
            res+=deleteImageById(imageId);
        }
        return res;
    }

}


