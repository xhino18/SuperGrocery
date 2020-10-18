package com.example.supergrocery.Interfaces;

import com.example.supergrocery.ROOM.OrderItem;

public interface AddOrRemoveBasketItem {

    void addClicked(OrderItem itemsModel, int position);

    void removeClicked(OrderItem itemsModel,int position);
}
