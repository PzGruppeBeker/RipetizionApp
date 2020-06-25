package com.example.ripetizionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class FragmentTeacher extends Fragment {

    private TextView profileName;
    private TextView profileSurname;
    private TextView profilePlace1;
    private TextView profilePlace2;
    private TextView profileNumber;
    private TextView profileMail;
    private TextView profileSubjects;
    private TextView profileHours;

    private TextInputLayout editreview;

    private RecyclerView rView;
    private ReviewAdapter adapter;
    private ReviewAdapterAdmin adapterAdmin;
    private RecyclerView.LayoutManager layoutManager;
    private TextView notfound;

    public FragmentTeacher() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_teacher, container, false);

        final String name = this.getArguments().getString("name");
        final String surname = this.getArguments().getString("surname");
        final String place_1 = this.getArguments().getString("place_1");
        final String place_2 = this.getArguments().getString("place_2");
        final String telephone = this.getArguments().getString("telephone");
        final String email = this.getArguments().getString("email");
        final String hours = this.getArguments().getString("hours");
        final ArrayList subjectsList = this.getArguments().getStringArrayList("subjects");
        final ArrayList reviewsList = this.getArguments().getStringArrayList("reviews");

        String admin = "0";

        if (this.getArguments().getString("admin").equals("1")) {
            admin = "1";
        }

        final String finalAdmin = admin;

        profileName = rootView.findViewById(R.id.profile_name_view);
        profileName.setText(name);
        profileSurname = rootView.findViewById(R.id.profile_surname_view);
        profileSurname.setText(surname);
        profilePlace1 = rootView.findViewById(R.id.profile_place_1_view);
        profilePlace1.setText(place_1);
        profilePlace2 = rootView.findViewById(R.id.profile_place_2_view);
        profilePlace2.setText(place_2);
        profileNumber = rootView.findViewById(R.id.profile_number_view);
        profileNumber.setText(telephone);
        profileMail = rootView.findViewById(R.id.profile_email_view);
        profileMail.setText(email);
        profileHours = rootView.findViewById(R.id.profile_hours_view);
        profileHours.setText(hours);


        final String subjects = SupportMethods.listToString2(subjectsList);
        profileSubjects = rootView.findViewById(R.id.profile_subjects_view);
        profileSubjects.setText(subjects);

        Button add = rootView.findViewById(R.id.add_review_button);
        add.setOnClickListener(new View.OnClickListener() {

            //onClick usato per pubblicare una recensione relativa alla pagina del professore
            //ma gli admin non possono farlo

            @Override
            public void onClick(View v) {
                editreview = rootView.findViewById(R.id.text_input_review);
                String review = editreview.getEditText().getText().toString();

                if (!review.isEmpty() & finalAdmin.equals("0")) {
                    SupportMethods.addReview(email, place_1, review);
                    editreview.getEditText().setText("");
                    SupportMethods.hideKeyboardFrom(getContext(), rootView);
                    Toast.makeText(getContext(), "Grazie per il tuo tempo, la tua recensione sarà visibile al più presto!", Toast.LENGTH_SHORT).show();
                    editreview.setError(null);
                    editreview.setErrorEnabled(false);
                } else if (!review.isEmpty() & finalAdmin.equals("1")) {
                    Toast.makeText(getContext(), "Non è possibile lasciare recensioni da amministratore", Toast.LENGTH_SHORT).show();
                } else {
                    editreview.setError("Questo campo non può essere lasciato vuoto");
                }
            }
        });

        if (reviewsList == null) {
            notfound = rootView.findViewById(R.id.text_not_found);
            notfound.setText("Nessuna recensione");

        } else if (finalAdmin.equals("1")) {

            //verificato il permesso admin si crea la lista delle recensioni cancellabili

            rView = rootView.findViewById(R.id.recyclerview_review);
            rView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getContext());
            adapterAdmin = new ReviewAdapterAdmin(reviewsList);

            rView.setLayoutManager(layoutManager);
            rView.setAdapter(adapterAdmin);

            adapterAdmin.setOnItemClickedListener(new ReviewAdapterAdmin.OnItemClickListener() {

                //onDeleteClick cancella la recensione dalla lista e dal database

                @Override
                public void onDeleteClickReview(int position) {
                    SupportMethods.removeReview(email, place_1, position);
                    reviewsList.remove(position);
                    adapterAdmin.notifyItemRemoved(position);
                }
            });

        } else {

            //verificato che si tratta di un utente si crea la lista delle recensioni

            rView = rootView.findViewById(R.id.recyclerview_review);
            rView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getContext());
            adapter = new ReviewAdapter(reviewsList);

            rView.setLayoutManager(layoutManager);
            rView.setAdapter(adapter);
        }

        return rootView;
    }

}