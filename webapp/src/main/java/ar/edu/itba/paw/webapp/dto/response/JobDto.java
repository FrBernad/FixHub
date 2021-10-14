package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@XmlType(name = "")
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
    private BigDecimal price;
    private boolean paused;
    private Integer averageRating;
    private Long totalRatings;

    private String url;

    private Set<String> imagesUrls;
    private String thumbnailImageUrl;

    private String reviewsUrls;

    public JobDto() {
        //use by Jersey
    }

    public JobDto(Job job, UriInfo uriInfo) {
        final UriBuilder uriBuilder = getJobUriBuilder(job, uriInfo);
        this.url = uriBuilder.build().toString();
        this.id = job.getId();
        this.description = job.getDescription();
        this.category = job.getCategory();
        this.jobProvided = job.getJobProvided();
        this.price = job.getPrice();
        this.paused = job.isPaused();
        this.provider = new UserDto(job.getProvider(), uriInfo);
        this.averageRating = job.getAverageRating();
        this.totalRatings = job.getTotalRatings();

        this.imagesUrls = job.getImages().stream()
            .map(i -> getJobUriBuilder(job, uriInfo).clone().path("/images").path(String.valueOf(i.getId())).build().toString())
            .collect(Collectors.toSet());

        Optional<String> thumbnailImageOpt = this.imagesUrls.stream().findFirst();
        thumbnailImageOpt.ifPresent(uri -> this.thumbnailImageUrl = uri);

        this.reviewsUrls = uriBuilder.clone().path("reviews").build().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public UserDto getProvider() {
        return provider;
    }

    public void setProvider(UserDto provider) {
        this.provider = provider;
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

    public Set<String> getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(Set<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public String getReviewsUrls() {
        return reviewsUrls;
    }

    public void setReviewsUrls(String reviewsUrls) {
        this.reviewsUrls = reviewsUrls;
    }
}
