package com.example.supergrocery.GetModels

data class ShopProducts (val error:Boolean,
                         val message: String,
                         val data: List<ShopProductsData>
)