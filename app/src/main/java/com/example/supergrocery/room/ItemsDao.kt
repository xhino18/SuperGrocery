package com.example.supergrocery.room

import androidx.room.*

@Dao
interface ItemsDao {
    @Insert
    suspend fun insert(items: OrderItem)

    @Update
    suspend fun update(items: OrderItem)

    @Query("DELETE FROM OrderItems")
    suspend fun deleteAll()

    @Query("SELECT * FROM OrderItems ORDER BY id")
    suspend fun getAllItems(): List<OrderItem>

    @Delete
    suspend fun delete(itemModel: OrderItem)
}