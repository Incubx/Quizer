package com.example.quizer.recyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.quizer.R;
import com.example.quizer.rest.Repository;

public class ServerIpSetterDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_set_server_ip, null);
        final EditText text = v.findViewById(R.id.server_ip_edit_text);
        String SERVER_PREF = "SERVER_IP";
        final SharedPreferences preferences = getActivity().getSharedPreferences(SERVER_PREF, 0);
        String server_ip = preferences.getString(SERVER_PREF, "");
        text.setText(server_ip);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.set_server_ip_string)
                .setPositiveButton(R.string.save, (dialogInterface, i) -> Repository.getInstance(getActivity()).setServerIP(text.getText().toString())).create();

    }

    public static ServerIpSetterDialog newInstance() {
        return new ServerIpSetterDialog();
    }
}
