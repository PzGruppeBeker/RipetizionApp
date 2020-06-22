package com.example.ripetizionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class FragmentLogin extends Fragment {

    private TextInputLayout viewEmail;
    private TextInputLayout viewPassword;

    public FragmentLogin() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        //verificare se è necessario cambiare l'id delle view in fragment_login, dato che i campi email
        //e password hanno lo stesso id di quelle in fragment_registration

        Button login = rootView.findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewEmail = rootView.findViewById(R.id.text_input_email_login);
                viewPassword = rootView.findViewById(R.id.text_input_password_login);

                final String email = viewEmail.getEditText().getText().toString().trim();
                final String password = viewPassword.getEditText().getText().toString().trim();
                //errore in rosso


                final String percorsoReg = "insegnanti"; //Percorso registrazione account.
                final String percorsoDati = "province"; //Percorso registrazione dati.

                FirebaseDatabase.getInstance().getReference().child(percorsoReg).getRef()
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Iterable <DataSnapshot> RegTeacherMail = dataSnapshot.getChildren();

                                for (DataSnapshot t : RegTeacherMail) {
                                    if (email.equals(SupportMethods.mailfromDB(t.getKey()))) {
                                        RegTeacher regTeacher = t.getValue(RegTeacher.class);
                                        if (regTeacher.getPassword().equals(password)){
                                            String Provincia = regTeacher.getProvincia();

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
                                                    args.putInt("telephone", teacher.getTel());
                                                    args.putStringArrayList("reviews", teacher.getRecensioni());
                                                    fragment.setArguments(args);

                                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                }
                                            });

                                        break;}
                                        else {
                                            Toast.makeText(getContext(), "Attenzione! La password del tuo account è sbagliata!", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                    }

                                    else if  (!RegTeacherMail.iterator().hasNext()){
                                        Toast.makeText(getContext(), "Attenzione! Non esiste alcun account legato a questa email!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

            }
        });

        return rootView;
    }
    
}
