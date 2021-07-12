package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.models.job.Review;
import ar.edu.itba.paw.webapp.dto.ReviewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

@Path("reviews")
@Component
public class ReviewController {


    @Autowired
    private ReviewService reviewService;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);


    @Path("/{reviewId}")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getReview(@PathParam("reviewId") long reviewId) {
        Review review = reviewService.getReviewById(reviewId).orElseThrow(ReviewNotFoundException::new); //FIXME: falta capturarla la excepcion
        LOGGER.info("Return review with id {}", review.getId());
        return Response.ok(new ReviewDto(review, uriInfo, securityContext)).build();
    }
}
