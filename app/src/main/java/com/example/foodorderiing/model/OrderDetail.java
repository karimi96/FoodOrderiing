package com.example.foodorderiing.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.w3c.dom.Text;

@Entity(tableName = "orderdetail")
public class OrderDetail {

    @PrimaryKey(autoGenerate = true)
    public int orderdetail_id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "price")
    public double price;

    @ColumnInfo(name = "amount")
    public int amount;

    @ColumnInfo(name = "code")
    public String code;


    public OrderDetail(int orderdetail_id, String name, String category, double price, int amount, String code) {
        this.orderdetail_id = orderdetail_id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.amount = amount;
        this.code = code;
    }

@Ignore
    public OrderDetail(String name, String category, double price, int amount, String code) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.amount = amount;
        this.code = code;
    }
}
