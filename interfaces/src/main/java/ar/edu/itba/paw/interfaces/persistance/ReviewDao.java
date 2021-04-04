package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.Review;

import java.sql.Timestamp;
import java.util.Collection;

public interface ReviewDao {

    Collection<Review> getReviewsByJobId(Job job);

    Review createReview(String description, Job job, int rating, Timestamp creationDate);

    int getReviewsCountByJobId(Job job);
}
