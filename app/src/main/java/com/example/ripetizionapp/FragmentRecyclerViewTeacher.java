package com.example.ripetizionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FragmentRecyclerViewTeacher extends Fragment {

    private RecyclerView rView;
    private TeacherAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<TeacherItem> test = new ArrayList<>();
    private TextView notfound;


    public FragmentRecyclerViewTeacher() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recyclerview_teacher, container, false);

        final String name = this.getArguments().getString("name");
        final String surname = this.getArguments().getString("surname");
        String place = this.getArguments().getString("place");
        final String subject = this.getArguments().getString("subject");

        //query

        FirebaseDatabase.getInstance().getReference().child("province").child(place).getRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> insegnanti = dataSnapshot.getChildren();
                        ArrayList<Teacher> match = new ArrayList<>(); //Lista degli insegnanti che corrispondono alle caratteristiche.
                        for (DataSnapshot nodo : insegnanti) {
                            Teacher t = nodo.getValue(Teacher.class);
                            if (SupportMethods.checkTeacher(t,name,surname,subject)){
                                match.add(t);
                            }
                        }

                        //aggiungere codice...

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        if (getArguments() == null) {
            notfound = rootView.findViewById(R.id.text_not_found);
            notfound.setText("La ricerca non ha prodotto alcun risultato!");
            // gestire eventualmente il backpressed

        } else {

            test.add(new TeacherItem(R.drawable.ic_person_black_24dp, "Florestano Pizzaro", "Teologia, Fisica"));
            test.add(new TeacherItem(R.drawable.ic_person_black_24dp, "Professor Spannacchiatta", "Approssimativologia"));
            test.add(new TeacherItem(R.drawable.ic_person_black_24dp, "Gaetano Maria Barbagli", "Storia del Facismo"));

            rView = rootView.findViewById(R.id.recyclerview_teacher);
            rView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getContext());
            adapter = new TeacherAdapter(test);

            rView.setLayoutManager(layoutManager);
            rView.setAdapter(adapter);

            adapter.setOnItemClickedListener(new TeacherAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(getContext(), test.get(position).getTeacherName(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        /**

        final String name="";
        final String surname="";
        String provincia="";
        final ArrayList<String> subjects = null;

        FirebaseDatabase.getInstance().getReference().child("province").child(provincia).getRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> insegnanti = dataSnapshot.getChildren();
                        ArrayList<Teacher> match = new ArrayList<>(); //Lista degli insegnanti che corrispondono alle caratteristiche.
                        for (DataSnapshot nodo : insegnanti) {
                            Teacher t = nodo.getValue(Teacher.class);
                            if (SupportMethods.checkTeacher(t,name,surname,subjects)){
                                match.add(t);
                            }
                        }

                        //aggiungere codice...

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
         */

        return rootView;
    }
}
