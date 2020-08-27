package com.example.quizer;

import androidx.fragment.app.Fragment;

import com.example.quizer.database.Repository;
import com.example.quizer.recyclerView.QuizListFragment;
import com.example.quizer.userCabinet.RegistrationFragment;
import com.example.quizer.userCabinet.User;

import java.sql.SQLException;


public class MainActivity extends SingleFragmentActivity {


    @Override
    public Fragment createFragment() {
        try {
            User user = Repository.getInstance(this).getUser();
            if (user != null) {
                return new QuizListFragment();
            } else
                return new RegistrationFragment();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}