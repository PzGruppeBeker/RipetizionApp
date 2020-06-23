package com.example.ripetizionapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeacherAdapterAdmin extends RecyclerView.Adapter<TeacherAdapterAdmin.TeacherViewHolder> {

    private OnItemClickListener teacherListener;
    private ArrayList<Teacher> teacherList;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);

    }

    public void setOnItemClickedListener(OnItemClickListener listener) {
        teacherListener = listener;
    }

    public static class TeacherViewHolder extends RecyclerView.ViewHolder {
        public TextView teacherName;
        public TextView teacherSubjects;
        public ImageView teacherDelete;

        public TeacherViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            teacherName = itemView.findViewById(R.id.text_teacher_name_admin);
            teacherSubjects = itemView.findViewById(R.id.text_teacher_subjects_admin);
            teacherDelete = itemView.findViewById(R.id.imageview_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        listener.onItemClick(position);
                    }
                }
            });

            teacherDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        listener.onDeleteClick(position);
                    }

                }
            });
        }
    }

    public TeacherAdapterAdmin(ArrayList<Teacher> teacher) {
        teacherList = teacher;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_item_admin, parent, false);
        TeacherViewHolder teacherViewHolder = new TeacherViewHolder(v, teacherListener);
        return teacherViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        Teacher currentItem = teacherList.get(position);
        holder.teacherName.setText(currentItem.getNome() + " " + currentItem.getCognome());
        ArrayList<String> subjectsList  = currentItem.getMaterie();
        String subjects = SupportMethods.listToString(subjectsList);
        holder.teacherSubjects.setText(subjects);
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }
}
