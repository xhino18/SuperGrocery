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

import java.util.ArrayList;
import java.util.List;

import static com.example.supergrocery.MainActivity2.tv_basket_quantity;


public class BasketFragment extends Fragment  implements AddOrRemoveBasketItem{
    List<OrderItemsModel> orderItemsModels= new ArrayList<>();
     AdapterBasketItems adapterBasketItems;
     RecyclerView recycleview_basket_items;
     LifecycleOwner owner;
    public static TextView tv_final_total;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View view= inflater.inflate(R.layout.fragment_basket, container, false);

        recycleview_basket_items=view.findViewById(R.id.recycleview_basket_items);
        recycleview_basket_items.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        tv_final_total=view.findViewById(R.id.tv_final_total);


        showBasketItems();

       return view;
    }

    public void showBasketItems(){
        orderItemsModels= ItemsDB.getInstance(getActivity()).orderItemDao().getAllItems();
        adapterBasketItems= new AdapterBasketItems(BasketFragment.this,orderItemsModels,owner);
        recycleview_basket_items.setAdapter(adapterBasketItems);

    }
    @Override
    public void addClicked(OrderItemsModel orderItemsModel,int position) {
        updateOrderItem(orderItemsModels.get(position), 1);
        adapterBasketItems.notifyDataSetChanged();
        recycleview_basket_items.setAdapter(adapterBasketItems);
        updateTotal();
        getTotalQuantity();


    }
    @Override
    public void removeClicked(OrderItemsModel orderItemsModel,int position) {
        if (orderItemsModels.get(position).getQuantity() == 1) {
            if (orderItemsModels.size() == 0) {
                OrderItemsModel orderItem = orderItemsModels.get(position);
                ItemsDB.getInstance(getActivity()).orderItemDao().delete(orderItem);
                adapterBasketItems.notifyDataSetChanged();
                recycleview_basket_items.setAdapter(adapterBasketItems);
                updateTotal();
                getTotalQuantity();

            } else {
                OrderItemsModel orderItem = orderItemsModels.get(position);
                ItemsDB.getInstance(getActivity()).orderItemDao().delete(orderItem);
                orderItemsModels.remove(position);
                adapterBasketItems.notifyDataSetChanged();
                recycleview_basket_items.setAdapter(adapterBasketItems);
                updateTotal();
                getTotalQuantity();
            }
        } else {
            updateOrderItem(orderItemsModels.get(position), -1);
            adapterBasketItems.notifyDataSetChanged();
            recycleview_basket_items.setAdapter(adapterBasketItems);
            updateTotal();
            getTotalQuantity();

        }
    }

        public void updateOrderItem(OrderItemsModel orderItem, int value) {
        int basketPosition = orderItemExistsOnBasket(orderItem);
        orderItemsModels.get(basketPosition).setQuantity(orderItemsModels.get(basketPosition).getQuantity() + value);
        orderItem.setQuantity(orderItemsModels.get(basketPosition).getQuantity());
        ItemsDB.getInstance(getActivity()).orderItemDao().update(orderItem);
        adapterBasketItems = new AdapterBasketItems(BasketFragment.this,orderItemsModels,owner);
        recycleview_basket_items.setAdapter(adapterBasketItems);

    }

    public int orderItemExistsOnBasket(OrderItemsModel productData) {
        for (int i = 0; i < orderItemsModels.size(); i++) {
            if (orderItemsModels.get(i).getId().equals(productData.getId())) {
                return i;
            }
        }
        return -1;
    }

    public int updateTotal(){
        int total=0;
        for (int i =0;i<orderItemsModels.size();i++){
            total=total +orderItemsModels.get(i).getPrice()*orderItemsModels.get(i).getQuantity();
        }
        tv_final_total.setText(total+" ALL");
        return total;

    }
    public void getTotalQuantity() {
        int totalquantity = 0;
        for (int i = 0; i < orderItemsModels.size(); i++) {
            totalquantity = totalquantity + orderItemsModels.get(i).getQuantity();
        }
        if (totalquantity == 0) {
            tv_basket_quantity.setVisibility(View.GONE);
        } else {
            tv_basket_quantity.setVisibility(View.VISIBLE);
        }

        tv_basket_quantity.setText(totalquantity + "");

    }


    }