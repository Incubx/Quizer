package com.example.quizer.rest;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {
    private static final String SERVER_PREF = "SERVER_IP";
    private static Repository repository;
    private static Context context;
    private final Retrofit retrofit;
    private static String serverIP = "http://192.168.9.10:8080/";


    public static Repository getInstance(Context context) {
        if (repository == null) {
            final SharedPreferences preferences = context.getSharedPreferences("SERVER_IP", 0);
            String ip = preferences.getString(SERVER_PREF, "");
            assert ip != null;
            if (ip.isEmpty()) {
                SharedPreferences.Editor editor = preferences.edit();
                String newServerIp = "127.0.0.1";
                editor.putString(SERVER_PREF, newServerIp);
                editor.apply();
            } else {
                serverIP = "http://" + ip + ":8080/";
            }
            repository = new Repository(context);
        }
        return repository;
    }

    private Repository(Context context) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.create();

        retrofit = new Retrofit.Builder()
                .baseUrl(serverIP)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Repository.context = context.getApplicationContext();
    }


    public QuizAPI getQuizAPI() {
        return retrofit.create(QuizAPI.class);
    }


    public static void setServerIP(String serverIP) {
        Repository.serverIP = "http://" + serverIP + ":8080/";
        repository = new Repository(context);
    }
}
