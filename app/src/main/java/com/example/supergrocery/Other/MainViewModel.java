package com.example.supergrocery.Other;

import android.content.SharedPreferences;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.Models.AllProductsData;
import com.example.supergrocery.Models.BannerData;
import com.example.supergrocery.Models.CategoriesData;
import com.example.supergrocery.Models.DiscountedProductsData;
import com.example.supergrocery.Models.ModelMain;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private SharedPreferences preferences;
    private API api;

    public MutableLiveData<ModelMain<List<CategoriesData>>> fragmentCategoriesLiveData = new MutableLiveData<>();
    public MutableLiveData<String> fragmentCategoriesErrorLiveData = new MutableLiveData<>();
    public MutableLiveData<ModelMain<List<CategoriesData>>> categoriesLiveData = new MutableLiveData<>();
    public MutableLiveData<String> categoriesErrorLiveData = new MutableLiveData<>();
    public MutableLiveData<ModelMain<List<BannerData>>> bannerLiveData = new MutableLiveData<>();
    public MutableLiveData<String> bannerErrorLiveData = new MutableLiveData<>();
    public MutableLiveData<ModelMain<List<AllProductsData>>> freeDeliveryLiveData = new MutableLiveData<>();
    public MutableLiveData<String> freeDeliveryErrorLiveData = new MutableLiveData<>();
    public MutableLiveData<ModelMain<List<DiscountedProductsData>>> discountedProductsLiveData = new MutableLiveData<>();
    public MutableLiveData<String> discountedProductsErrorLiveData = new MutableLiveData<>();



    @ViewModelInject
    public MainViewModel(SharedPreferences preferences, API api) {
        this.preferences = preferences;
        this.api = api;
    }

    public void getFragmentCategories() {
        Call<ModelMain<List<CategoriesData>>> call = api.getCategories();
        call.enqueue(new Callback<ModelMain<List<CategoriesData>>>() {
            @Override
            public void onResponse(Call<ModelMain<List<CategoriesData>>> call, Response<ModelMain<List<CategoriesData>>> response) {
                fragmentCategoriesLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ModelMain<List<CategoriesData>>> call, Throwable t) {
                fragmentCategoriesErrorLiveData.setValue(t.getMessage());
            }
        });
    }
    public void getCategories(String token) {
        API apiClient = ClientAPI.createAPI_With_Token(token);
        Call<ModelMain<List<CategoriesData>>> call = api.getCategories();
        call.enqueue(new Callback<ModelMain<List<CategoriesData>>>() {
            @Override
            public void onResponse(Call<ModelMain<List<CategoriesData>>> call, Response<ModelMain<List<CategoriesData>>> response) {
                categoriesLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ModelMain<List<CategoriesData>>> call, Throwable t) {
                categoriesErrorLiveData.setValue(t.getMessage());
            }
        });
    }
    public void getBanners(String token) {
        API apiClient = ClientAPI.createAPI_With_Token(token);
        Call<ModelMain<List<BannerData>>> call = api.getBanners();
        call.enqueue(new Callback<ModelMain<List<BannerData>>>() {
            @Override
            public void onResponse(Call<ModelMain<List<BannerData>>> call, Response<ModelMain<List<BannerData>>> response) {
                bannerLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ModelMain<List<BannerData>>> call, Throwable t) {
                bannerErrorLiveData.setValue(t.getMessage());
            }
        });
    }
    public void getFreeDeliveryProducts(String token) {
        API apiClient = ClientAPI.createAPI_With_Token(token);
        Call<ModelMain<List<AllProductsData>>> call = api.getFreeDeliveryProducts();
        call.enqueue(new Callback<ModelMain<List<AllProductsData>>>() {
            @Override
            public void onResponse(Call<ModelMain<List<AllProductsData>>> call, Response<ModelMain<List<AllProductsData>>> response) {
                freeDeliveryLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ModelMain<List<AllProductsData>>> call, Throwable t) {
                freeDeliveryErrorLiveData.setValue(t.getMessage());
            }
        });
    }
    public void getDiscountedProducts(String token) {
        API apiClient = ClientAPI.createAPI_With_Token(token);
        Call<ModelMain<List<DiscountedProductsData>>> call = api.getDiscountedProducts();
        call.enqueue(new Callback<ModelMain<List<DiscountedProductsData>>>() {
            @Override
            public void onResponse(Call<ModelMain<List<DiscountedProductsData>>> call, Response<ModelMain<List<DiscountedProductsData>>> response) {
                discountedProductsLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ModelMain<List<DiscountedProductsData>>> call, Throwable t) {
                discountedProductsErrorLiveData.setValue(t.getMessage());
            }
        });
    }
}
