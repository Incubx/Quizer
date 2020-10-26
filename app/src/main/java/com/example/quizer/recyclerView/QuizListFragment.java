package com.example.quizer.recyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizer.quizModel.Quiz;
import com.example.quizer.quiz.QuizActivity;
import com.example.quizer.rest.Repository;
import com.example.quizer.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuizListFragment extends Fragment {

    private RecyclerView recyclerView;
    private QuizAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quiz_list, container, false);
        recyclerView = v.findViewById(R.id.quizRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_about:
                Toast.makeText(getActivity(), "Тут будет справка о программе", Toast.LENGTH_LONG).show();
                return true;
            case R.id.set_server_btn:
                FragmentManager fm = getParentFragmentManager();
                ServerIpSetterDialog serverIpSetterDialog = ServerIpSetterDialog.newInstance();
                serverIpSetterDialog.setTargetFragment(QuizListFragment.this, 0);
                serverIpSetterDialog.show(fm, "result_dialog");
            case R.id.update_quiz_btn:
                updateUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void updateUI() {
        Repository.getInstance(getActivity())
                .getQuizAPI()
                .getQuizList().enqueue(new Callback<List<Quiz>>() {
            @Override
            public void onResponse(@NonNull Call<List<Quiz>> call, @NonNull Response<List<Quiz>> response) {
                List<Quiz> quizList = response.body();
                if (adapter == null) {
                    adapter = new QuizAdapter(quizList);
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.setQuizList(quizList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Quiz>> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "Ошибка получения тестов", Toast.LENGTH_LONG).show();
                if (adapter == null) {
                    adapter = new QuizAdapter(new ArrayList<Quiz>());
                    recyclerView.setAdapter(adapter);
                }
            }
        });

    }

    private class QuizHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView quizTitleText;
        private TextView quizSizeText;
        private Quiz quiz;

        public QuizHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_quiz, parent, false));
            itemView.setOnClickListener(this);
            quizSizeText = itemView.findViewById(R.id.quizSizeText);
            quizTitleText = itemView.findViewById(R.id.quizTitleText);
        }

        public void bind(Quiz quiz) {
            this.quiz = quiz;
            int quizSize = quiz.getSize();
            String sizeText = "Количество вопросов: " + quizSize;
            quizSizeText.setText(sizeText);
            quizTitleText.setText(quiz.getTitle());
            if (quiz.getSize() == 0) {
                itemView.setEnabled(false);
            }
        }

        @Override
        public void onClick(View view) {
            Intent intent = QuizActivity.newIntent(getActivity(), quiz.getId());
            startActivity(intent);
        }

    }


    private class QuizAdapter extends RecyclerView.Adapter<QuizHolder> {

        private List<Quiz> quizList;

        public QuizAdapter(List<Quiz> quizList) {
            this.quizList = quizList;
        }

        @NonNull
        @Override
        public QuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new QuizHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull QuizHolder holder, int position) {
            holder.bind(quizList.get(position));
        }

        @Override
        public int getItemCount() {
            return quizList.size();
        }

        public void setQuizList(List<Quiz> quizList) {
            this.quizList = quizList;
        }
    }
}
