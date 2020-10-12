package com.example.supergrocery.Interfaces;

import com.example.supergrocery.ROOM.OrderItem;
import com.example.supergrocery.ROOM.OrderItemsModel;

public interface AddOrRemoveBasketItem {

    void addClicked(OrderItem itemsModel, int position);

    void removeClicked(OrderItem itemsModel,int position);
}
