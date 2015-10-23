package com.epam.brest.course2015.dto;

import com.epam.brest.course2015.domain.User;

import java.util.List;

/**
 * Created by juga on 23.10.15.
 */
public class UserDto {

    private List<User> users;

    private int total;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
