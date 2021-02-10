package com.example.quizer.rest;

import com.example.quizer.quizModel.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface CategoryAPI {
    @Headers("Content-Type: application/json")
    @GET("/rest/category/")
    Call<List<Category>> getCategoryList();
}
