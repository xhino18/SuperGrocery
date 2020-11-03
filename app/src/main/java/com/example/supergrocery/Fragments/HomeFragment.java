package com.example.supergrocery.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.Adapters.AdapterBanner;
import com.example.supergrocery.Adapters.AdapterCategories;
import com.example.supergrocery.Adapters.AdapterDiscountedProducts;
import com.example.supergrocery.Adapters.AdapterFragmentCategories;
import com.example.supergrocery.Adapters.AdapterFreeDeliveryProducts;
import com.example.supergrocery.Models.AllProductsData;
import com.example.supergrocery.Models.BannerData;
import com.example.supergrocery.Models.CategoriesData;
import com.example.supergrocery.Models.DiscountedProductsData;
import com.example.supergrocery.MainActivity;
import com.example.supergrocery.Models.ModelMain;
import com.example.supergrocery.Other.MainSearchActivity;
import com.example.supergrocery.Other.MainViewModel;
import com.example.supergrocery.Other.SaveData;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.FragmentHomeBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    Gson gson;
    List<CategoriesData> categoriesDataList = new ArrayList<>();
    List<CategoriesData> categoriesData = new ArrayList<>();
    List<BannerData> bannerData = new ArrayList<>();
    List<DiscountedProductsData> modelDiscountedProductsData = new ArrayList<>();
    List<AllProductsData> freeDeliveryProducts = new ArrayList<>();
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
        mainViewModel.categoriesLiveData.observe(getViewLifecycleOwner(), categories -> {
            if (!categories.getError()) {
                categoriesData.addAll(categories.getData());
                adapterCategories = new AdapterCategories(getActivity(), categoriesData);
                binding.recycleviewFoodCategories.setAdapter(adapterCategories);
            } else {
                Toast.makeText(getActivity(), categories.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mainViewModel.categoriesErrorLiveData.observe(getViewLifecycleOwner(), message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show());

        mainViewModel.bannerLiveData.observe(getViewLifecycleOwner(), banner -> {
            if (!banner.getError()) {
                bannerData.addAll(banner.getData());
                adapterBanner = new AdapterBanner(getActivity(), bannerData);
                binding.recycleviewBanner.setAdapter(adapterBanner);
            } else {
                Toast.makeText(getActivity(), banner.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mainViewModel.bannerErrorLiveData.observe(getViewLifecycleOwner(), message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show());

        mainViewModel.discountedProductsLiveData.observe(getViewLifecycleOwner(), discountedProducts -> {
            if (!discountedProducts.getError()) {
                modelDiscountedProductsData.addAll(discountedProducts.getData());
                adapterDiscountedProducts = new AdapterDiscountedProducts(getActivity(), modelDiscountedProductsData);
                binding.recycleviewDicountedProducts.setAdapter(adapterDiscountedProducts);
            } else {
                Toast.makeText(getActivity(), discountedProducts.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mainViewModel.discountedProductsErrorLiveData.observe(getViewLifecycleOwner(), message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show());

        mainViewModel.freeDeliveryLiveData.observe(getViewLifecycleOwner(), freeDeliveryLiveData -> {
            if (!freeDeliveryLiveData.getError()) {
                freeDeliveryProducts.addAll(freeDeliveryLiveData.getData());
                adapterFreeDeliveryProducts = new AdapterFreeDeliveryProducts(getActivity(), freeDeliveryProducts);
                binding.recycleviewFreeDeliveryProducts.setAdapter(adapterFreeDeliveryProducts);
            } else {
                Toast.makeText(getActivity(), freeDeliveryLiveData.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mainViewModel.freeDeliveryErrorLiveData.observe(getViewLifecycleOwner(), message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show());


        return view;
    }
    public void init() {
        gson = new GsonBuilder().create();
        saveData = new SaveData(getContext());
        binding.recycleviewFoodCategories.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.recycleviewBanner.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.recycleviewDicountedProducts.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.recycleviewFreeDeliveryProducts.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
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

    private void searchCategory(String s) {

        categoriesData.addAll(categoriesDataList);
        for (int i = 0; i < categoriesData.size(); i++) {
            if (!categoriesData.get(i).getName().toUpperCase().contains(s.toUpperCase())) {
                categoriesData.remove(i);
                i--;
            }
        }
        if (!categoriesData.isEmpty()) {
            adapterBanner = new AdapterBanner(getContext(),bannerData);
            binding.recycleviewBanner.setAdapter(adapterBanner);


        }
    }
}