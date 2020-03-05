package com.example.ripetizionapp;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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

    public static void checkEmail(String givenemail, final String nome, final String conome, final String luogo, final String password, final String materie){

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
                    registrazione(email,nome,conome,luogo,password,materie,Ref);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private static void registrazione(String email, String nome, String conome, String luogo, String password, String materie, DatabaseReference percorsoReg){

        final String percorsoDati = "Luoghi";

        //Creazione arraylist materie da stringa.
        ArrayList<String> listamaterie = new ArrayList<String>(Arrays.asList(materie.split(" ")));

        //Creazione oggetti rins eins rispettivamente per registrazione password account e dati.
        RegInsegnante rins = new RegInsegnante(password,luogo);
        Insegnante ins = new Insegnante(nome,conome,luogo,0000,listamaterie);

        //Registrazione rins, utilizzando il percorso passato dal metodo di check.
        percorsoReg.child(email).setValue(rins);

        //Registrazione ins, serve creare nuovo percorso.
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference(percorsoDati).child(luogo);
        dataRef.child(email).setValue(ins);
    }

}


