package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.job.Review;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

public class ReviewDto {

    public static Collection<ReviewDto> mapReviewToDto(Collection<Review> reviews, UriInfo uriInfo, SecurityContext securityContext) {
        return reviews.stream().map(r -> new ReviewDto(r, uriInfo, securityContext)).collect(Collectors.toList());
    }

    public static UriBuilder getReviewUriBuilder(Review review, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path("reviews").path(String.valueOf(review.getId()));
    }

    private String url;
    private Long id;
    private String description;
    private int rating;
    private LocalDate creationDate;
    private UserDto reviewer;

    public ReviewDto() {
    }

    public ReviewDto(Review review, UriInfo uriInfo, SecurityContext securityContext) {
        this.id = review.getId();
        this.description = review.getDescription();
        this.rating = review.getRating();
        this.creationDate = review.getCreationDate();
        this.reviewer = new UserDto(review.getReviewer(), uriInfo, securityContext);
        final UriBuilder uriBuilder = getReviewUriBuilder(review, uriInfo);
        this.url = uriBuilder.build().toString();
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
