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

        if (this.getArguments().getString("admin").equals("1")) {
            Toast.makeText(getContext(), "EDAIDAIDAI!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getContext(), "NIENTEDAFARE!", Toast.LENGTH_SHORT).show();
        }

        final View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        Button search = rootView.findViewById(R.id.button_search_database);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "SEARCH INPUT RECEIVED", Toast.LENGTH_SHORT).show();

                viewName = rootView.findViewById(R.id.text_input_name);
                viewSurname = rootView.findViewById(R.id.text_input_surname);
                viewPlace = rootView.findViewById(R.id.text_input_place);
                viewSubjects = rootView.findViewById(R.id.text_input_subject);

                String name = viewName.getEditText().getText().toString().trim();
                String surname = viewSurname.getEditText().getText().toString().trim();
                String place = viewPlace.getEditText().getText().toString().trim();
                String subject = viewSubjects.getEditText().getText().toString().trim();

                if (!checkPlace(place)) {
                    Toast.makeText(getContext(), "Qualcosa non va!", Toast.LENGTH_SHORT).show();
                } else {

                    //se getArguments() è null vuol dire che siamo studenti e cerchiamo il prof, se invece è pieno
                    //siamo admin quindi prendiamo questo valore e lo passiamo alla recycler view
                    //arrivato lì si decide come mostrare i prof, con o senza delete

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

    private boolean checkPlace(String place) {
        if (place.isEmpty()){
            viewPlace.setError("Questo campo non può essere lasciato vuoto");
            return false;
        } else {
            viewPlace.setError(null);
            viewPlace.setErrorEnabled(false);
            return true;
        }
    }
}
