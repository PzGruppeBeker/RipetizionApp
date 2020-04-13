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

    //public static boolean checkEmail(String givenemail) {
    //    if (givenemail.isEmpty()) {
    //        Viewemail.setError("Il campo non può essere lasciato vuoto");
    //    }
    //   final String percorsoReg = "insegnanti";
    //    final String email = mailtoDB(givenemail);

    //    DatabaseReference Ref = FirebaseDatabase.getInstance().getReference(percorsoReg);
    //    Query query = Ref.equalTo(email);

    //    if (query.getRef().toString().equals(email)){
    //        return false;
    //    } else {
    //        return true;
    //    }
    //}

    /**
    public static void checkEmail(String givenemail, final String nome, final String cognome, final String luogo, final String password, final String materie){

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
                    registrazione(email,nome,cognome,luogo,password,materie,Ref);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    */

    public static void registrazione(String givenemail, String nome, String conome, String luogo, String password, String materie){

        String email = mailtoDB(givenemail);
        final String percorsoReg = "insegnanti"; //Percorso registrazione account.
        final String percorsoDati = "luoghi"; //Percorso registrazione dati.

        //Creazione arraylist materie da stringa.
        ArrayList<String> listamaterie = new ArrayList<String>(Arrays.asList(materie.split(" ")));

        //Creazione oggetti "rins" e "ins" rispettivamente per registrazione password account e dati.
        RegTeacher rins = new RegTeacher(password,luogo);
        Teacher ins = new Teacher(nome,conome,luogo,0000,listamaterie);

        //Registrazione rins, usando percorso Reg.
        DatabaseReference regRef = FirebaseDatabase.getInstance().getReference(percorsoReg);
        regRef.child(email).setValue(rins);

        //Registrazione ins, serve creare nuovo percorso.
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference(percorsoDati).child(luogo);
        dataRef.child(email).setValue(ins);
    }
}


