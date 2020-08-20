package com.example.quizer.model;

import androidx.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


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
    @DatabaseField(columnName = "free")
    private boolean free;
    @ForeignCollectionField(eager = true)
    private Collection<Question> questions;

    public Quiz(String title, int size, boolean solved, boolean free, @NonNull Collection<Question> questions) {
        this.title = title;
        this.size = size;
        this.solved = solved;
        this.free = free;
        this.questions = questions;
    }

    public Quiz(String title, int size, boolean solved, boolean free) {
        this.title = title;
        this.size = size;
        this.solved = solved;
        this.free = free;
        questions = new ArrayList<>();
    }

    public Quiz() {

    }

    public Collection<Question> getQuestions() {
        return questions;
    }


    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
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

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", size=" + size +
                ", solved=" + solved +
                ", free=" + free +
                '}';
    }


}
