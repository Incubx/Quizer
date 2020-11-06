package com.example.quizer.quiz;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.quizer.SingleFragmentActivity;
import com.example.quizer.quizModel.Quiz;

public class AnswersActivity extends SingleFragmentActivity {
    private static final String INTENT_EXTRA_QUIZ = "INTENT_EXTRA_QUIZ";

    @Override
    public Fragment createFragment() {
        Quiz quiz = (Quiz) getIntent().getSerializableExtra(INTENT_EXTRA_QUIZ);
        return AnswersFragment.newInstance(quiz);
    }

    @NonNull
    public static Intent newIntent(Context context, Quiz quiz) {
        Intent intent = new Intent(context, AnswersActivity.class);
        intent.putExtra(INTENT_EXTRA_QUIZ, quiz);
        return intent;
    }
}
