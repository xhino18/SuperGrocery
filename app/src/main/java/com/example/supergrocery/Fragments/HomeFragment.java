package com.example.supergrocery.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.Adapters.AdapterBanner;
import com.example.supergrocery.Adapters.AdapterCategories;
import com.example.supergrocery.Adapters.AdapterDiscountedProducts;
import com.example.supergrocery.Adapters.AdapterFreeDeliveryProducts;
import com.example.supergrocery.ModelsGet.AllProducts;
import com.example.supergrocery.ModelsGet.AllProductsData;
import com.example.supergrocery.ModelsGet.Banner;
import com.example.supergrocery.ModelsGet.BannerData;
import com.example.supergrocery.ModelsGet.Categories;
import com.example.supergrocery.ModelsGet.CategoriesData;
import com.example.supergrocery.ModelsGet.DiscountedProducts;
import com.example.supergrocery.ModelsGet.DiscountedProductsData;
import com.example.supergrocery.MainActivity;
import com.example.supergrocery.Other.MainSearchActivity;
import com.example.supergrocery.Other.SaveData;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.FragmentHomeBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    Gson gson;
    List<CategoriesData> categoriesDataList = new ArrayList<>();
    List<CategoriesData> categoriesData = new ArrayList<>();
    List<BannerData> bannerData = new ArrayList<>();
    List<DiscountedProductsData> modelDiscountedProductsData = new ArrayList<>();
    List<AllProductsData> allProductsData = new ArrayList<>();
    AdapterCategories adapterCategories;
    AdapterBanner adapterBanner;
    AdapterDiscountedProducts adapterDiscountedProducts;
    AdapterFreeDeliveryProducts adapterFreeDeliveryProducts;
    SaveData saveData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentHomeBinding.inflate(inflater,container,false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final View view =binding.getRoot();

        init();


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
        getall(saveData.getToken());

    }
    public void getall(String token) {
        API apiClient = ClientAPI.createAPI_With_Token(token);
        Call<Categories> call = apiClient.getCategories();
        Call<Banner> call1 = apiClient.getBanners();
        Call<AllProducts> call2 = apiClient.getFreeDeliveryProducts();
        Call<DiscountedProducts> call3 = apiClient.getDiscountedProducts();
        call.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        categoriesDataList.addAll(response.body().getData());
                        adapterCategories = new AdapterCategories(getContext(), categoriesDataList);
                        binding.recycleviewFoodCategories.setAdapter(adapterCategories);
                    } else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        call1.enqueue(new Callback<Banner>() {
            @Override
            public void onResponse(Call<Banner> call, Response<Banner> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        bannerData.addAll(response.body().getData());
                        adapterBanner = new AdapterBanner(getContext(),bannerData);
                        binding.recycleviewBanner.setAdapter(adapterBanner);

                    } else {
                        Toast.makeText(getContext(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Banner> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        call2.enqueue(new Callback<AllProducts>() {
            @Override
            public void onResponse(Call<AllProducts> call, Response<AllProducts> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        allProductsData.addAll(response.body().getData());
                        adapterFreeDeliveryProducts = new AdapterFreeDeliveryProducts(getContext(), allProductsData);
                        binding.recycleviewFreeDeliveryProducts.setAdapter(adapterFreeDeliveryProducts);
                    } else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AllProducts> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        call3.enqueue(new Callback<DiscountedProducts>() {
            @Override
            public void onResponse(Call<DiscountedProducts> call, Response<DiscountedProducts> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        modelDiscountedProductsData.addAll(response.body().getData());
                        adapterDiscountedProducts = new AdapterDiscountedProducts(getContext(), modelDiscountedProductsData);
                        binding.recycleviewDicountedProducts.setAdapter(adapterDiscountedProducts);
                    } else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DiscountedProducts> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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