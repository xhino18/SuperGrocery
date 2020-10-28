package com.example.supergrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.Adapters.AdapterCategories;
import com.example.supergrocery.Adapters.AdapterBanner;
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
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.ProductModelActivity.DiscountedProductsActivity;
import com.example.supergrocery.ProductModelActivity.FreeDeliveryActivity;
import com.example.supergrocery.ProductModelActivity.ProductsActivity;
import com.example.supergrocery.Other.SaveData;
import com.example.supergrocery.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.media.CamcorderProfile.get;

public class MainActivity extends AppCompatActivity implements ItemClickInterface {
    ActivityMainBinding activityMainBinding;

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
    boolean doubleBackToExitPressedOnce = false;
    public static final String token_login = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMGQ1YmY4MDY0MzUxNjU4NWNiY2M2MDYxZGIxMGYyYTkyMTM3ZWU3MTgyOWRlZWYyMzA5NDc1ZDU1MTk5ZDVlNDk2MjMxOGE3YzEzOWZkMmEiLCJpYXQiOjE1ODIxMDc1OTIsIm5iZiI6MTU4MjEwNzU5MiwiZXhwIjoxNjEzNzI5OTkyLCJzdWIiOiIxNSIsInNjb3BlcyI6W119.j-4iSBeEN-OSICn_-1Q26hkB4i3x2KP3WnW9bueL96W_MISJkoNhlFuvfDUPGIvunvVndwMJpGtHPvTjSSt2U9EXDtzq2XLIy1s7nchEhjNhlBBtABuJ1TWo7IyCpz4IPzwdLw_q8-LbjrG6EUcy8O6ZhuROV5JL2iftaMoYHHqpVwxZL2o2YG_cJjSHs_PgQS1IgVmkgVakgg5-u8n28qT_QIS36mcectV5OYdK_eDIsaAwDtuZKWp7KcndSXECwI9S6_bYCaJw6hYTrcq-hY_v_nVLzjS9vtOymbnuzSVkGgaincnne3Kw4PXBEOMBFu3L7Bcp9feQT7p-ra6VIbZUP3Z14h_6St93M8XupeyQP2FhHnlfJZFEKgz4rVN2Qo8nltkm_3Pkum-yXIQrys3F6p2Md2d6-pndGyaLT5W_pbuX7NXf6Jsa8YzQit1m9nS_mYzBJ1j8TvDC8ZlmvCoK7Cm9DLNn-ipzOQ76nOWvJun3DRdgek0uYyB26RPvwXRKfAhNwkj3dGmY7ejAFxrj0MbtWda59OGoGDJZZrs0DCbVwjo370sPLC4HiCM7nbVI4Y-_y4-AKI-SQkzGANyM2YZGPxGUpQpMwxp8hG3L0o6fz8jRkdklLUorFFnMeJUzfNmMQg_wKx10V4BAconKdrJDBqsWdFddRkRlzyA";
    SaveData saveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        final View view = activityMainBinding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        init();
//        getall(token_login);
        getall(saveData.getToken());

    }

    public void init() {
        gson = new GsonBuilder().create();
        saveData = new SaveData(this);
        activityMainBinding.recycleviewFoodCategories.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        activityMainBinding.recycleviewBanner.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        activityMainBinding.recycleviewDicountedProducts.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
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
        Call<Banner> call1 = apiClient.getBanners();
        Call<AllProducts> call2 = apiClient.getFreeDeliveryProducts();
        Call<DiscountedProducts> call3 = apiClient.getDiscountedProducts();
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
        call1.enqueue(new Callback<Banner>() {
            @Override
            public void onResponse(Call<Banner> call, Response<Banner> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        bannerData.addAll(response.body().getData());
                        adapterBanner = new AdapterBanner(MainActivity.this,bannerData);
                        activityMainBinding.recycleviewBanner.setAdapter(adapterBanner);

                    } else {
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Banner> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        call2.enqueue(new Callback<AllProducts>() {
            @Override
            public void onResponse(Call<AllProducts> call, Response<AllProducts> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        allProductsData.addAll(response.body().getData());
                        adapterFreeDeliveryProducts = new AdapterFreeDeliveryProducts(MainActivity.this, allProductsData);
                        activityMainBinding.recycleviewFreeDeliveryProducts.setAdapter(adapterFreeDeliveryProducts);
                    } else {
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AllProducts> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        call3.enqueue(new Callback<DiscountedProducts>() {
            @Override
            public void onResponse(Call<DiscountedProducts> call, Response<DiscountedProducts> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        modelDiscountedProductsData.addAll(response.body().getData());
                        adapterDiscountedProducts = new AdapterDiscountedProducts(MainActivity.this, modelDiscountedProductsData);
                        activityMainBinding.recycleviewDicountedProducts.setAdapter(adapterDiscountedProducts);
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
    }


    @Override
    public void freeDeliveryClicked(AllProductsData data) {
        Intent intent = new Intent(MainActivity.this, FreeDeliveryActivity.class);
        intent.putExtra("cat_name", data.getName());
        intent.putExtra("cat_price", data.getPrice());
        intent.putExtra("cat_image",data.getImage());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    public void dicountedProductsClicked(DiscountedProductsData data) {
        Intent intent = new Intent(MainActivity.this, DiscountedProductsActivity.class);
        intent.putExtra("cat_id", data.getId());
        intent.putExtra("cat_name", data.getName());
        intent.putExtra("cat_price", data.getPrice());
        intent.putExtra("cat_image",data.getImage());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void categoryClicked(CategoriesData data) {
        Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
        intent.putExtra("cat_id", data.getId());
        intent.putExtra("cat_name", data.getName());
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
            adapterBanner = new AdapterBanner(MainActivity.this,bannerData);
            activityMainBinding.recycleviewBanner.setAdapter(adapterBanner);


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
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}