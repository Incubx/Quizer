package com.example.quizer.database;

import android.content.Context;



//TODO create REST Client

import com.example.quizer.quizModel.Answer;
import com.example.quizer.quizModel.Question;
import com.example.quizer.quizModel.Quiz;
import com.example.quizer.userCabinet.User;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private static Repository repository;
    private Context context;
    private Retrofit retrofit;
    private final String BASE_URL = "http://localhost:8080";
    private UserDAO userDAO;

    public static Repository getInstance(Context context) {
        if (repository == null) {
            try {
                repository = new Repository(context);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return repository;
    }

    private Repository(Context context) throws SQLException {
        retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.context = context.getApplicationContext();
        DatabaseHelper helper = new DatabaseHelper(context);
        userDAO = helper.getUserDAO();
    }

    public User getUser() throws SQLException {
            return userDAO.getUser();
    }


    public QuizAPI getQuizAPI(){
        return retrofit.create(QuizAPI.class);
    }


    public Quiz getQuiz(String title) {
        return null;
    }

    public List<Quiz> getQuizList() {
        return null;
    }

    public List<Question> getQuestionsByQuiz(Quiz quiz) {
        return null;
    }

    public List<Answer> getAnswerByQuestion(Question currentQuestion) {
        return null;
    }

    public void updateUser(User user) {

    }

    public void updateQuiz(Quiz quiz) {

    }

    public void updateQuizList() {
    }

    public void addUser(User user) throws SQLException {
        userDAO.create(user);
    }

    public File getPhotoFile(User user) {
        user.getPhotoFileName()
    }
}
