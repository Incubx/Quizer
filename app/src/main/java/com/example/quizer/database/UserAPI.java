package com.example.quizer.database;

import com.example.quizer.userCabinet.User;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserAPI {

    @POST("/rest/user/authorize")
    Call<Integer> authorizeUser(@Body User user );

    @Headers("Content-Type: application/json")
    @POST("/rest/user/register")
    Call<Integer> registerUser(@Body User user);
}
