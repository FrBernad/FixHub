package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.job.Review;
import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

public class JobDto {

    private Long id;

    private String description;

    private JobCategory category;

    private String jobProvided;

    private UserDto provider;

    private BigDecimal price;

    private boolean paused;

//    private Integer averageRating;
//
//    private Long totalRatings;
    private Set<Image> images;

//    private Set<Review> reviews;

//    public static final Integer MAX_IMAGES_PER_JOB = 6;


    public JobDto() {
        //use by Jersey
    }

    public JobDto(Job job) {
        this.id = job.getId();
        this.description = job.getDescription();
        this.category = job.getCategory();
        this.jobProvided = job.getJobProvided();
        this.price = job.getPrice();
        this.paused = job.isPaused();
        this.provider = new UserDto(job.getProvider());
        this.images = job.getImages();
    }

    public UserDto getProvider() {
        return provider;
    }

    public void setProvider(UserDto provider) {
        this.provider = provider;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JobCategory getCategory() {
        return category;
    }

    public void setCategory(JobCategory category) {
        this.category = category;
    }

    public String getJobProvided() {
        return jobProvided;
    }

    public void setJobProvided(String jobProvided) {
        this.jobProvided = jobProvided;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
