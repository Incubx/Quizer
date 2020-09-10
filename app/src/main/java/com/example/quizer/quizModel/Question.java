package com.example.quizer.quizModel;

import androidx.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

@DatabaseTable(tableName = "Questions")
public class Question {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "text",unique = true)
    private String questionText;
    @DatabaseField(foreign = true,columnName = "quiz")
    private Quiz quiz;

    @ForeignCollectionField(eager = true)
    private Collection<Question> answers;


    public Question() {
//For ORMLite
    }

    public Question(String questionText, int rightAnswer, Quiz quiz) {
        this.questionText = questionText;
        this.quiz = quiz;

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
                '}';
    }
}
