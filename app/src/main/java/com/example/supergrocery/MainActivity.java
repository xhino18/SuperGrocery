package com.example.supergrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.GetModels.ModelCategories;
import com.example.supergrocery.GetModels.ModelCategoriesData;
import com.example.supergrocery.GetModels.ModelDiscountedProducts;
import com.example.supergrocery.GetModels.ModelDiscountedProductsData;
import com.example.supergrocery.GetModels.ModelFreeDeliveryProducts;
import com.example.supergrocery.GetModels.ModelFreeDeliveryProductsData;
import com.example.supergrocery.Other.ProductsActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ItemClickInterface {
    ImageView iv_menuicon;
    SearchView searchview_main;
    TextView tv_see_all;
    Gson gson;
    List<ModelCategoriesData> categoriesDataList = new ArrayList<>();
    List<ModelCategoriesData> categoriesData = new ArrayList<>();
    List<ModelDiscountedProductsData> modelDiscountedProductsData = new ArrayList<>();
    List<ModelFreeDeliveryProductsData> modelFreeDeliveryProductsData = new ArrayList<>();
    AdapterShopProducts adapterShopProducts;
    AdapterCategories adapterCategories;
    AdapterDiscountedProducts adapterDiscountedProducts;
    AdapterFreeDeliveryProducts adapterFreeDeliveryProducts;
    RecyclerView recyclerView_categories, recycleview_discounted_products, recycleview_free_delivery_products;
    public static final String token_login = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMGQ1YmY4MDY0MzUxNjU4NWNiY2M2MDYxZGIxMGYyYTkyMTM3ZWU3MTgyOWRlZWYyMzA5NDc1ZDU1MTk5ZDVlNDk2MjMxOGE3YzEzOWZkMmEiLCJpYXQiOjE1ODIxMDc1OTIsIm5iZiI6MTU4MjEwNzU5MiwiZXhwIjoxNjEzNzI5OTkyLCJzdWIiOiIxNSIsInNjb3BlcyI6W119.j-4iSBeEN-OSICn_-1Q26hkB4i3x2KP3WnW9bueL96W_MISJkoNhlFuvfDUPGIvunvVndwMJpGtHPvTjSSt2U9EXDtzq2XLIy1s7nchEhjNhlBBtABuJ1TWo7IyCpz4IPzwdLw_q8-LbjrG6EUcy8O6ZhuROV5JL2iftaMoYHHqpVwxZL2o2YG_cJjSHs_PgQS1IgVmkgVakgg5-u8n28qT_QIS36mcectV5OYdK_eDIsaAwDtuZKWp7KcndSXECwI9S6_bYCaJw6hYTrcq-hY_v_nVLzjS9vtOymbnuzSVkGgaincnne3Kw4PXBEOMBFu3L7Bcp9feQT7p-ra6VIbZUP3Z14h_6St93M8XupeyQP2FhHnlfJZFEKgz4rVN2Qo8nltkm_3Pkum-yXIQrys3F6p2Md2d6-pndGyaLT5W_pbuX7NXf6Jsa8YzQit1m9nS_mYzBJ1j8TvDC8ZlmvCoK7Cm9DLNn-ipzOQ76nOWvJun3DRdgek0uYyB26RPvwXRKfAhNwkj3dGmY7ejAFxrj0MbtWda59OGoGDJZZrs0DCbVwjo370sPLC4HiCM7nbVI4Y-_y4-AKI-SQkzGANyM2YZGPxGUpQpMwxp8hG3L0o6fz8jRkdklLUorFFnMeJUzfNmMQg_wKx10V4BAconKdrJDBqsWdFddRkRlzyA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson = new GsonBuilder().create();
        init();
        getall(token_login);

    }

    public void init() {
        recyclerView_categories = findViewById(R.id.recycleview_food_categories);
        recyclerView_categories.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recycleview_discounted_products = findViewById(R.id.recycleview_discounted_products);
        recycleview_discounted_products.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recycleview_free_delivery_products = findViewById(R.id.recycleview_free_delivery_products);
        recycleview_free_delivery_products.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        tv_see_all = findViewById(R.id.tv_see_all);
        tv_see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        iv_menuicon = findViewById(R.id.iv_menuicon);
        iv_menuicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        searchview_main = findViewById(R.id.searchview_main);
        searchview_main.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        Call<ModelCategories> call = apiClient.getCategories();
        Call<ModelDiscountedProducts> call1 = apiClient.getDiscountedProducts();
        Call<ModelFreeDeliveryProducts> call2 = apiClient.getFreeDeliveryProducts();
        call.enqueue(new Callback<ModelCategories>() {
            @Override
            public void onResponse(Call<ModelCategories> call, Response<ModelCategories> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        categoriesDataList.addAll(response.body().getData());
                        adapterCategories = new AdapterCategories(MainActivity.this, categoriesDataList);
                        recyclerView_categories.setAdapter(adapterCategories);
                    } else {
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelCategories> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        call1.enqueue(new Callback<ModelDiscountedProducts>() {
            @Override
            public void onResponse(Call<ModelDiscountedProducts> call, Response<ModelDiscountedProducts> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        modelDiscountedProductsData.addAll(response.body().getData());
                        adapterDiscountedProducts = new AdapterDiscountedProducts(MainActivity.this, modelDiscountedProductsData);
                        recycleview_discounted_products.setAdapter(adapterDiscountedProducts);
                    } else {
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelDiscountedProducts> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        call2.enqueue(new Callback<ModelFreeDeliveryProducts>() {
            @Override
            public void onResponse(Call<ModelFreeDeliveryProducts> call, Response<ModelFreeDeliveryProducts> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        modelFreeDeliveryProductsData.addAll(response.body().getData());
                        adapterFreeDeliveryProducts = new AdapterFreeDeliveryProducts(MainActivity.this, modelFreeDeliveryProductsData);
                        recycleview_free_delivery_products.setAdapter(adapterFreeDeliveryProducts);
                    } else {
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelFreeDeliveryProducts> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void categoryClicked(ModelCategoriesData data) {
        Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
        intent.putExtra("cat_id", data.getId());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void searchCategory(String s) {

        List<ModelDiscountedProductsData> discountedProductsData = new ArrayList<>();


        categoriesData.addAll(categoriesDataList);
        for (int i = 0; i < categoriesData.size(); i++) {
            if (!categoriesData.get(i).getName().toUpperCase().contains(s.toUpperCase())) {
                categoriesData.remove(i);
                i--;
            }
        }
        if (!categoriesData.isEmpty()) {
            adapterCategories = new AdapterCategories(MainActivity.this, categoriesData);
            recycleview_discounted_products.setAdapter(adapterCategories);


        }
    }


}