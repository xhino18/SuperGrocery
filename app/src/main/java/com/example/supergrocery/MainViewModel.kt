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
import timber.log.Timber

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
    val shopProductsLiveData = MutableLiveData<ModelMain<List<ShopProductsData>>>()

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
        Timber.e("update")
        viewModelScope.launch {
            orderItemLiveData.value = itemsDao.getAllItems()
        }
    }
    fun deleteAll() {
        viewModelScope.launch {
            itemsDao.deleteAll();
        }
    }
    fun deleteBasketItem(orderItem: OrderItem) {
        viewModelScope.launch {
            itemsDao.delete(orderItem);
            getBasketItems()
        }
    }
    fun updateBasket(orderItem: OrderItem) {
        viewModelScope.launch {
            itemsDao.update(orderItem);
        }
    }
    fun insertInBasket(orderItem: OrderItem) {
        viewModelScope.launch {
            itemsDao.insert(orderItem);
        }
    }
    fun incrementQuantity(item: OrderItem) {
        viewModelScope.launch {
            if (itemsDao.checkInBasket(item.id) > 0) {
                Timber.e("in basket")
                itemsDao.incrementQuantity(item.id)
            } else {
                Timber.e("not in basket")
                itemsDao.insert(item)
            }
            getBasketItems()
        }
    }
    fun decrementQuantity(item: OrderItem) {
        viewModelScope.launch {
            if (itemsDao.checkInBasket(item.id) > 0) {
                if(itemsDao.checkQuantityBasket(item.quantity)==1){
                    itemsDao.delete(item)
                }else {
                    itemsDao.decrementQuantity(item.id)
                }
            } else {
                itemsDao.delete(item)
            }
            getBasketItems()
        }
    }
    fun getShopProducts(id:Int){
        viewModelScope.launch {
            shopProductsLiveData.value=api.getProducts(id)
        }
    }

}