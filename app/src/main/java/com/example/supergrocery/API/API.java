package com.example.supergrocery.API;

import com.example.supergrocery.GetModels.ModelCategories;
import com.example.supergrocery.GetModels.ModelDiscountedProducts;
import com.example.supergrocery.GetModels.ModelFreeDeliveryProducts;
import com.example.supergrocery.GetModels.ModelShopProducts;
import com.example.supergrocery.PostModels.ModelRegister;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface API {


        @GET("categories")
        Call<ModelCategories> getCategories();

        @GET("packages")
        Call<ModelDiscountedProducts> getDiscountedProducts();

        @GET("package_items/1")
        Call<ModelFreeDeliveryProducts> getFreeDeliveryProducts();


        @GET("products_by_category/{id}")
        Call<ModelShopProducts> getProducts(@Path("id") int id);

        @FormUrlEncoded
        @POST("register")
        Call<ModelRegister> register(@Field("name") String name,
                                     @Field("email") String email,
                                     @Field("phoneNumber") String phone,
                                     @Field("nuis") String nuis);



    }

