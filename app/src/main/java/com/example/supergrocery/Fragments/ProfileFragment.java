package com.example.supergrocery.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.supergrocery.Other.EditProfileDialog;
import com.example.supergrocery.Other.SaveData;
import com.example.supergrocery.ROOM.ItemsDB;
import com.example.supergrocery.ROOM.OrderItem;
import com.example.supergrocery.databinding.ActivityMain2Binding;
import com.example.supergrocery.databinding.FragmentProfileBinding;

import java.util.ArrayList;
import java.util.List;




public class ProfileFragment extends Fragment {
    FragmentProfileBinding fragmentProfileBinding;
    ActivityMain2Binding activityMain2Binding;
    List<OrderItem> list = new ArrayList<>();
    SaveData saveData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        fragmentProfileBinding=FragmentProfileBinding.inflate(inflater,container,false);
        View view=fragmentProfileBinding.getRoot();


        activityMain2Binding=ActivityMain2Binding.inflate(getLayoutInflater());
        final EditProfileDialog alertDialog = new EditProfileDialog();
        getTotalQuantity();
        saveData=new SaveData(getActivity());
        fragmentProfileBinding.buttonEditProfile.setOnClickListener(v -> alertDialog.showDialog(getActivity(),"Title"));
        fragmentProfileBinding.tvProfileName.setText(saveData.get_name());
        fragmentProfileBinding.tvProfileEmail.setText(saveData.get_email());
        fragmentProfileBinding.tvProfilePhone.setText(saveData.get_phone_number());
        fragmentProfileBinding.tvProfileNuis.setText(saveData.get_nuis());




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