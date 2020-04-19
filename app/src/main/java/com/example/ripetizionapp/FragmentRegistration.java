package com.example.ripetizionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FragmentRegistration extends Fragment {

    private TextInputLayout viewEmail;
    private TextInputLayout viewName;
    private TextInputLayout viewSurname;
    private TextInputLayout viewPlace;
    private TextInputLayout viewPassword;
    private TextInputLayout viewSubjects;

    public FragmentRegistration() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_registration, container, false);

        Button confirm = rootView.findViewById(R.id.button_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewEmail = rootView.findViewById(R.id.text_input_email);
                viewName = rootView.findViewById(R.id.text_input_name);
                viewSurname = rootView.findViewById(R.id.text_input_surname);
                viewPlace = rootView.findViewById(R.id.text_input_place);
                viewPassword = rootView.findViewById(R.id.text_input_password);
                viewSubjects = rootView.findViewById(R.id.text_input_subject);

                String email = viewEmail.getEditText().getText().toString().trim();
                String name = viewName.getEditText().getText().toString().trim();
                String surname = viewSurname.getEditText().getText().toString().trim();
                String place = viewPlace.getEditText().getText().toString().trim();
                String password = viewPassword.getEditText().getText().toString().trim();
                String subjects = viewSubjects.getEditText().getText().toString().trim();

                if (!checkEmail(email) | !checkName(name) | !checkSurname(surname) | !checkPassword(password) | !checkSubjects(subjects) | !checkPlace(place)) {
                    Toast.makeText(getContext(), "Qualcosa non va, controlla che sia tutto in ordine!", Toast.LENGTH_SHORT).show();
                } else {
                    checkReg(email, name, surname, place, password, subjects);
                }


                //Toast.makeText(getContext(),freeEmail.toString(),Toast.LENGTH_LONG).show();
                //Toast.makeText(getContext(), "CONFIRM INPUT RECEIVED", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    private void checkReg(String givenemail, final String name, final String surname, final String place, final String password, final String subjects) {

        final String percorsoReg = "insegnanti";
        final String email = SupportMethods.mailtoDB(givenemail);

        FirebaseDatabase.getInstance().getReference().child(percorsoReg).getRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String registered;
                        Iterable<DataSnapshot> insegnanti = dataSnapshot.getChildren();
                        for (DataSnapshot nodo : insegnanti){
                            registered=nodo.getKey();
                            assert registered != null;
                            if (registered.equals(email)){
                                viewEmail.setError("Esiste già un profilo legato a questa mail");
                                break;
                            }
                            if (!insegnanti.iterator().hasNext()){
                                ArrayList<TeacherItem> test = new ArrayList<>();
                                SupportMethods.registrazione(email, name, surname, place, password, subjects);

                                viewEmail.setError(null);
                                viewEmail.setErrorEnabled(false);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    private boolean checkEmail(String email) {
        if (email.isEmpty()){
            viewName.setError("Il campo non può essere lasciato vuoto");
            return false;
        } else {
            viewName.setError(null);
            viewName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean checkName(String name) {
        if (name.isEmpty()) {
            viewName.setError("Il campo non può essere lasciato vuoto");
            return false;
        } else {
            viewName.setError(null);
            viewName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean checkSurname(String surname) {
        if (surname.isEmpty()) {
            viewSurname.setError("Il campo non può essere lasciato vuoto");
            return false;
        } else {
            viewSurname.setError(null);
            viewSurname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean checkPassword(String password) {
        if (password.isEmpty()) {
            viewPassword.setError("Il campo non può essere lasciato vuoto");
            return false;
        } else {
            viewPassword.setError(null);
            viewPassword.setErrorEnabled(false);
            return true;
        }
    }

    private boolean checkSubjects(String subjects) {
        if (subjects.isEmpty()) {
            viewSubjects.setError("Il campo non può essere lasciato vuoto");
            return false;
        } else {
            viewSubjects.setError(null);
            viewSubjects.setErrorEnabled(false);
            return true;
        }
    }

    private boolean checkPlace(String place) {
        if (place.isEmpty()) {
            viewPlace.setError("Il campo non può essere lasciato vuoto");
            return false;
        } else {
            viewPlace.setError(null);
            viewPlace.setErrorEnabled(false);
            return true;
        }
    }

}