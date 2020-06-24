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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class FragmentTeacherLogin extends Fragment {

    private TextView profileName;
    private TextView profileSurname;
    private EditText profilePlace1;
    private EditText profilePlace2;
    private EditText profileNumber;
    private EditText profileMail;
    private EditText profileSubjects;
    private EditText profileHours;

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
        final String telephone = this.getArguments().getString("telephone");
        final String email = this.getArguments().getString("email");
        final ArrayList subjectslist = this.getArguments().getStringArrayList("subjects");
        final ArrayList reviewslist = this.getArguments().getStringArrayList("reviews");
        final String hours = this.getArguments().getString("hours");

        profileName = rootView.findViewById(R.id.profile_name_login);
        profileName.setText(name);
        profileSurname = rootView.findViewById(R.id.profile_surname_login);
        profileSurname.setText(surname);
        profilePlace1 = rootView.findViewById(R.id.profile_place_1_login);
        profilePlace1.setText(place_1);
        profilePlace2 = rootView.findViewById(R.id.profile_place_2_login);
        profilePlace2.setText(place_2);
        profileNumber = rootView.findViewById(R.id.profile_number_login);
        profileNumber.setText(telephone);
        profileMail = rootView.findViewById(R.id.profile_email_login);
        profileMail.setText(email);
        profileHours = rootView.findViewById(R.id.profile_hours_login);
        profileHours.setText(hours);

        final String subjects = SupportMethods.listToString2(subjectslist);
        profileSubjects = rootView.findViewById(R.id.profile_subjects_login);
        profileSubjects.setText(subjects);

        if (reviewslist == null) {
            notfound = rootView.findViewById(R.id.text_not_found);
            notfound.setText("Non ci sono recensioni per questo professore al momento.");
        } else {
            rView = rootView.findViewById(R.id.recyclerview_review);
            rView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getContext());
            adapter = new ReviewAdapter(reviewslist);
            rView.setLayoutManager(layoutManager);
            rView.setAdapter(adapter);
        }

        Button confirm = rootView.findViewById(R.id.button_profile);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  newMail, newPassword, newLocalita, newOrario, newProvincia, stringMaterie, newTel;
                newMail = profileMail.getText().toString();
                newLocalita = profilePlace2.getText().toString();
                newProvincia = profilePlace1.getText().toString();
                newTel = profileNumber.getText().toString();
                stringMaterie = profileSubjects.getText().toString();
                ArrayList<String> newSubjects = new ArrayList<String>(Arrays.asList(stringMaterie.split("[,\n]")));

                if (!newProvincia.equals(place_1) || !newLocalita.equals(place_2) || !newMail.equals(email) || !newTel.equals(telephone) || !stringMaterie.equals(subjects)) {

                    SupportMethods.updateTeacher(email,newMail,newLocalita,newProvincia,newTel,newSubjects);

                    Toast.makeText(getContext(), "Informazioni profilo aggiornate.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Non è stato modificato nulla.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;

    }
}
