package com.example.ripetizionapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {

    private ArrayList<TeacherItem> teacherList;

    public static class TeacherViewHolder extends RecyclerView.ViewHolder {
        public ImageView teacherProfile;
        public TextView teacherName;
        public TextView teacherSubjects;

        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            teacherProfile = itemView.findViewById(R.id.imageview_profile);
            teacherName = itemView.findViewById(R.id.text_teacher_name);
            teacherSubjects = itemView.findViewById(R.id.text_teacher_subjects);
        }
    }

    public TeacherAdapter(ArrayList<TeacherItem> teacher) {
        teacherList = teacher;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_item, parent, false);
        TeacherViewHolder teacherViewHolder = new TeacherViewHolder(v);
        return teacherViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        TeacherItem currentItem = teacherList.get(position);
        holder.teacherProfile.setImageResource(currentItem.getTeacherProfile());
        holder.teacherName.setText(currentItem.getTeacherName());
        holder.teacherSubjects.setText(currentItem.getTeacherSubjects());
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }
}
