package com.example.foodorderiing.model;

import androidx.room.Ignore;

public class ChartModel {
    public String date;
    public double total;

    @Ignore
    public ChartModel(String date, double total) {
        this.date = date;
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
