package com.example.ripetizionapp;

import android.app.Activity;
import android.content.Context;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Arrays;


import androidx.annotation.NonNull;

public class SupportMethods {

    public static String mailtoDB(String s) {
        String n = s.replace('.', ':');
        return n;
    }

    public static String mailfromDB(String s) {
        String n = s.replace(':', '.');
        return n;
    }


    public static void registrazione(String givenemail, String nome, String conome, String provincia, String orari, String password, String materie) {

        String email = mailtoDB(givenemail);
        String materieLC = materie.toLowerCase();
        final String percorsoReg = "insegnanti"; //Percorso registrazione account.
        final String percorsoDati = "province"; //Percorso registrazione dati.

        //Creazione arraylist materie da stringa.
        ArrayList<String> listamaterie = new ArrayList<String>(Arrays.asList(materieLC.split("[,\n]")));

        //Creazione oggetti "rins" e "ins" rispettivamente per registrazione password account e dati.
        RegTeacher rins = new RegTeacher(password, provincia);
        Teacher ins = new Teacher(givenemail, nome, conome, provincia, orari, "0000", listamaterie);

        //Registrazione rins, usando percorso Reg.
        DatabaseReference regRef = FirebaseDatabase.getInstance().getReference(percorsoReg);
        regRef.child(email).setValue(rins);

        //Registrazione ins, serve creare nuovo percorso.
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference(percorsoDati).child(provincia.toLowerCase());
        dataRef.child(email).setValue(ins);
    }

    public static boolean checkTeacher(Teacher t, String givenName, String givenSurname, String givenSubject) {

        if (t.getNome().toLowerCase().equals(givenName.toLowerCase())) {
            return true;
        }
        if (t.getCognome().toLowerCase().equals(givenSurname.toLowerCase())) {
            return true;
        }

        ArrayList<String> subjects = t.materie;

        for (String sub : subjects) {
            if (sub.toLowerCase().equals(givenSubject.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static String listToString(ArrayList<String> l) {
        StringBuilder subjects = new StringBuilder();
        for (int i = 0; i <= l.size() - 1; i++) {
            if (i == l.size() - 1) {
                subjects.append(l.get(i));
            } else {
                subjects.append(l.get(i)).append(", ");
            }
        }
        String s = subjects.toString();
        return s;
    }

    public static String listToString2(ArrayList<String> l) {
        StringBuilder subjects = new StringBuilder();
        for (int i = 0; i <= l.size() - 1; i++) {
            if (i == l.size() - 1) {
                subjects.append(l.get(i));
            } else {
                subjects.append(l.get(i)).append("\n");
            }
        }
        String s = subjects.toString();
        return s;
    }

    public static void addReview(final String givenEmail, final String provincia, final String recensione) {
        final String percorsoDati = "province"; //Percorso registrazione dati.
        final String email = mailtoDB(givenEmail);
        final String percorsoRecensioni = "recensioni";

        FirebaseDatabase.getInstance().getReference().child(percorsoDati).child(provincia.toLowerCase())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final Teacher teacher;
                        Iterable<DataSnapshot> teachers = dataSnapshot.getChildren();
                        for (DataSnapshot t : teachers) {
                            if (t.getKey().equals(email)) {
                                teacher = t.getValue(Teacher.class);

                                FirebaseDatabase.getInstance().getReference().child(percorsoDati).child(provincia.toLowerCase())
                                        .child(SupportMethods.mailtoDB(givenEmail)).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.hasChild(percorsoRecensioni)) {
                                            ArrayList<String> recensioni = teacher.getRecensioni();
                                            recensioni.add(recensione);
                                            teacher.setRecensioni(recensioni);
                                            FirebaseDatabase.getInstance().getReference().child(percorsoDati).child(provincia.toLowerCase())
                                                    .child(email).setValue(teacher);
                                        } else {
                                            ArrayList<String> recensioni = new ArrayList<>();
                                            recensioni.add(recensione);
                                            teacher.setRecensioni(recensioni);
                                            FirebaseDatabase.getInstance().getReference().child(percorsoDati).child(provincia.toLowerCase())
                                                    .child(email).setValue(teacher);

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    public static void removeReview(String givenEmail, String givenProvincia, final int reviewPosition) {
        final String percorsoDati = "province"; //Percorso registrazione dati.
        final String provincia = givenProvincia.toLowerCase();
        final String email = mailtoDB(givenEmail);

        FirebaseDatabase.getInstance().getReference().child(percorsoDati).child(provincia)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> teachers = dataSnapshot.getChildren();
                        for (DataSnapshot t : teachers) {
                            if (t.getKey().equals(email)) {
                                Teacher teacher = t.getValue(Teacher.class);
                                teacher.deleteRecensione(reviewPosition);
                                FirebaseDatabase.getInstance().getReference().child(percorsoDati).child(provincia)
                                        .child(email).setValue(teacher);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    public static void deleteTeacher(String givenEmail) {
        final String percorsoReg = "insegnanti"; //Percorso registrazione account.
        final String percorsoDati = "province"; //Percorso registrazione dati.
        final String email = mailtoDB(givenEmail);

        FirebaseDatabase.getInstance().getReference().child(percorsoReg).child(email).getRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        RegTeacher rt = dataSnapshot.getValue(RegTeacher.class);

                        FirebaseDatabase.getInstance().getReference(percorsoDati).child(rt.provincia).child(email)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        dataSnapshot.getRef().removeValue();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                        FirebaseDatabase.getInstance().getReference().child(percorsoReg).child(email).removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    public static void updateTeacher(final String email, final String newEmail, final String newLocalita, final String newProvincia, final String newTel, final ArrayList<String> newMaterie, final String newPassword, final String newOrario) {
        final String percorsoReg, percorsoDati;
        percorsoReg = "insegnanti";
        percorsoDati = "province";

        FirebaseDatabase.getInstance().getReference().child(percorsoReg).child(mailtoDB(email))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final RegTeacher regTeacher = dataSnapshot.getValue(RegTeacher.class);
                        final String oldProvincia = regTeacher.getProvincia();

                        FirebaseDatabase.getInstance().getReference().child(percorsoDati).child(regTeacher.getProvincia().toLowerCase())
                                .child(mailtoDB(email)).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final Teacher teacher = dataSnapshot.getValue(Teacher.class);

                                if (!newLocalita.isEmpty()) {
                                    teacher.setLocalità(newLocalita);
                                }

                                if (!newTel.isEmpty()) {
                                    teacher.setTel(newTel);
                                }

                                if (!newProvincia.isEmpty()) {
                                    teacher.setProvincia(newProvincia);
                                    regTeacher.setProvincia(newProvincia);
                                }

                                if (!newPassword.isEmpty()) {
                                    regTeacher.setPassword(newPassword);
                                }

                                if (!newOrario.isEmpty()) {
                                    teacher.setOrario(newOrario);
                                }

                                if (!newMaterie.isEmpty()) {
                                    teacher.setMaterie(newMaterie);
                                }

                                if (!newEmail.isEmpty() & !newEmail.equals(teacher.getEmail())) {

                                    FirebaseDatabase.getInstance().getReference().child(percorsoReg)
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if (!dataSnapshot.hasChild(mailtoDB(newEmail))) {

                                                        // La nuova mail è libera.

                                                        FirebaseDatabase.getInstance().getReference().child(percorsoReg)
                                                                .child(mailtoDB(email)).removeValue();

                                                        FirebaseDatabase.getInstance().getReference().child(percorsoDati)
                                                                .child(oldProvincia).child(mailtoDB(email)).removeValue();

                                                        teacher.setEmail(newEmail);

                                                        FirebaseDatabase.getInstance().getReference().child(percorsoReg)
                                                                .child(mailtoDB(newEmail)).setValue(regTeacher);

                                                        FirebaseDatabase.getInstance().getReference().child(percorsoDati)
                                                                .child(teacher.getProvincia().toLowerCase()).child(mailtoDB(newEmail))
                                                                .setValue(teacher);


                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                } else {

                                    FirebaseDatabase.getInstance().getReference().child(percorsoReg)
                                            .child(mailtoDB(email)).removeValue();

                                    FirebaseDatabase.getInstance().getReference().child(percorsoDati)
                                            .child(oldProvincia).child(mailtoDB(email)).removeValue();

                                    FirebaseDatabase.getInstance().getReference().child(percorsoReg)
                                            .child(mailtoDB(email)).setValue(regTeacher);

                                    FirebaseDatabase.getInstance().getReference().child(percorsoDati)
                                            .child(teacher.getProvincia().toLowerCase()).child(mailtoDB(email))
                                            .setValue(teacher);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}


