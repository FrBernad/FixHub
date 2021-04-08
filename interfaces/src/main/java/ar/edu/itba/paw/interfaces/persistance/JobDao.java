package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategory;
import ar.edu.itba.paw.models.OrderOptions;
import ar.edu.itba.paw.models.User;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

public interface JobDao {

    Collection<Job> getJobsByCategory(String searchQuery, OrderOptions orderOptions,JobCategory category);

    Optional<Job> getJobById(long id);

    Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, User user);

    Collection<JobCategory> getJobsCategories();

}
