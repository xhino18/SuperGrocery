package com.example.supergrocery.PostModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MessageError (
       val error: Boolean,
       val message: String

)