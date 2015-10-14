package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by juga on 7.10.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional()
public class UserDaoImplTest {

    public static final String USER_LOGIN1 = "userLogin1";
    public static final String USER_PASSWORD1 = "userPassword1";

    @Autowired
    private UserDao userDao;

    private static final User user = new User(null, "userLogin3", "userPassword3", new Date());

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = userDao.getAllUsers();
        assertTrue(users.size() == 2);
    }

    @Test
    public void testGetUser() throws Exception {
        int userId = 1;
        User user = userDao.getUserById(userId);
        assertNotNull(user);
        assertTrue(user.getUserId().equals(userId));
    }

    @Test
    public void testGetUserByLogin() throws Exception {
        User user = userDao.getUserByLogin(USER_LOGIN1);
        assertNotNull(user);
        assertTrue(user.getLogin().equals(USER_LOGIN1));
    }

    @Test
    public void testAddUser() throws Exception {
        Integer userId = userDao.addUser(user);
        assertNotNull(userId);
        User newUser = userDao.getUserById(userId);
        assertTrue(user.getLogin().equals(newUser.getLogin()));
        assertTrue(user.getPassword().equals(newUser.getPassword()));
        assertTrue(user.getCreatedDate().equals(newUser.getCreatedDate()));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = userDao.getUserByLogin(USER_LOGIN1);
        user.setPassword(USER_PASSWORD1 + 12);
        userDao.updateUser(user);
        User newUser = userDao.getUserById(user.getUserId());
        assertTrue(user.getLogin().equals(newUser.getLogin()));
        assertTrue(user.getPassword().equals(newUser.getPassword()));
        assertTrue(user.getCreatedDate().equals(newUser.getCreatedDate()));
    }

    @Test
    public void testDeleteUser() throws Exception {
        List<User> users = userDao.getAllUsers();
        int sizeBefore = users.size();
        userDao.deleteUser(users.get(0).getUserId());
        assertTrue((sizeBefore - 1) == userDao.getAllUsers().size());
    }
}