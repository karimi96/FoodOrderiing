package com.example.foodorderiing.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.foodorderiing.model.OrderDetail;
import java.util.List;

@Dao
public interface OrderDetailDao {
    @Query("Select * from orderdetail where code = :code")
    List<OrderDetail> getSpecificOrder(String code);


    @Query("DELETE from orderdetail where code = :code")
   void deleteOneOrderDetail(String code);


    @Insert
    void insertOrderDetail(OrderDetail orderDetail);

    @Update
    void updateOrderDetail(OrderDetail orderDetail);

    @Delete
    void deleteOrderDetail(OrderDetail orderDetail);

}
