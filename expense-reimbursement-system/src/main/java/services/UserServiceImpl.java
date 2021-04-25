package services;

import dao.UserDao;
import dao.UserDaoImpl;
import models.User;
import utilities.Encryption;

import java.util.List;

/**
 * service for users
 * */
public class UserServiceImpl implements UserService{
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao){
        this.userDao = userDao;

    }

    public UserServiceImpl(String url,String username, String password) {
        userDao = new UserDaoImpl(url,username,password);
    }
    /**
     * get all users
     * @return list of users
     * */
    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    /**
     * get one user by id
     * @return user model
     * */
    @Override
    public User getOneById(int userId) {
        return userDao.getOneById(userId);
    }

    /**
     * get one user by username
     * @return user model
     * */
    @Override
    public User getOneByUsername(String userId) {
        return userDao.getOneByUsername(userId);
    }

    /**
     * create a user
     * @return successful if true
     * */
    @Override
    public boolean createOne(User user) {
        return userDao.createOne(user);
    }

    /**
     * delete user by id
     * @return true if successful
     * */
    @Override
    public boolean deleteOneById(int userId) {
        return userDao.deleteOneById(userId);
    }

    /**
     * delete on by username
     * @return true if successful
     * */
    @Override
    public boolean deleteOneByUsername(String userId) {
        return userDao.deleteOneByUsername(userId);
    }

    /**
     * validate user exists
     * @return user if exists, null if not found
     * */
    @Override
    public User validateCredentials(String username, String password) {
        //check if username exists in system
        User user = userDao.getOneByUsername(username);
        if(user == null) return null;
        String decryptedDBPassword = Encryption.decrypt(user.getPassword());
        if(!decryptedDBPassword.equals(password)) return null;

        return user;
    }

    @Override
    public User getOneByEmail(String email) {
        return userDao.getOneByEmail(email);
    }
}
