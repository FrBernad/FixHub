package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobDao jobDao;

    @Autowired
    private ImageService imageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(JobServiceImpl.class);

    @Transactional
    @Override
    public Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, boolean paused ,List<ImageDto> images, User user) {

        List<Image> jobImages;
        if (!images.isEmpty()) {
            LOGGER.debug("Job {} has images", jobProvided);
            jobImages = imageService.createImages(images);
        } else {
            LOGGER.debug("Job {} has no images", jobProvided);
            jobImages = new LinkedList<>();
        }

        Job job = jobDao.createJob(jobProvided, category, description, price, paused, user, jobImages);
        LOGGER.info("Created job {} with id {}", job.getJobProvided(), job.getId());
        return job;
    }

    @Override
    public Optional<Job> getJobById(long id) {
        LOGGER.debug("Retrieving job with id {}", id);
        return jobDao.getJobById(id);
    }

    @Override
    public Collection<JobCategory> getJobsCategories() {
        LOGGER.debug("Retrieving jobs categories");
        return jobDao.getJobsCategories();
    }

    @Override
    public Collection<Long> getImagesIdsByJobId(Long jobId) {
        LOGGER.debug("Retrieving job images for job id {}",jobId);
        return jobDao.getImagesIdsByJobId(jobId);
    }

    @Transactional
    @Override
    public void updateJob(String jobProvided, JobCategory category, String description, BigDecimal price, long jobID) {

        jobDao.updateJob(jobProvided,category,description,price,jobID);
    }

}
