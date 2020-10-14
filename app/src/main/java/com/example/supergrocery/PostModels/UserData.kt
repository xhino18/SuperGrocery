package com.example.supergrocery.PostModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserData (
        var id: Int,
        val name: String,
        val email: String,
        val nuis: String,
        val phone_number: String,
        val verified: Boolean,
        val minOrder: Int


)