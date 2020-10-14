package com.example.supergrocery.PostModels

import android.service.autofill.UserData
import com.example.supergrocery.GetModels.CategoriesData

data class User (
        val error:Boolean,
        val message: String,
        val data: List<UserData>


)