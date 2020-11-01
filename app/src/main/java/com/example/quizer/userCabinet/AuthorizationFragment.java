package com.example.quizer.userCabinet;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizer.R;
import com.example.quizer.recyclerView.ListActivity;
import com.example.quizer.rest.Repository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizationFragment extends Fragment {

    private EditText passwordText;
    private EditText emailText;
    private EditText serverIPText;
    private Button loginBtn;


    private class EditTextListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            loginBtn.setEnabled(isFieldFilled(passwordText) &&
                    isFieldFilled(emailText));
        }

        private boolean isFieldFilled(EditText editText) {
            return !editText.getText().toString().isEmpty();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_authorization, container, false);
        passwordText = v.findViewById(R.id.password_edit_text);
        emailText = v.findViewById(R.id.email_edit_text);
        serverIPText = v.findViewById(R.id.server_ip_edit_text);

        AuthorizationFragment.EditTextListener listener = new AuthorizationFragment.EditTextListener();
        passwordText.addTextChangedListener(listener);
        emailText.addTextChangedListener(listener);

        loginBtn = v.findViewById(R.id.login_btn);
        loginBtn.setEnabled(false);

        loginBtn.setOnClickListener(view -> {
            User user = new User(emailText.getText().toString(), passwordText.getText().toString());
            Repository.getInstance(getActivity()).setServerIP(serverIPText.getText().toString());
            Repository.getInstance(getActivity()).getUserAPI().authorizeUser(user).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    switch (response.body()) {
                        case -2:
                            Toast.makeText(getActivity(), "Неверный пароль", Toast.LENGTH_LONG).show();
                            passwordText.setText("");
                            loginBtn.setEnabled(false);
                            break;
                        case -3:
                            Toast.makeText(getActivity(), "Не зарегестрирован", Toast.LENGTH_LONG).show();
                            emailText.setText("");
                            passwordText.setText("");
                            loginBtn.setEnabled(false);
                            break;
                        default:
                            Repository.getInstance(getActivity()).saveUserId(response.body());
                            Intent intent = new Intent(getActivity(), ListActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            break;
                    }

                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Toast.makeText(getActivity(), "Ошибка подключения к серверу", Toast.LENGTH_LONG).show();
                }
            });
        });
        return v;
    }

}