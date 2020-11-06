package com.example.supergrocery.models

data class LoginData (
        val id: Int,
        val name: String,
        val email: String,
        val phone_number: String,
        val remember_token: String,
        val is_agent: String,
        val nipt: String,
        val created_at: String,
        val updated_at: String

)