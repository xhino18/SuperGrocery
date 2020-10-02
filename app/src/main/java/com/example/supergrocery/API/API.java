package com.example.supergrocery.API;

import com.example.supergrocery.Models.ModelCategories;
import com.example.supergrocery.Models.ModelDiscountedProducts;
import com.example.supergrocery.Models.ModelFreeDeliveryProducts;
import com.example.supergrocery.Models.ModelShopProducts;

import retrofit2.Call;
import retrofit2.http.GET;
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


    }

