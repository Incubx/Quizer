package com.example.quizer.quizModel;

import androidx.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

//TODO remove database


@DatabaseTable(tableName = "Quizzes")
public class Quiz implements Serializable {
    @DatabaseField(columnName = "id", generatedId = true)
    @SuppressWarnings("unused")
    private int id;
    @DatabaseField(columnName = "title", unique = true)
    private String title;
    @DatabaseField(columnName = "size")
    private int size;
    @DatabaseField(columnName = "solved")
    private boolean solved;
    @DatabaseField(columnName = "paid")
    private boolean paid;
    @ForeignCollectionField(eager = true)
    private Collection<Question> questions;

    public Quiz(String title, int size, boolean solved, boolean paid, @NonNull Collection<Question> questions) {
        this.title = title;
        this.size = size;
        this.solved = solved;
        this.paid = paid;
        this.questions = questions;
    }

    public Quiz(String title, int size, boolean solved, boolean paid) {
        this.title = title;
        this.size = size;
        this.solved = solved;
        this.paid = paid;
        questions = new ArrayList<>();
    }

    public Quiz() {

    }

    public int getId() {
        return id;
    }

    public Collection<Question> getQuestions() {
        return questions;
    }


    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public String getTitle() {
        return title;
    }

    public void addQuestion(Question question) {
        question.setQuiz(this);
        questions.add(question);
    }

    public int getSize() {
        return size;
    }

    @NonNull
    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", size=" + size +
                ", solved=" + solved +
                ", free=" + paid +
                ", questions="+questions+
                '}';
    }


}
