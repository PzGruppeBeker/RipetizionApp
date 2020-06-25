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
    private TeacherAdapterAdmin adapterAdmin;
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

        String admin = "0";

        if (this.getArguments().getString("admin").equals("1")) {
            admin = "1";
        }

        final String finalAdmin = admin;

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

                        } else if (finalAdmin.equals("1")) {

                            //verificato il permesso admin si crea la lista di professori in cui ogni elemento è eliminabile

                            rView = rootView.findViewById(R.id.recyclerview_teacher);
                            rView.setHasFixedSize(true);
                            layoutManager = new LinearLayoutManager(getContext());
                            adapterAdmin = new TeacherAdapterAdmin(match);

                            rView.setLayoutManager(layoutManager);
                            rView.setAdapter(adapterAdmin);

                            adapterAdmin.setOnItemClickedListener(new TeacherAdapterAdmin.OnItemClickListener() {

                                //onItemClick per entrare nella pagina del professore

                                @Override
                                public void onItemClick(int position) {

                                    FragmentTeacher fragment = new FragmentTeacher();
                                    Bundle args = new Bundle();
                                    args.putString("name", match.get(position).getNome());
                                    args.putString("surname", match.get(position).getCognome());
                                    args.putString("place_1", match.get(position).getProvincia());
                                    args.putString("place_2", match.get(position).getLocalità());
                                    args.putStringArrayList("subjects", match.get(position).getMaterie());
                                    args.putString("email", match.get(position).getEmail());
                                    args.putString("telephone", match.get(position).getTel());
                                    args.putStringArrayList("reviews", match.get(position).getRecensioni());
                                    args.putString("hours", match.get(position).getOrario());
                                    args.putString("admin", "1");
                                    fragment.setArguments(args);

                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                                }

                                //onDeleteClick per cancellare il professore dalla lista e dal database

                                @Override
                                public void onDeleteClick(int position) {
                                    SupportMethods.deleteTeacher(match.get(position).getEmail());
                                    match.remove(position);
                                    adapterAdmin.notifyItemRemoved(position);
                                }
                            });

                        } else {

                            //verificato che non è un admin ad accedere si crea la lista dei professori

                            rView = rootView.findViewById(R.id.recyclerview_teacher);
                            rView.setHasFixedSize(true);
                            layoutManager = new LinearLayoutManager(getContext());
                            adapter = new TeacherAdapter(match);

                            rView.setLayoutManager(layoutManager);
                            rView.setAdapter(adapter);

                            adapter.setOnItemClickedListener(new TeacherAdapter.OnItemClickListener() {

                                //onItemClick per entrare nella pagina del professore

                                @Override
                                public void onItemClick(int position) {

                                    FragmentTeacher fragment = new FragmentTeacher();
                                    Bundle args = new Bundle();
                                    args.putString("name", match.get(position).getNome());
                                    args.putString("surname", match.get(position).getCognome());
                                    args.putString("place_1", match.get(position).getProvincia());
                                    args.putString("place_2", match.get(position).getLocalità());
                                    args.putStringArrayList("subjects", match.get(position).getMaterie());
                                    args.putString("email", match.get(position).getEmail());
                                    args.putString("telephone", match.get(position).getTel());
                                    args.putStringArrayList("reviews", match.get(position).getRecensioni());
                                    args.putString("hours", match.get(position).getOrario());
                                    args.putString("admin", "0");
                                    fragment.setArguments(args);

                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        return rootView;
    }

}
