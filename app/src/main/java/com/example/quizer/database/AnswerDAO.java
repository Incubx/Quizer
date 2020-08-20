package com.example.quizer.database;

import com.example.quizer.model.Answer;

import com.j256.ormlite.dao.BaseDaoImpl;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;


public class AnswerDAO extends BaseDaoImpl<Answer, Integer> {
    protected AnswerDAO(ConnectionSource connectionSource, Class<Answer> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }



}



