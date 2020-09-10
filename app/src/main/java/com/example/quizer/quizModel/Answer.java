package com.example.quizer.quizModel;

import androidx.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Answers")
public class Answer {
    @DatabaseField(columnName = "id", generatedId = true)

    private int id;
    @DatabaseField(columnName = "text",unique = true)
    private String answerText;

    @DatabaseField(columnName = "isCorrect")
    private boolean correct;

    @DatabaseField(foreign = true,columnName = "questionId")
    private Question question;

    @SuppressWarnings("unused")
    public Answer() {
    }

    public Answer(String answerText) {
        this.answerText = answerText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @NonNull
    @Override
    public String toString() {
        return "Answer{" +
                "answerText='" + answerText + '\'' +
                '}';
    }
}
