package com.example.ripetizionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class FragmentTeacherLogin extends Fragment {

    private TextInputLayout viewEmail;
    private TextInputLayout viewPassword;

    public FragmentTeacherLogin() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_teacher_login, container, false);

        Button confirm = rootView.findViewById(R.id.button_profile);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewEmail = rootView.findViewById(R.id.text_input_email_login);
                viewPassword = rootView.findViewById(R.id.text_input_password_login);

                String givenEmail = viewEmail.getEditText().getText().toString().trim();
                final String password = viewPassword.getEditText().getText().toString().trim();

                final String percorsoReg = "insegnanti"; //Percorso registrazione account.
                final String percorsoDati = "province"; //Percorso registrazione dati.
                final String email = SupportMethods.mailtoDB(givenEmail);

                FirebaseDatabase.getInstance().getReference().child(percorsoReg).getRef().
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final Iterable <DataSnapshot> RegTeacherMail = dataSnapshot.getChildren();

                                for (DataSnapshot t : RegTeacherMail) {
                                    if (SupportMethods.mailfromDB(Objects.requireNonNull(t.getKey())).equals(email)) {
                                        RegTeacher regTeacher = t.getValue(RegTeacher.class);
                                        if (regTeacher.getPassword().equals(password)){
                                            String Provincia = regTeacher.getProvincia();

                                            FirebaseDatabase.getInstance().getReference().child(percorsoDati).child(Provincia)
                                                    .child(t.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    Teacher teacher = dataSnapshot.getValue(Teacher.class);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                }
                                            });

                                        }
                                        else {
                                            Toast.makeText(getContext(), "Attenzione! La password del tuo account è sbagliata!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    if (!RegTeacherMail.iterator().hasNext()){
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
