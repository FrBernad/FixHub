package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class JobServiceImp implements JobService {

    @Autowired
    private JobDao jobDao;

    @Autowired
    private ImageService imageService;

    @Transactional
    @Override
    public Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, List<ImageDto> images, User user) {

        List<Image> jobImages;
        if (!images.isEmpty())
            jobImages = imageService.createImages(images);
        else
            jobImages = new LinkedList<>();

        return jobDao.createJob(jobProvided, category, description, price, user, jobImages);
    }

    @Override
    public Optional<Job> getJobById(long id) {
        return jobDao.getJobById(id);
    }

    @Override
    public Collection<JobCategory> getJobsCategories() {
        return jobDao.getJobsCategories();
    }

    @Override
    public Collection<Long> getImagesIdsByJobId(Long jobId) {
        return jobDao.getImagesIdsByJobId(jobId);
    }

}
