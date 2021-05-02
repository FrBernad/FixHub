package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.models.*;
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
    public Job createJob(String jobProvided, JobCategory category, String description, BigDecimal price, boolean paused, List<ImageDto> images, User user) {

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

        Collection<User> providerFollowers = userDao.getAllUserFollowers(user.getId());

        for (User follower : providerFollowers) {
            sendNewJobNotificationMail(follower, job);
        }

        return job;
    }

    private void sendNewJobNotificationMail(User user, Job job) {
        try {
            Locale locale = LocaleContextHolder.getLocale();
            String url = new URL("http", appBaseUrl, "/paw-2021a-06/job/"+job.getId()).toString();
            User provider = job.getProvider();

            Map<String, Object> mailAttrs = new HashMap<>();
            mailAttrs.put("newJobUrl", url);
            mailAttrs.put("to", user.getEmail());
            mailAttrs.put("providerName",String.format("%s %s",provider.getName(),provider.getSurname()));
            mailAttrs.put("followerName",user.getName());
            mailAttrs.put("jobName",job.getJobProvided());

            emailService.sendMail("newJobNotification", messageSource.getMessage("email.newJobNotification", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Error, new job notification mail not sent");
        }
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
        LOGGER.debug("Retrieving job images for job id {}", jobId);
        return jobDao.getImagesIdsByJobId(jobId);
    }

    @Transactional
    @Override
    public void updateJob(String jobProvided, String description, BigDecimal price, boolean paused, List<ImageDto> images, long jobId, List<Long> imagesIdDeleted) {
        List<Image> jobImages;

        if (!images.isEmpty())
            jobImages = imageService.createImages(images);
        else
            jobImages = new LinkedList<>();

        jobDao.updateJob(jobProvided, description, price, paused, jobImages, jobId, imagesIdDeleted);

        if (!imagesIdDeleted.isEmpty()) {
            imageService.deleteImagesById(imagesIdDeleted);
        }
    }

    @Override
    @Transactional
    public void deleteImageFromJob(long jobId, long imageId, User user) {
        imageService.deleteImageById(imageId);
        jobDao.deleteImageById(imageId, jobId);
    }


}
