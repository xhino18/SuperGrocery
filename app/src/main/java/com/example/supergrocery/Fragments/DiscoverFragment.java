package com.example.supergrocery.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.supergrocery.MainActivity2;
import com.example.supergrocery.R;
import com.example.supergrocery.ROOM.ItemsDB;
import com.example.supergrocery.ROOM.OrderItemsModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.supergrocery.MainActivity2.tv_basket_quantity;


public class DiscoverFragment extends Fragment {
    List<OrderItemsModel> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_discover, container, false);
        getTotalQuantity();
        return view;
    }

    private void getTotalQuantity() {
        int totalquantity = 0;
        list = ItemsDB.getInstance(getActivity()).orderItemDao().getAllItems();
        for (int i = 0; i < list.size(); i++) {
            totalquantity = totalquantity + list.get(i).getQuantity();
        }
        if (totalquantity == 0) {
            tv_basket_quantity.setVisibility(View.GONE);
        } else {
            tv_basket_quantity.setVisibility(View.VISIBLE);
        }
        tv_basket_quantity.setText(totalquantity + "");
    }


}