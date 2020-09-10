package com.example.quizer.database;

import android.content.Context;
import android.util.Log;



//TODO create REST Client
import com.example.quizer.quizModel.Quiz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static Repository repository;
    private Context context;


    public static Repository getInstance(Context context) {
        if (repository == null) {
            try {
                repository = new Repository(context);
            } catch (SQLException e) {
                Log.e("MyERROR", "getInstance: " + e.getMessage());
            }
        }
        return repository;
    }

    private Repository(Context context) throws SQLException {

        this.context = context.getApplicationContext();


    }

    public List<Quiz> getQuizList() {
        return new ArrayList<>();
    }

    public Quiz getQuiz(String title) {
        return null;
    }










}
