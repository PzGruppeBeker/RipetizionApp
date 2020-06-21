package com.example.ripetizionapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.tasks.Task;
import com.google.common.base.Optional;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.Objects;

import androidx.annotation.NonNull;

public class SupportMethods {

    public static String mailtoDB (String s){
        String n = s.replace('.',':');
        return n;
    }
    public static String mailfromDB (String s){
        String n = s.replace(':','.');
        return n;
    }


    public static void registrazione(String givenemail, String nome, String conome, String provincia, String orari, String password, String materie){

        String email = mailtoDB(givenemail);
        String materieLC = materie.toLowerCase();
        final String percorsoReg = "insegnanti"; //Percorso registrazione account.
        final String percorsoDati = "province"; //Percorso registrazione dati.

        //Creazione arraylist materie da stringa.
        ArrayList<String> listamaterie = new ArrayList<String>(Arrays.asList(materieLC.split("[ \n]")));

        //Creazione oggetti "rins" e "ins" rispettivamente per registrazione password account e dati.
        RegTeacher rins = new RegTeacher(password,provincia);
        Teacher ins = new Teacher(email,nome,conome, provincia, orari,0000,listamaterie);

        //Registrazione rins, usando percorso Reg.
        DatabaseReference regRef = FirebaseDatabase.getInstance().getReference(percorsoReg);
        regRef.child(email).setValue(rins);

        //Registrazione ins, serve creare nuovo percorso.
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference(percorsoDati).child(provincia.toLowerCase());
        dataRef.child(email).setValue(ins);
    }
    /**
     * The method "checkTeacher" is useful just when a student need to find a teacher,
     * NOT for check free-email.
     */
    public static boolean checkTeacher (Teacher t, String givenName, String givenSurname, String givenSubject){

        if (t.getNome().toLowerCase().equals(givenName.toLowerCase())){
            return true;
        }
        if (t.getCognome().toLowerCase().equals(givenSurname.toLowerCase())){
            return true;
        }

        ArrayList<String> subjects = t.materie;

        for (String sub : subjects){
            if (sub.equals(givenSubject)){
                return true;
            }
        }
        return false;
    }

    public static String listToString (ArrayList<String> l){
        StringBuilder subjects = new StringBuilder();
        for (int i = 0; i <= l.size() - 1; i++ ) {
            if (i == l.size() - 1) {
                subjects.append(l.get(i));
            } else {
                subjects.append(l.get(i)).append(", ");
            }
        }
        String s = subjects.toString();
        return s;
    }

    public static void addReview(final String givenEmail, final String provincia, final String recensione){
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
                            if (t.getKey().equals(email)){
                                teacher = t.getValue(Teacher.class);

                                FirebaseDatabase.getInstance().getReference().child(percorsoDati).child(provincia.toLowerCase())
                                        .child(SupportMethods.mailtoDB(givenEmail)).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.hasChild(percorsoRecensioni)){
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

    public static void removeReview(String givenEmail, String provincia, String recensione, int nReview){
        String percorsoDati = "province"; //Percorso registrazione dati.
        String email = mailtoDB(givenEmail);
        String percorsoRecensioni = "recensioni";
        FirebaseDatabase.getInstance().getReference().child(percorsoDati).child(provincia.toLowerCase())
                .child(email).child(percorsoRecensioni).child(String.valueOf(nReview)).removeValue();
    }

    public static void loginTeacher (final String givenEmail, final String givenPassword) {
        final String percorsoReg = "insegnanti"; //Percorso registrazione account.
        final String percorsoDati = "province"; //Percorso registrazione dati.


        FirebaseDatabase.getInstance().getReference().child(percorsoReg).getRef().
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final Iterable <DataSnapshot> RegTeacherMail = dataSnapshot.getChildren();

                        for (DataSnapshot t : RegTeacherMail) {
                            if (mailfromDB(Objects.requireNonNull(t.getKey())).equals(givenEmail)) {
                                RegTeacher regTeacher = t.getValue(RegTeacher.class);
                                if (regTeacher.getPassword().equals(givenPassword)){
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
                                    // Comunicare che la password inserita è errata.
                                }
                            }

                            if (!RegTeacherMail.iterator().hasNext()){
                                // Comunicare che la mail indicata non è registrata.
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public static void deleteTeacher (String givenEmail){
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

    //updateTeacher dev'essere terminato!!

    public static void updateTeacher(final String givenEmail, final String givenNewEmail, final String newPassword, final String newLocalità, final String newOrario, final String newProvincia, final int newTel, final ArrayList<String> newMaterie){
        final String percorsoReg = "insegnanti"; //Percorso registrazione account.
        final String percorsoDati = "province"; //Percorso registrazione dati.
        final String email = SupportMethods.mailtoDB(givenEmail);
        //String newMail = SupportMethods.mailtoDB(givenNewEmail);

        FirebaseDatabase.getInstance().getReference().child(percorsoReg).child(email).getRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final RegTeacher regTeacherMod = dataSnapshot.getValue(RegTeacher.class);
                        if (!newPassword.isEmpty() & !newPassword.equals(regTeacherMod.password)) {
                            regTeacherMod.setPassword(newPassword);
                        }
                        if (!newProvincia.isEmpty() & !newProvincia.equals(regTeacherMod.provincia)){
                            regTeacherMod.setProvincia(newProvincia);
                        }

                        FirebaseDatabase.getInstance().getReference().child(percorsoDati).child(regTeacherMod.getProvincia().toLowerCase()).child(email).getRef()
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        final Teacher t = dataSnapshot.getValue(Teacher.class);

                                        assert t != null;
                                        if (!newLocalità.isEmpty() & !t.località.equals(newLocalità)){
                                            t.setLocalità(newLocalità);
                                        }
                                        if (!newProvincia.isEmpty() & !t.provincia.equals(newProvincia)){
                                            t.setProvincia(newProvincia);
                                        }
                                        if (newTel!=000 & t.tel!=newTel){
                                            t.setTel(newTel);
                                        }
                                        if (!newOrario.isEmpty() & !t.orario.equals(newOrario)){
                                            t.setOrario(newOrario);
                                        }
                                        if (!newMaterie.isEmpty() & !t.getMaterie().equals(newMaterie)){
                                            t.setMaterie(newMaterie);
                                        }

                                        if (!givenNewEmail.isEmpty() & !t.getEmail().equals(givenNewEmail)) {
                                            FirebaseDatabase.getInstance().getReference().child(percorsoReg)
                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            Iterable<DataSnapshot> regTeacher = dataSnapshot.getChildren();

                                                            for (DataSnapshot rt : regTeacher) {
                                                                if (mailfromDB(rt.getKey()).equals(givenNewEmail)){
                                                                    //comunicare che la nuova mail è già utilizzata.
                                                                    return;
                                                                }
                                                            }
                                                            t.setEmail(givenNewEmail);

                                                            deleteTeacher(email);

                                                            FirebaseDatabase.getInstance().getReference().child(percorsoReg).child(mailtoDB(t.getEmail()))
                                                                    .setValue(regTeacherMod);

                                                            FirebaseDatabase.getInstance().getReference().child(percorsoDati).child(t.getProvincia())
                                                                    .child(mailtoDB(t.getEmail())).setValue(t);

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                        } else {
                                            deleteTeacher(email);

                                            FirebaseDatabase.getInstance().getReference().child(percorsoReg).child(mailtoDB(t.getEmail()))
                                                    .setValue(regTeacherMod);

                                            FirebaseDatabase.getInstance().getReference().child(percorsoDati).child(t.getProvincia())
                                                    .child(mailtoDB(t.getEmail())).setValue(t);
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


