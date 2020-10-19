package com.example.supergrocery.GetModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AllProductsData (
        val id: Int,
        val name: String,
        val image: String,
        val description: String,
        val price: Int,
        val stock: Int,
        val unit: String,
        val sizes: String,
        val minimum_order: Int,
        val maximum_order: Int,
        val discount: Int,
        val discounted_price: Int,
        val prices:List<AllProductsPricesData>

)