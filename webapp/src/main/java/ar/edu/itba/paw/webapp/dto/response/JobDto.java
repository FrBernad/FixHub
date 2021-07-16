package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@XmlType(name="")
public class JobDto {

    public static UriBuilder getJobUriBuilder(Job job, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path("jobs").path(String.valueOf(job.getId()));
    }

    public static UriBuilder getJobImagesUriBuilder(Image image, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path("jobs").path("images").path(String.valueOf(image.getId()));
    }

    public static UriBuilder getContactUriBuilder(Job job, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path("jobs").path(String.valueOf(job.getId())).path("contact");
    }

    public static Collection<JobDto> mapJobToDto(Collection<Job> jobs, UriInfo uriInfo, SecurityContext securityContext) {
        return jobs.stream().map(p -> new JobDto(p, uriInfo, securityContext)).collect(Collectors.toList());
    }

    private Long id;

    private String description;

    private JobCategory category;

    private String jobProvided;

    private UserDto provider;

    private LocationDto location;

    private ScheduleDto schedule;

    private String url;

    private BigDecimal price;

    private boolean paused;

    private Integer averageRating;

    private Long totalRatings;

    private Set<String> images;

    private String thumbnailImage;

    private int maxImagesPerJob;

    public JobDto() {
        //use by Jersey
    }

    public JobDto(Job job, UriInfo uriInfo, SecurityContext securityContext) {

        final UriBuilder uriBuilder = getJobUriBuilder(job, uriInfo);
        this.url = uriBuilder.build().toString();

        this.id = job.getId();
        this.description = job.getDescription();
        this.category = job.getCategory();
        this.jobProvided = job.getJobProvided();
        this.price = job.getPrice();
        this.paused = job.isPaused();
        this.provider = new ProviderDto(job.getProvider(), uriInfo, securityContext);
        this.images = new HashSet<>();
        for (Image image : job.getImages()) {
            this.images.add(getJobImagesUriBuilder(image, uriInfo).build().toString());
        }
        Optional<String> maybeThumbailImage = this.images.stream().findFirst();
        maybeThumbailImage.ifPresent(uri -> this.thumbnailImage = uri);
        this.averageRating = job.getAverageRating();
        this.totalRatings = job.getTotalRatings();
        this.maxImagesPerJob = Job.MAX_IMAGES_PER_JOB;
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

    public UserDto getProvider() {
        return provider;
    }

    public void setProvider(UserDto provider) {
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

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public int getMaxImagesPerJob() {
        return maxImagesPerJob;
    }

    public void setMaxImagesPerJob(int maxImagesPerJob) {
        this.maxImagesPerJob = maxImagesPerJob;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public ScheduleDto getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleDto schedule) {
        this.schedule = schedule;
    }
}
