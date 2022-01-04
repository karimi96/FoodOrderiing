package com.example.foodorderiing.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Locale;

@Entity(tableName = "product")
public class Product {

    @PrimaryKey(autoGenerate = true)
    public int product_id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "price")
    public String price;

    @ColumnInfo(name = "picture")
    public String picture;

    @Ignore
    public int amount = 1;


    public Product(int product_id, String name, String category, String price,  String picture ) {
        this.product_id = product_id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.picture = picture;
    }


    @Ignore
    public Product(String name, String category, String price,  String picture) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.picture = picture;
    }
}
