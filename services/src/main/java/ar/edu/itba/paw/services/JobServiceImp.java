package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.JobService;
import ar.edu.itba.paw.models.Job;

import java.util.Collection;
import java.util.Optional;

@org.springframework.stereotype.Service
public class JobServiceImp implements JobService {
    @Override
    public Collection<Job> getJobs() {
        return null;
    }

    @Override
    public Optional<Job> getJobById(long id) {
        return Optional.empty();
    }

    @Override
    public Job createJob(String description, int averageRating, int serviceType, long id, long providerId) {
        return null;
    }
}
