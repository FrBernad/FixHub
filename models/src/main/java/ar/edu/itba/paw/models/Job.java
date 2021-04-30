package ar.edu.itba.paw.models;


import java.math.BigDecimal;
import java.util.Collection;

public class Job {
    private String description,jobProvided;
    private int averageRating;
    private JobCategory category;
    private long id;
    private User provider;
    private BigDecimal price;
    private long totalRatings;
    private Collection<Long> imagesId;
    private boolean paused;

    public Job(String description,String jobProvided, int averageRating, long totalRatings,JobCategory category, long id, BigDecimal price, boolean paused ,User provider, Collection<Long> imagesId) {
        this.description = description;
        this.jobProvided = jobProvided;
        this.averageRating = averageRating;
        this.category = category;
        this.totalRatings = totalRatings;
        this.id = id;
        this.provider = provider;
        this.price = price;
        this.paused = paused;
        this.imagesId=imagesId;
    }

    @Override
    public String toString() {
        return "Job{" +
                "description='" + description + '\'' +
                ", jobProvided='" + jobProvided + '\'' +
                ", averageRating=" + averageRating +
                ", category=" + category +
                ", id=" + id +
                ", provider=" + provider +
                ", price=" + price +
                ", totalRatings=" + totalRatings +
                '}';
    }

    public void addImageId(long imageId){
      imagesId.add(imageId);
    }

    public long getJobThumbnailId(){
        return imagesId.stream().findFirst().orElse(-1L);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobProvided() {
        return jobProvided;
    }

    public void setJobProvided(String jobProvided) {
        this.jobProvided = jobProvided;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public long getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(long totalRatings) {
        this.totalRatings = totalRatings;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }


    public JobCategory getCategory() { return category;}

    public void setCategory(JobCategory category) {this.category = category;}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getProvider() {
        return provider;
    }

    public void setProvider(User provider) {
        this.provider = provider;
    }

    public Collection<Long> getImagesId() {
        return imagesId;
    }

    public void setImagesId(Collection<Long> imagesId) {
        this.imagesId = imagesId;
    }
}
