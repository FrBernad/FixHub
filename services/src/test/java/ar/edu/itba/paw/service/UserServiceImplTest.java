package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String EMAIL = "email";
    private static final String PHONENUMBER = "phoneNumber";
    private static final String STATE = "state";
    private static final String CITY = "city";


    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl();

    @Mock
    private UserDao mockDao;

    @Test
    public void testCreate() throws DuplicateUserException {
        // 1. Setup!
        Mockito.when(mockDao.createUser(Mockito.eq(PASSWORD), Mockito.eq(NAME), Mockito.eq(SURNAME),
                Mockito.eq(EMAIL), Mockito.eq(PHONENUMBER), Mockito.eq(STATE), Mockito.eq(CITY), new ArrayList<>())).
                thenReturn(new User(1L, PASSWORD, NAME,SURNAME,EMAIL,PHONENUMBER,STATE,CITY, new ArrayList<>()));
        // 2. "ejercito" la class under test
        User maybeUser = userService.createUser(PASSWORD, NAME,SURNAME,EMAIL,PHONENUMBER,STATE,CITY);
        // 3. Asserts!
        Assert.assertNotNull(maybeUser);
        Assert.assertEquals(PASSWORD, maybeUser.getPassword());
        Assert.assertEquals(NAME, maybeUser.getName());
        Assert.assertEquals(SURNAME,maybeUser.getSurname());
        Assert.assertEquals(EMAIL,maybeUser.getEmail());
        Assert.assertEquals(PHONENUMBER,maybeUser.getPhoneNumber());
        Assert.assertEquals(CITY,maybeUser.getCity());
        Assert.assertEquals(STATE,maybeUser.getState());

    }

    @Test
    public void testCreateEmptyPassword() {
//        // 1. Setup!
//        // 2. "ejercito" la class under test
//        Optional<User> maybeUser = userService.create(USERNAME, "");// 3. Asserts!
//        Assert.assertNotNull(maybeUser);
//        Assert.assertFalse(maybeUser.isPresent());
    }

    @Test
    public void testCreateAlreadyExists() {
//        // 1. Setup!
//        Mockito.when(mockDao.findByUsername(Mockito.eq(USERNAME))).thenReturn(Optional.of(new User(1, USERNAME, PASSWORD)));
//        // 2. "ejercito" la class under test
//        Optional<User> maybeUser = userService.create(USERNAME, PASSWORD);
//        // 3. Asserts!
//        Assert.assertNotNull(maybeUser);
//        Assert.assertFalse(maybeUser.isPresent());
    }


}





