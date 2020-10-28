package com.example.supergrocery.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.Adapters.AdapterFragmentCategories;
import com.example.supergrocery.ModelsGet.Categories;
import com.example.supergrocery.ModelsGet.CategoriesData;
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
    FragmentShopBinding binding;
    List<CategoriesData> categoriesData = new ArrayList<>();
    AdapterFragmentCategories adapterFragmentCategories;
    Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentShopBinding.inflate(inflater,container,false);
        View view= binding.getRoot();
        binding.recycleviewFragmentCategories.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        gson = new GsonBuilder().create();
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

    getCategories(token_login);
        return view;
    }

    public void getCategories(String token) {
        API apiClient = ClientAPI.createAPI_With_Token(token);
        Call<Categories> call = apiClient.getCategories();
        call.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        categoriesData.addAll(response.body().getData());
                        adapterFragmentCategories = new AdapterFragmentCategories(getActivity(), categoriesData);
                        binding.recycleviewFragmentCategories.setAdapter(adapterFragmentCategories);
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
        adapterFragmentCategories = new AdapterFragmentCategories(getContext(),data);
        binding.recycleviewFragmentCategories.setAdapter(adapterFragmentCategories);
    }


}