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

        String admin = "0";

        if (this.getArguments() != null) {
            admin = "1";
        }

        final View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        Button search = rootView.findViewById(R.id.button_search_database);

        final String finalAdmin = admin;
        search.setOnClickListener(new View.OnClickListener() {

            //onClick verifica che il campo provincia non sia vuoto e procede alla ricerca del professore
            //secondo i paramentri dati (ricordando il permesso admin se c'è)

            @Override
            public void onClick(View v) {

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

                    FragmentRecyclerViewTeacher fragment = new FragmentRecyclerViewTeacher();
                    Bundle args = new Bundle();
                    args.putString("name", name);
                    args.putString("surname", surname);
                    args.putString("place", place);
                    args.putString("subject", subject);

                    if (finalAdmin.equals("1")) {
                        args.putString("admin", "1");
                    } else {
                        args.putString("admin", "0");
                    }
                    SupportMethods.hideKeyboardFrom(getContext(), rootView);

                    fragment.setArguments(args);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                }
            }
        });

        return rootView;
    }

    //verifica che il campo provincia non sia vuoto

    private boolean checkPlace(String place) {
        if (place.isEmpty()) {
            viewPlace.setError("Questo campo non può essere lasciato vuoto");
            return false;
        } else {
            viewPlace.setError(null);
            viewPlace.setErrorEnabled(false);
            return true;
        }
    }
}
