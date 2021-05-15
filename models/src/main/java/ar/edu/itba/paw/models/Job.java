package ar.edu.itba.paw.models;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jobs_j_id_seq")
    @SequenceGenerator(sequenceName = "jobs_j_id_seq", name = "jobs_j_id_seq", allocationSize = 1)
    @Column(name = "j_id")
    private long id;

    @Column(name = "j_description", length = 300, nullable = false)
    private String description;

    @Column(name = "j_category", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private JobCategory category;

    @Column(name = "j_job_provided", length = 50, nullable = false)
    private String jobProvided;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinTable(name = "users",
        joinColumns = @JoinColumn(name = "u_u_id")
    )
    private User provider;

    private int averageRating;

    @Column(name = "j_price", nullable = false)
    private BigDecimal price;

    @Column(name = "j_paused", nullable = false)
    private boolean paused;

    private long totalRatings;

    //JOB --- IMG
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "job_image",
        joinColumns = @JoinColumn(name = "ji_job_id"))
    private Collection<Long> imagesId;

    public static final int MAX_IMAGES_PER_JOB = 6;

    /* default */ Job() {
        // Just for Hibernate
    }

    public Job(String description, String jobProvided, int averageRating, long totalRatings, JobCategory category, long id, BigDecimal price, boolean paused, User provider, Collection<Long> imagesId) {
        this.description = description;
        this.jobProvided = jobProvided;
        this.averageRating = averageRating;
        this.category = category;
        this.totalRatings = totalRatings;
        this.id = id;
        this.provider = provider;
        this.price = price;
        this.paused = paused;
        this.imagesId = imagesId;
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

    public void addImageId(long imageId) {
        imagesId.add(imageId);
    }

    public Long getJobThumbnailId() {
        return imagesId.stream().findFirst().orElse(0L);
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


    public JobCategory getCategory() {
        return category;
    }

    public void setCategory(JobCategory category) {
        this.category = category;
    }

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
