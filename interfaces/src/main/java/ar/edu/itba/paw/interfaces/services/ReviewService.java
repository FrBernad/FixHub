package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.PaginatedSearchResult;
import ar.edu.itba.paw.models.Review;

import java.util.Collection;

public interface ReviewService {

    PaginatedSearchResult<Review> getReviewsByJobId(long jobId, int page, int itemsPerPage);

    Review createReview(String description, Job job, int rating);
}
