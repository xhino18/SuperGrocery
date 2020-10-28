package com.example.supergrocery.ModelsPost

data class UserRegisterData (
        val id: Int,
        val name: String,
        val email: String,
        val nuis: String,
        val phone_number: String,
        val verified: Boolean,
        val minOrder: Int


)