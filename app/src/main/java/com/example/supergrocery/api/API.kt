package com.example.supergrocery.api

import com.example.supergrocery.models.*
import retrofit2.Call
import retrofit2.http.*

interface API {
    
    companion object {
        const val NO_AUTHENTICATION_TRUE = "No-Authentication: true"
        const val FIREBASE_TOKEN = "token" // temporary placeholder
    
        const val PLATFORM_ID: Short = 2
    }
    
    @GET("categories")
    suspend fun getCategories(): ModelMain<List<CategoriesData>>

    @GET("banners")
    suspend fun getBanners(): ModelMain<List<BannerData>>

    @GET("packages")
    suspend fun getDiscountedProducts(): ModelMain<List<DiscountedProductsData>>

    @GET("products_by_category/13")
    suspend fun getFreeDeliveryProducts(): ModelMain<List<AllProductsData>>


    @GET("products_by_category/{id}")
    suspend fun getProducts(@Path("id") id: Int): ModelMain<List<ShopProductsData>>

    @FormUrlEncoded
    @POST("register")
    @Headers(NO_AUTHENTICATION_TRUE)
    suspend fun register(@Field("name") name: String,
                 @Field("email") email: String,
                 @Field("phone_number") phoneNumber: String,
                 @Field("account_type") accountType: Int,
                 @Field("nuis") nuis: String,
                 @Field("platform") platform: Short,
                 @Field("firebase_token") firebaseToken: String): ModelMainToken<UserRegisterData>

    @FormUrlEncoded
    @POST("edit_profile")
    @Headers(NO_AUTHENTICATION_TRUE)
    suspend fun editProfile(@Field("name") name: String,
                    @Field("email") email: String,
                    @Field("nuis") nuis: String): ModelMainToken<UserRegisterData>


    @FormUrlEncoded
    @POST("send_code")
    suspend fun sendCode(@Field("phone") phone: String): ModelSendCode


    @FormUrlEncoded
    @POST("verify_code")
    @Headers(NO_AUTHENTICATION_TRUE)
    suspend fun verifyCode(@Field("code") code: String,
                   @Field("user_id") userId: Int): ModelMainToken<UserRegisterData>
}