package com.example.ripetizionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    public FragmentRecyclerViewTeacher() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recyclerview_teacher, container, false);

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

        return rootView;
    }
}
