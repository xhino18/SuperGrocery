package com.example.supergrocery.GetModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BannerData (

        val id: Int,
        val title: String,
        val image: String,
        val url: String,
        val product_id: Int,
        val start_date: String,
        val end_date: String

)
