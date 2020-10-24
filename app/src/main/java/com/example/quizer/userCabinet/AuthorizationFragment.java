package com.example.quizer.userCabinet;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizationFragment extends Fragment {

    private EditText passwordText;
    private EditText emailText;
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
            if (isFieldFilled(passwordText) &&
                    isFieldFilled(emailText)) {
                loginBtn.setEnabled(true);
            }else  loginBtn.setEnabled(false);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            emailText.setFocusedByDefault(false);
        }
        emailText.setNextFocusDownId(R.id.email_edit_text);

        AuthorizationFragment.EditTextListener listener = new AuthorizationFragment.EditTextListener();
        passwordText.addTextChangedListener(listener);
        emailText.addTextChangedListener(listener);

        loginBtn = v.findViewById(R.id.login_btn);
        loginBtn.setEnabled(false);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(emailText.getText().toString(),passwordText.getText().toString());
                Repository.getInstance(getActivity()).getUserAPI().authorizeUser(user).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        switch (response.body()) {
                            case 100:
                                //TODO Переход в список тестов и сохранение юзера в префы
                                Toast.makeText(getActivity(), "Авторизован", Toast.LENGTH_LONG).show();
                                break;
                            case 102:
                                //TODO Очистика поля пароль
                                Toast.makeText(getActivity(), "Неверный пароль", Toast.LENGTH_LONG).show();
                                break;
                            case 103:
                                //TODO Переход на страницу регистрации с подставлением указанного emaila.
                                Toast.makeText(getActivity(), "Не зарегестрирован", Toast.LENGTH_LONG).show();
                                break;
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

}