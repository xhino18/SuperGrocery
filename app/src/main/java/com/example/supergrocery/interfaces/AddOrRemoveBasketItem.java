package com.example.supergrocery.interfaces;

import com.example.supergrocery.room.OrderItem;

public interface AddOrRemoveBasketItem {

    void addClicked(OrderItem itemsModel);

    void removeClicked(OrderItem itemsModel);
}
