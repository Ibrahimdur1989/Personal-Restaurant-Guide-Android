package com.example.project_g4_personalrestaurantguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_g4_personalrestaurantguide.roomDb.RestaurantViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private RestaurantViewModel viewModel;
    private Button addRestaurantBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupToolbar("Personal Restaurant Guide", false);


        recyclerView = findViewById(R.id.restaurantRecyclerView);
        addRestaurantBtn = findViewById(R.id.addRestaurantBtn);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ViewModel
        viewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);

        // Adapter
        adapter = new RestaurantAdapter(this, viewModel);
        recyclerView.setAdapter(adapter);

        //Observe DB
        viewModel.getAllRestaurants().observe(this, restaurants -> {
            adapter.setRestaurants(restaurants);
        });

        addRestaurantBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, AddEditRestaurantActivity.class));
        });

    }

}