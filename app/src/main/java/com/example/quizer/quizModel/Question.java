package com.example.quizer.quizModel;

import androidx.annotation.NonNull;

import java.util.List;



public class Question {

    private int id;
    private String questionText;
    private Quiz quiz;
    private List<Answer> answers;


    public Question(String questionText, int rightAnswer, Quiz quiz) {
        this.questionText = questionText;
        this.quiz = quiz;

    }


    public List<Answer> getAnswers() {
        return answers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getQuestionText() {
        return questionText;
    }


    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @NonNull
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + questionText + '\'' +
                ", answers="+answers+
                '}';
    }

    public int getRightAnswer() {
            for(int i=0;i<answers.size();i++){
                if(answers.get(i).isCorrect()) return i;
            }
            return -1;
    }
}
