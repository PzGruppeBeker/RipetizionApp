package com.example.ripetizionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentRecyclerViewTeacher extends Fragment {

    private RecyclerView rView;
    private TeacherAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView notfound;

    public FragmentRecyclerViewTeacher() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recyclerview_teacher, container, false);

        final String name = this.getArguments().getString("name");
        final String surname = this.getArguments().getString("surname");
        String place = this.getArguments().getString("place");
        final String subject = this.getArguments().getString("subject");

        FirebaseDatabase.getInstance().getReference().child("province").child(place.toLowerCase()).getRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> insegnanti = dataSnapshot.getChildren();
                        final ArrayList<Teacher> match = new ArrayList<>(); //Lista degli insegnanti che corrispondono alle caratteristiche.
                        if (name.isEmpty() & surname.isEmpty() & subject.isEmpty()) {
                            for (DataSnapshot nodo : insegnanti) {
                                Teacher t = nodo.getValue(Teacher.class);
                                match.add(t);
                            }
                        } else {
                            for (DataSnapshot nodo : insegnanti) {
                                Teacher t = nodo.getValue(Teacher.class);
                                if (SupportMethods.checkTeacher(t, name, surname, subject)) {
                                    match.add(t);
                                }
                            }
                        }

                        if (match.isEmpty()) {
                            notfound = rootView.findViewById(R.id.text_not_found);
                            notfound.setText("La ricerca non ha prodotto alcun risultato!");
                            // gestire eventualmente il backpressed

                        } else {
                            //test.add(new TeacherItem(R.drawable.ic_person_black_24dp, "Florestano Pizzaro", "Teologia, Fisica"));
                            //test.add(new TeacherItem(R.drawable.ic_person_black_24dp, "Professor Spannacchiatta", "Approssimativologia"));
                            //test.add(new TeacherItem(R.drawable.ic_person_black_24dp, "Gaetano Maria Barbagli", "Storia del Facismo"));

                            rView = rootView.findViewById(R.id.recyclerview_teacher);
                            rView.setHasFixedSize(true);
                            layoutManager = new LinearLayoutManager(getContext());
                            adapter = new TeacherAdapter(match);

                            rView.setLayoutManager(layoutManager);
                            rView.setAdapter(adapter);

                            adapter.setOnItemClickedListener(new TeacherAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    //Toast.makeText(getContext(), match.get(position).getNome() + match.get(position).getCognome(), Toast.LENGTH_SHORT).show();
                                    FragmentTeacher fragment = new FragmentTeacher();
                                    Bundle args = new Bundle();
                                    args.putString("name", match.get(position).getNome());
                                    args.putString("surname", match.get(position).getCognome());
                                    args.putString("place_1", match.get(position).getProvincia());
                                    args.putString("place_2", match.get(position).getLocalità());
                                    //args.putString("subject", match.get(position).getMaterie());
                                    //args.putString("email", match.get(position).getEmail());
                                    args.putInt("telephone", match.get(position).getTel());
                                    fragment.setArguments(args);

                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                                }
                            });
                        }
                        //aggiungere codice...
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        return rootView;
    }
}
