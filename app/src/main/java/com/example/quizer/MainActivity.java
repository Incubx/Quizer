package com.example.quizer;

import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import com.example.quizer.recyclerView.QuizListFragment;
import com.example.quizer.userCabinet.AuthorizationFragment;


public class MainActivity extends SingleFragmentActivity {
    private final String USER_ID_PREF = "USER_ID";

    @Override
    public Fragment createFragment() {
        SharedPreferences preferences = getSharedPreferences(USER_ID_PREF, 0);
        int userId = preferences.getInt(USER_ID_PREF,-1);
        if(userId==-1)
            return new AuthorizationFragment();
            //return new AuthorizationFragment();
        else return new QuizListFragment();

    }
}