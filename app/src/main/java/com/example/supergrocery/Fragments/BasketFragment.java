package com.example.supergrocery.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.supergrocery.Adapters.AdapterBasketItems;
import com.example.supergrocery.Interfaces.AddItemInBasket;
import com.example.supergrocery.Interfaces.AddOrRemoveBasketItem;
import com.example.supergrocery.MainActivity2;
import com.example.supergrocery.R;
import com.example.supergrocery.ROOM.ItemsDB;
import com.example.supergrocery.ROOM.OrderItemsModel;
import com.example.supergrocery.databinding.ActivityMain2Binding;
import com.example.supergrocery.databinding.FragmentBasketBinding;

import java.util.ArrayList;
import java.util.List;




public class BasketFragment extends Fragment implements AddOrRemoveBasketItem {
    FragmentBasketBinding fragmentBasketBinding;
    ActivityMain2Binding activityMain2Binding;
    
    List<OrderItemsModel> orderItemsModels = new ArrayList<>();
    List<OrderItemsModel> list = new ArrayList<>();
    AdapterBasketItems adapterBasketItems;
    LifecycleOwner owner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentBasketBinding=FragmentBasketBinding.inflate(inflater,container,false);
        View view=fragmentBasketBinding.getRoot();

        activityMain2Binding=ActivityMain2Binding.inflate(getLayoutInflater());
        fragmentBasketBinding.recycleviewBasketItems.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        getTotalQuantity();
        showBasketItems();
        updateTotal();

        return view;
    }

    public void showBasketItems() {
        orderItemsModels = ItemsDB.getInstance(getActivity()).orderItemDao().getAllItems();
        adapterBasketItems = new AdapterBasketItems(BasketFragment.this, orderItemsModels, owner);
       fragmentBasketBinding.recycleviewBasketItems.setAdapter(adapterBasketItems);
    }
    @Override
    public void addClicked(OrderItemsModel orderItemsModel, int position) {
        updateOrderItem(orderItemsModels.get(position), 1);
        adapterBasketItems.notifyDataSetChanged();
       fragmentBasketBinding.recycleviewBasketItems.setAdapter(adapterBasketItems);
        updateTotal();
        getTotalQuantity();
    }
    @Override
    public void removeClicked(OrderItemsModel orderItemsModel, int position) {
        if (orderItemsModels.get(position).getQuantity() == 1) {
            if (orderItemsModels.size() == 0) {
                OrderItemsModel orderItem = orderItemsModels.get(position);
                ItemsDB.getInstance(getActivity()).orderItemDao().delete(orderItem);
                adapterBasketItems.notifyDataSetChanged();
               fragmentBasketBinding.recycleviewBasketItems.setAdapter(adapterBasketItems);
                updateTotal();
                getTotalQuantity();
            } else {
                OrderItemsModel orderItem = orderItemsModels.get(position);
                ItemsDB.getInstance(getActivity()).orderItemDao().delete(orderItem);
                orderItemsModels.remove(position);
                adapterBasketItems.notifyDataSetChanged();
               fragmentBasketBinding.recycleviewBasketItems.setAdapter(adapterBasketItems);
                updateTotal();
                getTotalQuantity();
            }
        } else {
            updateOrderItem(orderItemsModels.get(position), -1);
            adapterBasketItems.notifyDataSetChanged();
           fragmentBasketBinding.recycleviewBasketItems.setAdapter(adapterBasketItems);
            updateTotal();
            getTotalQuantity();
        }
    }
    public void updateOrderItem(OrderItemsModel orderItem, int value) {
        int basketPosition = orderItemExistsOnBasket(orderItem);
        orderItemsModels.get(basketPosition).setQuantity(orderItemsModels.get(basketPosition).getQuantity() + value);
        orderItem.setQuantity(orderItemsModels.get(basketPosition).getQuantity());
        ItemsDB.getInstance(getActivity()).orderItemDao().update(orderItem);
        adapterBasketItems = new AdapterBasketItems(BasketFragment.this, orderItemsModels, owner);
       fragmentBasketBinding.recycleviewBasketItems.setAdapter(adapterBasketItems);

    }
    public int orderItemExistsOnBasket(OrderItemsModel productData) {
        for (int i = 0; i < orderItemsModels.size(); i++) {
            if (orderItemsModels.get(i).getId().equals(productData.getId())) {
                return i;
            }
        }
        return -1;
    }
    public void updateTotal() {
        double total = 0;
        for (int i = 0; i < orderItemsModels.size(); i++) {
            total = total + orderItemsModels.get(i).getPrice() * orderItemsModels.get(i).getQuantity();
        }
        fragmentBasketBinding.tvFinalTotal.setText(total + " ALL");
    }
    public void getTotalQuantity() {
        int totalquantity = 0;
        for (int i = 0; i < orderItemsModels.size(); i++) {
            totalquantity = totalquantity + orderItemsModels.get(i).getQuantity();
        }
        if (totalquantity == 0) {
            activityMain2Binding.tvBasketQuantity.setVisibility(View.GONE);
        } else {
            activityMain2Binding.tvBasketQuantity.setVisibility(View.VISIBLE);
        }

        activityMain2Binding.tvBasketQuantity.setText(totalquantity + "");
    }
}