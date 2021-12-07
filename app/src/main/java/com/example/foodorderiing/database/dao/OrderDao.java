package com.example.foodorderiing.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodorderiing.model.Order;
import com.example.foodorderiing.model.OrderDetail;
import com.example.foodorderiing.model.Product;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("Select * from ordering")
    List<Order> getOrderList();


    @Query("Select total from ordering where date = :datte limit 1 " )
   String date( String datte);


    @Query("Select total from ordering where date = :datee ")
    List<String> alldate( String datee);


    @Insert
    void insertOrder(Order order);

    @Update
    void updateOrder(Order order );

    @Delete
    void deleteOrder(Order order);

}
