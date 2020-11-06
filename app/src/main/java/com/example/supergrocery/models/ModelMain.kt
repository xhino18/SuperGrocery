package com.example.supergrocery.models

open class ModelMain<T>(
        val error: Boolean=true,
        val message: String?=null,
        val data: T?=null
)