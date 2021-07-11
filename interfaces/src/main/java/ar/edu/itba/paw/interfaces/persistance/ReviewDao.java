package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.Review;
import ar.edu.itba.paw.models.user.User;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;

public interface ReviewDao {

    Collection<Review> getReviewsByJob(Job job, int page, int itemsPerPage);

    Review createReview(String description, Job job, int rating, Timestamp creationDate, User user);

    int getReviewsCountByJob(Job job);

    Optional<Review> getReviewById(long reviewId);
}
