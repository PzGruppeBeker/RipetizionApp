package com.example.ripetizionapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentLogin extends Fragment {

    private TextInputLayout viewEmail;
    private TextInputLayout viewPassword;
    private Button login;
    private Switch switch1;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String SWITCH = "switch";

    private String EMAILSTORED;
    private String PASSWORDSTORED;
    private Boolean SWITCHSTORED;

    public FragmentLogin() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        login = rootView.findViewById(R.id.button_login);
        switch1 = rootView.findViewById(R.id.button_switch);
        viewEmail = rootView.findViewById(R.id.text_input_email_login);
        viewPassword = rootView.findViewById(R.id.text_input_password_login);

        loadData();
        updateViews();

        login.setOnClickListener(new View.OnClickListener() {

            //onClick verifica che email e password inserite corrispondano ad un utente registrato in precedenza (admin o professore)
            //e rende possibile l'accesso

            @Override
            public void onClick(View v) {

                final String email = viewEmail.getEditText().getText().toString().trim();
                final String password = viewPassword.getEditText().getText().toString().trim();

                if (!checkEmail(email) | !checkPassword(password)) {
                    Toast.makeText(getContext(), "Qualcosa non va!", Toast.LENGTH_SHORT).show();
                } else {
                    final String percorsoReg = "insegnanti"; //Percorso registrazione account.
                    final String percorsoDati = "province"; //Percorso registrazione dati.

                    FirebaseDatabase.getInstance().getReference().child(percorsoReg).getRef()
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Iterable<DataSnapshot> RegTeacherMail = dataSnapshot.getChildren();

                                    for (DataSnapshot t : RegTeacherMail) {
                                        if (email.equals(SupportMethods.mailfromDB(t.getKey()))) {
                                            final RegTeacher regTeacher = t.getValue(RegTeacher.class);
                                            if (regTeacher.getPassword().equals(password)) {
                                                if (regTeacher.getAdmin().equals("1")) {

                                                    FragmentSearch fragment = new FragmentSearch();
                                                    Bundle args = new Bundle();
                                                    args.putString("admin", "1");
                                                    fragment.setArguments(args);
                                                    SupportMethods.hideKeyboardFrom(getContext(), rootView);
                                                    if (switch1.isChecked()) {
                                                        saveData(email, password);
                                                    } else {
                                                        saveData("", "");
                                                    }
                                                    saveState();
                                                    Toast.makeText(getContext(), "ADMIN MODE", Toast.LENGTH_LONG).show();

                                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                                                    break;

                                                } else {

                                                    String Provincia = regTeacher.getProvincia().toLowerCase();

                                                    FirebaseDatabase.getInstance().getReference().child(percorsoDati).child(Provincia)
                                                            .child(t.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            Teacher teacher = dataSnapshot.getValue(Teacher.class);

                                                            FragmentTeacherLogin fragment = new FragmentTeacherLogin();
                                                            Bundle args = new Bundle();
                                                            args.putString("name", teacher.getNome());
                                                            args.putString("surname", teacher.getCognome());
                                                            args.putString("place_1", teacher.getProvincia());
                                                            args.putString("place_2", teacher.getLocalità());
                                                            args.putStringArrayList("subjects", teacher.getMaterie());
                                                            args.putString("email", teacher.getEmail());
                                                            args.putString("telephone", teacher.getTel());
                                                            args.putStringArrayList("reviews", teacher.getRecensioni());
                                                            args.putString("hours", teacher.getOrario());
                                                            args.putString("password", regTeacher.getPassword());

                                                            fragment.setArguments(args);

                                                            if (switch1.isChecked()) {
                                                                saveData(email, password);
                                                            } else {
                                                                saveData("", "");
                                                            }
                                                            saveState();
                                                            SupportMethods.hideKeyboardFrom(getContext(), rootView);

                                                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                                        }
                                                    });
                                                    break;
                                                }
                                            } else {
                                                Toast.makeText(getContext(), "Attenzione! La password del tuo account è sbagliata!", Toast.LENGTH_SHORT).show();
                                                break;
                                            }
                                        } else if (!RegTeacherMail.iterator().hasNext()) {
                                            Toast.makeText(getContext(), "Attenzione! Non esiste alcun account legato a questa email!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }


            }
        });

        return rootView;
    }

    //salvataggio di mail e password

    public void saveData(String mail, String psw) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL, mail);
        editor.putString(PASSWORD, psw);
        editor.apply();
    }

    //salvataggio della posizione dello switch per ricordare mail e password

    public void saveState() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SWITCH, switch1.isChecked());
        editor.apply();
    }

    //caricamento dei dati precedentemente salvati se ce ne sono

    public void loadData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        EMAILSTORED = sharedPreferences.getString(EMAIL, "");
        PASSWORDSTORED = sharedPreferences.getString(PASSWORD, "");
        SWITCHSTORED = sharedPreferences.getBoolean(SWITCH, false);
    }

    //update delle views edittext una volta recuperate eventuali mail e password salvate in precedenza

    public void updateViews() {
        viewEmail.getEditText().setText(EMAILSTORED);
        viewPassword.getEditText().setText(PASSWORDSTORED);
        switch1.setChecked(SWITCHSTORED);
    }

    //verifica che il campo mail non sia vuoto

    private boolean checkEmail(String email) {
        if (email.isEmpty()) {
            viewEmail.setError("Questo campo non può essere lasciato vuoto");
            return false;
        } else {
            viewEmail.setError(null);
            viewEmail.setErrorEnabled(false);
            return true;
        }
    }

    //verifica che il campo password non sia vuoto

    private boolean checkPassword(String password) {
        if (password.isEmpty()) {
            viewPassword.setError("Questo campo non può essere lasciato vuoto");
            return false;
        } else {
            viewPassword.setError(null);
            viewPassword.setErrorEnabled(false);
            return true;
        }
    }
}
