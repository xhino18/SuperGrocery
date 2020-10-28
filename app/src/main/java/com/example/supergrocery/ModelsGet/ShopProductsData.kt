package com.example.supergrocery.ModelsGet

data class ShopProductsData (val id: Int,
                             val name: String,
                             val image: String,
                             val description: String,
                             val price: Int,
                             val stock: Int,
                             val unit: String?,
                             val sizes: String,
                             val minimum_order: Int,
                             val maximum_order: Int,
                             val discount: Int,
                             val discounted_price: Int,
                             val prices: List<Any>

)