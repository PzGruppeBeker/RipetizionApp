package com.example.ripetizionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class FragmentRegistration extends Fragment {

    public FragmentRegistration() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_registration, container, false);
        return rootView;
    }

    public void confirmInput(View V) {
        Toast.makeText(getActivity(), "Prova input", Toast.LENGTH_SHORT).show();
    }
}
