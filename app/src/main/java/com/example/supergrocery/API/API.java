package com.example.supergrocery.API;

import com.example.supergrocery.Models.AllProductsData;
import com.example.supergrocery.Models.BannerData;
import com.example.supergrocery.Models.CategoriesData;
import com.example.supergrocery.Models.DiscountedProductsData;
import com.example.supergrocery.Models.ModelMain;
import com.example.supergrocery.Models.ModelMainToken;
import com.example.supergrocery.Models.ShopProductsData;
import com.example.supergrocery.Models.ModelSendCode;
import com.example.supergrocery.Models.UserRegisterData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface API {

        String NO_AUTHENTICATION_TRUE = "No-Authentication: true";
        String FIREBASE_TOKEN = "token"; // temporary placeholder
        short PLATFORM_ID = 2;



        @GET("categories")
        Call<ModelMain<List<CategoriesData>>> getCategories();

        @GET("banners")
        Call<ModelMain<List<BannerData>>> getBanners();

        @GET("packages")
        Call<ModelMain<List<DiscountedProductsData>>> getDiscountedProducts();

        @GET("products_by_category/13")
        Call<ModelMain<List<AllProductsData>>> getFreeDeliveryProducts();


        @GET("products_by_category/{id}")
        Call<ModelMain<List<ShopProductsData>>> getProducts(@Path("id") int id);


        @FormUrlEncoded
        @POST("register")
        @Headers(NO_AUTHENTICATION_TRUE)
        Call<ModelMainToken<UserRegisterData>> register(@Field("name") String name,
                                                        @Field("email") String email,
                                                        @Field("phone_number") String phoneNumber,
                                                        @Field("account_type") int accountType,
                                                        @Field("nuis") String nuis,
                                                        @Field("platform") short platform,
                                                        @Field("firebase_token") String firebaseToken);

        @FormUrlEncoded
        @POST("edit_profile")
        @Headers(NO_AUTHENTICATION_TRUE)
        Call<ModelMainToken<UserRegisterData>> editProfile(@Field("name") String name,
                                                           @Field("email") String email,
                                                           @Field("nuis") String nuis);


        @FormUrlEncoded
        @POST("send_code")
        Call<ModelSendCode> sendCode(@Field("phone") String phone);


        @FormUrlEncoded
        @POST("verify_code")
        @Headers(NO_AUTHENTICATION_TRUE)
        Call<ModelMainToken<UserRegisterData>> verifyCode(@Field("code") String code,
                                                          @Field("user_id") int userId);



    }

