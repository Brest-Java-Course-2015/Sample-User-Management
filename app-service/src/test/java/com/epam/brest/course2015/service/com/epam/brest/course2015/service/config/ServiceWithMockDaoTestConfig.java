package com.epam.brest.course2015.service.com.epam.brest.course2015.service.config;

import com.epam.brest.course2015.dao.UserDao;
import com.epam.brest.course2015.service.UserServiceImpl;
import org.easymock.EasyMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceWithMockDaoTestConfig {

    @Bean
    UserDao mockUserDao(){
        UserDao mockUserDao = EasyMock.createMock(UserDao.class);
        return mockUserDao;
    }

    @Bean
    @Autowired
    public UserServiceImpl userService(UserDao mockUserDao){
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(mockUserDao);
        return userService;
    }
}
