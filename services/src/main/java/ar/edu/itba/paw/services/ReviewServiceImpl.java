package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.models.Review;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

@org.springframework.stereotype.Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Override
    public Collection<Review> getReviewsByJobId(long jobId) {
        return reviewDao.getReviewsByJobId(jobId);
    }

    @Override
    public Review createReview(String description, long jobId, int rating) {
        return reviewDao.createReview(description, jobId, rating, Timestamp.valueOf(LocalDateTime.now()));
    }


}
