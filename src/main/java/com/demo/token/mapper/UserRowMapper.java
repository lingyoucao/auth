package com.demo.token.mapper;

import com.demo.token.model.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;



public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("ID"));
        user.setUsername(resultSet.getString("USERNAME"));
        user.setPassword(resultSet.getString("PASSWORD"));
        user.setRoles(Arrays.asList(resultSet.getString("ROLE").split(",")));
        user.setCreate_date(resultSet.getDate("CREATE_DATE"));
        user.setModify_date(resultSet.getDate("MODIFY_DATE"));
        return user;
    }

}
