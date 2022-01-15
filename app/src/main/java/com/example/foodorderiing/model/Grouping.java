package com.example.foodorderiing.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "grouping")
public class Grouping {

    @PrimaryKey(autoGenerate = true)
    public int grouping_id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "picture")
    public String picture;

    public int picture2;


    public Grouping(int grouping_id, String name, String picture,  int picture2) {
        this.grouping_id = grouping_id;
        this.name = name;
        this.picture = picture;
        this.picture2 = picture2;
    }

    @Ignore
    public Grouping(String name, String picture) {
        this.name = name;
        this.picture = picture;
    }

    @Ignore
    public Grouping(String name, int picture2) {
        this.name = name;
        this.picture2 = picture2;
    }

}
