package com.example.ripetizionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.Fragment;

public class FragmentRegistration extends Fragment {

    public FragmentRegistration() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_registration, container, false);

        Button confirm = rootView.findViewById(R.id.button_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextInputLayout Viewemail = rootView.findViewById(R.id.text_input_email);
                TextInputLayout Viewname = rootView.findViewById(R.id.text_input_name);
                TextInputLayout Viewsurname = rootView.findViewById(R.id.text_input_surname);
                //TextInputLayout Viewplace = rootView.findViewById(R.id.text_input_place);
                TextInputLayout Viewpassword = rootView.findViewById(R.id.text_input_password);
                TextInputLayout Viewsubjects = rootView.findViewById(R.id.text_input_subject);

                String email = Viewemail.getEditText().getText().toString().trim();
                String nome = Viewname.getEditText().getText().toString().trim();
                String cognome = Viewsurname.getEditText().getText().toString().trim();
                //String luogo = Viewplace.getEditText().getText().toString().trim();
                String password = Viewpassword.getEditText().getText().toString().trim();
                String materie = Viewsubjects.getEditText().getText().toString().trim();



                Toast.makeText(getContext(), "CONFIRM INPUT RECEIVED", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
