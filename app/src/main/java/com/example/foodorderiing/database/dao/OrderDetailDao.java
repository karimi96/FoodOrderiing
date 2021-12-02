package com.example.foodorderiing.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodorderiing.model.OrderDetail;
import com.example.foodorderiing.model.Product;

import java.util.List;

@Dao
public interface OrderDetailDao {
    @Query("Select * from orderdetail")
    List<OrderDetail> getOrderDetailList();


    @Query("Select * from orderdetail where code = :code")
    List<OrderDetail> getSpecificOrder(String code);


//    @Delete
//    void deleteOneObject(String code);
//

    @Insert
    void insertOrderDetail(OrderDetail orderDetail);

    @Update
    void updateOrderDetail(OrderDetail orderDetail);

    @Delete
    void deleteOrderDetail(OrderDetail orderDetail);

}
