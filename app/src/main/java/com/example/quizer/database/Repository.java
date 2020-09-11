package com.example.quizer.database;

import android.content.Context;



//TODO create REST Client

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
                repository = new Repository(context);
        }
        return repository;
    }

    private Repository(Context context) {
        retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.context = context.getApplicationContext();
        //UserDAO = new UserDAO();
    }


    public QuizAPI getQuizAPI(){
        return retrofit.create(QuizAPI.class);
    }










}
