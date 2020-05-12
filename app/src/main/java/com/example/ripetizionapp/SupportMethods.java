package com.example.ripetizionapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class SupportMethods {
    public static String mailtoDB (String s){
        String n = s.replace('.',':');
        return n;
    }
    public static String mailfromDB (String s){
        String n = s.replace(':','.');
        return n;
    }

    /**
    public static void checkEmail(String givenemail, final String nome, final String cognome, final String provincia, final String password, final String materie){

        final String percorsoReg = "insegnanti";
        final String email = mailtoDB(givenemail);
        final DatabaseReference Ref = FirebaseDatabase.getInstance().getReference(percorsoReg);

        Ref.addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(email)){
                    //la mail è occupata.
                }
                else {
                    //la mail è libera.
                    registrazione(email,nome,cognome,provincia,password,materie,Ref);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    */

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
}


