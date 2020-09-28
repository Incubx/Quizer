package com.example.quizer.quizModel;

import androidx.annotation.NonNull;



public class Answer {

    private int id;
    private String answerText;

    private boolean correct;

    private Question question;

    public Answer() {
    }


    public boolean isCorrect() {
        return correct;
    }


    public String getAnswerText() {
        return answerText;
    }

    @NonNull
    @Override
    public String toString() {
        return "Answer{" +
                "answerText='" + answerText + '\'' +
                '}';
    }
}
