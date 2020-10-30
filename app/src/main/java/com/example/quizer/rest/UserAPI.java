package com.example.quizer.rest;

import com.example.quizer.userCabinet.User;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {

    @POST("/rest/user/authorize")
    Call<Integer> authorizeUser(@Body User user );

}
