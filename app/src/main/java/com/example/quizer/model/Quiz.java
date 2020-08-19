package com.example.quizer.model;


import java.io.Serializable;
import java.util.List;

public class Quiz implements Serializable {
    private String title;
    private int size;
    private boolean solved;
    private List<Question> questionList;
    private boolean free;

    public Quiz(String title, int size, boolean solved, List<Question> questionList, boolean free) {
        this.title = title;
        this.size = size;
        this.solved = solved;
        this.questionList = questionList;
        this.free = free;
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


    public int getSize() {
        return size;
    }


    public List<Question> getQuestionList() {
        return questionList;
    }

}
