package ar.edu.itba.paw.models.job;

import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jobs_j_id_seq")
    @SequenceGenerator(sequenceName = "jobs_j_id_seq", name = "jobs_j_id_seq", allocationSize = 1)
    @Column(name = "j_id")
    private Long id;

    @Column(name = "j_description", length = 300, nullable = false)
    private String description;

    @Column(name = "j_category", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private JobCategory category;

    @Column(name = "j_job_provided", length = 50, nullable = false)
    private String jobProvided;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "j_provider_id")
    private User provider;

    @Column(name = "j_price", nullable = false)
    private BigDecimal price;

    @Column(name = "j_paused", nullable = false)
    private boolean paused;

    @Transient
    private Integer averageRating;

    @Transient
    private Long totalRatings;

    @OneToMany
    @JoinTable(name = "job_image",
        joinColumns = @JoinColumn(name = "ji_job_id"),
        inverseJoinColumns = @JoinColumn(name = "ji_image_id"))
    private Set<Image> images;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "job")
    private Set<Review> reviews;

    public static final Integer MAX_IMAGES_PER_JOB = 6;


    /* default */
    protected Job() {
        // Just for Hibernate
    }

    public Job(String description, String jobProvided, Integer averageRating, Long totalRatings, JobCategory category, BigDecimal price, boolean paused, User provider, Set<Image> images) {
        this.description = description;
        this.jobProvided = jobProvided;
        this.averageRating = averageRating;
        this.category = category;
        this.totalRatings = totalRatings;
        this.provider = provider;
        this.price = price;
        this.paused = paused;
        this.images = images;
    }

    public void addImage(Image image) {
        images.add(image);
    }

    public Long getJobThumbnailId() {
        return images.stream().findFirst().orElse(null).getImageId();
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

    public Integer getAverageRating() {
        return averageRating;
    }

    public Long getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(Long totalRatings) {
        this.totalRatings = totalRatings;
    }

    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }


    public JobCategory getCategory() {
        return category;
    }

    public void setCategory(JobCategory category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getProvider() {
        return provider;
    }

    public void setProvider(User provider) {
        this.provider = provider;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Job)) return false;
        Job job = (Job) o;
        return paused == job.paused && Objects.equals(id, job.id) && Objects.equals(description, job.description) && category == job.category && Objects.equals(jobProvided, job.jobProvided) && Objects.equals(provider, job.provider) && Objects.equals(price, job.price) && Objects.equals(averageRating, job.averageRating) && Objects.equals(totalRatings, job.totalRatings) && Objects.equals(images, job.images) && Objects.equals(reviews, job.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, category, jobProvided, provider, price, paused, averageRating, totalRatings, images, reviews);
    }

    @Override
    public String toString() {
        return "Job{" +
            "id=" + id +
            ", description='" + description + '\'' +
            ", category=" + category +
            ", jobProvided='" + jobProvided + '\'' +
            ", provider=" + provider +
            ", price=" + price +
            ", paused=" + paused +
            ", averageRating=" + averageRating +
            ", totalRatings=" + totalRatings +
            ", images=" + images +
            ", reviews=" + reviews +
            '}';
    }
}
