package com.example.project_g4_personalrestaurantguide;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_g4_personalrestaurantguide.roomDb.Restaurant;
import com.example.project_g4_personalrestaurantguide.roomDb.RestaurantViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    private RecyclerView searchRecyclerView;
    private EditText searchEditText;

    private RestaurantAdapter adapter;
    private RestaurantViewModel viewModel;

    private final List<Restaurant> allRestaurants = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupToolbar("Search", true);

        searchRecyclerView = findViewById(R.id.searchRecyclerView);
        searchEditText = findViewById(R.id.searchEditText);

        viewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);
        adapter = new RestaurantAdapter(this, viewModel);

        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchRecyclerView.setAdapter(adapter);

        viewModel.getAllRestaurants().observe(this, restaurants -> {
            allRestaurants.clear();
            if (restaurants != null) {
                allRestaurants.addAll(restaurants);
            }

            filterRestaurants(searchEditText.getText().toString());
        });

        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterRestaurants(s.toString());
            }
        });
    }

    private void filterRestaurants(String query) {

        if (TextUtils.isEmpty(query)) {

            adapter.setRestaurants(new ArrayList<>(allRestaurants));
            return;
        }

        String lowerQuery = query.toLowerCase().trim();
        List<Restaurant> filtered = new ArrayList<>();

        for (Restaurant restaurant : allRestaurants) {
            if (restaurant == null) continue;

            String name = restaurant.name != null ? restaurant.name.toLowerCase() : "";
            String address = restaurant.address != null ? restaurant.address.toLowerCase(): "";
            String tags = restaurant.tags != null ? restaurant.tags.toLowerCase() : "";

            if (name.contains(lowerQuery)
                    || address.contains(lowerQuery)
                    || tags.contains(lowerQuery)) {
                   filtered.add(restaurant);

            }

        }

        adapter.setRestaurants(filtered);
    }
}