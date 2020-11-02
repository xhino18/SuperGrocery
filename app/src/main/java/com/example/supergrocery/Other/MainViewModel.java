package com.example.supergrocery.Other;

import android.content.SharedPreferences;
import android.widget.Toast;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.supergrocery.API.API;
import com.example.supergrocery.Adapters.AdapterFragmentCategories;
import com.example.supergrocery.ModelsGet.Categories;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private SharedPreferences preferences;
    private API api;

    public MutableLiveData<Categories> categoriesLiveData = new MutableLiveData<>();
    public MutableLiveData<String> categoriesErrorLiveData = new MutableLiveData<>();


    @ViewModelInject
    public MainViewModel(SharedPreferences preferences, API api) {
        this.preferences = preferences;
        this.api = api;
    }

    public void getCategories() {
        Call<Categories> call = api.getCategories();
        call.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                categoriesLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                categoriesErrorLiveData.setValue(t.getMessage());
            }
        });
    }
}
