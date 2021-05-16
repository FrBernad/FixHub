package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.PaginatedSearchResult;
import ar.edu.itba.paw.models.job.Review;
import ar.edu.itba.paw.models.user.User;

public interface ReviewService {

    PaginatedSearchResult<Review> getReviewsByJobId(long jobId, int page, int itemsPerPage);

    Review createReview(String description, Job job, int rating, User user);
}
