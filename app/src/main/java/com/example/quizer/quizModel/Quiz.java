package com.example.quizer.quizModel;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Quiz implements Serializable {
    private int id;
    private String title;
    private int size;
    private List<Question> questions;
    private boolean isCompleted;
    private Category category;

    public Quiz(String title, int size, @NonNull List<Question> questions, boolean isCompleted) {
        this.title = title;
        this.size = size;
        this.questions = questions;
        this.isCompleted = isCompleted;

    }

    public Quiz(String title, int size) {
        this.title = title;
        this.size = size;
        questions = new ArrayList<>();
    }

    public Quiz() {

    }

    public int getId() {
        return id;
    }

    public List<Question> getQuestions() {
        return questions;
    }


    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public Category getCategory() {
        return category;
    }


    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @NonNull
    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", size=" + size +
                ", questions=" + questions +
                '}';
    }


}
