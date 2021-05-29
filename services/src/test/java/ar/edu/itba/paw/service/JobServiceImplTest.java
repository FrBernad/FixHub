package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.exceptions.IllegalOperationException;
import ar.edu.itba.paw.interfaces.exceptions.MaxImagesPerJobException;
import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.ImageDto;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.services.JobServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.*;

import static ar.edu.itba.paw.services.UserServiceImpl.DEFAULT_ROLES;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class JobServiceImplTest {

    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String EMAIL = "email";
    private static final String PHONENUMBER = "phoneNumber";
    private static final String STATE = "state";
    private static final String CITY = "city";

    private static final String DESCRIPTION = "arreglo techos y los dejo como nuevos, años de experiencia";
    private static final String JOB_PROVIDED = "arreglo techos";
    private static final JobCategory CATEGORY = JobCategory.TECHISTA;
    private static final BigDecimal PRICE = BigDecimal.valueOf(3000);
    private static final boolean PAUSED = false;



    private static final User DEFAULT_USER = new User(PASSWORD, NAME, SURNAME, EMAIL,
        PHONENUMBER, STATE, CITY, DEFAULT_ROLES);

    private static final Image IMAGE1 = new Image(1L, new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, "image/jpeg");
    private static final Image IMAGE2 = new Image(2L, new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, "image/jpeg");
    private static final Image IMAGE3 = new Image(3L, new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, "image/jpeg");

    private static final Set<Image> IMAGES = new HashSet<Image>() {{
        add(IMAGE1);
        add(IMAGE2);
        add(IMAGE3);
    }};

    private static final ImageDto IMAGE_TO_UPLOAD1 = new ImageDto(
        new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, "image/jpeg");
    private static final ImageDto IMAGE_TO_UPLOAD2 = new ImageDto(
        new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, "image/jpeg");
    private static final ImageDto IMAGE_TO_UPLOAD3 = new ImageDto(
        new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, "image/jpeg");
    private static final ImageDto IMAGE_TO_UPLOAD4 = new ImageDto(
        new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, "image/jpeg");

    private static final Collection<ImageDto> IMAGES_TO_UPLOAD = new LinkedList<ImageDto>() {{
        add(IMAGE_TO_UPLOAD1);
        add(IMAGE_TO_UPLOAD2);
        add(IMAGE_TO_UPLOAD3);
        add(IMAGE_TO_UPLOAD4);
    }};

    private static final List<Image> IMAGES_UPLOADED = new LinkedList<Image>() {{
        add(new Image(4L, IMAGE_TO_UPLOAD1.getData(), IMAGE_TO_UPLOAD1.getMimeType()));
        add(new Image(5L, IMAGE_TO_UPLOAD2.getData(), IMAGE_TO_UPLOAD2.getMimeType()));
        add(new Image(6L, IMAGE_TO_UPLOAD3.getData(), IMAGE_TO_UPLOAD3.getMimeType()));
        add(new Image(7L, IMAGE_TO_UPLOAD4.getData(), IMAGE_TO_UPLOAD4.getMimeType()));
    }};

    private static final List<Long> IMAGES_ID_TO_DELETE = new LinkedList<Long>() {{
        add(1L);
        add(2L);
    }};

    @InjectMocks
    private JobServiceImpl jobService = new JobServiceImpl();

    @Mock
    private JobDao mockJobDao;

    @Mock
    private UserDao userDao;

    @Mock
    private ImageService mockImageService;

    @Mock
    private Job job;


    @Test
    public void testCreate() {

        Set<Image> images = new HashSet<>(IMAGES_UPLOADED);
        List<ImageDto> imagesToUpload = new LinkedList<>(IMAGES_TO_UPLOAD);

        Job defaultJob = new Job(DESCRIPTION, JOB_PROVIDED, 0, 0L, CATEGORY,
            PRICE, PAUSED, DEFAULT_USER,images);


        when(mockJobDao.createJob(Mockito.eq(JOB_PROVIDED), Mockito.eq(CATEGORY),
            Mockito.eq(DESCRIPTION), Mockito.eq(PRICE), Mockito.eq(PAUSED),
            Mockito.eq(DEFAULT_USER), Mockito.any())).thenReturn(defaultJob);

        lenient().when(job.getJobProvided()).thenReturn("");
        lenient().when(job.getId()).thenReturn(1L);

        when(mockImageService.createImages(imagesToUpload))
            .thenReturn(images);

        jobService.createJob(JOB_PROVIDED, CATEGORY, DESCRIPTION,
            PRICE, false, imagesToUpload, DEFAULT_USER);

        Mockito.verify(mockJobDao).createJob(JOB_PROVIDED, CATEGORY,
            DESCRIPTION, PRICE, PAUSED,
            DEFAULT_USER, images);
    }

    @Test(expected = IllegalOperationException.class)
    public void testUpdateJobWithImagesNotCorrespondingToTheJob() {

        List<Long> imagesToDelete = new LinkedList<Long>() {{
            add(4L);
            add(5L);
        }};

        jobService.updateJob(JOB_PROVIDED, DESCRIPTION, PRICE, false, new LinkedList<>(),
            job, imagesToDelete);

        Mockito.verify(job).setJobProvided(JOB_PROVIDED);
        Mockito.verify(job).setDescription(DESCRIPTION);
        Mockito.verify(job).setPrice(PRICE);
        Mockito.verify(job).setPaused(false);

    }

    @Test(expected = MaxImagesPerJobException.class)
    public void testUpdateJobWithMaxImagesUploadPerJob() {

        List<ImageDto> imagesToUpload = new LinkedList<>(IMAGES_TO_UPLOAD);
        Set<Image> images = new LinkedHashSet<>(IMAGES);


        when(job.getImages()).thenReturn(images);

        jobService.updateJob(JOB_PROVIDED, DESCRIPTION, PRICE, false,
            imagesToUpload, job, new LinkedList<>());


        Mockito.verify(job).setJobProvided(JOB_PROVIDED);
        Mockito.verify(job).setDescription(DESCRIPTION);
        Mockito.verify(job).setPrice(PRICE);
        Mockito.verify(job).setPaused(false);


    }

    @Test
    public void testUpdateJob() {
        List<ImageDto> imagesToUpload = new LinkedList<>(IMAGES_TO_UPLOAD);
        Set<Image> imagesUploaded = new HashSet<>(IMAGES_UPLOADED);
        Set<Image> images = new LinkedHashSet<>(IMAGES);

        when(mockImageService.createImages(imagesToUpload))
            .thenReturn(imagesUploaded);

        when(job.getImages()).thenReturn(images);

        jobService.updateJob(JOB_PROVIDED, DESCRIPTION, PRICE, false,
            imagesToUpload, job, IMAGES_ID_TO_DELETE);

        Mockito.verify(job).setJobProvided(JOB_PROVIDED);
        Mockito.verify(job).setDescription(DESCRIPTION);
        Mockito.verify(job).setPrice(PRICE);
        Mockito.verify(job).setPaused(false);

    }

}
