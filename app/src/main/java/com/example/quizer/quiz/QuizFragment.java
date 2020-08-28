package com.example.quizer.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.quizer.R;
import com.example.quizer.quizModel.Answer;
import com.example.quizer.quizModel.Question;
import com.example.quizer.quizModel.Quiz;
import com.example.quizer.database.Repository;
import com.example.quizer.recyclerView.ListFragmentActivity;
import com.example.quizer.userCabinet.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class QuizFragment extends Fragment implements View.OnClickListener {

    private final String CORRECT_ANSWERS_KEY = "correct_answers";
    private final String QUESTION_INDEX = "index";

    private static final String TITLE_ARG = "TITLE_ARG";
    private static final int REQUEST_RATING = 0;

    private TextView questionTextView;
    private List<Button> btnList;
    int questionIndex;
    List<Question> questions;
    Question currentQuestion;
    Quiz quiz;
    private int correctAnswers;


    public static QuizFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(TITLE_ARG, title);
        QuizFragment fragment = new QuizFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //set View for fragment and get views by id.
        View v = inflater.inflate(R.layout.fragment_main_quiz, container, false);
        questionTextView = v.findViewById(R.id.questionText);
        btnList = new ArrayList<>();
        //get buttons from Layout
        btnList.add((Button) v.findViewById(R.id.answerBtn1));
        btnList.add((Button) v.findViewById(R.id.answerBtn2));
        btnList.add((Button) v.findViewById(R.id.answerBtn3));
        btnList.add((Button) v.findViewById(R.id.answerBtn4));
        //set this fragment as onClickListener
        for (Button btn : btnList) {
            btn.setOnClickListener(this);
        }

        if (savedInstanceState != null) {
            questionIndex = savedInstanceState.getInt(QUESTION_INDEX, 0);
            correctAnswers = savedInstanceState.getInt(CORRECT_ANSWERS_KEY, 0);
        }

        //Get Quiz title from fragment's arguments.
        if (getArguments() != null) {
            String quizTitle = getArguments().getString(TITLE_ARG);
            quiz = Repository.getInstance(getActivity()).getQuiz(quizTitle);
            questions = Repository.getInstance(getActivity()).getQuestionsByQuiz(quiz);
            setCurrentQuestion();
        }
        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(QUESTION_INDEX, questionIndex);
        savedInstanceState.putInt(CORRECT_ANSWERS_KEY, correctAnswers);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_RATING) {
            int rating = data.getIntExtra(ResultDialogFragment.EXTRA_RATING, 0);
            if (rating > 0) {
                Toast.makeText(getActivity(), "Thanks for your " + rating + " stars!", Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

        Intent intent = new Intent(getActivity(), ListFragmentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }

    private void setCurrentQuestion() {
        currentQuestion = questions.get(questionIndex);
        //Получаем ответы и устанавливаем текст на кнопках
        questionTextView.setText(currentQuestion.getQuestionText());
        List<Answer> answers = Repository.getInstance(getActivity()).getAnswerByQuestion(currentQuestion);
        for (int i = 0; i < 4; i++) {
            if (answers.size() > i)
                btnList.get(i).setText(answers.get(i).getAnswerText());
            else btnList.get(i).setVisibility(View.INVISIBLE);
        }
    }


    private String setFinalText() {
        if (correctAnswers > 0)
            return String.format(getResources().getString(R.string.result), correctAnswers);
        else return getResources().getString(R.string.bad_result);
    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        int answer = btnList.indexOf(btn);
        System.out.println(correctAnswers);
        if (answer == currentQuestion.getRightAnswer()) {
            correctAnswers++;
        }
        questionIndex++;
        if (questionIndex >= questions.size()) {
            try {
                finishQuiz();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else
            setCurrentQuestion();

    }

    private void finishQuiz() throws SQLException {
        if (quiz.getSize() == correctAnswers) {
            User user = Repository.getInstance(getActivity()).getUser();
            if (!quiz.isSolved()) {
                user.increaseRating(1);
                Repository.getInstance(getActivity()).updateUser(user);
            }
            quiz.setSolved(true);
        }
        Repository.getInstance(getActivity()).updateQuiz(quiz);
        Toast.makeText(getActivity(), quiz.isSolved() + quiz.getTitle(), Toast.LENGTH_LONG).show();
        String finalText = setFinalText();
        FragmentManager fm = getParentFragmentManager();
        ResultDialogFragment resultAlert = ResultDialogFragment.newInstance(finalText);
        resultAlert.setTargetFragment(QuizFragment.this, REQUEST_RATING);
        resultAlert.show(fm, "result_dialog");
    }


}
