package com.example.foodorderiing.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.w3c.dom.Text;

@Entity(tableName = "ordering")
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
    public  String total;

    @ColumnInfo(name = "describtion")
    public String discrebtion;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "time")
    public String time;


    public Order(int order_id, String name, String code, int customerID, int statusCustomer, String total, String discrebtion , String date , String time ) {
        this.order_id = order_id;
        this.name = name;
        this.code = code;
        this.customerID = customerID;
        this.statusCustomer = statusCustomer;
        this.total = total;
        this.discrebtion = discrebtion;
        this.date = date;
        this.time = time;
    }

    @Ignore
    public Order(String name, String code, int customerID, int statusCustomer, String total, String discrebtion , String date , String time ) {
        this.name = name;
        this.code = code;
        this.customerID = customerID;
        this.statusCustomer = statusCustomer;
        this.total = total;
        this.discrebtion = discrebtion;
        this.date = date;
        this.time = time;

    }
}
