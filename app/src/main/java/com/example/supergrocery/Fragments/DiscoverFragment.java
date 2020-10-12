package com.example.supergrocery.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.supergrocery.MainActivity2;
import com.example.supergrocery.R;
import com.example.supergrocery.ROOM.ItemsDB;
import com.example.supergrocery.ROOM.OrderItem;
import com.example.supergrocery.ROOM.OrderItemsModel;
import com.example.supergrocery.databinding.ActivityMain2Binding;
import com.example.supergrocery.databinding.FragmentDiscoverBinding;

import java.util.ArrayList;
import java.util.List;



public class DiscoverFragment extends Fragment {
    FragmentDiscoverBinding fragmentDiscoverBinding;
    ActivityMain2Binding activityMain2Binding;
    List<OrderItem> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentDiscoverBinding=FragmentDiscoverBinding.inflate(inflater,container,false);
        View view = fragmentDiscoverBinding.getRoot();

        activityMain2Binding=ActivityMain2Binding.inflate(getLayoutInflater());
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
            activityMain2Binding.tvBasketQuantity.setVisibility(View.GONE);
        } else {
            activityMain2Binding.tvBasketQuantity.setVisibility(View.VISIBLE);
        }
        activityMain2Binding.tvBasketQuantity.setText(totalquantity + "");
    }


}