package com.example.supergrocery.PostModels

import android.service.autofill.UserData
import com.example.supergrocery.GetModels.CategoriesData

data class UserRegister (
        val error:Boolean,
        val message: String,
        val data: UserRegisterData


)