package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategories;
import ar.edu.itba.paw.models.User;

import java.util.Collection;
import java.util.Optional;

public interface JobService {
    Collection<Job> getJobs();

    Optional<Job> getJobById(long id);

    Job createJob(String jobProvided, Number jobType, String description, User provider);

//   TODO: crear categories service
    Collection<JobCategories> getJobsCategories();

    Collection<Job> getJobsBySearchPhrase(String phrase);

    Collection<Job> getJobsByCategory(long categoryId);

    Collection<Job> getJobsOrderByCategory(long categoryId);

    Collection<Job> getJobsOrderByRating();
}

