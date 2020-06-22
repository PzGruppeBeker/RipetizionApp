package com.example.ripetizionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class FragmentTeacher extends Fragment {

    private TextView profileName;
    private TextView profileSurname;
    private TextView profilePlace1;
    private TextView profilePlace2;
    private TextView profileNumber;
    private TextView profileMail;
    //private TextView profileSubjects;

    private TextInputLayout editreview;

    private RecyclerView rView;
    private ReviewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView notfound;

    public FragmentTeacher() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_teacher, container, false);

        final String name = this.getArguments().getString("name");
        final String surname = this.getArguments().getString("surname");
        final String place_1 = this.getArguments().getString("place_1");
        final String place_2 = this.getArguments().getString("place_2");
        final int telephone = this.getArguments().getInt("telephone");
        final String email = this.getArguments().getString("email");
        //final String subject = this.getArguments().getString("subject");
        final ArrayList reviews = this.getArguments().getStringArrayList("reviews");

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
        profileMail = rootView.findViewById(R.id.profile_email);
        profileMail.setText(profileMail.getText() + email);

        Button add = rootView.findViewById(R.id.add_review_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editreview = rootView.findViewById(R.id.text_input_review);
                String review = editreview.getEditText().getText().toString();
                Toast.makeText(getContext(), review, Toast.LENGTH_SHORT).show();
                if (!review.isEmpty()){
                    SupportMethods.addReview(email,place_1,review);
                    editreview.getEditText().setText("");
                    SupportMethods.hideKeyboardFrom(getContext(), rootView);
                    Toast.makeText(getContext(), "Grazie per il tuo tempo, la tua recensione sarà visibile al più presto!", Toast.LENGTH_SHORT).show();
                    editreview.setError(null);
                    editreview.setErrorEnabled(false);
                } else {
                    editreview.setError("Questo campo non può essere lasciato vuoto");
                }
            }
        });

        if (reviews == null) {
            notfound = rootView.findViewById(R.id.text_not_found);
            notfound.setText("Non ci sono recensioni per questo professore al momento.");
        } else {
            rView = rootView.findViewById(R.id.recyclerview_review);
            rView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getContext());
            adapter = new ReviewAdapter(reviews);

            rView.setLayoutManager(layoutManager);
            rView.setAdapter(adapter);
            //aggiungere mail e materie scrivendo un metodo di supporto che le renda stringhe
            //creare le risorse text in modo da poter aggiungere "nome: xxxxx"
        }

        return rootView;
    }

}