package com.example.foodorderiing.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodorderiing.model.Customer;
import com.example.foodorderiing.model.Grouping;
import com.example.foodorderiing.model.Product;

import java.util.List;

@Dao
public interface CustomerDao {
    @Query("Select * from customer")
    List<Customer> getCustomerList();


    @Query("Select * from customer where phone = :phone limit 1")
    Customer getCustomer(String phone);


    @Query("Select * from customer where customer_id = :id limit 1")
    Customer getID(int id);

    @Query("Select * from customer where customer_id = :id limit 1")
    Customer getOneName(int id);


    @Insert
    void insertCustomer(Customer customer);
    @Update
    void updateCustomer(Customer customer);
    @Delete
    void deleteCustomer(Customer customer);

}
