package com.example.quizer.database;

import com.example.quizer.quizModel.Question;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class QuestionAnswerDAO extends BaseDaoImpl<QuestionAnswer, Integer> {
    protected QuestionAnswerDAO(ConnectionSource connectionSource, Class<QuestionAnswer> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }


    public List<QuestionAnswer> getQuestionAnswerByQuestion(Question question) throws SQLException {
        return queryForEq("question_id", question);

    }
}



