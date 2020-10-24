package com.example.quizer.userCabinet;

import android.content.Intent;
import android.os.Build;
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
import com.example.quizer.database.Repository;
import com.example.quizer.recyclerView.ListFragmentActivity;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationFragment extends Fragment {

    private EditText serverIPText;
    private EditText nicknameText;
    private EditText passwordText;
    private EditText emailText;
    private Button registerBtn;

    private class EditTextListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (isFilled(nicknameText) &&
                    isFilled(passwordText) &&
                    isFilled(emailText)) {
                registerBtn.setEnabled(true);
            } else registerBtn.setEnabled(false);
        }

        private boolean isFilled(EditText editText) {
            return !editText.getText().toString().isEmpty();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registration, container, false);
        nicknameText = v.findViewById(R.id.nickname_edit_text);
        passwordText = v.findViewById(R.id.password_edit_text);
        emailText = v.findViewById(R.id.email_edit_text);
        serverIPText = v.findViewById(R.id.server_ip_edit_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nicknameText.setFocusedByDefault(false);
        }
        nicknameText.setNextFocusDownId(R.id.email_edit_text);
        emailText.setNextFocusDownId(R.id.email_edit_text);

        EditTextListener listener = new EditTextListener();
        nicknameText.addTextChangedListener(listener);
        passwordText.addTextChangedListener(listener);
        emailText.addTextChangedListener(listener);

        registerBtn = v.findViewById(R.id.login_btn);
        registerBtn.setEnabled(false);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = getUserFromForm();
                Repository.getInstance(getActivity()).setServerIP(serverIPText.getText().toString());
                Repository.getInstance(getActivity()).getUserAPI().registerUser(user).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.body() == -1) {
                            //TODO Переход на страницу авторизации с подставлением указанного emaila.
                            Toast.makeText(getActivity(), "Уже зарегестрирован", Toast.LENGTH_LONG).show();
                        } else {
                            //TODO Переход в список тестов и сохранение юзера в префы
                            Repository.getInstance(getActivity()).saveUserId(response.body());
                            Intent intent = new Intent(getActivity(), ListFragmentActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }

                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });
            }
        });

        return v;
    }

    private User getUserFromForm() {
        return new User(nicknameText.getText().toString(),
                emailText.getText().toString(),
                passwordText.getText().toString());
    }


}