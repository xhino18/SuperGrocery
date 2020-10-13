package com.example.supergrocery.API;

import com.example.supergrocery.GetModels.Categories;
import com.example.supergrocery.GetModels.DiscountedProducts;
import com.example.supergrocery.GetModels.FreeDeliveryProducts;
import com.example.supergrocery.GetModels.Login;
import com.example.supergrocery.GetModels.ModelCategories;
import com.example.supergrocery.GetModels.ModelDiscountedProducts;
import com.example.supergrocery.GetModels.ModelFreeDeliveryProducts;
import com.example.supergrocery.GetModels.ModelShopProducts;
import com.example.supergrocery.GetModels.ShopProducts;
import com.example.supergrocery.PostModels.ModelRegister;
import com.example.supergrocery.PostModels.ModelSendCode;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface API {
        String FIREBASE_TOKEN = "token"; // temporary placeholder
        short PLATFORM_ID = 2;



        @GET("categories")
        Call<Categories> getCategories();

        @GET("packages")
        Call<DiscountedProducts> getDiscountedProducts();

        @GET("package_items/1")
        Call<FreeDeliveryProducts> getFreeDeliveryProducts();


        @GET("products_by_category/{id}")
        Call<ShopProducts> getProducts(@Path("id") int id);

        @FormUrlEncoded
        @POST("register")
        Call<ModelRegister> register(@Field("name") String name,
                                     @Field("email") String email,
                                     @Field("phone_number") String phoneNumber,
                                     @Field("account_type") int accountType,
                                     @Field("nuis") String nuis,
                                     @Field("platform") short platform,
                                     @Field("firebase_token") String firebaseToken);



        @FormUrlEncoded
        @POST
        Call<ModelSendCode> sendCode(@Field("phone") String phone);


        @FormUrlEncoded
        @POST
        Call<ModelSendCode> verifyCode(@Field("code") String code);

        @FormUrlEncoded
        @POST("login")
        Call<Login> login(@Field("phone") String phone);


    }

