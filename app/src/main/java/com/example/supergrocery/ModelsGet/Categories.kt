package com.example.supergrocery.ModelsGet

data class Categories (
        val error:Boolean,
        val message: String,
        val data: List<CategoriesData>)