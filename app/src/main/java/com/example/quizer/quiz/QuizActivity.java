package com.example.quizer.quiz;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.quizer.SingleFragmentActivity;


public class QuizActivity extends SingleFragmentActivity {
    private static String INTENT_EXTRA_QUIZ = "INTENT_EXTRA_TITLE";


    @Override
    public Fragment createFragment() {
        String quizTitle = getIntent().getStringExtra(INTENT_EXTRA_QUIZ);
        return QuizFragment.newInstance(quizTitle);
    }

    @NonNull
    public static Intent newIntent(Context context, String quizTitle) {
        Intent intent = new Intent(context, QuizActivity.class);
        intent.putExtra(INTENT_EXTRA_QUIZ, quizTitle);
        return intent;
    }

    /*@Override
    public void onBackPressed() {
        Intent intent = new Intent(,ListFragmentActivity.class,)
    }*/
}