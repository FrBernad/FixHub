package ar.edu.itba.paw.models.job;


import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviews_u_id_seq")
    @SequenceGenerator(sequenceName = "reviews_u_id_seq", name = "reviews_u_id_seq", allocationSize = 1)
    @Column(name = "r_id")
    private Long id;

    @Column(name="r_description", length = 300,nullable = false)
    private String description;

    @Column(name="r_rating",nullable = false)
    private int rating;

    @Column(name="r_creation_date",nullable = false)
    private LocalDate creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="r_job_id",nullable = false)
    private Job job;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="r_reviewer_id",nullable = false)
    private User reviewer;

    public Review(String description, Job job, int rating, LocalDate creationDate, User reviewer) {
        this.description = description;
        this.job = job;
        this.rating = rating;
        this.creationDate = creationDate;
        this.reviewer = reviewer;
    }

    protected Review() {

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

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
