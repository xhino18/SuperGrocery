package com.example.supergrocery.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.Adapters.AdapterCategories;
import com.example.supergrocery.Adapters.AdapterDiscountedProducts;
import com.example.supergrocery.Adapters.AdapterFreeDeliveryProducts;
import com.example.supergrocery.MainActivity;
import com.example.supergrocery.Models.ModelCategories;
import com.example.supergrocery.Models.ModelCategoriesData;
import com.example.supergrocery.Models.ModelDiscountedProducts;
import com.example.supergrocery.Models.ModelDiscountedProductsData;
import com.example.supergrocery.Models.ModelFreeDeliveryProducts;
import com.example.supergrocery.Models.ModelFreeDeliveryProductsData;
import com.example.supergrocery.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.supergrocery.MainActivity.token_login;


public class HomeFragment extends Fragment {
    Gson gson;
    List<ModelCategoriesData> categoriesDataList = new ArrayList<>();
    List<ModelDiscountedProductsData> modelDiscountedProductsData = new ArrayList<>();
    List<ModelFreeDeliveryProductsData> modelFreeDeliveryProductsData = new ArrayList<>();
    AdapterCategories adapterCategories;
    AdapterDiscountedProducts adapterDiscountedProducts;
    AdapterFreeDeliveryProducts adapterFreeDeliveryProducts;
    RecyclerView recyclerView_categories,recycleview_discounted_products,recycleview_free_delivery_products;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView_categories = view.findViewById(R.id.recycleview_food_categories);
        recyclerView_categories.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recycleview_discounted_products=view.findViewById(R.id.recycleview_discounted_products);
        recycleview_discounted_products.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recycleview_free_delivery_products=view.findViewById(R.id.recycleview_free_delivery_products);
        recycleview_free_delivery_products.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        gson=new GsonBuilder().create();

        getall(token_login);
        return view;
    }

    public void getall(String token) {
        API apiClient = ClientAPI.createAPI_With_Token(token);
        Call<ModelCategories> call = apiClient.getCategories();
        Call<ModelDiscountedProducts> call1 = apiClient.getDiscountedProducts();
        Call<ModelFreeDeliveryProducts> call2 = apiClient.getFreeDeliveryProducts();
        call.enqueue(new Callback<ModelCategories>() {
            @Override
            public void onResponse(Call<ModelCategories> call, Response<ModelCategories> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        categoriesDataList.addAll(response.body().getData());
                        adapterCategories = new AdapterCategories(getActivity(), categoriesDataList);
                        recyclerView_categories.setAdapter(adapterCategories);
                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelCategories> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        call1.enqueue(new Callback<ModelDiscountedProducts>() {
            @Override
            public void onResponse(Call<ModelDiscountedProducts> call, Response<ModelDiscountedProducts> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        modelDiscountedProductsData.addAll(response.body().getData());
                        adapterDiscountedProducts = new AdapterDiscountedProducts(getActivity(), modelDiscountedProductsData);
                        recycleview_discounted_products.setAdapter(adapterDiscountedProducts);
                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelDiscountedProducts> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        call2.enqueue(new Callback<ModelFreeDeliveryProducts>() {
            @Override
            public void onResponse(Call<ModelFreeDeliveryProducts> call, Response<ModelFreeDeliveryProducts> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        modelFreeDeliveryProductsData.addAll(response.body().getData());
                        adapterFreeDeliveryProducts = new AdapterFreeDeliveryProducts(getActivity(),modelFreeDeliveryProductsData);
                        recycleview_free_delivery_products.setAdapter(adapterFreeDeliveryProducts);
                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelFreeDeliveryProducts> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




}