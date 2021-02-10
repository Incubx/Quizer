package com.example.quizer.rest;

import com.example.quizer.userCabinet.User;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAPI {

    @POST("/rest/user/authorize")
    Call<Integer> authorizeUser(@Body User user );

    @POST("/rest/user/finishQuiz")
    Call<Integer> finishQuiz(@Query("userId") Integer userId, @Query("quizId") Integer quizId, @Query("rating") Integer rating);

}
