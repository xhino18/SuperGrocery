package com.example.supergrocery.ModelsGet

data class Login (
        val error:Boolean,
        val message: String,
        val token: String,
        val data: List<LoginData>

)