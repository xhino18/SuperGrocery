package com.example.supergrocery.ModelsPost

data class UserRegister(
        val error: Boolean,
        val message: String,
        val token: String,
        val data: UserRegisterData


)