package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.JobDao;
import ar.edu.itba.paw.interfaces.JobService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategory;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
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
    public Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, User user) {
        return jobDao.createJob(jobProvided,category,description, price, user);
    }
    @Override
    public Collection<JobCategory> getJobsCategories(){
        return jobDao.getJobsCategories();
    }

    @Override
    public Collection<Job> getJobsBySearchPhrase(String phrase) {
        return jobDao.getJobsBySearchPhrase(phrase);
    }

    @Override
    public Collection<Job> getJobsByCategory(long categoryId) {
        return jobDao.getJobsByCategory(categoryId);
    }

    @Override
    public Collection<Job> getJobsOrderByCategory(long categoryId) {
        return jobDao.getJobsOrderByCategory(categoryId);
    }

    @Override
    public Collection<Job> getJobsOrderByRating() {
        return jobDao.getJobsOrderByRating();
    }

}
