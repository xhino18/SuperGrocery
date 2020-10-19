package com.example.supergrocery.GetModels

data class AllProducts (
        val error:Boolean,
        val message: String,
        val data: List<AllProductsData>

)