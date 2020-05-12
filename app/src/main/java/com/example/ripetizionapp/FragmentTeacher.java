package com.example.ripetizionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class FragmentTeacher extends Fragment {

    private TextView profileName;
    private TextView profileSurname;
    private TextView profilePlace1;
    private TextView profilePlace2;
    private TextView profileNumber;

    public FragmentTeacher() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_teacher, container, false);

        final String name = this.getArguments().getString("name");
        final String surname = this.getArguments().getString("surname");
        final String place_1 = this.getArguments().getString("place_1");
        final String place_2 = this.getArguments().getString("place_2");
        final int telephone = this.getArguments().getInt("telephone");
        //final String email = this.getArguments().getString("email");
        //final String subject = this.getArguments().getString("subject");

        profileName = rootView.findViewById(R.id.profile_name);
        profileName.setText(profileName.getText() + name);
        profileSurname = rootView.findViewById(R.id.profile_surname);
        profileSurname.setText(profileSurname.getText() + surname);
        profilePlace1 = rootView.findViewById(R.id.profile_place_1);
        profilePlace1.setText(profilePlace1.getText() + place_1);
        profilePlace2 = rootView.findViewById(R.id.profile_place_2);
        profilePlace2.setText(profilePlace2.getText() + place_2);
        profileNumber = rootView.findViewById(R.id.profile_number);
        profileNumber.setText(profileNumber.getText() + String.valueOf(telephone));

        //aggiungere mail e materie scrivendo un metodo di supporto che le renda stringhe
        //creare le risorse text in modo da poter aggiungere "nome: xxxxx"

        return rootView;
    }
}
