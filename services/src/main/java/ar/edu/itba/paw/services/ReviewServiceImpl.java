package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.PaginatedSearchResult;
import ar.edu.itba.paw.models.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

@org.springframework.stereotype.Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    private static final int DEFAULT_ITEMS_PER_PAGE = 6;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewService.class);

    @Override
    public PaginatedSearchResult<Review> getReviewsByJobId(long jobId, int page, int itemsPerPage) {

        LOGGER.debug("Retrieving total reviews count for job {}", jobId);
        final int totalReviews = reviewDao.getReviewsCountByJobId(jobId);

        if (itemsPerPage <= 0) {
            LOGGER.debug("Assigning default items per page");
            itemsPerPage = DEFAULT_ITEMS_PER_PAGE;
        }

        LOGGER.debug("Retrieving paginated reviews");
        return new PaginatedSearchResult<>("", "", "", page, itemsPerPage, totalReviews,
            reviewDao.getReviewsByJobId(jobId, page, itemsPerPage));
    }

    @Override
    public Review createReview(String description, Job job, int rating) {
        final Review review = reviewDao.createReview(description, job, rating, Timestamp.valueOf(LocalDateTime.now()));
        LOGGER.debug("Created review for job {} with id {}",job.getJobProvided(),job.getId());
        return review;
    }


}
