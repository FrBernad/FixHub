package ar.edu.itba.paw.webapp.form;


import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public class EditJobForm {

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

    private List<MultipartFile> images;

    private List<String> imagesIdDeleted;

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

    public List<String> getImagesIdDeleted() {
        return imagesIdDeleted;
    }

    public void setImagesIdDeleted(List<String> imagesIdDeleted) {
        this.imagesIdDeleted = imagesIdDeleted;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
