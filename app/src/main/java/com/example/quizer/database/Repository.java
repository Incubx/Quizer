package com.example.quizer.database;

import android.content.Context;



//TODO create REST Client

import com.example.quizer.quizModel.Answer;
import com.example.quizer.quizModel.Question;
import com.example.quizer.quizModel.Quiz;
import com.example.quizer.userCabinet.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private static Repository repository;
    private Context context;
    private Retrofit retrofit;
    private final String BASE_URL = "http://192.168.9.9:8080/";
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

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);



        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.create();

        retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client.build())
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


    public void updateQuiz(Quiz quiz) {

    }

    public void updateQuizList() {
    }

    public void addUser(User user) throws SQLException {
        userDAO.create(user);
    }

    public File getPhotoFile(User user){
        String filename = user.getPhotoFileName();
        File fileDir = context.getFilesDir();
        return new File(fileDir,filename);
    }

    public void updateUser(User user) throws SQLException {
        userDAO.update(user);
    }
}
