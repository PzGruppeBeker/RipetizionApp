package com.example.ripetizionapp;

import android.content.Intent;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;

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


    public static void registrazione(String givenemail, String nome, String conome, String provincia, String password, String materie){

        String email = mailtoDB(givenemail);
        String materieLC = materie.toLowerCase();
        final String percorsoReg = "insegnanti"; //Percorso registrazione account.
        final String percorsoDati = "province"; //Percorso registrazione dati.

        //Creazione arraylist materie da stringa.
        ArrayList<String> listamaterie = new ArrayList<String>(Arrays.asList(materieLC.split("[ \n]")));

        //Creazione oggetti "rins" e "ins" rispettivamente per registrazione password account e dati.
        RegTeacher rins = new RegTeacher(password,provincia);
        Teacher ins = new Teacher(email,nome,conome,provincia,0000,listamaterie);

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

    public static void addReview(String givenEmail, String provincia, String recensione){
        String percorsoDati = "province"; //Percorso registrazione dati.
        String email = mailtoDB(givenEmail);
        String percorsoRecensioni = "recensioni";

        FirebaseDatabase.getInstance().getReference().child(percorsoDati).child(provincia.toLowerCase())
                .child(email).child(percorsoRecensioni).setValue(recensione);

    }

    public static void removeReview(String givenEmail, String provincia, String recensione, int nReview){
        String percorsoDati = "province"; //Percorso registrazione dati.
        String email = mailtoDB(givenEmail);
        String percorsoRecensioni = "recensioni";
        FirebaseDatabase.getInstance().getReference().child(percorsoDati).child(provincia.toLowerCase())
                .child(email).child(percorsoRecensioni).child(String.valueOf(nReview)).removeValue();
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

    public static void updateTeacher(String givenEmail, String givenNewEmail, String newPassword, final String newLocalità, final String newProvincia, final int newTel, final ArrayList<String> newMaterie){
        final String percorsoReg = "insegnanti"; //Percorso registrazione account.
        final String percorsoDati = "province"; //Percorso registrazione dati.
        final String email = SupportMethods.mailtoDB(givenEmail);
        String newMail = SupportMethods.mailtoDB(givenNewEmail);

        FirebaseDatabase.getInstance().getReference().child(percorsoReg).child(email).getRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        RegTeacher rt = dataSnapshot.getValue(RegTeacher.class);

                        FirebaseDatabase.getInstance().getReference().child(percorsoDati).child(rt.getProvincia().toLowerCase()).child(email).getRef()
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Teacher t = dataSnapshot.getValue(Teacher.class);

                                        if (!t.località.equals(newLocalità)){
                                            t.setLocalità(newLocalità);
                                        }
                                        if (!t.provincia.equals(newProvincia)){
                                            t.setProvincia(newProvincia);
                                        }
                                        if (t.tel!=newTel){
                                            t.setTel(newTel);
                                        }
                                        if (!t.getMaterie().equals(newMaterie)){
                                            t.setMaterie(newMaterie);
                                        }

                                        deleteTeacher(email);


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
}


