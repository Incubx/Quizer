package com.example.quizer.database;

import com.example.quizer.userCabinet.User;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class UserDAO extends BaseDaoImpl<User, Integer> {
    protected UserDAO(ConnectionSource connectionSource, Class<User> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }


    public User getUser() throws SQLException {
        List<User> userList = queryForAll();
        if (userList.size() == 0) return null;
        else return userList.get(0);
    }
}
