package com.example.supergrocery.Models

open class ModelMain<T>(
        val error: Boolean=true,
        val message: String?=null,
        val data: T?=null
)