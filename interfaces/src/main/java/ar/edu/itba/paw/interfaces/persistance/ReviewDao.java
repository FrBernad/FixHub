package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.Review;
import ar.edu.itba.paw.models.user.User;

import java.sql.Timestamp;
import java.util.Collection;

public interface ReviewDao {

    Collection<Review> getReviewsByJobId(long jobId, int page, int itemsPerPage);

    Review createReview(String description, Job job, int rating, Timestamp creationDate, User user);

    int getReviewsCountByJobId(long jobId);
}
