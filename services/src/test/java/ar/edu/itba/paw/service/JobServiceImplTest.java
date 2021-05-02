package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistance.JobDao;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.JobServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.*;

import static ar.edu.itba.paw.models.JobCategory.*;
import static ar.edu.itba.paw.services.UserServiceImpl.DEFAULT_ROLES;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class JobServiceImplTest {

    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String EMAIL = "email";
    private static final String PHONENUMBER = "phoneNumber";
    private static final String STATE = "state";
    private static final String CITY = "city";

    private static final List<Image> IMAGES = Collections.emptyList();
    private static final String DESCRIPTION = "arreglo techos y los dejo como nuevos, años de experiencia";
    private static final String JOB_PROVIDED = "arreglo techos";
    private static final JobCategory CATEGORY = TECHISTA;
    private static final BigDecimal PRICE = BigDecimal.valueOf(3000);


    private static final User DEFAULT_USER = new User(1L, PASSWORD, NAME, SURNAME,
        EMAIL, PHONENUMBER, STATE, CITY,
        DEFAULT_ROLES, null, null);

    private static final Job DEFAULT_JOB = new Job(DESCRIPTION, JOB_PROVIDED, 0, 0, CATEGORY, 1L, PRICE, false, DEFAULT_USER, 0L, new ArrayList<>());

    @InjectMocks
    private JobServiceImpl jobService = new JobServiceImpl();

    @Mock
    private JobDao mockJobDao;

    @Mock
    private UserDao userDao;

    @Test
    public void testCreate() {
        when(mockJobDao.createJob(Mockito.eq(JOB_PROVIDED), Mockito.eq(CATEGORY), Mockito.eq(DESCRIPTION), Mockito.eq(PRICE), Mockito.eq(false), Mockito.eq(DEFAULT_USER), Mockito.any())).thenReturn(DEFAULT_JOB);
        when(userDao.getAllUserFollowers(Mockito.eq(DEFAULT_USER.getId()))).thenReturn(Collections.emptyList());

        Job maybeJob = jobService.createJob(JOB_PROVIDED, CATEGORY, DESCRIPTION, PRICE, false, Collections.emptyList(), DEFAULT_USER);

        assertNotNull(maybeJob);
        assertEquals(JOB_PROVIDED, maybeJob.getJobProvided());
        assertEquals(CATEGORY, maybeJob.getCategory());
        assertEquals(DESCRIPTION, maybeJob.getDescription());
        assertEquals(PRICE, maybeJob.getPrice());
        assertFalse(maybeJob.isPaused());
        assertEquals(DEFAULT_USER, maybeJob.getProvider());
    }
}
