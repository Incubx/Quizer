package com.example.quizer.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    private TextView timerView;
    private CountDownTimer timer;

    int questionIndex;
    Quiz quiz;
    List<Question> currentQuestionList;
    Question currentQuestion;

    private int correctAnswers;
    private final List<Answer> userAnswers = new ArrayList<>();


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

        timerView = v.findViewById(R.id.timerView);
        questionTextView = v.findViewById(R.id.questionText);
        btnList = new ArrayList<>();
        //get buttons from Layout
        btnList.add(v.findViewById(R.id.answerBtn1));
        btnList.add(v.findViewById(R.id.answerBtn2));
        btnList.add(v.findViewById(R.id.answerBtn3));
        btnList.add(v.findViewById(R.id.answerBtn4));
        //set this fragment as onClickListener
        for (Button btn : btnList) {
            btn.setOnClickListener(this);
        }

        if (savedInstanceState != null) {
            questionIndex = savedInstanceState.getInt(QUESTION_INDEX, 0);
            correctAnswers = savedInstanceState.getInt(CORRECT_ANSWERS_KEY, 0);
        }

        //Get Quiz id from fragment's arguments.
        if (getArguments() != null) {
            final int quizId = getArguments().getInt(ID_ARG);
            Repository.getInstance(getActivity()).getQuizAPI().getQuizById(quizId).enqueue(new Callback<Quiz>() {
                @Override
                public void onResponse(@NonNull Call<Quiz> call, @NonNull Response<Quiz> response) {
                    quiz = response.body();
                    currentQuestionList = quiz.getQuestions();
                    getActivity().setTitle(quiz.getTitle());
                    setCurrentQuestion();
                    startTimer();
                }

                @Override
                public void onFailure(@NonNull Call<Quiz> call, @NonNull Throwable t) {
                    Toast.makeText(getActivity(), "Ошибка получения текста " + t.getMessage(), Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
            });

        }
        return v;
    }

    private void startTimer() {
        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                int minutes = (int) l / 1000 / 60;
                int seconds = (int) (l / 1000 - minutes * 60);
                timerView.setText(String.format(getResources().getString(R.string.timer_text), minutes, seconds));
            }

            @Override
            public void onFinish() {
                Toast.makeText(getActivity(), "TIME IS OVER", Toast.LENGTH_LONG).show();
                finishQuiz();
            }
        };
        timer.start();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(QUESTION_INDEX, questionIndex);
        savedInstanceState.putInt(CORRECT_ANSWERS_KEY, correctAnswers);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == Activity.RESULT_CANCELED) {
            Intent intent = new Intent(getActivity(), ListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Repository.getInstance(getActivity()).setUserAnswers(userAnswers);
            Intent intent = AnswersActivity.newIntent(getActivity(), quiz);
            startActivity(intent);
        }
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
        userAnswers.add(currentQuestion.getAnswers().get(answer));

        if (answer == currentQuestion.getRightAnswer()) {
            correctAnswers++;
        }
        questionIndex++;
        if (questionIndex == currentQuestionList.size()) {
            finishQuiz();
        } else
            setCurrentQuestion();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private void finishQuiz() {
        System.out.println(userAnswers);
        int userId = Repository.getInstance(getActivity()).getUserId();
        int rating = (int) ((double) correctAnswers / quiz.getSize() * 100);
        Repository.getInstance(getActivity())
                .getUserAPI()
                .finishQuiz(userId, quiz.getId(), rating)
                .enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        String finalText = setFinalText();
                        FragmentManager fm = getParentFragmentManager();
                        ResultDialogFragment resultAlert = ResultDialogFragment.newInstance(finalText);
                        resultAlert.setTargetFragment(QuizFragment.this, REQUEST_RATING);
                        resultAlert.show(fm, "result_dialog");
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(getActivity(), "Не удалось передать данные о прохождении", Toast.LENGTH_LONG).show();

                    }
                });

    }

    private String setFinalText() {
        if (correctAnswers > 0)
            return String.format(getResources().getString(R.string.result), correctAnswers, quiz.getSize());
        else return getResources().getString(R.string.bad_result);
    }


}
