package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.IllegalOperationException;
import ar.edu.itba.paw.interfaces.exceptions.MaxImagesPerJobException;
import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.interfaces.services.NotificationService;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.image.NewImageDto;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.job.JobStatus;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static ar.edu.itba.paw.models.job.Job.MAX_IMAGES_PER_JOB;

@org.springframework.stereotype.Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobDao jobDao;

    @Autowired
    private ImageService imageService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private NotificationService notificationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(JobServiceImpl.class);

    @Transactional
    @Override
    public Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, boolean paused, User provider, List<NewImageDto> images) {
        Set<Image> jobImages = null;
        if (!images.isEmpty()) {
            LOGGER.debug("Job {} has images", jobProvided);
            jobImages = imageService.createImages(images);
        } else
            LOGGER.debug("Job {} has no images", jobProvided);

        final Job job = jobDao.createJob(jobProvided, category, description, price, paused, provider, jobImages);
        LOGGER.info("Created job {} with id {}", job.getId(), job.getJobProvided());
        return job;
    }

    @Transactional
    @Override
    public Optional<Job> getJobById(long id) {
        LOGGER.debug("Retrieving job with id {}", id);
        return jobDao.getJobById(id);
    }

    @Transactional
    @Override
    public Optional<JobContact> getContactById(long id) {
        LOGGER.debug("Retrieving contact with id {}", id);
        return jobDao.getContactById(id);
    }

    @Transactional
    @Override
    public Collection<String> getJobsCategories() {
        LOGGER.debug("Retrieving jobs categories");
        return jobDao.getJobsCategories().stream().map(Enum::name).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Collection<String> getJobsOrder() {
        LOGGER.debug("Retrieving jobs order");
        return jobDao.getJobsOrder().stream().map(Enum::name).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Collection<String> getJobsRequestsOrder() {
        return jobDao.getJobsRequestsOrder().stream().map(Enum::name).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Collection<String> getJobsRequestsStatus() {
        return jobDao.getJobsRequestsStatus().stream().map(Enum::name).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void acceptJob(JobContact jc) {
        LOGGER.debug("Accepting job");
        jc.setStatus(JobStatus.IN_PROGRESS);
        emailService.sendJobConfirmationEmail(jc, LocaleContextHolder.getLocale());
        notificationService.createRequestStatusChangeAcceptForUser(jc.getUser(), jc);
    }

    @Transactional
    @Override
    public void rejectJob(JobContact jc) {
        LOGGER.debug("Rejecting job");
        jc.setStatus(JobStatus.REJECTED);
        emailService.sendJobCancellationEmail(jc, LocaleContextHolder.getLocale());
        notificationService.createRequestStatusRejectChangeForUser(jc.getUser(), jc);
    }

    @Transactional
    @Override
    public void finishJob(JobContact jc) {
        LOGGER.debug("Finishing job");
        jc.setStatus(JobStatus.FINISHED);
        emailService.sendJobFinishedEmail(jc, LocaleContextHolder.getLocale());
        notificationService.createRequestStatusFinishedChangeForUser(jc.getUser(), jc);
    }

    @Transactional
    @Override
    public void cancelJob(JobContact jc) {
        LOGGER.debug("Cancelling job");
        jc.setStatus(JobStatus.CANCELED);
        emailService.sendUserJobCancellationEmail(jc, LocaleContextHolder.getLocale());
        notificationService.createRequestStatusChangeForProvider(jc.getProvider(), jc);
    }

    @Transactional
    @Override
    public void updateJob(String jobProvided, String description, BigDecimal price, boolean paused, List<NewImageDto> imagesToUpload, Job job, List<Long> imagesIdToDelete) {

        LOGGER.debug("Updating job");

        job.setJobProvided(jobProvided);
        job.setDescription(description);
        job.setPrice(price);
        job.setPaused(paused);

        Set<Image> jobImages = job.getImages();

        if (!imagesIdToDelete.isEmpty()) {
            boolean contains = jobImages.stream().map(Image::getId).collect(Collectors.toSet()).containsAll(imagesIdToDelete);

            //If a user tries to delete images that are not from the job to update
            if (!contains) {
                LOGGER.warn("error: tried to delete image not corresponding to job");
                throw new IllegalOperationException();
            }
        }


        //If a job reaches the limit of images
        if (jobImages.size() - imagesIdToDelete.size() + imagesToUpload.size() > MAX_IMAGES_PER_JOB) {
            LOGGER.warn("error: tried to add more images than permitted");
            throw new MaxImagesPerJobException();
        }

        LOGGER.debug("Deleting job images");
        jobImages.removeIf(ji -> imagesIdToDelete.contains(ji.getId()));

        if (!imagesToUpload.isEmpty()) {
            LOGGER.debug("Job {} has images", jobProvided);
            Set<Image> images = imageService.createImages(imagesToUpload);
            jobImages.addAll(images);
        }
    }

}
