package com.example.foodorderiing.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodorderiing.database.dao.CustomerDao;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.database.dao.OrderDao;
import com.example.foodorderiing.database.dao.OrderDetailDao;
import com.example.foodorderiing.database.dao.ProductDao;
import com.example.foodorderiing.model.Customer;
import com.example.foodorderiing.model.Grouping;
import com.example.foodorderiing.model.Order;
import com.example.foodorderiing.model.OrderDetail;
import com.example.foodorderiing.model.Product;

@Database(entities = {Grouping.class, Product.class, Customer.class , Order.class , OrderDetail.class } , exportSchema = false , version = 1)
public abstract class DatabaseHelper extends RoomDatabase {
    private static final String DB_NAME = "db_name";
    private static DatabaseHelper instance;


    public static synchronized DatabaseHelper getInstance(Context context){
        if(instance ==null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    DatabaseHelper.class,
                    DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract ProductDao productDao();
    public abstract GroupingDao groupingDao();
    public abstract CustomerDao customerDao();
    public abstract OrderDao orderDao();
    public abstract OrderDetailDao orderDetailDao();

}
