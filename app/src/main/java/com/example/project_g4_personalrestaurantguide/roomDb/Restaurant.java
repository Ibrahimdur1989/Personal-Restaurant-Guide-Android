package com.example.project_g4_personalrestaurantguide.roomDb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "restaurants")
public class Restaurant {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String address;
    public String phone;
    public String description;

    // Comma separated tags: "vegan, italian, organic"
    public String tags;

    public float rating; // 1-5 stars
    public double latitude;
    public double longitude;

}
