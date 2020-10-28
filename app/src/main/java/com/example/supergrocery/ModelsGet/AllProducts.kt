package com.example.supergrocery.ModelsGet

data class AllProducts (
        val error:Boolean,
        val message: String,
        val data: List<AllProductsData>

)