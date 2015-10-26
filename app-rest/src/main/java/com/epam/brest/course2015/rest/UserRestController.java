package com.epam.brest.course2015.rest;

import com.epam.brest.course2015.domain.User;
import com.epam.brest.course2015.dto.UserDto;
import com.epam.brest.course2015.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by juga on 16.10.15.
 */
@RestController
public class UserRestController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public @ResponseBody List<User> getUsers() {
        LOGGER.debug("getUsers()");
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Integer addUser(@RequestBody User user) {
        LOGGER.debug("addUser: user = {}", user);
        return userService.addUser(user);
    }

    @RequestMapping(value = "/user/{id}/{password}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void updateUser(@PathVariable(value = "id") Integer id, @PathVariable(value = "password") String password) {
        LOGGER.debug("updateUser: id = {}", id);
        userService.updateUser(new User(id, password));
    }

    @RequestMapping(value = "/user/{login}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public @ResponseBody User getUser(@PathVariable(value = "login") String login) {
        LOGGER.debug("getUser: login = {}", login);
        return userService.getUserByLogin(login);
    }


    @RequestMapping(value = "/userdto", method = RequestMethod.GET)
    public @ResponseBody UserDto getUserDto() {
        LOGGER.debug("getUserDto()");
        return userService.getUserDto();
    }
}
