package com.example.quizer.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizer.R;
import com.example.quizer.quizModel.Answer;
import com.example.quizer.quizModel.Question;
import com.example.quizer.quizModel.Quiz;
import com.example.quizer.rest.Repository;

import java.util.List;

public class AnswersFragment extends Fragment {
    private static final String QUIZ_ARG = "QUIZ_ARG";

    public static AnswersFragment newInstance(Quiz quiz) {
        Bundle args = new Bundle();
        args.putSerializable(QUIZ_ARG, quiz);
        AnswersFragment fragment = new AnswersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        View view = v.findViewById(R.id.category_list);
        view.setVisibility(View.GONE);

        List<Answer> userAnswers = Repository.getInstance(getActivity()).getUserAnswers();
        Quiz quiz = (Quiz) getArguments().getSerializable(QUIZ_ARG);
        getActivity().setTitle(quiz.getTitle());

        AnswersAdapter adapter = new AnswersAdapter(quiz.getQuestions(), userAnswers);
        recyclerView.setAdapter(adapter);
        return v;

    }

    private class AnswersHolder extends RecyclerView.ViewHolder {
        private final TextView questionText;
        private final TextView userAnswerText;
        private final TextView correctAnswerText;
        private final ConstraintLayout layout;

        public AnswersHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_answers, parent, false));
            questionText = itemView.findViewById(R.id.question_text);
            userAnswerText = itemView.findViewById(R.id.user_answer_text);
            correctAnswerText = itemView.findViewById(R.id.correct_answer_text);
            layout = itemView.findViewById(R.id.list_item_answer);
        }

        public void bind(Question question, Answer answer) {
            questionText.setText(question.getQuestionText());
            String userAnswerString = answer.getAnswerText();
            String userAnswer = getResources()
                    .getString(R.string.user_answer, userAnswerString);
            userAnswerText.setText(userAnswer);
            String correctAnswerString = question.getRightAnswerText();
            String correctAnswer = getResources()
                    .getString(R.string.correct_answer, correctAnswerString);
            correctAnswerText.setText(correctAnswer);
            correctAnswerText.setBackground(getResources().getDrawable(R.drawable.correct_answer));
            if (userAnswerString.equals(correctAnswerString)) {
                layout.setBackgroundColor(getResources().getColor(R.color.correct_answer));
                userAnswerText.setBackground(getResources().getDrawable(R.drawable.correct_user_answer));
            } else {
                layout.setBackgroundColor(getResources().getColor(R.color.incorrect_answer));
                userAnswerText.setBackground(getResources().getDrawable(R.drawable.incorrect_answer));

            }
        }


    }

    private class AnswersAdapter extends RecyclerView.Adapter<AnswersHolder> {

        private final List<Question> questionList;
        private final List<Answer> userAnswers;

        public AnswersAdapter(List<Question> quizList, List<Answer> userAnswers) {
            this.questionList = quizList;
            this.userAnswers = userAnswers;
        }

        @NonNull
        @Override
        public AnswersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new AnswersHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull AnswersHolder holder, int position) {
            holder.bind(questionList.get(position), userAnswers.get(position));
        }

        @Override
        public int getItemCount() {
            return userAnswers.size();
        }

    }
}
