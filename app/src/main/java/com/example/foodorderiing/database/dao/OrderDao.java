package com.example.foodorderiing.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodorderiing.model.Order;
import com.example.foodorderiing.model.OrderDetail;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("Select * from ordering")
    List<Order> getOrderList();


    @Insert
    void insertOrder(Order order);

    @Update
    void updateOrder(Order order );

    @Delete
    void deleteOrder(Order order);

}
