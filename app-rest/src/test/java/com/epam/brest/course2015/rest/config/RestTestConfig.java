package com.epam.brest.course2015.rest.config;

import com.epam.brest.course2015.dao.UserDao;
import com.epam.brest.course2015.service.UserService;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Rest Test Config
 */
@Configuration
@ComponentScan({"com.epam.brest.course2015.rest"})
public class RestTestConfig {

    @Bean
    UserService userService(){
        UserService mockUserService = EasyMock.createMock(UserService.class);
        return mockUserService;
    }
}
