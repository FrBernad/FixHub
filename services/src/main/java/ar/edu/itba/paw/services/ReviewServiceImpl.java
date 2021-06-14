package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.NoContactFoundException;
import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.pagination.PaginatedSearchResult;
import ar.edu.itba.paw.models.job.Review;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

@org.springframework.stereotype.Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private UserService userService;

    private static final int DEFAULT_ITEMS_PER_PAGE = 6;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewService.class);

    @Override
    public PaginatedSearchResult<Review> getReviewsByJob(Job job, int page, int itemsPerPage) {

        if (page < 0) {
            LOGGER.debug("Page number {} is invalid, defaulting to 0", page);
            page = 0;
        }

        if (itemsPerPage <= 0) {
            LOGGER.debug("Items per page {} is invalid, assigning default", itemsPerPage);
            itemsPerPage = DEFAULT_ITEMS_PER_PAGE;
        }

        LOGGER.debug("Retrieving total reviews count for job {}", job.getId());
        final int totalReviews = reviewDao.getReviewsCountByJob(job);
        final int totalPages = (int) Math.ceil((float) totalReviews / itemsPerPage);

        if (page >= totalPages) {
            LOGGER.debug("Page number {} is higher than totalPages {}, defaulting to {}", page, totalPages, totalPages - 1);
            page = totalPages - 1;
        }

        LOGGER.debug("Retrieving page {} for reviews", page);
        final Collection<Review> reviews = reviewDao.getReviewsByJob(job, page, itemsPerPage);

        return new PaginatedSearchResult<>("", "", "", page, itemsPerPage, totalReviews, reviews);
    }

    @Transactional
    @Override
    public Review createReview(String description, Job job, int rating, User user) {
        if (!userService.hasContactJobProvided(job.getProvider(), user,job))
            throw new NoContactFoundException();
        else {
            final Review review = reviewDao.createReview(description, job, rating, Timestamp.valueOf(LocalDateTime.now()), user);
            LOGGER.debug("Created review for job {} with id {} by user with id {}", job.getJobProvided(), job.getId(), user.getId());
            job.getReviews().add(review);
            return review;
        }
    }


}
