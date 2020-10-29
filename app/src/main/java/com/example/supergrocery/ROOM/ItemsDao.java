package com.example.supergrocery.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemsDao {

    @Insert
    void insert(OrderItem items);

    @Update
    void update(OrderItem items);

    @Query("DELETE FROM OrderItems")
    void deleteAll();

    @Query("SELECT * FROM OrderItems ORDER BY id")
    List<OrderItem> getAllItems();

    @Delete
    void delete(OrderItem itemModel);

}
