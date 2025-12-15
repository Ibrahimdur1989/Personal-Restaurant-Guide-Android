package com.example.project_g4_personalrestaurantguide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends BaseActivity implements OnMapReadyCallback {

    private double latitude;
    private double longitude;
    private String name;
    private Button btnGetDirections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        btnGetDirections = findViewById(R.id.btn_get_directions);

        // Toolbar
        // setupToolbar("Map Screen", true);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Map Screen");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        // Get values from Intent
        latitude = getIntent().getDoubleExtra("latitude", 0.0);
        longitude = getIntent().getDoubleExtra("longitude", 0.0);
        name = getIntent().getStringExtra("name");

        if (latitude == 0.0 && longitude == 0.0) {
            Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load map fragment
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Directions Button
        btnGetDirections.setOnClickListener(v -> {
            Uri uri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");

            try {
                startActivity(intent);
            } catch (Exception e) {
                Uri fallback = Uri.parse("geo:" + latitude + "," + longitude);
                startActivity(new Intent(Intent.ACTION_VIEW, fallback));
            }
        });
    }

    // Display map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng restaurantLocation = new LatLng(latitude, longitude);

        // Add marker at restaurant location
        googleMap.addMarker(new MarkerOptions()
                .position(restaurantLocation)
                .title(name != null ? name : "Restaurant"));

        // Move camera to restaurant
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, 17f));

        // disable auto-centering on user location
        googleMap.setOnMyLocationChangeListener(null);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
