package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface JobDao {

    Optional<Job> getJobById(long id);

    Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, User user, List<Image> images);

    Collection<JobCategory> getJobsCategories();

    Collection<Long> getImagesIdsByJobId(Long jobId);

    Collection<Job> getJobsByProviderId(String searchBy, OrderOptions orderOptions, Long providerId, int page, int itemsPerPage);

    Collection<Job> getJobsByCategory(String searchBy, OrderOptions orderOptions, JobCategory category, int page, int itemsPerPage);

    Integer getJobsCountByCategory(String searchBy, OrderOptions orderOptions, JobCategory category);

    Integer getJobsCountByProviderId(String searchBy, OrderOptions orderOptions, Long providerId);
}

