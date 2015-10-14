package com.epam.brest.course2015.service;

import com.epam.brest.course2015.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created by juga on 14.10.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-service.xml"})
@Transactional()
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetAllUsers() throws Exception {

    }

    @Test
    public void testAddUser() throws Exception {

    }
}