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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizer.R;

public class LogInFragment extends Fragment {

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
            }
        }

        private boolean isFieldFilled(EditText editText) {
            return !editText.getText().toString().isEmpty();
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registration, container, false);
        passwordText = v.findViewById(R.id.password_edit_text);
        emailText = v.findViewById(R.id.email_edit_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            emailText.setFocusedByDefault(false);
        }
        emailText.setNextFocusDownId(R.id.email_edit_text);

        LogInFragment.EditTextListener listener = new LogInFragment.EditTextListener();
        passwordText.addTextChangedListener(listener);
        emailText.addTextChangedListener(listener);

        loginBtn = v.findViewById(R.id.login_btn);
        loginBtn.setEnabled(false);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendForm();
            }
        });
        return v;
    }


    private void sendForm() {

    }

}