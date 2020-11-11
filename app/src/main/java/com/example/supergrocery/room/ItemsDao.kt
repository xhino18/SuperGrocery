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

    @Query("UPDATE OrderItems SET quantity = quantity + 1 WHERE id = :id")
    suspend fun incrementQuantity(id: Int)

    @Query("UPDATE OrderItems SET quantity = quantity - 1 WHERE id = :id")
    suspend fun decrementQuantity(id: Int)

    @Query("SELECT COUNT(id) FROM OrderItems WHERE id = :id")
    suspend fun checkInBasket(id: Int): Int

    @Query("SELECT COUNT(quantity) FROM OrderItems WHERE id = :id")
    suspend fun checkQuantityBasket(id: Int): Int
}