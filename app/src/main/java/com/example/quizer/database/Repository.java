package com.example.quizer.database;

import android.content.Context;

import com.example.quizer.userCabinet.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import java.sql.SQLException;

import okhttp3.OkHttpClient;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private static Repository repository;
    private Context context;
    private Retrofit retrofit;
    private final String BASE_URL = "http://192.168.9.4:8080/";
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

    public UserAPI getUserAPI(){ return retrofit.create(UserAPI.class);}


    public QuizAPI getQuizAPI(){
        return retrofit.create(QuizAPI.class);
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
