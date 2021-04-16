package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface JobDao {

    Collection<Job> getJobsByCategory(String searchQuery, OrderOptions orderOptions,JobCategory category);

    Optional<Job> getJobById(long id);

    Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, User user, List<Image> images);

    Collection<JobCategory> getJobsCategories();

    Collection<Long> getImagesIdsByJobId(Long jobId);
    Collection<Job> getJobByProviderId(long id);

}
