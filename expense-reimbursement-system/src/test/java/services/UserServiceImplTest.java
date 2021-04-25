package services;

import dao.TestingConfig;
import dao.UserDao;
import dao.UserDaoImpl;
import models.User;
import models.UserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    UserService userService;
    UserDao mockUserDao = Mockito.mock(UserDaoImpl.class);
    @BeforeEach
    void setUp() {
        this.userService = new UserServiceImpl(mockUserDao);
        TestingConfig.h2InitDao();
    }

    @AfterEach
    void tearDown() {
        TestingConfig.h2DestroyDao();
    }

    @Test
    void validateCredentials() {
        User user = new User("testuser","passworduser","Test", "User","testuser@email.com",new UserRole(1));
        Mockito.when(mockUserDao.getOneByUsername("testuser")).thenReturn(user);

        //correct credentials
        assertEquals(user.toString(),userService.validateCredentials("testuser","passworduser").toString());
        //incorrect username
        Mockito.verify(mockUserDao, Mockito.times(1)).getOneByUsername("testuser");
    }

    @Test
    void getAll() {
        List<User> users = new ArrayList<>();
        users.add(new User("testuser","passworduser","Test", "User","testuser@email.com",new UserRole(1)));
        users.add(new User("testuser2","passworduser2","Test2", "User2","testuser@email.com",new UserRole(2)));
        Mockito.when(mockUserDao.getAll()).thenReturn(users);
        assertEquals(users,userService.getAll());
    }

    @Test
    void getOneById() {
        User user = new User("testuser","passworduser","Test", "User","testuser@email.com",new UserRole(1));
        Mockito.when(mockUserDao.getOneById(0)).thenReturn(user);
        assertEquals(user,userService.getOneById(0));
    }

    @Test
    void getOneByUsername() {
        User user = new User("testuser","passworduser","Test", "User","testuser@email.com",new UserRole(1));
        Mockito.when(mockUserDao.getOneByUsername("testuser")).thenReturn(user);
        assertEquals(user,userService.getOneByUsername("testuser"));
    }

    @Test
    void createOne() {
        User user = new User("testuser","passworduser","Test", "User","testuser@email.com",new UserRole(1));
        Mockito.when(mockUserDao.createOne(user)).thenReturn(true);
        assertTrue(userService.createOne(user));
    }

    @Test
    void deleteOneById() {
        Mockito.when(mockUserDao.deleteOneById(1)).thenReturn(true);
        assertTrue(userService.deleteOneById(1));
    }

    @Test
    void deleteOneByUsername() {
        Mockito.when(mockUserDao.deleteOneByUsername("username")).thenReturn(true);
        assertTrue(userService.deleteOneByUsername("username"));
    }
}
