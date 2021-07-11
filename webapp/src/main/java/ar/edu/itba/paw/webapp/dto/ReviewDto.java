package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.job.Review;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

public class ReviewDto {

    public static Collection<ReviewDto> mapReviewToDto(Collection<Review> reviews, UriInfo uriInfo) {
        return reviews.stream().map(r -> new ReviewDto(r, uriInfo)).collect(Collectors.toList());
    }

    public ReviewDto() {
    }

    public ReviewDto(Review review, UriInfo uriInfo) {
        this.id = review.getId();
        this.description = review.getDescription();
        this.rating = review.getRating();
        this.creationDate = review.getCreationDate();
        this.reviewer = new UserDto(review.getReviewer(), uriInfo);
    }

    private Long id;

    private String description;

    private int rating;

    private LocalDate creationDate;

    private UserDto reviewer;


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

    public UserDto getReviewer() {
        return reviewer;
    }

    public void setReviewer(UserDto reviewer) {
        this.reviewer = reviewer;
    }
}
