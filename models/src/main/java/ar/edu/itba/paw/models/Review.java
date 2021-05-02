package ar.edu.itba.paw.models;


import java.time.LocalDate;

public class Review {
    private Number id;
    private String description;
    private Number jobId;
    private int rating;
    private LocalDate creationDate;
    private User reviewer;

    public Review(Number id, String description, Number jobId, int rating, LocalDate creationDate, User reviewer) {
        this.id = id;
        this.description = description;
        this.jobId = jobId;
        this.rating = rating;
        this.creationDate = creationDate;
        this.reviewer = reviewer;
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
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
