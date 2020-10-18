package com.example.supergrocery.GetModels

data class Banner (
        val error:Boolean,
        val message: String,
        val data: List<BannerData>

)