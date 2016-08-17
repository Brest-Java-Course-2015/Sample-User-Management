package com.epam.brest.course2015.rest.config;

import com.epam.brest.course2015.dao.UserDao;
import com.epam.brest.course2015.dao.UserDaoImpl;
import com.epam.brest.course2015.service.UserService;
import com.epam.brest.course2015.service.UserServiceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableTransactionManagement
@ComponentScan({"com.epam.brest.course2015.rest"})
public class RootConfig {

    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        propertyPlaceholderConfigurer.setLocations(new Resource[]{new ClassPathResource("app.properties"),
                new ClassPathResource("database.properties")});
        return propertyPlaceholderConfigurer;
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("create-tables.sql")
                .addScript("data-script.sql")
                .build();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    @Bean
    @Autowired
    public UserDao userDao(DataSource dataSource) {
        UserDao userDao = new UserDaoImpl(dataSource);
        return userDao;
    }

    @Bean
    @Autowired
    public UserService userService(UserDao userDao) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userDao);
        return userService;
    }

    @Bean
    public ObjectMapper jacksonObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }


    private MappingJackson2HttpMessageConverter jsonConverter() {
        MappingJackson2HttpMessageConverter jacksonConverter = new
                MappingJackson2HttpMessageConverter();
        jacksonConverter.setSupportedMediaTypes(Arrays.asList(MediaType.valueOf("application/json")));
        jacksonConverter.setObjectMapper(jacksonObjectMapper());
        jacksonConverter.setPrettyPrint(true);
        return jacksonConverter;
    }


    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();

        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(jsonConverter());

        requestMappingHandlerAdapter.setMessageConverters(converters);
        return requestMappingHandlerAdapter;
    }

}
