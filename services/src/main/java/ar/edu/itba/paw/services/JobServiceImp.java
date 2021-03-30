package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.JobCategoriesDao;
import ar.edu.itba.paw.interfaces.JobDao;
import ar.edu.itba.paw.interfaces.JobService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategories;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;

@org.springframework.stereotype.Service
public class JobServiceImp implements JobService {

    @Autowired
    private JobDao jobDao;

    @Autowired
    private JobCategoriesDao jobCategoriesDao;

    @Override
    public Collection<Job> getJobs() {
        return jobDao.getJobs();
    }

    @Override
    public Optional<Job> getJobById(long id) {
        return jobDao.getJobById(id);
    }

    @Override
    public Job createJob(String jobProvided, Number jobType, String description, User user) {
        return jobDao.createJob(jobProvided,jobType,description,user);
    }

    @Override
    public Collection<JobCategories> getJobsCategories(){
        return jobCategoriesDao.getJobs();
    }

    @Override
    public Collection<Job> getJobsBySearchPhrase(String phrase) {
        return jobDao.getJobsBySearchPhrase(phrase);
    }

    @Override
    public Collection<Job> getJobsByCategory(long categoryId) {
        return jobDao.getJobsByCategory(categoryId);
    }
}
