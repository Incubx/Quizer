package com.example.quizer.recyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizer.quizModel.Category;
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
    private Spinner categoryListSpinner;

    private String category;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        categoryListSpinner = v.findViewById(R.id.category_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        category = "No category";
        setCategoryList();

        categoryListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = (String) adapterView.getItemAtPosition(i);
                updateUI();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setHasOptionsMenu(true);
        updateUI();
        return v;
    }

    private void setCategoryList() {
        List<String> categoriesNameList = new ArrayList<>();
        categoriesNameList.add("No category");
        Repository.getInstance(getActivity()).getCategoryAPI().getCategoryList().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> categoryList = response.body();
                for (Category category : categoryList) {
                    categoriesNameList.add(category.getName());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            getActivity(), android.R.layout.simple_spinner_item, categoriesNameList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categoryListSpinner.setAdapter(adapter);

                }
                for (int i = 0; i < categoriesNameList.size(); i++) {
                    String categoryName = categoriesNameList.get(i);
                    if (QuizListFragment.this.category.equals(categoryName)) {
                        categoryListSpinner.setSelection(i);
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getActivity(), "Не удалось получить список категорий", Toast.LENGTH_LONG).show();

            }
        });
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentManager fm = getParentFragmentManager();
        switch (item.getItemId()) {
            case R.id.menu_item_about:
                Toast.makeText(getActivity(), "Тут будет справка о программе", Toast.LENGTH_LONG).show();
                return true;
            case R.id.set_server_btn:
                ServerIpSetterDialog serverIpSetterDialog = ServerIpSetterDialog.newInstance();
                serverIpSetterDialog.setTargetFragment(QuizListFragment.this, 0);
                serverIpSetterDialog.show(fm, "result_dialog");
                return true;
            case R.id.update_quiz_btn:
                setCategoryList();
                updateUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void updateUI() {
        int userId = Repository.getInstance(getActivity()).getUserId();
        //

        Repository.getInstance(getActivity())
                .getQuizAPI()
                .getQuizList(userId, category).enqueue(new Callback<List<Quiz>>() {
            @Override
            public void onResponse(Call<List<Quiz>> call, Response<List<Quiz>> response) {
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
            public void onFailure(Call<List<Quiz>> call, Throwable t) {
                Toast.makeText(getActivity(), "Ошибка получения тестов", Toast.LENGTH_LONG).show();
                if (adapter == null) {
                    adapter = new QuizAdapter(new ArrayList<>());
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.setQuizList(new ArrayList<>());
                    adapter.notifyDataSetChanged();
                }
            }

        });

    }

    private class QuizHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView quizTitleText;
        private final TextView quizSizeText;
        private final ImageView isCompleted;
        private Quiz quiz;

        public QuizHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_quiz, parent, false));
            itemView.setOnClickListener(this);
            quizSizeText = itemView.findViewById(R.id.quizSizeText);
            quizTitleText = itemView.findViewById(R.id.quizTitleText);
            isCompleted = itemView.findViewById(R.id.isCompletedStarView);

        }

        public void bind(Quiz quiz) {
            this.quiz = quiz;
            int quizSize = quiz.getSize();
            String sizeText = "Количество вопросов: " + quizSize;
            quizSizeText.setText(sizeText);
            quizTitleText.setText(quiz.getTitle());
            if (quiz.isCompleted()) {
                isCompleted.setVisibility(View.VISIBLE);
            } else isCompleted.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onClick(View view) {
            if (quiz.getSize() == 0) {
                Toast.makeText(getActivity(), "В тесте пока нет вопросов", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = QuizActivity.newIntent(getActivity(), quiz.getId());
                startActivity(intent);
            }
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
