package com.example.supergrocery.API;

import com.example.supergrocery.Models.ModelCategories;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface API {
        String BASE_URL = "https://develop.almotech.co/digital_supplier/public/api/";


        @GET("categories")
        Call<ModelCategories> getCategories();


        /**@GET("category_products/{id}")
        Call<ModelProducts> getProducts(@Path("id") int id);
         **/

    }

