package services;

import models.Reimbursement;

import java.util.List;

/**
 * service for reimbursements
 * */
public interface ReimbursementService {
    List<Reimbursement> getAll();
    List<Reimbursement> getAllGivenUsername(String userId);
    boolean createOne(Reimbursement reimbursement);
    boolean approveOne(int reimbId, int resId);
    boolean denyOne(int reimbId, int resId);
    boolean deleteOne(int reimbId);

    Reimbursement getOneById(int reimbId);
}
