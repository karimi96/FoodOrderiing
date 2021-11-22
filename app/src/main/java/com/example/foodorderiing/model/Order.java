package com.example.foodorderiing.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.w3c.dom.Text;

@Entity(tableName = "order")
public class Order {

    @PrimaryKey(autoGenerate = true)
    public int order_id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "unitcood")
    public String code;

    @ColumnInfo(name = "customerID")
    public int customerID;

    @ColumnInfo(name = "statusCustomer")
    public int statusCustomer;

    @ColumnInfo(name = "total")
    public  double total;

    @ColumnInfo(name = "describtion")
    public Text discrebtion;


    public Order(int order_id, String name, String code, int customerID, int statusCustomer, double total, Text discrebtion) {
        this.order_id = order_id;
        this.name = name;
        this.code = code;
        this.customerID = customerID;
        this.statusCustomer = statusCustomer;
        this.total = total;
        this.discrebtion = discrebtion;
    }

    @Ignore
    public Order(String name, String code, int customerID, int statusCustomer, double total, Text discrebtion) {
        this.name = name;
        this.code = code;
        this.customerID = customerID;
        this.statusCustomer = statusCustomer;
        this.total = total;
        this.discrebtion = discrebtion;
    }
}
