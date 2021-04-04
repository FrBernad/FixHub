package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.Review;

import java.util.Collection;

public interface ReviewService {

    Collection<Review> getReviewsByJobId(Job job);

    Review createReview(String description, Job job, int rating);
}
