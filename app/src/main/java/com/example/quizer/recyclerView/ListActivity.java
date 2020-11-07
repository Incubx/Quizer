package com.example.quizer.recyclerView;

import androidx.fragment.app.Fragment;

import com.example.quizer.SingleFragmentActivity;

public class ListActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new QuizListFragment();
    }
}