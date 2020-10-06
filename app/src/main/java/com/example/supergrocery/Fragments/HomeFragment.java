package com.example.supergrocery.Fragments;

import android.content.Intent;
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
import com.example.supergrocery.GetModels.ModelCategories;
import com.example.supergrocery.GetModels.ModelCategoriesData;
import com.example.supergrocery.GetModels.ModelDiscountedProducts;
import com.example.supergrocery.GetModels.ModelDiscountedProductsData;
import com.example.supergrocery.GetModels.ModelFreeDeliveryProducts;
import com.example.supergrocery.GetModels.ModelFreeDeliveryProductsData;
import com.example.supergrocery.MainActivity;
import com.example.supergrocery.MainActivity2;
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
    private void go(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        go();
        final View view =  inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }



}