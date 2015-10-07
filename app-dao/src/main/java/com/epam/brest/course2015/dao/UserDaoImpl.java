package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by juga on 7.10.15.
 */
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("select * from user", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        });
    }

    @Override
    public User getUserById(Integer id) {
        return null;
    }

}
