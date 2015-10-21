package com.epam.brest.course2015.service;

import com.epam.brest.course2015.dao.UserDao;
import com.epam.brest.course2015.domain.User;
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
 * Created by juga on 21.10.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-service-mock.xml"})
public class UserServiceMockTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

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
}
