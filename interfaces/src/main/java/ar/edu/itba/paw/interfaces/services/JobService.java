package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.image.NewImageDto;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.user.User;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface JobService {
    Optional<Job> getJobById(long id);

    Optional<JobContact> getContactById(long id);

    Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, boolean paused, User provider);

    void addImagesToJob(Job job, List<NewImageDto> imagesToUpload);

    Collection<JobCategory> getJobsCategories();

    void acceptJob(JobContact jc);

    void rejectJob(JobContact jc);

    void finishJob(JobContact jc);

    void updateJob(String jobProvided, String description, BigDecimal price, boolean paused, List<NewImageDto> images, Job job, List<Long> imagesIdDeleted);

}

