package com.example.supergrocery.ModelsGet

data class ShopProducts (val error:Boolean,
                         val message: String,
                         val data: List<ShopProductsData>
)