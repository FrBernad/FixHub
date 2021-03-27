package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.JobDao;
import ar.edu.itba.paw.interfaces.JobService;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;

@org.springframework.stereotype.Service
public class JobServiceImp implements JobService {

    @Autowired
    private JobDao jobDao;

    @Override
    public Collection<Job> getJobs() {
        return jobDao.getJobs();
    }

    @Override
    public Optional<Job> getJobById(long id) {
        return jobDao.getJobById(id);
    }

    @Override
    public Job createJob(String description, int averageRating, int serviceType, long id, User provider) {
        return jobDao.createJob(description, averageRating, serviceType, provider);
    }
}