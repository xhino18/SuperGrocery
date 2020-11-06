package com.example.supergrocery.models

data class ModelSendCode (
        val error: Boolean,
        val message: String,
        val user_id: Int
)