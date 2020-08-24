package com.example.quizer.quizModel;

import androidx.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Questions")
public class Question {
    @DatabaseField(columnName = "id", generatedId = true)
    @SuppressWarnings("unused")
    private int id;
    @DatabaseField(columnName = "text",unique = true)
    private String questionText;
    @DatabaseField(columnName = "rightAnswer")
    private int rightAnswer;
    @DatabaseField(foreign = true,columnName = "quiz")
    private Quiz quiz;



@SuppressWarnings("unused")
    public Question() {
//For ORMLite
    }

    public Question(String questionText, int rightAnswer, Quiz quiz) {
        this.questionText = questionText;
        this.rightAnswer = rightAnswer;
        this.quiz = quiz;

    }


    public String getQuestionText() {
        return questionText;
    }

    public int getRightAnswer() {
        return rightAnswer;
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
                ", rightAnswer=" + rightAnswer +
                ", quiz=" + quiz +
                '}';
    }
}
