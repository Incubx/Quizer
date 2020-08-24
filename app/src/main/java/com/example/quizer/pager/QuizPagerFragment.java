package com.example.quizer.pager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.quizer.quizModel.Quiz;
import com.example.quizer.database.Repository;
import com.example.quizer.R;

import java.util.List;

public class QuizPagerFragment extends Fragment {
    private ViewPager2 quizPager;

    private static final String TITLE_ARG = "Title";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quiz_pager, container, false);
        quizPager = v.findViewById(R.id.quiz_pager);
        quizPager.setAdapter(new PagerAdapter(this));

        List<Quiz> quizList = Repository.getInstance(getActivity()).getQuizList();
        String title = getArguments().getString(TITLE_ARG);
        for (int i = 0; i < quizList.size(); i++) {
            if (quizList.get(i).getTitle().equals(title)) {
                quizPager.setCurrentItem(i);
                break;
            }
        }

        return v;
    }

    public static QuizPagerFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(TITLE_ARG, title);
        QuizPagerFragment fragment = new QuizPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private class PagerAdapter extends FragmentStateAdapter {

        private List<Quiz> quizList;

        public PagerAdapter(Fragment fragment){
            super(fragment);
            quizList = Repository.getInstance(getActivity()).getQuizList();
        }


        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return QuizAboutFragment.newInstance(quizList.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return quizList.size();
        }
    }


}
