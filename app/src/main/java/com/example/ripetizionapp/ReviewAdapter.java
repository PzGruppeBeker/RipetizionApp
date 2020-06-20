package com.example.ripetizionapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ArrayList<String> reviewList;

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {

        public TextView review;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            review = itemView.findViewById(R.id.text_review);
        }
    }

    public ReviewAdapter(ArrayList<String> rList) {
        reviewList = rList;
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        ReviewViewHolder rvh = new ReviewViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        String currentItem = reviewList.get(position);
        holder.review.setText(currentItem);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}