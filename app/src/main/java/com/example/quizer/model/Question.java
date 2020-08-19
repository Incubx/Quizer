package com.example.quizer.model;

public class Question {
    private int questionResId;
    private int rightAnswer;
    private int[] answersResIds;

    public Question(int questionResId, int rightAnswer, int[] answersResids) {
        this.questionResId = questionResId;
        this.rightAnswer = rightAnswer;
        this.answersResIds = answersResids;
    }

    public int getQuestionResId() {
        return questionResId;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public int[] getAnswersResIds() {
        return answersResIds;
    }

}
