package com.example.quizer.recyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.quizer.R;
import com.example.quizer.quizModel.Category;
import com.example.quizer.rest.Repository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChooseCategoryDialog extends DialogFragment {

    private Spinner spinner;
    public static final String EXTRA_CATEGORY = "Category";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_category, null);
        spinner = v.findViewById(R.id.category_list);

        Repository.getInstance(getActivity()).getCategoryAPI().getCategoryList().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> categoryList = response.body();
                List<String> categoriesNameList = new ArrayList<>();
                categoriesNameList.add("No category");
                for (Category category : categoryList) {
                    categoriesNameList.add(category.getName());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            ChooseCategoryDialog.this.getActivity(), android.R.layout.simple_spinner_item, categoriesNameList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.set_server_ip_string)
                .setPositiveButton(R.string.save, (dialogInterface, i) -> {
                    String category = (String) spinner.getSelectedItem();
                    sendResult(category);
                }).create();

    }

    private void sendResult(String category) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CATEGORY, category);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }

    public static ChooseCategoryDialog newInstance() {
        return new ChooseCategoryDialog();
    }
}
