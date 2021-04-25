package dao;

import models.Reimbursement;
import models.Type;
import models.User;
import models.UserRole;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReimbursementDaoImplTest {
    UserDao userDao = new UserDaoImpl(TestingConfig.url,TestingConfig.username, TestingConfig.password);
    ReimbursementDao reimbursementDao = new ReimbursementDaoImpl(TestingConfig.url,TestingConfig.username, TestingConfig.password);

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

        userDao.createOne(employee);
        //userDao.createOne(financeManager);
        employee = userDao.getOneByUsername(employee.getUsername());
        //financeManager = userDao.getOneByUsername(financeManager.getUsername());

        assertTrue(reimbursementDao.createOne(new Reimbursement(
                543.21,
                employee,
                new Type(2)
        )));
    }

    @Test
    void approveOne() {
        User employee = new User( "employeeTest1", "employeePassword1", "Employee", "Test1", "etest1@email.com", new UserRole(1,"EMPLOYEE"));
        User financeManager = new User( "fManagerTest1", "fManagerPassword1", "Financial", "Manager", "fmanager1@email.com", new UserRole(2,"MANAGER"));

        userDao.createOne(employee);
        userDao.createOne(financeManager);
        employee = userDao.getOneByUsername(employee.getUsername());
        financeManager = userDao.getOneByUsername(financeManager.getUsername());

        reimbursementDao.createOne(new Reimbursement(
                543.21,
                employee,
                new Type(2)
        ));

        List<Reimbursement> reimbursements = reimbursementDao.getAll();
        Reimbursement reimbursement = null;
        for(Reimbursement r : reimbursements)
            if(r.getAuthor().getUsername().equals(employee.getUsername()))
                reimbursement = r;

        assertTrue(reimbursementDao.approveOne(reimbursement.getId(),financeManager.getId()));
    }

    @Test
    void denyOne() {
        User employee = new User( "employeeTest1", "employeePassword1", "Employee", "Test1", "etest1@email.com", new UserRole(1,"EMPLOYEE"));
        User financeManager = new User( "fManagerTest1", "fManagerPassword1", "Financial", "Manager", "fmanager1@email.com", new UserRole(2,"MANAGER"));

        userDao.createOne(employee);
        userDao.createOne(financeManager);
        employee = userDao.getOneByUsername(employee.getUsername());
        financeManager = userDao.getOneByUsername(financeManager.getUsername());

        reimbursementDao.createOne(new Reimbursement(
                543.21,
                employee,
                new Type(2)
        ));

        List<Reimbursement> reimbursements = reimbursementDao.getAll();
        Reimbursement reimbursement = null;
        for(Reimbursement r : reimbursements)
            if(r.getAuthor().getUsername().equals(employee.getUsername()))
                reimbursement = r;

        assertTrue(reimbursementDao.denyOne(reimbursement.getId(),financeManager.getId()));
    }

    @Test
    void deleteOne(){
        User employee = new User( "employeeTest1", "employeePassword1", "Employee", "Test1", "etest1@email.com", new UserRole(1,"EMPLOYEE"));
        User financeManager = new User( "fManagerTest1", "fManagerPassword1", "Financial", "Manager", "fmanager1@email.com", new UserRole(2,"MANAGER"));

        userDao.createOne(employee);
        userDao.createOne(financeManager);
        employee = userDao.getOneByUsername(employee.getUsername());
        financeManager = userDao.getOneByUsername(financeManager.getUsername());

        reimbursementDao.createOne(new Reimbursement(
                543.21,
                employee,
                new Type(2)
        ));

        List<Reimbursement> reimbursements = reimbursementDao.getAll();
        Reimbursement reimbursement = null;
        for(Reimbursement r : reimbursements)
            if(r.getAuthor().getUsername().equals(employee.getUsername()))
                reimbursement = r;

        assertTrue(reimbursementDao.deleteOne(reimbursement.getId()));
    }

}
