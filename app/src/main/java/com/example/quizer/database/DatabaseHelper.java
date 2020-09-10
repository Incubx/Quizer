package com.example.quizer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.quizer.quizModel.Answer;
import com.example.quizer.quizModel.Question;
import com.example.quizer.quizModel.Quiz;
import com.example.quizer.userCabinet.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "quizBase.db";
    private static final int VERSION = 1;

    private static QuizDAO quizDAO;
    private static QuestionDAO questionDAO;
    private static AnswerDAO answerDAO;
    private static QuestionAnswerDAO questionAnswerDAO;
    private static UserDAO userDAO;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Quiz.class);
            TableUtils.createTable(connectionSource, Question.class);
            TableUtils.createTable(connectionSource, Answer.class);
            TableUtils.createTable(connectionSource, QuestionAnswer.class);
            TableUtils.createTable(connectionSource,User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Quiz.class, true);
            TableUtils.dropTable(connectionSource, Question.class, true);
            TableUtils.dropTable(connectionSource, Answer.class, true);
            TableUtils.dropTable(connectionSource, QuestionAnswer.class,true);
            TableUtils.dropTable(connectionSource, User.class,true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        super.close();
        quizDAO = null;
        questionDAO = null;
        answerDAO=null;
    }

    public QuizDAO getQuizDAO() throws SQLException {
        if (quizDAO == null)
            quizDAO = new QuizDAO(getConnectionSource(), Quiz.class);
        return quizDAO;
    }

    public UserDAO getUserDAO() throws SQLException {
        if (userDAO == null)
            userDAO = new UserDAO(getConnectionSource(), User.class);
        return userDAO;
    }

    public QuestionDAO getQuestionDAO() throws SQLException {
        if (questionDAO == null)
            questionDAO = new QuestionDAO(getConnectionSource(), Question.class);
        return questionDAO;
    }

    public AnswerDAO getAnswerDAO() throws SQLException {
        if (answerDAO == null)
            answerDAO = new AnswerDAO(getConnectionSource(), Answer.class);
        return answerDAO;
    }

    public QuestionAnswerDAO getQuestionAnswerDAO() throws SQLException {
        if (questionAnswerDAO == null)
            questionAnswerDAO = new QuestionAnswerDAO(getConnectionSource(), QuestionAnswer.class);
        return questionAnswerDAO;
    }



}
