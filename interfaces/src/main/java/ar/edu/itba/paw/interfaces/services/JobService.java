package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategory;
import ar.edu.itba.paw.models.User;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

public interface JobService {
    Optional<Job> getJobById(long id);

    Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, User provider);

    Collection<JobCategory> getJobsCategories();

}

