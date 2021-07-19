package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.Review;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewDaoImpl.class);

    @Override
    public Review createReview(String description, Job job, int rating, Timestamp creationDate, User user) {
        LOGGER.debug("Creating review for job with id {}",job.getId());
        final Review review = new Review(description, job, rating, creationDate.toLocalDateTime().toLocalDate(), user);
        em.persist(review);
        return review;
    }

    @Override
    public Collection<Review> getReviewsByJob(Job job, int page, int itemsPerPage) {
        LOGGER.debug("Retrieving reviews for job with id {}", job.getId());
        final List<Object> variables = new LinkedList<>();
        variables.add(job.getId());
        final String offsetAndLimitQuery = getOffsetAndLimitQuery(page, itemsPerPage, variables);

        final String filteredIdsSelectQuery =
            " select r_id FROM " +
                " REVIEWS r where r_job_id = ? " +
                " order by r.r_creation_date desc, r_id desc " + offsetAndLimitQuery;

        Query filteredIdsSelectNativeQuery = em.createNativeQuery(filteredIdsSelectQuery);

        setQueryVariables(filteredIdsSelectNativeQuery, variables);

        @SuppressWarnings("unchecked") final List<Long> filteredIds = ((List<Number>) filteredIdsSelectNativeQuery.getResultList())
            .stream()
            .map(Number::longValue)
            .collect(Collectors.toList());

        if (filteredIds.isEmpty())
            return Collections.emptyList();

        return em.createQuery("from Review r where id IN :filteredIds order by creationDate desc, r.id desc", Review.class)
            .setParameter("filteredIds", filteredIds)
            .getResultList();
    }

    @Override
    public int getReviewsCountByJob(Job job) {
        LOGGER.debug("Retrieving reviews count for job with id {}", job.getId());
        List<Object> variables = new LinkedList<>();

        variables.add(job.getId());

        final String query = "SELECT count(*) as total FROM REVIEWS r WHERE r_job_id = ?";

        Query nativeQuery = em.createNativeQuery(query);

        setQueryVariables(nativeQuery, variables);

        return ((BigInteger) nativeQuery.getSingleResult()).intValue();
    }

    private String getOffsetAndLimitQuery(int page, int itemsPerPage, List<Object> variables) {
        final StringBuilder offsetAndLimitQuery = new StringBuilder();
        if (page > 0) {
            offsetAndLimitQuery.append(" OFFSET ? ");
            variables.add(page * itemsPerPage);
        }
        if (itemsPerPage > 0) {
            offsetAndLimitQuery.append(" LIMIT ? ");
            variables.add(itemsPerPage);
        }
        return offsetAndLimitQuery.toString();
    }

    private void setQueryVariables(Query query, Collection<Object> variables) {
        int i = 1;
        for (Object variable : variables) {
            query.setParameter(i, variable);
            i++;
        }
    }

    @Override
    public Optional<Review> getReviewById(long reviewId){
        LOGGER.debug("Retrieving review with id {}", reviewId);
        final TypedQuery<Review> query = em.createQuery("from Review as r where r.id = :reviewId",Review.class);
        query.setParameter("reviewId",reviewId);
        return query.getResultList().stream().findFirst();
    }


}
