package com.example.foodorderiing.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodorderiing.model.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("Select * from product")
    List<Product> getProductList();


    @Query("Select * from product where product_id = :id limit 1")
    List<Product> get(int id);


    @Query("Select * from product where category = :category limit 1" )
    List<Product> get_product_by_category(String category);


    @Insert
    void insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);

}
