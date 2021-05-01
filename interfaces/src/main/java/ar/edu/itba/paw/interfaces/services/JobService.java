package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface JobService {
    Optional<Job> getJobById(long id);

    Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, boolean paused, List<ImageDto> images, User provider);

    Collection<JobCategory> getJobsCategories();

    Collection<Long> getImagesIdsByJobId(Long jobId);

    void updateJob(String jobProvided, JobCategory category, String description, BigDecimal price, List<ImageDto> images, long jobId);

    void deleteImageFromJob(long jobId, long imageId, User user);
}

