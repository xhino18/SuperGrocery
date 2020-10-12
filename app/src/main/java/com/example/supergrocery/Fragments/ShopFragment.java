package com.example.supergrocery.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.Adapters.AdapterFragmentCategories;
import com.example.supergrocery.Adapters.AdapterFragmentDiscountedProducts;
import com.example.supergrocery.GetModels.Categories;
import com.example.supergrocery.GetModels.CategoriesData;
import com.example.supergrocery.GetModels.DiscountedProducts;
import com.example.supergrocery.GetModels.DiscountedProductsData;
import com.example.supergrocery.GetModels.ModelCategories;
import com.example.supergrocery.GetModels.ModelCategoriesData;
import com.example.supergrocery.GetModels.ModelDiscountedProducts;
import com.example.supergrocery.GetModels.ModelDiscountedProductsData;
import com.example.supergrocery.R;
import com.example.supergrocery.ROOM.ItemsDB;
import com.example.supergrocery.ROOM.OrderItem;
import com.example.supergrocery.ROOM.OrderItemsModel;
import com.example.supergrocery.databinding.ActivityMain2Binding;
import com.example.supergrocery.databinding.FragmentShopBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.supergrocery.MainActivity.token_login;


public class ShopFragment extends Fragment {
    FragmentShopBinding fragmentShopBinding;
    ActivityMain2Binding activityMain2Binding;

    List<OrderItem> list = new ArrayList<>();
    public static List<CategoriesData> modelCategoriesData_shop_fragment = new ArrayList<>();
    List<DiscountedProductsData> modelDiscountedProducts = new ArrayList<>();
    AdapterFragmentCategories adapterFragmentCategories_shop_fragment;
    AdapterFragmentDiscountedProducts adapterFragmentDiscountedProducts;
    Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentShopBinding=FragmentShopBinding.inflate(inflater,container,false);
        View view=fragmentShopBinding.getRoot();
        activityMain2Binding=ActivityMain2Binding.inflate(getLayoutInflater());
        getTotalQuantity();
        fragmentShopBinding.recycleviewFragmentCategories.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        fragmentShopBinding.recycleviewFragmentDiscountedProducts.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        gson = new GsonBuilder().create();


        getCategories(token_login);
        return view;
    }

    public void getCategories(String token) {
        API apiClient = ClientAPI.createAPI_With_Token(token);
        Call<Categories> call = apiClient.getCategories();
        Call<DiscountedProducts> call1 = apiClient.getDiscountedProducts();
        call.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        modelCategoriesData_shop_fragment.addAll(response.body().getData());
                        adapterFragmentCategories_shop_fragment = new AdapterFragmentCategories(getActivity(), modelCategoriesData_shop_fragment);
                        fragmentShopBinding.recycleviewFragmentCategories.setAdapter(adapterFragmentCategories_shop_fragment);
                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        call1.enqueue(new Callback<DiscountedProducts>() {
            @Override
            public void onResponse(Call<DiscountedProducts> call, Response<DiscountedProducts> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        modelDiscountedProducts.addAll(response.body().getData());
                        adapterFragmentDiscountedProducts = new AdapterFragmentDiscountedProducts(getActivity(), modelDiscountedProducts);
                        fragmentShopBinding.recycleviewFragmentDiscountedProducts.setAdapter(adapterFragmentDiscountedProducts);
                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DiscountedProducts> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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