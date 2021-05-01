package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface JobDao {

    Optional<Job> getJobById(long id);

    Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, boolean paused,User user, List<Image> images);

    Collection<JobCategory> getJobsCategories();

    Collection<Long> getImagesIdsByJobId(Long jobId);

    Collection<Job> getJobsByProviderId(String searchBy, OrderOptions orderOption, Long providerId, int page, int itemsPerPage);

    Collection<Job> getJobsByCategory(String searchBy, OrderOptions orderOption, JobCategory category, String state, String city, int page, int itemsPerPage);

    Integer getJobsCountByCategory(String searchBy, JobCategory category, String state, String city);

    Integer getJobsCountByProviderId(String searchBy, Long providerId);

    int deleteImageById(long imageId, long jobId);
    void updateJob(String jobProvided, JobCategory category, String description, BigDecimal price, boolean paused,List<Image> images, long jobId);

    int deleteImageById(long imageId, long jobId);
}

