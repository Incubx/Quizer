package com.example.quizer.database;

import com.example.quizer.quizModel.Quiz;
import com.example.quizer.userCabinet.User;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAPI {

    @GET("/rest/user/")
    Call<User> getUserByEmail(@Query("email") String email );

    @Headers("Content-Type: application/json")
    @POST("/rest/user")
    Call<Integer> registerUser(@Body User user);
}
