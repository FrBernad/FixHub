package ar.edu.itba.paw.models;


import java.sql.Timestamp;

public class Review {
    private Number id;
    private String description;
    private Number jobId;
    private int rating;
    private Timestamp creationDate;

    public Review(Number id, String description, Number jobId, int rating, Timestamp creationDate) {
        this.id = id;
        this.description = description;
        this.jobId = jobId;
        this.rating = rating;
        this.creationDate = creationDate;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Number getJobId() {
        return jobId;
    }

    public void setJobId(Number jobId) {
        this.jobId = jobId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", jobId=" + jobId +
                ", rating=" + rating +
                ", creationDate=" + creationDate +
                '}';
    }
}
