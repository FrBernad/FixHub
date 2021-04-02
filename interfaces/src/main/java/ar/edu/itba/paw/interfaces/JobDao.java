package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategory;
import ar.edu.itba.paw.models.User;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

public interface JobDao {

    Collection<Job> getJobs();

    Optional<Job> getJobById(long id);

    Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, User user);

    Collection<Job> getJobsBySearchPhrase(String phrase);

    Collection<Job> getJobsByCategory(long jobCategory);

    Collection<Job> getJobsOrderByCategory(long categoryId);

    Collection<Job> getJobsOrderByRating();

    Collection<JobCategory> getJobsCategories();

    Collection<Job> getJobsBySearchCategory(String category);


}
