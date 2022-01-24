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
public interface GroupingDao {
    @Query("Select * from grouping")
    List<Grouping> getGroupingList();

    @Query("Select name from grouping")
    List<String> getname();

    @Query("Select * from grouping where name = :name limit 1")
    Grouping getOneName(String name);



    @Insert
    void insertGrouping(Grouping grouping);

    @Update
    void updateGrouping(Grouping grouping );

    @Delete
    void deleteGrouping(Grouping grouping);

}
