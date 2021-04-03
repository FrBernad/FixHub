package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategory;
import ar.edu.itba.paw.models.User;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

public interface JobService {
    Collection<Job> getJobs();

    Optional<Job> getJobById(long id);

    Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, User provider);

    Collection<Job> getJobsBySearchPhrase(String phrase);

    Collection<JobCategory> getJobsCategories();

    Collection<Job> getJobsByCategory(long categoryId);

    Collection<Job> getJobsOrderByCategory(long categoryId);

    Collection<Job> getJobsOrderByRating();

    Collection<Job> getJobsBySearchCategory(String category);
}

