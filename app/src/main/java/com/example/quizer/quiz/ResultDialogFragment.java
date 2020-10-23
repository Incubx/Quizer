package com.example.quizer.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.quizer.R;

public class ResultDialogFragment extends DialogFragment {
    private static final String RESULTS_TEXT = "RESULTS_TEXT";

    public static final String EXTRA_RATING = "EXTRA_RATING";


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_results_alert, null);
        TextView text = v.findViewById(R.id.result_text);
        final String results = getArguments().getString(RESULTS_TEXT);
        text.setText(results);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.result_dialog_title)
                .setPositiveButton(R.string.ok_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        okBtnPressed();
                    }
                }).create();

    }

    private void okBtnPressed() {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }

    public static ResultDialogFragment newInstance(String message) {
        Bundle args = new Bundle();
        args.putString(RESULTS_TEXT, message);
        ResultDialogFragment resultAlert = new ResultDialogFragment();
        resultAlert.setArguments(args);
        return resultAlert;
    }


}