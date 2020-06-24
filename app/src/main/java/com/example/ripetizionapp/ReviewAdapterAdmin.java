package com.example.ripetizionapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAdapterAdmin extends RecyclerView.Adapter<ReviewAdapterAdmin.ReviewViewHolder> {

    private ArrayList<String> reviewList;
    private OnItemClickListener reviewListener;

    public interface OnItemClickListener{
        void onDeleteClickReview(int position);

    }

    public void setOnItemClickedListener(OnItemClickListener listener) {
        reviewListener = listener;
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {

        public TextView review;
        public ImageView reviewDelete;

        public ReviewViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            review = itemView.findViewById(R.id.text_review_admin);
            reviewDelete = itemView.findViewById(R.id.image_view_delete_review);

            reviewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        listener.onDeleteClickReview(position);
                    }
                }
            });
        }
    }

    public ReviewAdapterAdmin(ArrayList<String> rList) {
        reviewList = rList;
    }

    @NonNull
    @Override
    public ReviewAdapterAdmin.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_delete, parent, false);
        ReviewViewHolder rvh = new ReviewViewHolder(v, reviewListener);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterAdmin.ReviewViewHolder holder, int position) {
        String currentItem = reviewList.get(position);
        holder.review.setText(currentItem);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}