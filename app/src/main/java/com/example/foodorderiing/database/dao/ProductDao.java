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
public interface ProductDao {
    @Query("Select * from product")
    List<Product> getProductList();


    @Query("Select * from product where product_id = :id limit 1")
    List<Product> get(int id);


    @Query("Select * from product where category = :category" )
    List<Product> get_product_by_category(String category);


    @Query("Select count(*) from product where category = :category" )
   int count(String category);


    @Query("Select * from product where name = :name limit 1")
    Product getOneName(String name);



    @Insert
    void insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);

}
