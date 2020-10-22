package com.example.quizer.database;

import android.content.Context;
import android.content.SharedPreferences;

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
    private static final String SERVER_PREF = "SERVER_IP";
    private static Repository repository;
    private static Context context;
    private Retrofit retrofit;
    private static String serverIP = "http://127.0.0.1:8080/";
    private UserDAO userDAO;

    public static Repository getInstance(Context context) {
        if (repository == null) {
            try {
                final SharedPreferences preferences = context.getSharedPreferences("SERVER_IP", 0);
                String ip = preferences.getString(SERVER_PREF, "");
                assert ip != null;
                if (ip.isEmpty()) {
                    SharedPreferences.Editor editor = preferences.edit();
                    String newServerIp = "127.0.0.1";
                    editor.putString(SERVER_PREF, newServerIp);
                    editor.apply();
                }
                else {
                    serverIP = "http://"+ip+":8080/";
                }
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

        retrofit = new Retrofit.Builder()
                .baseUrl(serverIP)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client.build())
                .build();

        Repository.context = context.getApplicationContext();
        DatabaseHelper helper = new DatabaseHelper(context);
        userDAO = helper.getUserDAO();
    }

    public User getUser() throws SQLException {
        return userDAO.getUser();
    }

    public UserAPI getUserAPI() {
        return retrofit.create(UserAPI.class);
    }


    public QuizAPI getQuizAPI() {
        return retrofit.create(QuizAPI.class);
    }


    public File getPhotoFile(User user) {
        String filename = user.getPhotoFileName();
        File fileDir = context.getFilesDir();
        return new File(fileDir, filename);
    }

    public static void setServerIP(String serverIP) {
        Repository.serverIP = "http://" + serverIP + ":8080/";
        try {
            repository = new Repository(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
