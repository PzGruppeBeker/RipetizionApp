package com.example.ripetizionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class FragmentSearch extends Fragment {

    public FragmentSearch() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        Button search = rootView.findViewById(R.id.button_search_database);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "SEARCH INPUT RECEIVED", Toast.LENGTH_SHORT).show();

                //al click creare la recycler view, tenendo conto che i valori possono essere nulli ma non tutti
                //e passare le informazioni al database tramite query, per poi creare un array  di oggetti
                //TeacherItem se possibile, e da lì visualizzarli in elenco
                //intanto creo un array di prova con nomi e immagine standard su FragmentRecyclerViewTeacher

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentRecyclerViewTeacher()).addToBackStack(null).commit();
            }
        });

        return rootView;
    }
}
