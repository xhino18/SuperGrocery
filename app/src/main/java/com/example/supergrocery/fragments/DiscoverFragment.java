package com.example.supergrocery.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.supergrocery.databinding.FragmentDiscoverBinding;


public class DiscoverFragment extends Fragment {
    FragmentDiscoverBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentDiscoverBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        return view;
    }



}