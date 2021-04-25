package services;

import dao.ReimbursementDao;
import dao.ReimbursementDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;
import models.Reimbursement;

import java.util.List;

public class ReimbursementServiceImpl implements ReimbursementService{

    private ReimbursementDao reimbursementDao;
    private UserDao userDao;

    public ReimbursementServiceImpl(String url, String username, String password){
        reimbursementDao = new ReimbursementDaoImpl(url,username,password);
        userDao = new UserDaoImpl(url,username,password);

    }

    /**
     * list all Reimbursements
     * @return list of reimbursements
     * */
    @Override
    public List<Reimbursement> getAll() {
        return reimbursementDao.getAll();
    }

    /**
     * list all Reimbursements given username
     * @return list of reimbursements
     * */
    @Override
    public List<Reimbursement> getAllGivenUsername(String userId) {
        return reimbursementDao.getAllGivenUsername(userId);
    }

    /**
     * create a reimbursement
     * @return true if successful
     * */
    @Override
    public boolean createOne(Reimbursement reimbursement) {
        return reimbursementDao.createOne(reimbursement);
    }

    /**
     * approve a reimbursement
     * @return true if successful
     * */
    @Override
    public boolean approveOne(int reimbId, int resId) {
        return reimbursementDao.approveOne(reimbId,resId);
    }

    /**
     * deny a reimbursement
     * @return true if successful
     * */
    @Override
    public boolean denyOne(int reimbId, int resId) {
        return reimbursementDao.denyOne(reimbId,resId);
    }


    /**
     * delete a reimbursement
     * @return true if successful
     * */
    @Override
    public boolean deleteOne(int reimbId) {
        return reimbursementDao.deleteOne(reimbId);
    }

    /**
     * get one reimbursement
     * @return true one reimbursement
     * */
    @Override
    public Reimbursement getOneById(int reimbId) {
        return reimbursementDao.getOneGivenId(reimbId);
    }
}
