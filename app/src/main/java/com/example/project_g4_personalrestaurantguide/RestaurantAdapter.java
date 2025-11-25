package com.example.project_g4_personalrestaurantguide;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurantList;
    private OnDeleteClickListener deleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public RestaurantAdapter(List<Restaurant> restaurantList, OnDeleteClickListener listener) {
        this.restaurantList = restaurantList;
        this.deleteClickListener = listener;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_item, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.name.setText(restaurant.getName());
        holder.location.setText(restaurant.getLocation());
        String[] tagsArray = restaurant.getTags();
        holder.tags.setText(TextUtils.join(", ", tagsArray));
        holder.ratingBar.setRating(restaurant.getRating());
        holder.imageView.setImageResource(restaurant.getImageResId());

        // Show delete button when user clicks on the card
        holder.itemView.setOnClickListener(v -> {
            if (holder.deleteBtn.getVisibility() == View.INVISIBLE) {
                holder.deleteBtn.setVisibility(View.VISIBLE);
            } else {
                holder.deleteBtn.setVisibility(View.INVISIBLE);
            }
        });

        holder.deleteBtn.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        TextView name, location, tags;
        ImageView imageView;
        RatingBar ratingBar;
        Button deleteBtn;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.restaurantName);
            location = itemView.findViewById(R.id.location);
            tags = itemView.findViewById(R.id.restaurantTags);
            imageView = itemView.findViewById(R.id.restaurantImage);
            ratingBar = itemView.findViewById(R.id.restaurantRating);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
