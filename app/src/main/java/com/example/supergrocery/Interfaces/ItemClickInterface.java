package com.example.supergrocery.Interfaces;

import com.example.supergrocery.ModelsGet.AllProductsData;
import com.example.supergrocery.ModelsGet.CategoriesData;
import com.example.supergrocery.ModelsGet.DiscountedProductsData;

public interface ItemClickInterface {

    void freeDeliveryClicked(AllProductsData data);

    void dicountedProductsClicked(DiscountedProductsData data);

    void categoryClicked(CategoriesData data);

}
