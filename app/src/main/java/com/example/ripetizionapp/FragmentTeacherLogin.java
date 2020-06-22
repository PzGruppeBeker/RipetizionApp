package com.example.ripetizionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import com.google.android.material.textfield.TextInputLayout;

public class FragmentTeacherLogin extends Fragment {

    private TextInputLayout viewEmail;
    private TextInputLayout viewPassword;

    public FragmentTeacherLogin() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_teacher_login, container, false);

        Button confirm = rootView.findViewById(R.id.button_profile);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewEmail = rootView.findViewById(R.id.text_input_email_login);
                viewPassword = rootView.findViewById(R.id.text_input_password_login);

                String email = viewEmail.getEditText().getText().toString().trim();
                String password = viewPassword.getEditText().getText().toString().trim();

            }
        });

        return rootView;

    }
}
