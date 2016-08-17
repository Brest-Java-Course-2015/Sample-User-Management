package com.epam.brest.course2015.service.com.epam.brest.course2015.service.config;

import com.epam.brest.course2015.dao.UserDao;
import com.epam.brest.course2015.dao.UserDaoImpl;
import com.epam.brest.course2015.dao.config.DataTestConfig;
import com.epam.brest.course2015.service.UserService;
import com.epam.brest.course2015.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
public class ServiceTestConfig extends DataTestConfig {

    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        propertyPlaceholderConfigurer.setLocations(new Resource[]{new ClassPathResource("app.properties"),
                new ClassPathResource("database.properties")});
        return propertyPlaceholderConfigurer;
    }

    @Bean
    @Autowired
    public UserDao userDao(DataSource dataSource){
        UserDao userDao = new UserDaoImpl(dataSource);
        return userDao;
    }

    @Bean
    @Autowired
    public UserService userService(UserDao userDao){
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userDao);
        return userService;
    }

}