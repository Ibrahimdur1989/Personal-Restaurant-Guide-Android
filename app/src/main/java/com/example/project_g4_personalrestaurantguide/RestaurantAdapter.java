package com.example.project_g4_personalrestaurantguide;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_g4_personalrestaurantguide.roomDb.Restaurant;
import com.example.project_g4_personalrestaurantguide.roomDb.RestaurantViewModel;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurantList = new ArrayList<>();

    private RestaurantViewModel viewModel;
    private Context context;


    public RestaurantAdapter(Context context, RestaurantViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    public void setRestaurants(List<Restaurant> restaurants){
        this.restaurantList = restaurants;
        notifyDataSetChanged();
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

        holder.name.setText(restaurant.name);
        holder.location.setText(restaurant.address);
        holder.tags.setText(restaurant.tags);
        holder.ratingBar.setRating(restaurant.rating);
        holder.imageView.setImageResource(R.drawable.img);

        // Initially hide delete button
        holder.deleteBtn.setVisibility(View.INVISIBLE);

        // Tap card -> open details/edit activity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RestaurantDetails.class);
            intent.putExtra("restaurant_id", restaurant.id);
            v.getContext().startActivity(intent);
        });

        // Long press - Show/hide delete button
        holder.itemView.setOnLongClickListener(v -> {
            if (holder.deleteBtn.getVisibility() == View.INVISIBLE) {
                holder.deleteBtn.setVisibility(View.VISIBLE);
            } else {
                holder.deleteBtn.setVisibility(View.INVISIBLE);
            }
            return true; //Consume long press
        });

        // Delete from DB
        holder.deleteBtn.setOnClickListener(v -> {
            viewModel.delete(restaurant);
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
