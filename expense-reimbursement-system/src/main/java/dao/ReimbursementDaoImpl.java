package dao;

import models.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DAO implementation for the ers_embursement table
 * */
public class ReimbursementDaoImpl implements ReimbursementDao{
    final static Logger loggy = Logger.getLogger(UserDaoImpl.class);
    private final String url;
    private final String username;
    private final String password;

    public ReimbursementDaoImpl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * get all reimbursements
     * @return array list of all reimbursements
     * */
    @Override
    public List<Reimbursement> getAll() {
        //my arrayList
        List<Reimbursement> reimbursements = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "SELECT r.reimb_id, r.reimb_amount, r.reimb_submitted, r.reimb_resolved, r.reimb_description, r.reimb_receipt, \n" +
                    "u.ers_user_id, u.ers_username, u.ers_password, u.user_first_name, u.user_last_name, u.user_email,\n" +
                    "eur.ers_user_role_id, eur.user_role,\n" +
                    "s.ers_user_id, s.ers_username, s.ers_password, s.user_first_name, s.user_last_name, s.user_email,\n" +
                    "eur2.ers_user_role_id, eur2.user_role, \n" +
                    "ers.reimb_status_id, ers.reimb_status,\n" +
                    "ert.reimb_type_id, ert.reimb_type \n" +
                    "FROM ers_reimbursement r\n" +
                    "LEFT JOIN ers_users u ON u.ers_user_id = r.reimb_author \n" +
                    "INNER JOIN ers_user_roles eur ON eur.ers_user_role_id = u.user_role_id\n" +
                    "LEFT JOIN ers_users s ON s.ers_user_id = r.reimb_resolver \n" +
                    "LEFT JOIN ers_user_roles eur2 ON eur2.ers_user_role_id  = s.user_role_id \n" +
                    "LEFT JOIN ers_reimbursement_status ers ON ers.reimb_status_id = r.reimb_status_id \n" +
                    "LEFT JOIN ers_reimbursement_type ert ON ert.reimb_type_id = r.reimb_type_id;";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery(); //<----query not update

            while(rs.next()){
                UserRole authorRole = new UserRole(rs.getInt(13), rs.getString(14));

                User author = new User(
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        authorRole
                );
                UserRole resolverRole = new UserRole(rs.getInt(21), rs.getString(22));

                User resolver = new User(
                        rs.getInt(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getString(10),
                        resolverRole
                );
                Status status = new Status(rs.getInt(23));
                Type type = new Type(rs.getInt(25));

                reimbursements.add(
                        new Reimbursement(
                                rs.getInt(1),
                                rs.getDouble(2),
                                rs.getObject(3, Date.class),
                                rs.getObject(4,Date.class),
                                rs.getString(5),
                                rs.getBytes(6),
                                author,
                                resolver,
                                status,
                                type
                        ));
            }

        }catch(SQLException e){
            e.printStackTrace();
            loggy.error(e);
        }

        return reimbursements;
    }

    /**
     * get all reimbursements given a username
     * @param userId username of user
     * @return array list of all reimbursements
     * */
    @Override
    public List<Reimbursement> getAllGivenUsername(String userId) {
        //my arrayList
        List<Reimbursement> reimbursements = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "SELECT r.reimb_id, r.reimb_amount, r.reimb_submitted, r.reimb_resolved, r.reimb_description, r.reimb_receipt, \n" +
                    "u.ers_user_id, u.ers_username, u.ers_password, u.user_first_name, u.user_last_name, u.user_email,\n" +
                    "eur.ers_user_role_id, eur.user_role,\n" +
                    "s.ers_user_id, s.ers_username, s.ers_password, s.user_first_name, s.user_last_name, s.user_email,\n" +
                    "eur2.ers_user_role_id, eur2.user_role, \n" +
                    "ers.reimb_status_id, ers.reimb_status,\n" +
                    "ert.reimb_type_id, ert.reimb_type \n" +
                    "FROM ers_reimbursement r\n" +
                    "LEFT JOIN ers_users u ON u.ers_user_id = r.reimb_author \n" +
                    "INNER JOIN ers_user_roles eur ON eur.ers_user_role_id = u.user_role_id\n" +
                    "LEFT JOIN ers_users s ON s.ers_user_id = r.reimb_resolver \n" +
                    "LEFT JOIN ers_user_roles eur2 ON eur2.ers_user_role_id  = s.user_role_id \n" +
                    "LEFT JOIN ers_reimbursement_status ers ON ers.reimb_status_id = r.reimb_status_id \n" +
                    "LEFT JOIN ers_reimbursement_type ert ON ert.reimb_type_id = r.reimb_type_id\n" +
                    "WHERE u.ers_username = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,userId);

            ResultSet rs = ps.executeQuery(); //<----query not update

            while(rs.next()){
                UserRole authorRole = new UserRole(rs.getInt(13), rs.getString(14));

                User author = new User(
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        authorRole
                );
                UserRole resolverRole = new UserRole(rs.getInt(21), rs.getString(22));

                User resolver = new User(
                        rs.getInt(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getString(10),
                        resolverRole
                );
                Status status = new Status(rs.getInt(23));
                Type type = new Type(rs.getInt(25));

                reimbursements.add(
                        new Reimbursement(
                                rs.getInt(1),
                                rs.getDouble(2),
                                rs.getObject(3,Date.class),
                                rs.getObject(4,Date.class),
                                rs.getString(5),
                                rs.getBytes(6),
                                author,
                                resolver,
                                status,
                                type
                        ));
            }

        }catch(SQLException e){
            e.printStackTrace();
            loggy.error(e);
        }

        return reimbursements;
    }

    /**
     * get one reimbursement given a id
     * @param reimbId username of user
     * @return one reimbursement
     * */
    @Override
    public Reimbursement getOneGivenId(int reimbId) {
        //my arrayList
        Reimbursement reimbursement = null;

        try(Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "SELECT r.reimb_id, r.reimb_amount, r.reimb_submitted, r.reimb_resolved, r.reimb_description, r.reimb_receipt, \n" +
                    "u.ers_user_id, u.ers_username, u.ers_password, u.user_first_name, u.user_last_name, u.user_email,\n" +
                    "eur.ers_user_role_id, eur.user_role,\n" +
                    "s.ers_user_id, s.ers_username, s.ers_password, s.user_first_name, s.user_last_name, s.user_email,\n" +
                    "eur2.ers_user_role_id, eur2.user_role, \n" +
                    "ers.reimb_status_id, ers.reimb_status,\n" +
                    "ert.reimb_type_id, ert.reimb_type \n" +
                    "FROM ers_reimbursement r\n" +
                    "LEFT JOIN ers_users u ON u.ers_user_id = r.reimb_author \n" +
                    "INNER JOIN ers_user_roles eur ON eur.ers_user_role_id = u.user_role_id\n" +
                    "LEFT JOIN ers_users s ON s.ers_user_id = r.reimb_resolver \n" +
                    "LEFT JOIN ers_user_roles eur2 ON eur2.ers_user_role_id  = s.user_role_id \n" +
                    "LEFT JOIN ers_reimbursement_status ers ON ers.reimb_status_id = r.reimb_status_id \n" +
                    "LEFT JOIN ers_reimbursement_type ert ON ert.reimb_type_id = r.reimb_type_id\n" +
                    "WHERE r.reimb_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,reimbId);

            ResultSet rs = ps.executeQuery(); //<----query not update

            while(rs.next()){
                UserRole authorRole = new UserRole(rs.getInt(13), rs.getString(14));

                User author = new User(
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        authorRole
                );
                UserRole resolverRole = new UserRole(rs.getInt(21), rs.getString(22));

                User resolver = new User(
                        rs.getInt(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getString(10),
                        resolverRole
                );
                Status status = new Status(rs.getInt(23));
                Type type = new Type(rs.getInt(25));

                reimbursement =
                        new Reimbursement(
                                rs.getInt(1),
                                rs.getDouble(2),
                                rs.getObject(3,Date.class),
                                rs.getObject(4,Date.class),
                                rs.getString(5),
                                rs.getBytes(6),
                                author,
                                resolver,
                                status,
                                type
                        );
            }

        }catch(SQLException e){
            e.printStackTrace();
            loggy.error(e);
        }

        return reimbursement;
    }

    /**
     * create a reimbursement
     * @param reimbursement reimbursement to be added
     * @return true if successful
     * */
    @Override
    public boolean createOne(Reimbursement reimbursement) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "INSERT INTO ers_reimbursement (reimb_amount,reimb_description,reimb_author,reimb_status_id, reimb_type_id, reimb_receipt)\n" +
                    "VALUES (?,?,?,?,?,?);";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1,reimbursement.getAmount());
            ps.setString(2,reimbursement.getDescription());
            ps.setInt(3,reimbursement.getAuthor().getId());
            ps.setInt(4,reimbursement.getStatus().getId());
            ps.setInt(5,reimbursement.getType().getId());
            ps.setBytes(6,reimbursement.getReceipt());


            return ps.executeUpdate() != 0;


        }catch(SQLException e){
            e.printStackTrace();
            loggy.error(e);
        }

        return false;
    }

    /**
     * updates a reimbursement
     * @param reimbId id of reimbursement to update
     * @param resId id of resolver user
     * @return true if successful
     * */
    @Override
    public boolean approveOne(int reimbId, int resId) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "UPDATE ers_reimbursement \n" +
                    "SET reimb_status_id = 2, reimb_resolver = ?, reimb_resolved = NOW()\n" +
                    "WHERE reimb_id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,resId);
            ps.setInt(2,reimbId);


            return ps.executeUpdate() != 0;


        }catch(SQLException e){
            e.printStackTrace();
            loggy.error(e);
        }

        return false;
    }

    /**
     * deny a reimbursement
     * @param reimbId id of reimbursement to update
     * @param resId id of resolver user
     * @return true if successful
     * */
    @Override
    public boolean denyOne(int reimbId, int resId) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "UPDATE ers_reimbursement \n" +
                    "SET reimb_status_id = 3, reimb_resolver = ?, reimb_resolved = NOW()\n" +
                    "WHERE reimb_id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,resId);
            ps.setInt(2,reimbId);


            return ps.executeUpdate() != 0;


        }catch(SQLException e){
            e.printStackTrace();
            loggy.error(e);
        }

        return false;
    }

    /**
     * deletes a reimbursement
     * @param reimbId id of reimbursement to delete
     * @return true if successful
     * */
    @Override
    public boolean deleteOne(int reimbId) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "DELETE FROM ers_reimbursement WHERE reimb_id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,reimbId);


            return ps.executeUpdate() != 0;


        }catch(SQLException e){
            e.printStackTrace();
            loggy.error(e);
        }

        return false;
    }



}
