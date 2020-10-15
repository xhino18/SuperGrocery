package com.example.supergrocery.API;

import com.example.supergrocery.GetModels.Categories;
import com.example.supergrocery.GetModels.DiscountedProducts;
import com.example.supergrocery.GetModels.FreeDeliveryProducts;
import com.example.supergrocery.GetModels.Login;
import com.example.supergrocery.GetModels.ShopProducts;
import com.example.supergrocery.PostModels.ModelRegister;
import com.example.supergrocery.PostModels.ModelSendCode;
import com.example.supergrocery.PostModels.UserRegister;

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
        @GET("banners")
        Call<DiscountedProducts> getBanners();

        @GET("packages")
        Call<DiscountedProducts> getDiscountedProducts();

        @GET("packages")
        Call<FreeDeliveryProducts> getFreeDeliveryProducts();


        @GET("products_by_category/{id}")
        Call<ShopProducts> getProducts(@Path("id") int id);

        @FormUrlEncoded
        @POST("register")
        Call<UserRegister> register(@Field("name") String name,
                                    @Field("email") String email,
                                    @Field("phone_number") String phoneNumber,
                                    @Field("account_type") int accountType,
                                    @Field("nuis") String nuis,
                                    @Field("platform") short platform,
                                    @Field("firebase_token") String firebaseToken);



        @FormUrlEncoded
        @POST("send_code")
        Call<ModelSendCode> sendCode(@Field("phone") String phone);


        @FormUrlEncoded
        @POST("verify_code")
        Call<ModelRegister> verifyCode(@Field("code") String code,
                              @Field("user_id") int userId);

        @FormUrlEncoded
        @POST("login")
        Call<Login> login(@Field("phone") String phone);


    }

