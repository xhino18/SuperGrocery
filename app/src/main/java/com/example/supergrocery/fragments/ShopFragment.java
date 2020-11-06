package com.example.supergrocery.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.supergrocery.MainViewModel;
import com.example.supergrocery.adapters.AdapterFragmentCategories;
import com.example.supergrocery.api.API;
import com.example.supergrocery.models.CategoriesData;
import com.example.supergrocery.other.SaveData;
import com.example.supergrocery.databinding.FragmentShopBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

import javax.inject.Inject;


@AndroidEntryPoint
public class ShopFragment extends Fragment {
    FragmentShopBinding binding;
    List<CategoriesData> categoriesData = new ArrayList<>();
    AdapterFragmentCategories adapterFragmentCategories;
    Gson gson;
    SaveData saveData;

    @Inject
    API api;

    private MainViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding = FragmentShopBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.recycleviewFragmentCategories.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        init();
        setUpCategoriesList();

        return view;
    }

    private void init() {
        gson = new GsonBuilder().create();
        saveData = new SaveData(getContext());
        binding.searchviewMain2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchCategory(newText);
                return true;
            }
        });

        viewModel.getCategories();
    }

    private void setUpCategoriesList() {
        viewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), categories -> {
            if (!categories.getError()) {
                categoriesData.addAll(categories.getData());
                adapterFragmentCategories = new AdapterFragmentCategories(getActivity(), categoriesData);
                binding.recycleviewFragmentCategories.setAdapter(adapterFragmentCategories);
            } else {
                Toast.makeText(getActivity(), categories.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchCategory(String s) {
        List<CategoriesData> data = new ArrayList<>();
        data.addAll(categoriesData);
        for (int i = 0; i < data.size(); i++) {
            if (!data.get(i).getName().toUpperCase().contains(s.toUpperCase())) {
                data.remove(i);
                i--;
            }
        }
        adapterFragmentCategories = new AdapterFragmentCategories(getContext(), data);
        binding.recycleviewFragmentCategories.setAdapter(adapterFragmentCategories);
    }
}