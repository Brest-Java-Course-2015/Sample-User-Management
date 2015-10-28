package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.epam.brest.course2015.domain.User.UserFields.*;

/**
 * Created by juga on 7.10.15.
 */
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LogManager.getLogger();

    @Value("${user.select}")
    private String userSelectSql;

    @Value("${user.selectById}")
    private String userSelectByIdSql;

    @Value("${user.selectByLogin}")
    private String userSelectByLoginSql;

    @Value("${user.countUsers}")
    private String countUserSql;

    @Value("${user.totalUsersCount}")
    private String totalUsersCountSql;

    @Value("${user.insertUser}")
    private String insertUserSql;

    @Value("${user.updateUser}")
    private String updateUserSql;

    @Value("${user.deleteUser}")
    private String deleteUserSql;

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllUsers() {
        LOGGER.debug("getAllUsers()");
        return jdbcTemplate.query(userSelectSql, new UserRowMapper());
    }

    @Override
    public User getUserById(Integer userId) {
        LOGGER.debug("getUserById({})", userId);
        return jdbcTemplate.queryForObject(userSelectByIdSql, new Object[]{userId}, new UserRowMapper());
    }

    @Override
    public User getUserByLogin(String login) {
        LOGGER.debug("getUserByLogin({})", login);
        return jdbcTemplate.queryForObject(userSelectByLoginSql, new Object[]{login}, new UserRowMapper());
    }

    @Override
    public Integer getCountUsers(String login) {
        LOGGER.debug("getCountUsers(): login = {}", login);
        return jdbcTemplate.queryForObject(countUserSql, new String[]{login}, Integer.class);
    }

    public Integer getTotalUsersCount() {
        LOGGER.debug("getTotalUsersCount()");
        return jdbcTemplate.queryForObject(totalUsersCountSql, Integer.class);
    }

    @Override
    public Integer addUser(User user) {
        LOGGER.debug("addUser(user): login = {}", user.getLogin());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(insertUserSql, getParametersMap(user), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateUser(User user) {
        LOGGER.debug("updateUser(user): {}", user.getLogin());
        jdbcTemplate.update(updateUserSql, new Object[]{user.getPassword(), user.getUpdatedDate(), user.getUserId()});
    }

    @Override
    public void deleteUser(Integer userId) {
        LOGGER.debug("deleteUser(): {}", userId);
        jdbcTemplate.update(deleteUserSql, new Object[]{userId});
    }

    private MapSqlParameterSource getParametersMap(User user) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_ID.getValue(), user.getUserId());
        parameterSource.addValue(LOGIN.getValue(), user.getLogin());
        parameterSource.addValue(PASSWORD.getValue(), user.getPassword());
        parameterSource.addValue(CREATED_DATE.getValue(), user.getCreatedDate());
        parameterSource.addValue(UPDATED_DATE.getValue(), user.getUpdatedDate());
        return parameterSource;
    }

    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User(resultSet.getInt(USER_ID.getValue()),
                    resultSet.getString(LOGIN.getValue()),
                    resultSet.getString(PASSWORD.getValue()),
                    resultSet.getTimestamp(CREATED_DATE.getValue()),
                    resultSet.getTimestamp(UPDATED_DATE.getValue()));
            return user;
        }
    }
}
