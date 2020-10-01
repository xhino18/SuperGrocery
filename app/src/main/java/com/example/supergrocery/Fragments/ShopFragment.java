package com.example.supergrocery.Fragments;

import android.media.session.MediaSession;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.Adapters.AdapterCategories;
import com.example.supergrocery.Adapters.AdapterFragmentCategories;
import com.example.supergrocery.Adapters.AdapterFragmentDiscountedProducts;
import com.example.supergrocery.MainActivity;
import com.example.supergrocery.Models.ModelCategories;
import com.example.supergrocery.Models.ModelCategoriesData;
import com.example.supergrocery.Models.ModelDiscountedProducts;
import com.example.supergrocery.Models.ModelDiscountedProductsData;
import com.example.supergrocery.Models.ModelFreeDeliveryProducts;
import com.example.supergrocery.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.supergrocery.MainActivity.Token;


public class ShopFragment extends Fragment {
    RecyclerView recycleview_fragment_categories,recycleview_fragment_discounted_products;
    List<ModelCategoriesData> modelCategoriesData = new ArrayList<>();
    List<ModelDiscountedProductsData> modelDiscountedProducts=new ArrayList<>();
    AdapterFragmentCategories adapterFragmentCategories;
    AdapterFragmentDiscountedProducts adapterFragmentDiscountedProducts;
    Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_shop, null);

        recycleview_fragment_categories = view.findViewById(R.id.recycleview_fragment_categories);
        recycleview_fragment_categories.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recycleview_fragment_discounted_products=view.findViewById(R.id.recycleview_fragment_discounted_products);
        recycleview_fragment_discounted_products.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        gson = new GsonBuilder().create();

        getCategories(Token);
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
                        modelCategoriesData.addAll(response.body().getData());
                        adapterFragmentCategories = new AdapterFragmentCategories(getActivity(), modelCategoriesData);
                        recycleview_fragment_categories.setAdapter(adapterFragmentCategories);
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
}