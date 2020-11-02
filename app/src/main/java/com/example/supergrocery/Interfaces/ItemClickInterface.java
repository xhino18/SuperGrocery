package com.example.supergrocery.Interfaces;

import com.example.supergrocery.Models.AllProductsData;
import com.example.supergrocery.Models.CategoriesData;
import com.example.supergrocery.Models.DiscountedProductsData;

public interface ItemClickInterface {

    void freeDeliveryClicked(AllProductsData data);

    void dicountedProductsClicked(DiscountedProductsData data);

    void categoryClicked(CategoriesData data);

}
