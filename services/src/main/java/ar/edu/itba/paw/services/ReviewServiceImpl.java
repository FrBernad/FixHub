package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.NoContactFoundException;
import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.PaginatedSearchResult;
import ar.edu.itba.paw.models.job.Review;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

        LOGGER.debug("Retrieving total reviews count for job {}", job.getId());
        final int totalReviews = reviewDao.getReviewsCountByJob(job);

        if (itemsPerPage <= 0) {
            LOGGER.debug("Assigning default items per page");
            itemsPerPage = DEFAULT_ITEMS_PER_PAGE;
        }

        LOGGER.debug("Retrieving paginated reviews");
        return new PaginatedSearchResult<>("", "", "", page, itemsPerPage, totalReviews,
            reviewDao.getReviewsByJob(job, page, itemsPerPage));
    }

    @Override
    public Review createReview(String description, Job job, int rating, User user) {
        if (!userService.hasContactJobProvided(job.getProvider(), user))
            throw new NoContactFoundException();
        else {
            final Review review = reviewDao.createReview(description, job, rating, Timestamp.valueOf(LocalDateTime.now()), user);
            LOGGER.debug("Created review for job {} with id {} by user with id {}", job.getJobProvided(), job.getId(),user.getId());
            return review;
        }
    }


}
