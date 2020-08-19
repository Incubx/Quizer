package com.example.quizer.pager;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.quizer.SingleFragmentActivity;

public class QuizPagerActivity extends SingleFragmentActivity {
    private static final String INTENT = "QuizPagerActivityIntent";


    @Override
    public Fragment createFragment() {

        String title = getIntent().getStringExtra(INTENT);
        return QuizPagerFragment.newInstance(title);
    }


    public static Intent newIntent(Context context, String quizTitle) {
        Intent intent = new Intent(context, QuizPagerActivity.class);
        intent.putExtra(INTENT, quizTitle);
        return intent;
    }
}
