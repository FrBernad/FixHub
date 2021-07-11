package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.pagination.PaginatedSearchResult;
import ar.edu.itba.paw.models.job.Review;
import ar.edu.itba.paw.models.user.User;

import java.util.Optional;

public interface ReviewService {

    PaginatedSearchResult<Review> getReviewsByJob(Job job, int page, int pageSize);

    Review createReview(String description, Job job, int rating, User user);

    Optional<Review> getReviewById(long reviewId);
}
