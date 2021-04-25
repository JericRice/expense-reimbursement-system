package dao;

import models.User;

import java.util.List;
/**
 * dao implementation for the ers_users table
 * */
public interface UserDao {
    List<User> getAll();
    User getOneById(int userId);
    User getOneByUsername(String userId);
    boolean createOne(User user);
    boolean deleteOneById(int userId);
    boolean deleteOneByUsername(String userId);

    User getOneByEmail(String email);
}
