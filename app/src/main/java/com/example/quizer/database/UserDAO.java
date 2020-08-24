package com.example.quizer.database;

import com.example.quizer.quizModel.Quiz;
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
        return queryForAll().get(0);
    }
}
