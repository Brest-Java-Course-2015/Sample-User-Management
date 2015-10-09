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

    public static final String GET_ALL_USERS = "select * from user";
    public static final String GET_USER_BY_ID = "select * from user where userId = ?";

    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(GET_ALL_USERS, new UserRowMapper());
    }

    @Override
    public User getUserById(Integer id) {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID, new Object[]{id}, new UserRowMapper());
    }

    private class UserRowMapper implements RowMapper<User> {

        public static final String USER_ID = "userId";
        public static final String LOGIN = "login";

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setUserId(resultSet.getInt(USER_ID)); //Ctrl+Alt+C
            user.setLogin(resultSet.getString(LOGIN));
            user.setPassword(resultSet.getString("password"));
            return user;
        }
    }
}
