package com.example.foodorderiing.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "grouping")
public class Grouping {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    public String name;
//    @ColumnInfo(name = "picture")
//    public String picture;


    public Grouping(int id, String name) {
        this.id = id;
        this.name = name;
//        this.picture = picture;
    }

    @Ignore
    public Grouping(String name) {
        this.name = name;
    }


}
