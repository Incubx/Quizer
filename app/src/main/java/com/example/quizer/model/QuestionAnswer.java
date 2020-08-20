package com.example.quizer.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "QuestionAnswer")
public class QuestionAnswer {

    @DatabaseField(columnName = "id", generatedId = true)
    @SuppressWarnings("unused")
    private int id;

    @DatabaseField(columnName = "answer_id", foreign = true,foreignAutoRefresh = true)
    Answer answer;
    @DatabaseField(columnName = "question_id", foreign = true)
    Question question;

    @SuppressWarnings("unused")
    public QuestionAnswer() {
    }

    public QuestionAnswer(Question question, Answer answer) {
        this.answer = answer;
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

}
