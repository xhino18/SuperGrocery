package com.example.supergrocery.Interfaces;

import com.example.supergrocery.ROOM.OrderItemsModel;

public interface AddOrRemoveBasketItem {

    void addClicked(OrderItemsModel itemsModel,int position);

    void removeClicked(OrderItemsModel itemsModel,int position);
}
