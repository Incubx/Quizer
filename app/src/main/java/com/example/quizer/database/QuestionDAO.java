package com.example.quizer.database;

import com.example.quizer.quizModel.Question;
import com.example.quizer.quizModel.Quiz;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class QuestionDAO extends BaseDaoImpl<Question, Integer> {
    protected QuestionDAO(ConnectionSource connectionSource, Class<Question> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Question> getQuestionsByQuiz(Quiz quiz) throws SQLException {
        return queryForEq("quiz", quiz);
    }
}



