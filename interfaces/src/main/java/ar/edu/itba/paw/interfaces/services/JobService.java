package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.image.ImageDto;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.job.JobStatus;
import ar.edu.itba.paw.models.user.User;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface JobService {
    Optional<Job> getJobById(long id);

    Optional<JobContact> getContactById(long id);

    Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, boolean paused, List<ImageDto> images, User provider);

    Collection<JobCategory> getJobsCategories();

    void updateJobState(JobContact jc, JobStatus state);

    void updateJob(String jobProvided, String description, BigDecimal price, boolean paused,List<ImageDto> images, Job job,List<Long> imagesIdDeleted);

}

