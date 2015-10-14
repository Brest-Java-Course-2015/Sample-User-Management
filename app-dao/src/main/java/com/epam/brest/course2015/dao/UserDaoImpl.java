package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.epam.brest.course2015.domain.User.*;
import static com.epam.brest.course2015.domain.User.UserFields.*;

/**
 * Created by juga on 7.10.15.
 */
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LogManager.getLogger();

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @Value("${user.select}")
    private String userSelect;

    @Value("${user.selectById}")
    private String userSelectById;

    @Value("${user.selectByLogin}")
    private String userSelectByLogin;

    @Value("${user.insertUser}")
    private String insertUser;

    @Value("${user.updateUser}")
    private String updateUser;

    @Value("${user.deleteUser}")
    private String deleteUser;

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllUsers() {
        LOGGER.info("getAllUsers()");
        return jdbcTemplate.query(userSelect, new UserRowMapper());
    }

    @Override
    public User getUserById(Integer userId) {
        LOGGER.info("getUserById({})", userId);
        return jdbcTemplate.queryForObject(userSelectById, new Object[]{userId}, new UserRowMapper());
    }

    @Override
    public User getUserByLogin(String login) {
        LOGGER.info("getUserByLogin({})", login);
        return jdbcTemplate.queryForObject(userSelectByLogin, new Object[]{login.toLowerCase()}, new UserRowMapper());
    }

    @Override
    public Integer addUser(User user) {
        LOGGER.info("addUser(user): {}", user.getLogin(), dateFormat.format(user.getCreatedDate()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(insertUser, getParametersMap(user), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateUser(User user) {
        LOGGER.info("updateUser(user): {}", user.getLogin());
        jdbcTemplate.update(updateUser, new Object[]{user.getPassword(), user.getUserId()});
    }

    @Override
    public void deleteUser(Integer userId) {
        LOGGER.info("userId: {}", userId);
        jdbcTemplate.update(deleteUser, new Object[]{userId});
    }

    private MapSqlParameterSource getParametersMap(User user) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_ID.getValue(), user.getUserId());
        parameterSource.addValue(LOGIN.getValue(), user.getLogin());
        parameterSource.addValue(PASSWORD.getValue(), user.getPassword());
        parameterSource.addValue(CREATED_DATE.getValue(), user.getCreatedDate());
        return parameterSource;
    }

    private class UserRowMapper implements RowMapper<User> {

        public static final String USER_ID = "userId";

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User(resultSet.getInt(USER_ID),
                    resultSet.getString(LOGIN.getValue()),
                    resultSet.getString(PASSWORD.getValue()),
                    resultSet.getTimestamp("createdDate")); //Ctrl+Alt+C
            return user;
        }
    }
}
