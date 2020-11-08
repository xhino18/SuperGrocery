package com.example.supergrocery.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.supergrocery.MainViewModel;
import com.example.supergrocery.adapters.AdapterBanner;
import com.example.supergrocery.adapters.AdapterCategories;
import com.example.supergrocery.adapters.AdapterDiscountedProducts;
import com.example.supergrocery.adapters.AdapterFreeDeliveryProducts;
import com.example.supergrocery.api.API;
import com.example.supergrocery.models.AllProductsData;
import com.example.supergrocery.models.BannerData;
import com.example.supergrocery.models.CategoriesData;
import com.example.supergrocery.models.DiscountedProductsData;
import com.example.supergrocery.MainActivity;
import com.example.supergrocery.other.MainSearchActivity;
import com.example.supergrocery.other.SaveData;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.FragmentHomeBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    Gson gson;

    AdapterCategories adapterCategories;
    AdapterBanner adapterBanner;
    AdapterDiscountedProducts adapterDiscountedProducts;
    AdapterFreeDeliveryProducts adapterFreeDeliveryProducts;
    SaveData saveData;

    @Inject
    API api;
    private MainViewModel mainViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding=FragmentHomeBinding.inflate(inflater,container,false);
        final View view =binding.getRoot();

        init();
        setUpCategoriesList();
        setUpBannerList();
        setUpDiscountedProducts();
        setUpFreeDeliveryProductsList();

        return view;
    }

    public void init() {
        gson = new GsonBuilder().create();
        saveData = new SaveData(getContext());

        binding.recycleviewFoodCategories.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        adapterCategories = new AdapterCategories(getContext());
        binding.recycleviewFoodCategories.setAdapter(adapterCategories);

        binding.recycleviewBanner.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        adapterBanner = new AdapterBanner(getContext());
        binding.recycleviewBanner.setAdapter(adapterBanner);

        binding.recycleviewDicountedProducts.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        adapterDiscountedProducts = new AdapterDiscountedProducts(getActivity());
        binding.recycleviewDicountedProducts.setAdapter(adapterDiscountedProducts);

        binding.recycleviewFreeDeliveryProducts.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        adapterFreeDeliveryProducts = new AdapterFreeDeliveryProducts(getActivity());
        binding.recycleviewFreeDeliveryProducts.setAdapter(adapterFreeDeliveryProducts);
        binding.tvSeeAll.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MainActivity.class);
            intent.putExtra("goToShop",true);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.see_all_anim, R.anim.slide_out_left);
        });

        binding.tvSearchBar.setOnClickListener(view -> {
            Intent intent=new Intent(getContext(),MainSearchActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        });
        mainViewModel.getCategories();
        mainViewModel.getBanners();
        mainViewModel.getDiscountedProducts();
        mainViewModel.getFreeDeliveryProducts();

    }

    private void setUpFreeDeliveryProductsList() {
        mainViewModel.getFreeDeliveryLiveData().observe(getViewLifecycleOwner(),listModelMain ->  {
            if (!listModelMain.getError()) {
                adapterFreeDeliveryProducts.submitList(listModelMain.getData());
            } else {
                Toast.makeText(getActivity(), listModelMain.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpDiscountedProducts() {
        mainViewModel.getDiscountedProductsLiveData().observe(getViewLifecycleOwner(),listModelMain ->  {
            if (!listModelMain.getError()) {
                adapterDiscountedProducts.submitList(listModelMain.getData());

            } else {
                Toast.makeText(getActivity(), listModelMain.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpBannerList() {
        mainViewModel.getBannerLiveData().observe(getViewLifecycleOwner(),listModelMain -> {
            if (!listModelMain.getError()) {
                adapterBanner.submitList(listModelMain.getData());
            } else {
                Toast.makeText(getActivity(), listModelMain.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpCategoriesList() {
        mainViewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(),listModelMain -> {
            if (!listModelMain.getError()) {
                adapterCategories.submitList(listModelMain.getData());
            } else {
                Toast.makeText(getActivity(), listModelMain.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}