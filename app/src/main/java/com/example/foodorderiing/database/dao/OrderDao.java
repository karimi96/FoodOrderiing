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
    List<String> allTotal( String datee);

    @Query("Select * from ordering where name = :name ")
    List<Order> listByName( String name);

    @Query("select * from ordering where date>=datetime('now', 'now', '-7 day')")
    List<Order> getOrderListDate();

    @Query("Select * from ordering where customerID = :id limit 1 " )
    Order getid( int id);

    @Query("DELETE from ordering where customerID = :id " )
    void deteteID(int id);


//    @Query("select * from ordering where date>= datetimee('','','')")
//    List<Order> gett();
//
//    SELECT * FROM MemberInformation WHERE DateOfAd  BETWEEN datetime('now', '-30 days') AND datetime('now');
//
//
//    date(timestring, modifier, modifier, ...)
//
//    SELECT date('now','+14 day');

//    @Query("SELECT * FROM fare WHERE createdDateDb >=datetime('now', '-30 day')")



//    @Query("select * from User where created_date>=datetime('now', :duration)")
//    LiveData<List<User>> fetchUserByDuration(String duration);


    @Insert
    void insertOrder(Order order);

    @Update
    void updateOrder(Order order );

    @Delete
    void deleteOrder(Order order);

}
