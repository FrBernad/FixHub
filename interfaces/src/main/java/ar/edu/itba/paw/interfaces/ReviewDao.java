package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Review;

import java.sql.Timestamp;
import java.util.Collection;

public interface ReviewDao {

    Collection<Review> getReviewsByJobId(long jobId);

    Review createReview(String description, long jobId, int rating, Timestamp creationDate);
}