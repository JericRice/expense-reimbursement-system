package dao;

import models.User;
import models.UserRole;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * dao implementation for the ers_users table
 * */
public class UserDaoImpl implements UserDao{
    final static Logger loggy = Logger.getLogger(UserDaoImpl.class);
    private final String url;
    private final String username;
    private final String password;

    public UserDaoImpl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * get all users in system
     * @return list of all users
     * */
    @Override
    public List<User> getAll() {
        //my arrayList
        List<User> users = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "SELECT u.ers_user_id, u.ers_username, u.ers_password, u.user_first_name, u.user_last_name, u.user_email, eur.ers_user_role_id ,eur.user_role\n" +
                    "FROM ers_users u\n" +
                    "INNER JOIN ers_user_roles eur ON eur.ers_user_role_id = u.user_role_id;";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery(); //<----query not update

            while(rs.next()){
                UserRole role = new UserRole(
                        rs.getInt(7),
                        rs.getString(8)
                );
                users.add(
                        new User(rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                role
                        ));
            }

        }catch(SQLException e){
            e.printStackTrace();
            loggy.error(e);
        }

        return users;
    }

    /**
     * get one user from database given by username
     * @param userId username of user
     * @return user object
     * */

    @Override
    public User getOneByUsername(String userId) {
        //my arrayList
        User user = null;

        try(Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "SELECT u.ers_user_id, u.ers_username, u.ers_password, u.user_first_name, u.user_last_name, u.user_email, eur.ers_user_role_id ,eur.user_role\n" +
                    "FROM ers_users u\n" +
                    "INNER JOIN ers_user_roles eur ON eur.ers_user_role_id = u.user_role_id " +
                    "WHERE ers_username = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,userId);

            ResultSet rs = ps.executeQuery(); //<----query not update

            while(rs.next()){
                UserRole role = new UserRole(
                        rs.getInt(7),
                        rs.getString(8)
                );
                user =
                        new User(rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                role
                        );
            }

        }catch(SQLException e){
            e.printStackTrace();
            loggy.error(e);
        }

        return user;
    }
    /**
     * get one user from database given id
     * @param userId id of user
     * @return user object
     * */
    @Override
    public User getOneById(int userId) {
        //my arrayList
        User user = null;

        try(Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "SELECT u.ers_user_id, u.ers_username, u.ers_password, u.user_first_name, u.user_last_name, u.user_email, eur.ers_user_role_id ,eur.user_role\n" +
                    "FROM ers_users u\n" +
                    "INNER JOIN ers_user_roles eur ON eur.ers_user_role_id = u.user_role_id " +
                    "WHERE ers_user_id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,userId);

            ResultSet rs = ps.executeQuery(); //<----query not update

            while(rs.next()){
                UserRole role = new UserRole(
                        rs.getInt(7),
                        rs.getString(8)
                );
                user =
                        new User(rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                role
                        );
            }

        }catch(SQLException e){
            e.printStackTrace();
            loggy.error(e);
        }

        return user;
    }

    /**
     * create a user
     * @param user user to be added
     * @return true if successful
     * */
    @Override
    public boolean createOne(User user) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "INSERT INTO ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?);";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getFirstname());
            ps.setString(4,user.getLastname());
            ps.setString(5,user.getEmail());
            ps.setInt(6,user.getRole().getId());


            return ps.executeUpdate() != 0;


        }catch(SQLException e){
            e.printStackTrace();
            loggy.error(e);
        }

        return false;
    }

    /**
     * delete a user given id
     * @param userId id to be deleted
     * @return true if successful
     * */
    @Override
    public boolean deleteOneById(int userId) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "DELETE FROM ers_users WHERE ers_user_id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,userId);


            return ps.executeUpdate() != 0;


        }catch(SQLException e){
            e.printStackTrace();
            loggy.error(e);
        }

        return false;
    }

    /**
     * delete a user given username
     * @param userId username of user to be added
     * @return true if successful
     * */
    @Override
    public boolean deleteOneByUsername(String userId) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "DELETE FROM ers_users WHERE ers_username = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,userId);


            return ps.executeUpdate() != 0;


        }catch(SQLException e){
            e.printStackTrace();
            loggy.error(e);
        }

        return false;
    }

    @Override
    public User getOneByEmail(String email) {
        //my arrayList
        User user = null;

        try(Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "SELECT u.ers_user_id, u.ers_username, u.ers_password, u.user_first_name, u.user_last_name, u.user_email, eur.ers_user_role_id ,eur.user_role\n" +
                    "FROM ers_users u\n" +
                    "INNER JOIN ers_user_roles eur ON eur.ers_user_role_id = u.user_role_id " +
                    "WHERE user_email = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,email);

            ResultSet rs = ps.executeQuery(); //<----query not update

            while(rs.next()){
                UserRole role = new UserRole(
                        rs.getInt(7),
                        rs.getString(8)
                );
                user =
                        new User(rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                role
                        );
            }

        }catch(SQLException e){
            e.printStackTrace();
            loggy.error(e);
        }

        return user;
    }
}
