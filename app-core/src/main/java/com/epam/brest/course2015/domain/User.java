package com.epam.brest.course2015.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * Created by juga on 5.10.15.
 */
public class User {

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    private Integer userId;

    private String login;

    //@JsonIgnore
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date updatedDate = new Date();

    public User() {
    }

    public User(Integer userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(Integer userId, String login, String password) {
        this.userId = userId;
        this.login = login;
        this.password = password;
    }

    public User(Integer userId, String login, String password, Date createdDate, Date updatedDate) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public enum UserFields {

        USER_ID("userId"),
        LOGIN("login"),
        PASSWORD("password"),
        CREATED_DATE("createdDate"),
        UPDATED_DATE("updatedDate");

        UserFields(String value) {
            this.value = value;
        }

        private final String value;

        public String getValue() {
            return value;
        }

    }

    public boolean equals(Object obj) {
        return login.equals(((User) obj).getLogin());
    }

    @Override
    public String toString() {
        return String.format("User: {" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", createdDate=" + DATE_FORMAT.format(createdDate) +
                ", updatedDate=" + DATE_FORMAT.format(updatedDate) +
                '}');
    }
}
