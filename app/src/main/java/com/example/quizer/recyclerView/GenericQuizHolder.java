package com.example.quizer.recyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizer.quizModel.Quiz;
import com.example.quizer.R;

public abstract class GenericQuizHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public GenericQuizHolder(@NonNull View itemView) {
        super(itemView);
    }

    protected TextView quizTitleText;
    protected TextView quizSizeText;
    protected ImageView quizSolvedImage;
    protected Button aboutBtn;
    protected Quiz quiz;


    public void bind(Quiz quiz) {
        this.quiz = quiz;
        int quizSize = quiz.getSize();
        //TODO Change to resource!
        String sizeText = quizSize > 1 ? quizSize + " questions" : quizSize + " question";
        quizSizeText.setText(sizeText);
        quizTitleText.setText(quiz.getTitle());

        if (quiz.isSolved()) {
            quizSolvedImage.setVisibility(View.VISIBLE);
        } else quizSolvedImage.setVisibility(View.INVISIBLE);
    }

    protected void findViews() {
        quizSizeText = itemView.findViewById(R.id.quizSizeText);
        quizTitleText = itemView.findViewById(R.id.quizTitleText);
        quizSolvedImage = itemView.findViewById(R.id.quiz_solved);
        aboutBtn = itemView.findViewById(R.id.about_btn);
        aboutBtn.setVisibility(View.INVISIBLE);

    }

}
