package com.example.quizer.pager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizer.model.Quiz;
import com.example.quizer.database.Repository;
import com.example.quizer.R;


public class QuizAboutFragment extends Fragment {

    private TextView titleTextView;
    private TextView isSolvedTextView;
    private TextView questionNumberTextView;

    private final static String QUIZ_TITLE = "QuizNumber";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quiz_about, container, false);
        titleTextView = v.findViewById(R.id.quiz_title_text);
        isSolvedTextView = v.findViewById(R.id.is_solved_text);
        questionNumberTextView = v.findViewById(R.id.question_number_text);
        String title = getArguments().getString(QUIZ_TITLE);
        Quiz quiz = null;

        quiz = Repository.getInstance(getActivity()).getQuiz(title);

        titleTextView.setText(quiz.getTitle());
        if (quiz.isSolved())
            isSolvedTextView.setText("Quiz is solved");
        else
            isSolvedTextView.setText("Quiz isn't solved!");

        // questionNumberTextView.setText("Quiz has" + quiz.getQuestions().size() + " questions");
        return v;
    }

    public static Fragment newInstance(String quizTitle) {
        Fragment quizAboutFragment = new QuizAboutFragment();
        Bundle args = new Bundle();
        args.putString(QUIZ_TITLE, quizTitle);
        quizAboutFragment.setArguments(args);
        return quizAboutFragment;
    }
}
