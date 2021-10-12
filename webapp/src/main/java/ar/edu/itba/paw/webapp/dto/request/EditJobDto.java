package ar.edu.itba.paw.webapp.dto.request;


import ar.edu.itba.paw.webapp.dto.customValidations.ImageSizeConstraint;
import ar.edu.itba.paw.webapp.dto.customValidations.ImageTypeConstraint;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public class EditJobDto {

    @NotEmpty
    @Size(max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$")
    private String jobProvided;

    @NotEmpty
    @Size(max = 300)
    private String description;

    @NotNull
    @Range(min = 1, max = 999999)
    private BigDecimal price;

    @Size(max = 6)
    @ImageTypeConstraint(contentType = {"image/png","image/jpeg"})
    @ImageSizeConstraint(size=3000000)
    private List<MultipartFile> images;

    private List<Long> imagesIdDeleted;

    private boolean paused;

    public String getJobProvided() {
        return jobProvided;
    }

    public void setJobProvided(String jobProvided) {
        this.jobProvided = jobProvided;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public List<Long> getImagesIdDeleted() {
        return imagesIdDeleted;
    }

    public void setImagesIdDeleted(List<Long> imagesIdDeleted) {
        this.imagesIdDeleted = imagesIdDeleted;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
