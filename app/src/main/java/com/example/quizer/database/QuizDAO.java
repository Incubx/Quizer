package com.example.quizer.database;

import com.example.quizer.quizModel.Quiz;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class QuizDAO extends BaseDaoImpl<Quiz, Integer> {
    protected QuizDAO(ConnectionSource connectionSource, Class<Quiz> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Quiz> getQuizList() throws SQLException {
        return this.queryForAll();
    }
    public Quiz getQuizByTitle(String title) throws SQLException {
       List<Quiz> result =this.queryForEq("title",title);
       return result.get(0);
    }

}
