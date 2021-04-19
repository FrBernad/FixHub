package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.PaginatedSearchResult;
import ar.edu.itba.paw.models.Review;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

@org.springframework.stereotype.Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    private final int DEFAULT_ITEMS_PER_PAGE = 6;

    @Override
    public PaginatedSearchResult<Review> getReviewsByJobId(long jobId, int page, int itemsPerPage) {
        int totalReviews = reviewDao.getReviewsCountByJobId(jobId);

        if (itemsPerPage <= 0) {
            itemsPerPage = DEFAULT_ITEMS_PER_PAGE;
        }

        return new PaginatedSearchResult<>("", "", "", page, itemsPerPage, totalReviews,
            reviewDao.getReviewsByJobId(jobId, page, itemsPerPage));
    }

    @Override
    public Review createReview(String description, Job job, int rating) {
        return reviewDao.createReview(description, job, rating, Timestamp.valueOf(LocalDateTime.now()));
    }


}
