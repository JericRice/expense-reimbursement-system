package dao;

import models.User;
import models.UserRole;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {

    UserDao userDao = new UserDaoImpl(TestingConfig.url,TestingConfig.username, TestingConfig.password);


    @BeforeEach
    void setUp() {
        TestingConfig.h2InitDao();
    }

    @AfterEach
    void tearDown() {
        TestingConfig.h2DestroyDao();
    }

    @Test
    void createOne() {
        User employee = new User( "employeeTest1", "employeePassword1", "Employee", "Test1", "etest1@email.com", new UserRole(1,"EMPLOYEE"));

        assertTrue(userDao.createOne(employee));
        assertFalse(userDao.createOne(employee));
    }

    @Test
    void deleteOneById() {
        User employee = new User( "employeeTest1", "employeePassword1", "Employee", "Test1", "etest1@email.com", new UserRole(1,"EMPLOYEE"));
        userDao.createOne(employee);
        employee = userDao.getOneByUsername(employee.getUsername());
        assertTrue(userDao.deleteOneById(employee.getId()));
    }

    @Test
    void deleteOneByUsername() {
        User employee = new User( "employeeTest1", "employeePassword1", "Employee", "Test1", "etest1@email.com", new UserRole(1,"EMPLOYEE"));
        userDao.createOne(employee);
        assertTrue(userDao.deleteOneByUsername(employee.getUsername()));
    }
}
