package com.example.quizer.model;

import android.content.Context;

import com.example.quizer.R;

import java.util.ArrayList;
import java.util.List;

public class QuizLab {

    private static QuizLab sQuizLab;

    private List<Quiz> quizList;

    public static QuizLab getInstance(Context context) {
        if (sQuizLab == null) {
            sQuizLab = new QuizLab(context);
        }
        return sQuizLab;
    }

    private QuizLab(Context context) {
        quizList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            List<Question> questions = new ArrayList<>();
            //Создаем  вопросы
            Question question = new Question(R.string.Question1, 0,
                    new int[]{R.string.answer1_1, R.string.answer1_2, R.string.answer1_3, R.string.answer1_4});
            //Добавляем вопрос в список вопросов
            questions.add(question);
            if (i == 1) {
                question = new Question(R.string.Question2, 1,
                        new int[]{R.string.answer2_1, R.string.answer2_2, R.string.answer2_3, R.string.answer2_4});
                questions.add(question);
            }
            boolean free = i%2==0;
            quizList.add(new Quiz("Quiz №" + (i + 1), questions.size(), false, questions,free));
        }
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }

    public Quiz getQuiz(String title) {
        for (Quiz quiz : quizList) {
            if (quiz.getTitle().equals(title)) {
                return quiz;
            }
        }
        return null;
    }
}
