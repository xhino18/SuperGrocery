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
import com.example.supergrocery.GetModels.ModelCategories;
import com.example.supergrocery.GetModels.ModelCategoriesData;
import com.example.supergrocery.GetModels.ModelDiscountedProducts;
import com.example.supergrocery.GetModels.ModelDiscountedProductsData;
import com.example.supergrocery.R;
import com.example.supergrocery.ROOM.ItemsDB;
import com.example.supergrocery.ROOM.OrderItemsModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.supergrocery.MainActivity.token_login;
import static com.example.supergrocery.MainActivity2.tv_basket_quantity;


public class ShopFragment extends Fragment {
    List<OrderItemsModel> list = new ArrayList<>();
    public static RecyclerView recycleview_fragment_categories_shop_fragment;
    RecyclerView recycleview_fragment_discounted_products;
    public static List<ModelCategoriesData> modelCategoriesData_shop_fragment = new ArrayList<>();
    List<ModelDiscountedProductsData> modelDiscountedProducts = new ArrayList<>();
    public static AdapterFragmentCategories adapterFragmentCategories_shop_fragment;
    AdapterFragmentDiscountedProducts adapterFragmentDiscountedProducts;
    Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getTotalQuantity();
        final View view = inflater.inflate(R.layout.fragment_shop, null);

        recycleview_fragment_categories_shop_fragment = view.findViewById(R.id.recycleview_fragment_categories);
        recycleview_fragment_categories_shop_fragment.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recycleview_fragment_discounted_products = view.findViewById(R.id.recycleview_fragment_discounted_products);
        recycleview_fragment_discounted_products.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        gson = new GsonBuilder().create();


        getCategories(token_login);
        return view;
    }

    public void getCategories(String token) {
        API apiClient = ClientAPI.createAPI_With_Token(token);
        Call<ModelCategories> call = apiClient.getCategories();
        Call<ModelDiscountedProducts> call1 = apiClient.getDiscountedProducts();
        call.enqueue(new Callback<ModelCategories>() {
            @Override
            public void onResponse(Call<ModelCategories> call, Response<ModelCategories> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        modelCategoriesData_shop_fragment.addAll(response.body().getData());
                        adapterFragmentCategories_shop_fragment = new AdapterFragmentCategories(getActivity(), modelCategoriesData_shop_fragment);
                        recycleview_fragment_categories_shop_fragment.setAdapter(adapterFragmentCategories_shop_fragment);
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
                        modelDiscountedProducts.addAll(response.body().getData());
                        adapterFragmentDiscountedProducts = new AdapterFragmentDiscountedProducts(getActivity(), modelDiscountedProducts);
                        recycleview_fragment_discounted_products.setAdapter(adapterFragmentDiscountedProducts);
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