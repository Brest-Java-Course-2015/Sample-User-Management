package com.epam.brest.course2015.service;

import com.epam.brest.course2015.dao.UserDao;
import com.epam.brest.course2015.domain.User;
import com.epam.brest.course2015.service.com.epam.brest.course2015.service.config.ServiceWithMockDaoTestConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.easymock.EasyMock.*;

/**
 * UserService Mock Test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceWithMockDaoTestConfig.class)
public class UserServiceMockTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserDao mockUserDao;

    private static final User user = new User("userLogin3", "userPassword3");

    @After
    public void clean() {
        verify(mockUserDao);
        reset(mockUserDao);
    }

    @Test
    public void testLogUser() {
        replay(mockUserDao);
        userService.logUser(user);
    }

    @Test
    public void testGetUserByLogin() {
        expect(mockUserDao.getUserByLogin(user.getLogin())).andReturn(user);
        replay(mockUserDao);
        User result = userService.getUserByLogin(user.getLogin());
        Assert.assertTrue(result == user);
    }

    @Test
    public void testAddUser() {
        expect(mockUserDao.getCountUsers("userLogin3")).andReturn(-10);
        expect(mockUserDao.addUser(new User("userLogin3", ""))).andReturn(5);
        replay(mockUserDao);
        int id = userService.addUser(user);
        Assert.assertTrue(id == 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddExistUser() {
        expect(mockUserDao.getCountUsers("userLogin3")).andReturn(1);
        replay(mockUserDao);
        userService.addUser(user);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetUserByLoginException() {
        expect(mockUserDao.getUserByLogin(user.getLogin())).andThrow(new UnsupportedOperationException());
        replay(mockUserDao);
        userService.getUserByLogin(user.getLogin());
    }
}
