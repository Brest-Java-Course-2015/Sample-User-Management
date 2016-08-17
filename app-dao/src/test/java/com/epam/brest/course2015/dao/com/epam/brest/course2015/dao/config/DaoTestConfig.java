package com.epam.brest.course2015.dao.com.epam.brest.course2015.dao.config;

import com.epam.brest.course2015.dao.UserDao;
import com.epam.brest.course2015.dao.UserDaoImpl;
import com.epam.brest.course2015.dao.config.DataTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
public class DaoTestConfig extends DataTestConfig {

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

}
