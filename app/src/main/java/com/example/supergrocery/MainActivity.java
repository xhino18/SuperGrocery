package com.example.supergrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.Adapters.AdapterCategories;
import com.example.supergrocery.Adapters.AdapterDiscountedProducts;
import com.example.supergrocery.Adapters.AdapterFreeDeliveryProducts;
import com.example.supergrocery.Adapters.AdapterShopProducts;
import com.example.supergrocery.GetModels.Categories;
import com.example.supergrocery.GetModels.CategoriesData;
import com.example.supergrocery.GetModels.DiscountedProducts;
import com.example.supergrocery.GetModels.DiscountedProductsData;
import com.example.supergrocery.GetModels.FreeDeliveryProducts;
import com.example.supergrocery.GetModels.FreeDeliveryProductsData;
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.GetModels.ModelCategories;
import com.example.supergrocery.GetModels.ModelCategoriesData;
import com.example.supergrocery.GetModels.ModelDiscountedProducts;
import com.example.supergrocery.GetModels.ModelDiscountedProductsData;
import com.example.supergrocery.GetModels.ModelFreeDeliveryProducts;
import com.example.supergrocery.GetModels.ModelFreeDeliveryProductsData;
import com.example.supergrocery.Other.ProductsActivity;
import com.example.supergrocery.Other.SaveData;
import com.example.supergrocery.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ItemClickInterface {
    ActivityMainBinding activityMainBinding;
    Gson gson;
    List<CategoriesData> categoriesDataList = new ArrayList<>();
    List<CategoriesData> categoriesData = new ArrayList<>();
    List<DiscountedProductsData> modelDiscountedProductsData = new ArrayList<>();
    List<FreeDeliveryProductsData> modelFreeDeliveryProductsData = new ArrayList<>();
    AdapterCategories adapterCategories;
    AdapterDiscountedProducts adapterDiscountedProducts;
    AdapterFreeDeliveryProducts adapterFreeDeliveryProducts;
    boolean doubleBackToExitPressedOnce = false;
    public static final String token_login = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMGQ1YmY4MDY0MzUxNjU4NWNiY2M2MDYxZGIxMGYyYTkyMTM3ZWU3MTgyOWRlZWYyMzA5NDc1ZDU1MTk5ZDVlNDk2MjMxOGE3YzEzOWZkMmEiLCJpYXQiOjE1ODIxMDc1OTIsIm5iZiI6MTU4MjEwNzU5MiwiZXhwIjoxNjEzNzI5OTkyLCJzdWIiOiIxNSIsInNjb3BlcyI6W119.j-4iSBeEN-OSICn_-1Q26hkB4i3x2KP3WnW9bueL96W_MISJkoNhlFuvfDUPGIvunvVndwMJpGtHPvTjSSt2U9EXDtzq2XLIy1s7nchEhjNhlBBtABuJ1TWo7IyCpz4IPzwdLw_q8-LbjrG6EUcy8O6ZhuROV5JL2iftaMoYHHqpVwxZL2o2YG_cJjSHs_PgQS1IgVmkgVakgg5-u8n28qT_QIS36mcectV5OYdK_eDIsaAwDtuZKWp7KcndSXECwI9S6_bYCaJw6hYTrcq-hY_v_nVLzjS9vtOymbnuzSVkGgaincnne3Kw4PXBEOMBFu3L7Bcp9feQT7p-ra6VIbZUP3Z14h_6St93M8XupeyQP2FhHnlfJZFEKgz4rVN2Qo8nltkm_3Pkum-yXIQrys3F6p2Md2d6-pndGyaLT5W_pbuX7NXf6Jsa8YzQit1m9nS_mYzBJ1j8TvDC8ZlmvCoK7Cm9DLNn-ipzOQ76nOWvJun3DRdgek0uYyB26RPvwXRKfAhNwkj3dGmY7ejAFxrj0MbtWda59OGoGDJZZrs0DCbVwjo370sPLC4HiCM7nbVI4Y-_y4-AKI-SQkzGANyM2YZGPxGUpQpMwxp8hG3L0o6fz8jRkdklLUorFFnMeJUzfNmMQg_wKx10V4BAconKdrJDBqsWdFddRkRlzyA";
    SaveData saveData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        final View view=activityMainBinding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        init();
//        getall(token_login);
        getall(saveData.getToken());

    }

    public void init() {
        gson = new GsonBuilder().create();
        saveData=new SaveData(this);
        activityMainBinding.recycleviewFoodCategories.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        activityMainBinding.recycleviewDiscountedProducts.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        activityMainBinding.recycleviewFreeDeliveryProducts.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        activityMainBinding.tvSeeAll.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        activityMainBinding.ivMenuicon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        activityMainBinding.searchviewMain.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    }

    public void getall(String token) {
        API apiClient = ClientAPI.createAPI_With_Token(token);
        Call<Categories> call = apiClient.getCategories();
        Call<DiscountedProducts> call1 = apiClient.getBanners();
        Call<FreeDeliveryProducts> call2 = apiClient.getFreeDeliveryProducts();
        call.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        categoriesDataList.addAll(response.body().getData());
                        adapterCategories = new AdapterCategories(MainActivity.this, categoriesDataList);
                        activityMainBinding.recycleviewFoodCategories.setAdapter(adapterCategories);
                    } else {
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        call1.enqueue(new Callback<DiscountedProducts>() {
            @Override
            public void onResponse(Call<DiscountedProducts> call, Response<DiscountedProducts> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        modelDiscountedProductsData.addAll(response.body().getData());
                        adapterDiscountedProducts = new AdapterDiscountedProducts(MainActivity.this, modelDiscountedProductsData);
                        activityMainBinding.recycleviewDiscountedProducts.setAdapter(adapterDiscountedProducts);
                    } else {
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DiscountedProducts> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        call2.enqueue(new Callback<FreeDeliveryProducts>() {
            @Override
            public void onResponse(Call<FreeDeliveryProducts> call, Response<FreeDeliveryProducts> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        modelFreeDeliveryProductsData.addAll(response.body().getData());
                        adapterFreeDeliveryProducts = new AdapterFreeDeliveryProducts(MainActivity.this, modelFreeDeliveryProductsData);
                        activityMainBinding.recycleviewFreeDeliveryProducts.setAdapter(adapterFreeDeliveryProducts);
                    } else {
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FreeDeliveryProducts> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void categoryClicked(CategoriesData data) {
        Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
        intent.putExtra("cat_id", data.getId());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
            adapterCategories = new AdapterCategories(MainActivity.this, categoriesData);
            activityMainBinding.recycleviewDiscountedProducts.setAdapter(adapterCategories);


        }
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}