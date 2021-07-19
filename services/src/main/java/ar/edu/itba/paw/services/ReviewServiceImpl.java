package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.NoContactFoundException;
import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.Review;
import ar.edu.itba.paw.models.pagination.PaginatedSearchResult;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);

    @Transactional
    @Override
    public PaginatedSearchResult<Review> getReviewsByJob(Job job, int page, int pageSize) {

        if (page < 0) {
            LOGGER.debug("Page number {} is invalid", page);
            return null;
        }

        if (pageSize <= 0) {
            LOGGER.debug("Items per page {} is invalid", pageSize);
            return null;
        }

        LOGGER.debug("Retrieving total reviews count for job {}", job.getId());
        final int totalReviews = reviewDao.getReviewsCountByJob(job);
        final int totalPages = (int) Math.ceil((float) totalReviews / pageSize);

        if (totalPages == 0 || page >= totalPages) {
            LOGGER.debug("Page number {} is higher than totalPages {} or there is no jobs", page, totalPages);
            return new PaginatedSearchResult<>(0, pageSize, 0, Collections.emptyList());
        }

        LOGGER.debug("Retrieving page {} for reviews", page);
        final Collection<Review> reviews = reviewDao.getReviewsByJob(job, page, pageSize);

        return new PaginatedSearchResult<>(page, pageSize, totalReviews, reviews);
    }

    @Transactional
    @Override
    public Review createReview(String description, Job job, int rating, User user) {
        if (!userService.hasContactJobProvided(job.getProvider(), user, job))
            throw new NoContactFoundException();
        else {
            final Review review = reviewDao.createReview(description, job, rating, Timestamp.valueOf(LocalDateTime.now()), user);
            LOGGER.debug("Created review for job {} with id {} by user with id {}", job.getJobProvided(), job.getId(), user.getId());
            job.getReviews().add(review);
            return review;
        }
    }

    @Transactional
    @Override
    public Optional<Review> getReviewById(long reviewId) {
        LOGGER.debug("Retrieving review with id {}", reviewId);
        return reviewDao.getReviewById(reviewId);
    }


}
