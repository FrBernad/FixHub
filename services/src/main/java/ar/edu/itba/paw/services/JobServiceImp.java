package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.interfaces.services.JobService;
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
    public Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, User user) {
        return jobDao.createJob(jobProvided,category,description, price, user);
    }


    @Override
    public Optional<Job> getJobById(long id) {
        return jobDao.getJobById(id);
    }

    @Override
    public Collection<JobCategory> getJobsCategories(){
        return jobDao.getJobsCategories();
    }

}
