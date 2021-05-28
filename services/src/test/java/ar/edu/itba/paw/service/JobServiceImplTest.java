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
import static org.junit.Assert.*;
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
    private static final long JOB_ID = 1L;


    private static final User DEFAULT_USER = new User(PASSWORD, NAME, SURNAME, EMAIL,
        PHONENUMBER, STATE, CITY, DEFAULT_ROLES);

    private static final Image IMAGE1 = new Image(1L,new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, "image/jpeg");
    private static final Image IMAGE2 = new Image(2L,new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, "image/jpeg");
    private static final Image IMAGE3 = new Image(3L,new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, "image/jpeg");

    private static final Set<Image> IMAGES = new HashSet<Image>() {{
        add(IMAGE1);
        add(IMAGE2);
        add(IMAGE3);
    }};

    private static final Job DEFAULT_JOB = new Job(DESCRIPTION, JOB_PROVIDED, 0, 0L, CATEGORY,
        PRICE, false, DEFAULT_USER, IMAGES);

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
        add(new Image(4L,IMAGE_TO_UPLOAD1.getData(), IMAGE_TO_UPLOAD1.getMimeType()));
        add(new Image(5L,IMAGE_TO_UPLOAD2.getData(), IMAGE_TO_UPLOAD2.getMimeType()));
        add(new Image(6L,IMAGE_TO_UPLOAD3.getData(), IMAGE_TO_UPLOAD3.getMimeType()));
        add(new Image(7L,IMAGE_TO_UPLOAD4.getData(), IMAGE_TO_UPLOAD4.getMimeType()));
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
    private ImageService mockImageService;

    @Mock
    private UserDao userDao;

    @Test
    public void testCreate() {
        when(mockJobDao.createJob(Mockito.eq(JOB_PROVIDED), Mockito.eq(CATEGORY),
            Mockito.eq(DESCRIPTION), Mockito.eq(PRICE), Mockito.eq(false),
            Mockito.eq(DEFAULT_USER), Mockito.any())).thenReturn(DEFAULT_JOB);

        Job maybeJob = jobService.createJob(JOB_PROVIDED, CATEGORY, DESCRIPTION,
            PRICE, false, Collections.emptyList(), DEFAULT_USER);

        assertNotNull(maybeJob);
        assertEquals(JOB_PROVIDED, maybeJob.getJobProvided());
        assertEquals(CATEGORY, maybeJob.getCategory());
        assertEquals(DESCRIPTION, maybeJob.getDescription());
        assertEquals(PRICE, maybeJob.getPrice());
        assertFalse(maybeJob.isPaused());
        assertEquals(DEFAULT_USER, maybeJob.getProvider());
    }

    @Test(expected = IllegalOperationException.class)
    public void testUpdateJobWithImagesNotCorrespondingToTheJob() {
        Job job = new Job(DESCRIPTION, JOB_PROVIDED, 0, 0L, CATEGORY,
            PRICE, false, DEFAULT_USER, new LinkedHashSet<>(IMAGES));

        List<Long> imagesToDelete = new LinkedList<Long>() {{
            add(4L);
            add(5L);
        }};

        jobService.updateJob(JOB_PROVIDED, DESCRIPTION, PRICE, false, new LinkedList<>(),
            job, imagesToDelete);

    }

    @Test(expected = MaxImagesPerJobException.class)
    public void testUpdateJobWithMaxImagesUploadPerJob() {
        Job job = new Job(DESCRIPTION, JOB_PROVIDED, 0, 0L, CATEGORY,
            PRICE, false, DEFAULT_USER, new LinkedHashSet<>(IMAGES));

        jobService.updateJob(JOB_PROVIDED, DESCRIPTION, PRICE, false,
            new LinkedList<>(IMAGES_TO_UPLOAD), job, new LinkedList<>());
    }

    @Test
    public void testUpdateJob() {
        Job job = new Job(DESCRIPTION, JOB_PROVIDED, 0, 0L, CATEGORY,
            PRICE, false, DEFAULT_USER, new LinkedHashSet<>(IMAGES));

        when(mockImageService.createImages(Mockito.eq(new LinkedList<>(IMAGES_TO_UPLOAD))))
            .thenReturn(new LinkedHashSet<>(IMAGES_UPLOADED));

        jobService.updateJob(JOB_PROVIDED, DESCRIPTION, PRICE, false,
            new LinkedList<>(IMAGES_TO_UPLOAD), job, IMAGES_ID_TO_DELETE);
    }

}
