package com.example.supergrocery.Other;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.Adapters.AdapterFreeDeliveryProducts;
import com.example.supergrocery.GetModels.CategoriesData;
import com.example.supergrocery.GetModels.DiscountedProductsData;
import com.example.supergrocery.GetModels.FreeDeliveryProducts;
import com.example.supergrocery.GetModels.FreeDeliveryProductsData;
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.ActivityFreeDeliveryBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreeDeliveryActivity extends AppCompatActivity implements ItemClickInterface {
    ActivityFreeDeliveryBinding binding;
    SaveData saveData;
    List<FreeDeliveryProductsData> freeDeliveryProductsData=new ArrayList<>();
    AdapterFreeDeliveryProducts adapterFreeDeliveryProducts;
    Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityFreeDeliveryBinding.inflate(getLayoutInflater());
        final View view= binding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        init();
        getall(saveData.getToken());

    }

    private void init() {
        gson=new GsonBuilder().create();
        saveData=new SaveData(this);
        binding.recycleviewMoreFreeProducts.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        String name=getIntent().getStringExtra("cat_name");
        int price=getIntent().getIntExtra("cat_price",-1);
        String image=getIntent().getStringExtra("cat_image");

        binding.bannerItemName.setText(name);
        binding.bannerItemPrice.setText(price+" ALL");
        Glide.with(this).load(Links.categories_images+image).into(binding.imageviewDiscountProduct);

        binding.ivBackimage.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        binding.buttonAddToCart.setOnClickListener(v -> {


        });
    }
    public void getall(String token) {
        API apiClient = ClientAPI.createAPI_With_Token(token);
        Call<FreeDeliveryProducts> call = apiClient.getFreeDeliveryProducts();
        call.enqueue(new Callback<FreeDeliveryProducts>() {
            @Override
            public void onResponse(Call<FreeDeliveryProducts> call, Response<FreeDeliveryProducts> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        freeDeliveryProductsData.addAll(response.body().getData());
                        adapterFreeDeliveryProducts = new AdapterFreeDeliveryProducts(FreeDeliveryActivity.this, freeDeliveryProductsData);
                        binding.recycleviewMoreFreeProducts.setAdapter(adapterFreeDeliveryProducts);
                    } else {
                        Toast.makeText(FreeDeliveryActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FreeDeliveryActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FreeDeliveryProducts> call, Throwable t) {
                Toast.makeText(FreeDeliveryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void freeDeliveryClicked(FreeDeliveryProductsData data) {
        Intent intent = new Intent(FreeDeliveryActivity.this, FreeDeliveryActivity.class);
        intent.putExtra("cat_name", data.getName());
        intent.putExtra("cat_price", data.getPrice());
        intent.putExtra("cat_image",data.getImage());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


    }

    @Override
    public void dicountedProductsClicked(DiscountedProductsData data) {

    }

    @Override
    public void categoryClicked(CategoriesData data) {

    }
}