package com.example.ripetizionapp;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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

    public static boolean checkEmail (final String email) {
        FirebaseFirestore ff = FirebaseFirestore.getInstance();
        DocumentReference docRef = ff.collection("insegnanti").document(email);
        if (docRef.get()==null)
    }
}
