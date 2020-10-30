package com.example.quizer.quiz;

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
import com.example.quizer.rest.Repository;
import com.example.quizer.recyclerView.ListActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuizFragment extends Fragment implements View.OnClickListener {

    private final String CORRECT_ANSWERS_KEY = "correct_answers";
    private final String QUESTION_INDEX = "index";

    private static final String ID_ARG = "ID_ARG";
    private static final int REQUEST_RATING = 0;

    private TextView questionTextView;
    private List<Button> btnList;
    int questionIndex;
    Quiz quiz;
    List<Question> currentQuestionList;
    Question currentQuestion;

    private int correctAnswers;


    public static QuizFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(ID_ARG, id);
        QuizFragment fragment = new QuizFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

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
            final int quizId = getArguments().getInt(ID_ARG);
            Repository.getInstance(getActivity()).getQuizAPI().getQuizById(quizId).enqueue(new Callback<Quiz>() {
                @Override
                public void onResponse(@NonNull Call<Quiz> call, @NonNull Response<Quiz> response) {
                    quiz = response.body();
                    currentQuestionList = quiz.getQuestions();
                    getActivity().setTitle(quiz.getTitle());
                    setCurrentQuestion();
                }

                @Override
                public void onFailure(@NonNull Call<Quiz> call, @NonNull Throwable t) {
                    Toast.makeText(getActivity(), "Ошибка получения текста " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

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

        Intent intent = new Intent(getActivity(), ListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }

    private void setCurrentQuestion() {
        currentQuestion = currentQuestionList.get(questionIndex);
        //Получаем ответы и устанавливаем текст на кнопках
        questionTextView.setText(currentQuestion.getQuestionText());
        List<Answer> answers = currentQuestion.getAnswers();

        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).getAnswerText().equals("")) {
                btnList.get(i).setVisibility(View.INVISIBLE);
            } else {
                btnList.get(i).setText(answers.get(i).getAnswerText());
                btnList.get(i).setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        int answer = btnList.indexOf(btn);

        if (answer == currentQuestion.getRightAnswer()) {
            correctAnswers++;
        }
        questionIndex++;
        if (questionIndex == currentQuestionList.size()) {
            finishQuiz();
        } else
            setCurrentQuestion();

    }

    private void finishQuiz() {
        String finalText = setFinalText();
        FragmentManager fm = getParentFragmentManager();
        ResultDialogFragment resultAlert = ResultDialogFragment.newInstance(finalText);
        resultAlert.setTargetFragment(QuizFragment.this, REQUEST_RATING);
        resultAlert.show(fm, "result_dialog");
    }

    private String setFinalText() {
        if (correctAnswers > 0)
            return String.format(getResources().getString(R.string.result), correctAnswers, quiz.getSize());
        else return getResources().getString(R.string.bad_result);
    }


}
