package services;

import models.User;

import java.util.List;


/**
 * service for users
 * */
public interface UserService {
    List<User> getAll();
    User getOneById(int userId);
    User getOneByUsername(String userId);
    boolean createOne(User user);
    boolean deleteOneById(int userId);
    boolean deleteOneByUsername(String userId);
    User validateCredentials(String username, String password);

    User getOneByEmail(String email);
}
