package com.example.supergrocery.Interfaces;

import com.example.supergrocery.GetModels.BannerData;
import com.example.supergrocery.GetModels.CategoriesData;
import com.example.supergrocery.GetModels.DiscountedProductsData;
import com.example.supergrocery.GetModels.FreeDeliveryProductsData;

public interface ItemClickInterface {

    void freeDeliveryClicked(FreeDeliveryProductsData data);

    void dicountedProductsClicked(DiscountedProductsData data);

    void categoryClicked(CategoriesData data);

}
