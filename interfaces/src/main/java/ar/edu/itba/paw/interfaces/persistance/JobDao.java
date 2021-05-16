package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.user.User;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface JobDao {

    Optional<Job> getJobById(long id);

    Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, boolean paused, User user, List<Image> images);

    Collection<JobCategory> getJobsCategories();

    Collection<Job> getJobsByProviderId(String searchBy, OrderOptions orderOption, Long providerId, int page, int itemsPerPage);

    Collection<Job> getJobsByCategory(String searchBy, OrderOptions orderOption, JobCategory category, String state, String city, int page, int itemsPerPage);

    Integer getJobsCountByCategory(String searchBy, JobCategory category, String state, String city);

    Integer getJobsCountByProviderId(String searchBy, Long providerId);

    void updateJob(String jobProvided,String description, BigDecimal price, boolean paused,List<Image> images, long jobId,List<Long> imagesIdDeleted);

}

