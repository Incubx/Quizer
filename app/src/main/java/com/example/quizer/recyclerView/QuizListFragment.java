package com.example.quizer.recyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizer.quizModel.Quiz;
import com.example.quizer.quiz.QuizActivity;
import com.example.quizer.database.Repository;
import com.example.quizer.R;
import com.example.quizer.userCabinet.UserCabinetActivity;

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
                Toast.makeText(getActivity(), "This is about Toast!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_item_user_cabinet:
                //this intent doesn't contain any user info, cause of 1 user in DataBase.
                Intent intent = UserCabinetActivity.newIntent(getActivity());
                startActivity(intent);
                return true;
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
                Toast.makeText(getActivity(), "Error getting QuizList", Toast.LENGTH_LONG).show();
                if (adapter == null) {
                    adapter = new QuizAdapter(new ArrayList<Quiz>());
                    recyclerView.setAdapter(adapter);
                }
            }
        });

    }

    private class QuizHolderPaid extends GenericQuizHolder {

        public QuizHolderPaid(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_quiz_not_free, parent, false));
            itemView.setOnClickListener(this);
            findViews();
            Button buyBtn = itemView.findViewById(R.id.buy_button);
            buyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quiz.setPaid(true);
                    Toast.makeText(getActivity(), "Thanks for buying this quiz!", Toast.LENGTH_LONG).show();
                    updateUI();
                }
            });
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), R.string.buy_notice, Toast.LENGTH_LONG).show();
            /*Intent intent = QuizActivity.newIntent(getActivity(), quiz.getTitle());
            startActivity(intent);*/
        }
    }

    private class QuizHolderFree extends GenericQuizHolder {


        public QuizHolderFree(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_quiz, parent, false));
            itemView.setOnClickListener(this);
            findViews();
        }

        @Override
        public void onClick(View view) {
            Intent intent = QuizActivity.newIntent(getActivity(), quiz.getId());
            startActivity(intent);
        }
    }


    private class QuizAdapter extends RecyclerView.Adapter<GenericQuizHolder> {

        private final int FREE_QUIZ = 0;
        private final int NOT_FREE_QUIZ = 1;
        private List<Quiz> quizList;

        public QuizAdapter(List<Quiz> quizList) {
            this.quizList = quizList;
        }

        @NonNull
        @Override
        public GenericQuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            switch (viewType) {
                case FREE_QUIZ:
                    return new QuizHolderFree(layoutInflater, parent);
                case NOT_FREE_QUIZ:
                    return new QuizHolderPaid(layoutInflater, parent);
            }
            return new QuizHolderPaid(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull GenericQuizHolder holder, int position) {
            holder.bind(quizList.get(position));
        }

        @Override
        public int getItemCount() {
            return quizList.size();
        }

        @Override
        public int getItemViewType(int position) {

            if (quizList.get(position).isPaid())
                return NOT_FREE_QUIZ;
            else return FREE_QUIZ;
        }

        public void setQuizList(List<Quiz> quizList) {
            this.quizList = quizList;
        }
    }
}
