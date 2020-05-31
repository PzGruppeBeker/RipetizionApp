package com.example.ripetizionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class FragmentLogin extends Fragment {

    public FragmentLogin() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        //verificare se è necessario cambiare l'id delle view in fragment_login, dato che i campi email
        //e password hanno lo stesso id di quelle in fragment_registration

        Button login = rootView.findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "LOGIN INPUT RECEIVED", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
    
}
