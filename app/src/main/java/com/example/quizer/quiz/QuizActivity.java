package com.example.quizer.quiz;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.quizer.SingleFragmentActivity;


public class QuizActivity extends SingleFragmentActivity {
    private static final String INTENT_EXTRA_QUIZ = "INTENT_EXTRA_ID";


    @Override
    public Fragment createFragment() {
        int quizId = getIntent().getIntExtra(INTENT_EXTRA_QUIZ,-1);
        return QuizFragment.newInstance(quizId);
    }

    @NonNull
    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, QuizActivity.class);
        intent.putExtra(INTENT_EXTRA_QUIZ, id);
        return intent;
    }

}