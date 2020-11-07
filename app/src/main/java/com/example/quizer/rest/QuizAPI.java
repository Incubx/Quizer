package com.example.quizer.rest;

import com.example.quizer.quizModel.Quiz;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface QuizAPI {
    @Headers("Content-Type: application/json")
    @GET("/rest/quiz/user/{id}")
    Call<List<Quiz>> getQuizList(@Path("id") int id);

    @Headers("Content-Type: application/json")
    @GET("/rest/quiz/{id}")
    Call<Quiz> getQuizById(@Path("id") int id);
}
