package com.example.supergrocery.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.supergrocery.ROOM.ItemsDB;
import com.example.supergrocery.ROOM.OrderItem;
import com.example.supergrocery.databinding.ActivityMain2Binding;
import com.example.supergrocery.databinding.FragmentDiscoverBinding;

import java.util.ArrayList;
import java.util.List;



public class DiscoverFragment extends Fragment {
    FragmentDiscoverBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentDiscoverBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        return view;
    }



}