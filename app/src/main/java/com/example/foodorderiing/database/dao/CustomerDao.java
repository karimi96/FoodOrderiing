package com.example.foodorderiing.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodorderiing.model.Customer;
import com.example.foodorderiing.model.Product;

import java.util.List;

@Dao
public interface CustomerDao {
    @Query("Select * from customer")
    List<Customer> getCustomerList();

    @Insert
    void insertCustomer(Customer customer);
    @Update
    void updateCustomer(Customer customer);
    @Delete
    void deleteCustomer(Customer customer);

}
