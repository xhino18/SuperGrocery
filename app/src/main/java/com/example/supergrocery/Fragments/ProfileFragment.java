package com.example.supergrocery.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.supergrocery.Other.EditProfileDialog;
import com.example.supergrocery.R;
import com.example.supergrocery.ROOM.ItemsDB;
import com.example.supergrocery.ROOM.OrderItemsModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.supergrocery.MainActivity2.tv_basket_quantity;


public class ProfileFragment extends Fragment {
    Button button_edit_profile;
    List<OrderItemsModel> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        final EditProfileDialog alertDialog = new EditProfileDialog();

        button_edit_profile=view.findViewById(R.id.button_edit_profile);
        getTotalQuantity();

        button_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.showDialog(getActivity(),"Title");
            }
        });
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