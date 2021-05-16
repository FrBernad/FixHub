package ar.edu.itba.paw.models.job;


import ar.edu.itba.paw.models.user.User;

import java.time.LocalDate;

public class Review {
    private Long id;
    private String description;
    private Long jobId;
    private int rating;
    private LocalDate creationDate;
    private User reviewer;

    public Review(Long id, String description, Long jobId, int rating, LocalDate creationDate, User reviewer) {
        this.id = id;
        this.description = description;
        this.jobId = jobId;
        this.rating = rating;
        this.creationDate = creationDate;
        this.reviewer = reviewer;
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

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
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
