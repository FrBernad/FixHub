package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.user.User;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class JobDto {

    public static UriBuilder getJobUriBuilder(Job job, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path("jobs").path(String.valueOf(job.getId()));
    }

    public static Collection<JobDto> mapJobToDto(Collection<Job> jobs, UriInfo uriInfo) {
        return jobs.stream().map(p -> new JobDto(p, uriInfo)).collect(Collectors.toList());
    }

    private Long id;

    private String description;

    private JobCategory category;

    private String jobProvided;

    private UserDto provider;

    private String url;

    private BigDecimal price;

    private boolean paused;

    private Integer averageRating;

    private Long totalRatings;

    private Set<URI> images;

//    private Set<Review> reviews;

    private int maxImagesPerJob;


    public JobDto() {
        //use by Jersey
    }

    public JobDto(Job job, UserDto provider,UriInfo uriInfo) {

        final UriBuilder uriBuilder = getJobUriBuilder(job, uriInfo);
        this.url = uriBuilder.build().toString();

        this.id = job.getId();
        this.description = job.getDescription();
        this.category = job.getCategory();
        this.jobProvided = job.getJobProvided();
        this.price = job.getPrice();
        this.paused = job.isPaused();
        this.provider = provider;
        this.images = new HashSet<>();
        for (Image image : job.getImages()) {
            this.images.add(ImageDto.getImageUriBuilder(image, uriInfo).build());
        }
        this.averageRating = job.getAverageRating();
        this.totalRatings = job.getTotalRatings();
        this.maxImagesPerJob = Job.MAX_IMAGES_PER_JOB;

    }

    public UserDto getProvider() {
        return provider;
    }

    public void setProvider(UserDto provider) {
        this.provider = provider;
    }

    public Set<URI> getImages() {
        return images;
    }

    public void setImages(Set<URI> images) {
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

    public URI getProvider() {
        return provider;
    }

    public void setProvider(URI provider) {
        this.provider = provider;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Integer getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }

    public Long getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(Long totalRatings) {
        this.totalRatings = totalRatings;
    }

    public Set<URI> getImages() {
        return images;
    }

    public void setImages(Set<URI> images) {
        this.images = images;
    }

    public int getMaxImagesPerJob() {
        return maxImagesPerJob;
    }

    public void setMaxImagesPerJob(int maxImagesPerJob) {
        this.maxImagesPerJob = maxImagesPerJob;
    }
}
