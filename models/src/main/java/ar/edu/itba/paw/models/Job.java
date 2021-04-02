package ar.edu.itba.paw.models;


import java.math.BigDecimal;

public class Job {
    private String description,jobProvided;
    private int averageRating;
    private JobCategory category;
    private Number id;
    private User provider;
    private BigDecimal price;
    private long totalRatings;

    public Job(String description,String jobProvided, int averageRating, long totalRatings,JobCategory category, Number id, BigDecimal price, User provider) {
        this.description = description;
        this.jobProvided = jobProvided;
        this.averageRating = averageRating;
        this.category = category;
        this.totalRatings = totalRatings;
        this.category = category;
        this.id = id;
        this.provider = provider;
        this.price = price;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public User getProvider() {
        return provider;
    }

    public void setProvider(User provider) {
        this.provider = provider;
    }
}
