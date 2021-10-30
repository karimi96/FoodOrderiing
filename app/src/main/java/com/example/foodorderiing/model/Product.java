package com.example.foodorderiing.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "product")
public class Product {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "city")
    public String city;


    public Product(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    @Ignore
    public Product(String name, String city) {
        this.name = name;
        this.city = city;
    }
}
