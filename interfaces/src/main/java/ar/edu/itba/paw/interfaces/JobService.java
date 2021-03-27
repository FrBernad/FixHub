package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.User;

import java.util.Collection;
import java.util.Optional;

public interface JobService {
    Collection<Job> getJobs();

    Optional<Job> getJobById(long id);

    Job createJob(String description, int averageRating, int serviceType, long id, User provider);
}
