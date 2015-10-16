package com.epam.brest.course2015.service;

import com.epam.brest.course2015.dao.UserDao;
import com.epam.brest.course2015.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by juga on 14.10.15.
 */
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger();

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() {
        LOGGER.debug("getAllUsers()");
        return userDao.getAllUsers();
    }

    @Override
    public Integer addUser(User user) {
        Assert.notNull(user, "User should not be null.");

        String login = ((user.getLogin() == null) || (user.getLogin().length() == 0)) ? "empty" : user.getLogin();
        LOGGER.debug("addUser(): user login = ",
                ((user.getLogin() == null) || (user.getLogin().length() == 0)) ? "empty" : user.getLogin());
        Assert.isNull(user.getUserId(), "UserId should be null.");
        Assert.hasText(user.getLogin(), "User login should not be null.");
        Assert.hasText(user.getPassword(), "User password should not be null.");
        return userDao.addUser(user);
    }
}
