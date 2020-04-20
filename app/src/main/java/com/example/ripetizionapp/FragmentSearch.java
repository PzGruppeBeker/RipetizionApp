package com.example.ripetizionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputLayout;

public class FragmentSearch extends Fragment {

    private TextInputLayout viewName;
    private TextInputLayout viewSurname;
    private TextInputLayout viewPlace;
    private TextInputLayout viewSubjects;

    public FragmentSearch() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        Button search = rootView.findViewById(R.id.button_search_database);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "SEARCH INPUT RECEIVED", Toast.LENGTH_SHORT).show();

                //al click creare la recycler view, tenendo conto che i valori possono essere nulli ma non tutti
                //e passare le informazioni al database tramite query, per poi creare un array  di oggetti
                //TeacherItem se possibile, e da lì visualizzarli in elenco
                //intanto creo un array di prova con nomi e immagine standard su FragmentRecyclerViewTeacher

                viewName = rootView.findViewById(R.id.text_input_name);
                viewSurname = rootView.findViewById(R.id.text_input_surname);
                viewPlace = rootView.findViewById(R.id.text_input_place);
                viewSubjects = rootView.findViewById(R.id.text_input_subject);

                String name = viewName.getEditText().getText().toString().trim();
                String surname = viewSurname.getEditText().getText().toString().trim();
                String place = viewPlace.getEditText().getText().toString().trim();
                String subject = viewSubjects.getEditText().getText().toString().trim();

                if (name.isEmpty() && surname.isEmpty() && subject.isEmpty() && place.isEmpty()) {
                    Toast.makeText(getContext(), "Almeno un campo deve contenere delle informazioni di ricerca!", Toast.LENGTH_SHORT).show();
                } else {
                    FragmentRecyclerViewTeacher fragment = new FragmentRecyclerViewTeacher();
                    Bundle args = new Bundle();
                    args.putString("name", name);
                    args.putString("surname", surname);
                    args.putString("place", place);
                    args.putString("subject", subject);
                    fragment.setArguments(args);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                }
            }
        });

        return rootView;
    }
}
