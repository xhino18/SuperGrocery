package com.example.supergrocery.PostModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserRegister(
        val error: Boolean,
        val message: String,
        val token: String,
        val data: UserRegisterData


)