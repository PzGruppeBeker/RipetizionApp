package com.example.ripetizionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentTeacher extends Fragment implements ReviewDialog.ReviewDialogListener {

    private String Remail = null;
    private String Rprovince = null;
    private TextView profileName;
    private TextView profileSurname;
    private TextView profilePlace1;
    private TextView profilePlace2;
    private TextView profileNumber;

    private RecyclerView rView;
    private ReviewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView notfound;

    public FragmentTeacher() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_teacher, container, false);

        final String name = this.getArguments().getString("name");
        final String surname = this.getArguments().getString("surname");
        final String place_1 = this.getArguments().getString("place_1");
        final String place_2 = this.getArguments().getString("place_2");
        final int telephone = this.getArguments().getInt("telephone");
        final String email = this.getArguments().getString("email");
        Remail = this.getArguments().getString("email");
        Rprovince = this.getArguments().getString("place_1");

        //final String subject = this.getArguments().getString("subject");

        profileName = rootView.findViewById(R.id.profile_name);
        profileName.setText(profileName.getText() + name);
        profileSurname = rootView.findViewById(R.id.profile_surname);
        profileSurname.setText(profileSurname.getText() + surname);
        profilePlace1 = rootView.findViewById(R.id.profile_place_1);
        profilePlace1.setText(profilePlace1.getText() + place_1);
        profilePlace2 = rootView.findViewById(R.id.profile_place_2);
        profilePlace2.setText(profilePlace2.getText() + place_2);
        profileNumber = rootView.findViewById(R.id.profile_number);
        profileNumber.setText(profileNumber.getText() + String.valueOf(telephone));

        Button add = rootView.findViewById(R.id.add_review_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });

        ArrayList<String> reviewList = new ArrayList<>();
        reviewList.add("Le lezioni di questo professore sono un tot avanti.");
        reviewList.add("L'abbigliamento del professore è piuttosto gay.");
        reviewList.add("Un ebreo e un irlandese entrano in una lavanderia cinese... Con un'anatra gay. Chi non sfotte in compagnia... Io penso che ce l'abbia piccolo.");

        rView = rootView.findViewById(R.id.recyclerview_review);
        rView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new ReviewAdapter(reviewList);

        rView.setLayoutManager(layoutManager);
        rView.setAdapter(adapter);

        //aggiungere mail e materie scrivendo un metodo di supporto che le renda stringhe
        //creare le risorse text in modo da poter aggiungere "nome: xxxxx"

        return rootView;
    }

    private void openDialog() {
        ReviewDialog revdial = new ReviewDialog();
        revdial.show(getActivity().getSupportFragmentManager(), "review");
    }


    //@Override
    public void addReview(String review) {

        String percorsoDati = "province"; //Percorso registrazione dati.
        String adaptedemail = SupportMethods.mailtoDB(Remail);
        String percorsoRecensioni = "recensioni";

        FirebaseDatabase.getInstance().getReference().child(percorsoDati).child(Rprovince.toLowerCase())
                .child(adaptedemail).child(percorsoRecensioni).setValue(review);


    }
}
