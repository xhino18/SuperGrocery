package com.example.supergrocery.GetModels

data class Categories (
        val error:Boolean,
        val message: String,
        val data: List<CategoriesData>)