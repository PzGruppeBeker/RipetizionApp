package com.example.ripetizionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentTeacherLogin extends Fragment {

    private TextView profileName;
    private TextView profileSurname;
    private EditText profilePlace1;
    private EditText profilePlace2;
    private EditText profileNumber;
    private EditText profileMail;
    private EditText profileSubjects;

    private RecyclerView rView;
    private ReviewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView notfound;

    public FragmentTeacherLogin() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_teacher_login, container, false);

        final String name = this.getArguments().getString("name");
        final String surname = this.getArguments().getString("surname");
        final String place_1 = this.getArguments().getString("place_1");
        final String place_2 = this.getArguments().getString("place_2");
        final int telephone = this.getArguments().getInt("telephone");
        final String email = this.getArguments().getString("email");
        final ArrayList subjectslist = this.getArguments().getStringArrayList("subjects");
        final ArrayList reviewslist = this.getArguments().getStringArrayList("reviews");

        profileName = rootView.findViewById(R.id.profile_name_login);
        profileName.setText(name);
        profileSurname = rootView.findViewById(R.id.profile_surname_login);
        profileSurname.setText(surname);
        profilePlace1 = rootView.findViewById(R.id.profile_place_1_login);
        profilePlace1.setText(place_1);
        profilePlace2 = rootView.findViewById(R.id.profile_place_2_login);
        profilePlace2.setText(place_2);
        profileNumber = rootView.findViewById(R.id.profile_number_login);
        profileNumber.setText(String.valueOf(telephone));
        profileMail = rootView.findViewById(R.id.profile_email_login);
        profileMail.setText(email);

        String subjects = SupportMethods.listToString2(subjectslist);
        profileSubjects = rootView.findViewById(R.id.profile_subjects_login);
        profileSubjects.setText(subjects);

        Button confirm = rootView.findViewById(R.id.button_profile);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Grazie per il tuo tempo, la tua recensione sarà visibile al più presto!", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;

    }
}
