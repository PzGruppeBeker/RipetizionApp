package com.example.ripetizionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FragmentRegistration extends Fragment {

    private TextInputLayout viewEmail;
    private TextInputLayout viewName;
    private TextInputLayout viewSurname;
    private TextInputLayout viewPlace;
    private TextInputLayout viewPassword;
    private TextInputLayout viewSubjects;
    private TextInputLayout viewHours;

    public FragmentRegistration() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_registration, container, false);

        Button confirm = rootView.findViewById(R.id.button_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {

            //onClick verifica che tutti i campi inseriti siano conformi e che la mail non sia già occupata
            //in caso positivo viene creato il profilo sul database viene mostrato un toast di conferma

            @Override
            public void onClick(View v) {

                viewEmail = rootView.findViewById(R.id.text_input_email);
                viewName = rootView.findViewById(R.id.text_input_name);
                viewSurname = rootView.findViewById(R.id.text_input_surname);
                viewPlace = rootView.findViewById(R.id.text_input_place);
                viewPassword = rootView.findViewById(R.id.text_input_password);
                viewSubjects = rootView.findViewById(R.id.text_input_subject);
                viewHours = rootView.findViewById(R.id.text_input_hours);

                String email = viewEmail.getEditText().getText().toString().trim();
                String name = viewName.getEditText().getText().toString().trim();
                String surname = viewSurname.getEditText().getText().toString().trim();
                String place = viewPlace.getEditText().getText().toString().trim();
                String password = viewPassword.getEditText().getText().toString().trim();
                String subjects = viewSubjects.getEditText().getText().toString().trim();
                String hours = viewHours.getEditText().getText().toString().trim();

                if (!checkEmail(email) | !checkName(name) | !checkSurname(surname) | !checkPassword(password) | !checkSubjects(subjects) | !checkPlace(place) | !checkHours(hours)) {
                    Toast.makeText(getContext(), "Qualcosa non va, controlla che sia tutto in ordine!", Toast.LENGTH_SHORT).show();
                    SupportMethods.hideKeyboardFrom(getContext(), rootView);
                } else {
                    checkReg(email, name, surname, place, password, subjects, hours);
                    SupportMethods.hideKeyboardFrom(getContext(), rootView);
                }


            }
        });
        return rootView;
    }

    /**
     * Il metodo "checkReg" verifica che l'indirizzo e-mail indicato non sia già legato ad un account.
     * Se è libero procede ad invocare il metodo "registrazione" che eseguirà la registrazione su database.
     *
     * @param givenemail
     * @param name
     * @param surname
     * @param place
     * @param password
     * @param subjects
     * @param hours
     */
    private void checkReg(String givenemail, final String name, final String surname, final String place, final String password, final String subjects, final String hours) {

        final String percorsoReg = "insegnanti";
        final String email = SupportMethods.mailtoDB(givenemail);

        FirebaseDatabase.getInstance().getReference().child(percorsoReg).getRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String registered;
                        Iterable<DataSnapshot> insegnanti = dataSnapshot.getChildren();
                        for (DataSnapshot nodo : insegnanti) {
                            registered = nodo.getKey();
                            assert registered != null;
                            if (registered.equals(email)) {
                                viewEmail.setError("Esiste già un profilo legato a questa mail");
                                break;
                            }
                            if (!insegnanti.iterator().hasNext()) {
                                SupportMethods.registrazione(email, name, surname, place, hours, password, subjects);
                                Toast.makeText(getContext(), "Registrazione effettuata! Procedi al login per visualizzare il tuo profilo!", Toast.LENGTH_SHORT).show();

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

    //verifica che il campo email non sia vuoto

    private boolean checkEmail(String email) {
        if (email.isEmpty()) {
            viewName.setError("Il campo non può essere lasciato vuoto");
            return false;
        } else {
            viewName.setError(null);
            viewName.setErrorEnabled(false);
            return true;
        }
    }

    //verifica che il campo nome non sia vuoto

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

    //verifica che il campo cognome non sia vuoto

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

    //verifica che il campo password non sia vuoto

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

    //verifica che il campo materie non sia vuoto

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

    //verifica che il campo provincia non sia vuoto

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

    //verifica che il campo orari non sia vuoto

    private boolean checkHours(String hours) {
        if (hours.isEmpty()) {
            viewHours.setError("Il campo non può essere lasciato vuoto");
            return false;
        } else {
            viewHours.setError(null);
            viewHours.setErrorEnabled(false);
            return true;
        }
    }

}