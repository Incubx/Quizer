package com.example.quizer.recyclerView;

import androidx.fragment.app.Fragment;

import com.example.quizer.SingleFragmentActivity;

public class ListFragmentActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new QuizListFragment();
    }
}