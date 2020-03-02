package com.example.ripetizionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    /**
        //Ottieni riferimeno a database Firebase.

        FirebaseDatabase DB = FirebaseDatabase.getInstance();
        DatabaseReference Ref = DB.getReference();

        //Test aggiunta nodo.
        ArrayList<String> m = new ArrayList<>();
        String m1="mate";
        String m2="ita";
        m.add(m1);
        m.add(m2);
        Insegnante i = new Insegnante("Bo","Ma",m);
        Ref.child("Prof").setValue(i);
     */
    }
}
