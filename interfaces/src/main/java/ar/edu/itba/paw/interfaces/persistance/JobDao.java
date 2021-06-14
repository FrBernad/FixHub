package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.pagination.OrderOptions;
import ar.edu.itba.paw.models.user.User;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface JobDao {

    Optional<Job> getJobById(long id);

    Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, boolean paused, User provider, Set<Image> images);

    Collection<JobCategory> getJobsCategories();

    Collection<Job> getJobsByProvider(String searchBy, OrderOptions orderOption, User provider, int page, int itemsPerPage);

    Collection<Job> getJobsByCategory(String searchBy, OrderOptions orderOption, JobCategory category, String state, String city, int page, int itemsPerPage);

    Integer getJobsCountByCategory(String searchBy, JobCategory category, String state, String city);

    Integer getJobsCountByProvider(User provider, String searchBy);

}

