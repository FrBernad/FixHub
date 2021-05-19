package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.IllegalOperationException;
import ar.edu.itba.paw.interfaces.exceptions.MaxImagesPerJobException;
import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static ar.edu.itba.paw.models.job.Job.MAX_IMAGES_PER_JOB;

@org.springframework.stereotype.Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobDao jobDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ImageService imageService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private String appBaseUrl;

    @Autowired
    private MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(JobServiceImpl.class);

    @Transactional
    @Override
    public Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, boolean paused, List<ImageDto> images, User provider) {

        Set<Image> jobImages = null;
        if (!images.isEmpty()) {
            LOGGER.debug("Job {} has images", jobProvided);
            jobImages = imageService.createImages(images);
        } else {
            LOGGER.debug("Job {} has no images", jobProvided);
        }

        final Job job = jobDao.createJob(jobProvided, category, description, price, paused, provider, jobImages);
        LOGGER.info("Created job {} with id {}", job.getJobProvided(), job.getId());

        final Collection<User> providerFollowers = provider.getFollowers();

        for (User follower : providerFollowers) {
            sendNewJobNotificationMail(follower, job);
        }

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

    @Transactional
    @Override
    public void updateJob(String jobProvided, String description, BigDecimal price, boolean paused, List<ImageDto> imagesToUpload, Job job, List<Long> imagesIdToDelete) {
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

    private void sendNewJobNotificationMail(User user, Job job) {
        try {
            final Locale locale = LocaleContextHolder.getLocale();
            final String url = new URL("http", appBaseUrl, "/paw-2021a-06/job/" + job.getId()).toString();
            final User provider = job.getProvider();

            final Map<String, Object> mailAttrs = new HashMap<>();
            mailAttrs.put("newJobUrl", url);
            mailAttrs.put("to", user.getEmail());
            mailAttrs.put("providerName", String.format("%s %s", provider.getName(), provider.getSurname()));
            mailAttrs.put("followerName", user.getName());
            mailAttrs.put("jobName", job.getJobProvided());

            emailService.sendMail("newJobNotification", messageSource.getMessage("email.newJobNotification", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Error, new job notification mail not sent");
        }
    }

}
