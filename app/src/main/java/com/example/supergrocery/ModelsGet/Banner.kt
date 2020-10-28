package com.example.supergrocery.ModelsGet

data class Banner (
        val error:Boolean,
        val message: String,
        val data: List<BannerData>

)