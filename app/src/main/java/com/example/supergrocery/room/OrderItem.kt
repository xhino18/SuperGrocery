package com.example.supergrocery.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "OrderItems")
data class OrderItem(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: Int,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "url_image")
        val urlImage: String,
        @ColumnInfo(name = "price")
        val price: Int,
        @ColumnInfo(name = "quantity")
        var quantity: Int

)
{
        fun incrementQuantity(){
               quantity++ }

        fun reductionQuantity(){
                quantity-- }

}