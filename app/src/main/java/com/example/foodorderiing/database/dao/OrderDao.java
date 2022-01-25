package com.example.foodorderiing.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.foodorderiing.model.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("Select * from ordering")
    List<Order> getOrderList();


    @Query("Select * from ordering order by date DESC")
    List<Order> getOrderListByDate();


    @Query("Select * from ordering group by total")
    List<Order> getOrderListByTotal();


    @Query("Select total from ordering where date = :datte limit 1 " )
   String date( String datte);

    @Query("Select total from ordering where date = :datee ")
    List<String> dailyTotal( String datee);

    @Query("Select * from ordering where name = :name ")
    List<Order> listByName( String name);

    @Query("select * from ordering where date >= :date")
    List<Order> getOrderListDate(String date);

    @Query("Select * from ordering where customerID = :id limit 1 " )
    Order getid( int id);

    @Query("DELETE from ordering where customerID = :id " )
    void deteteID(int id);

    @Query("Select date from ordering")
    List<String> getAllDate();

    @Query("Select total from ordering")
    List<String> getAllTotal();


    @Insert
    void insertOrder(Order order);

    @Update
    void updateOrder(Order order );

    @Delete
    void deleteOrder(Order order);

}
