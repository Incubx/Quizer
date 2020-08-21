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
import android.widget.Button;
import android.widget.RatingBar;
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
        final RatingBar ratingBar = v.findViewById(R.id.ratingBar);
        ratingBar.setStepSize(1);
        Button shareBtn = v.findViewById(R.id.share_button);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, results);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Quizer!");
                intent =Intent.createChooser(intent,"Quizer sends!");
                startActivity(intent);
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.result_dialog_title)
                .setPositiveButton(R.string.ok_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        int rating = (int) ratingBar.getRating();
                        sendResult(Activity.RESULT_OK, rating);
                    }
                }).create();

    }

    private String getReport() {
        return getString(R.string.share_text, "quiz!", 2);
    }

    private void sendResult(int resultCode, int rating) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RATING, rating);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    public static ResultDialogFragment newInstance(String message) {
        Bundle args = new Bundle();
        args.putString(RESULTS_TEXT, message);
        ResultDialogFragment resultAlert = new ResultDialogFragment();
        resultAlert.setArguments(args);
        return resultAlert;
    }


}