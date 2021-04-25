package dao;

import models.Reimbursement;

import java.time.LocalDateTime;
import java.util.List;

/**
 * dao implementation for the ers_reimbursement table
 * */
public interface ReimbursementDao {
    List<Reimbursement> getAll();
    List<Reimbursement> getAllGivenUsername(String userId);
    boolean createOne(Reimbursement reimbursement);
    boolean approveOne(int reimbId, int resId);
    boolean denyOne(int reimbId, int resId);
    boolean deleteOne(int reimbId);
    public Reimbursement getOneGivenId(int reimbId);
}
