package com.example.supergrocery.interfaces;

import com.example.supergrocery.models.AllProductsData;
import com.example.supergrocery.models.CategoriesData;
import com.example.supergrocery.models.DiscountedProductsData;

public interface ItemClickInterface {

    void freeDeliveryClicked(AllProductsData data);

    void dicountedProductsClicked(DiscountedProductsData data);

    void categoryClicked(CategoriesData data);

}
