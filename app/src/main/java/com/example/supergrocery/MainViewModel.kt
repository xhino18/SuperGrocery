package com.example.supergrocery

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supergrocery.api.API
import com.example.supergrocery.models.*
import com.example.supergrocery.room.ItemsDao
import com.example.supergrocery.room.OrderItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel @ViewModelInject constructor(
        val preferences: SharedPreferences,
        val api: API,
        private val itemsDao: ItemsDao
) : ViewModel() {

    val categoriesLiveData = MutableLiveData<ModelMain<List<CategoriesData>>>()
    val bannerLiveData = MutableLiveData<ModelMain<List<BannerData>>>()
    val freeDeliveryLiveData = MutableLiveData<ModelMain<List<AllProductsData>>>()
    val discountedProductsLiveData = MutableLiveData<ModelMain<List<DiscountedProductsData>>>()
    val orderItemLiveData = MutableLiveData<List<OrderItem>>()

    fun getCategories() {
        viewModelScope.launch {
            categoriesLiveData.value = api.getCategories()
        }
    }

    fun getBanners() {
        viewModelScope.launch {
            bannerLiveData.value = api.getBanners()
        }
    }

    fun getFreeDeliveryProducts() {
        viewModelScope.launch {
            freeDeliveryLiveData.value = api.getFreeDeliveryProducts()
        }
    }

    fun getDiscountedProducts() {
        viewModelScope.launch {
            discountedProductsLiveData.value = api.getDiscountedProducts()
        }
    }

    fun getBasketItems() {
        viewModelScope.launch {
            orderItemLiveData.value = itemsDao.getAllItems()
        }
    }
}