package com.example.quizer.database;

import com.example.quizer.quizModel.Quiz;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface QuizAPI {

    @GET("/rest/quiz/")
    Call<List<Quiz>> getQuizList();

    @Headers("Content-Type: application/json")
    @GET("/rest/quiz/{id}")
    Call<Quiz> getQuizById(@Path("id") int id);
}
