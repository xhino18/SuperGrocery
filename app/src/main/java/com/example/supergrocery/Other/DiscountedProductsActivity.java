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
import com.example.supergrocery.Adapters.AdapterDiscountedProducts;
import com.example.supergrocery.GetModels.CategoriesData;
import com.example.supergrocery.GetModels.DiscountedProducts;
import com.example.supergrocery.GetModels.DiscountedProductsData;
import com.example.supergrocery.GetModels.FreeDeliveryProductsData;
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.ActivityDiscountedProductsBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscountedProductsActivity extends AppCompatActivity implements ItemClickInterface {
    ActivityDiscountedProductsBinding binding;
    SaveData saveData;
    List<DiscountedProductsData> discountedProductsData=new ArrayList<>();
    AdapterDiscountedProducts adapterDiscountedProducts;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityDiscountedProductsBinding.inflate(getLayoutInflater());
        final View view= binding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        init();
        getall(saveData.getToken());
    }

    private void init() {
        gson=new GsonBuilder().create();
        saveData=new SaveData(this);
        binding.recycleviewMoreDiscountedProducts.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
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
    }

    public void getall(String token) {
        API apiClient = ClientAPI.createAPI_With_Token(token);
        Call<DiscountedProducts> call = apiClient.getDiscountedProducts();
        call.enqueue(new Callback<DiscountedProducts>() {
            @Override
            public void onResponse(Call<DiscountedProducts> call, Response<DiscountedProducts> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        discountedProductsData.addAll(response.body().getData());
                        adapterDiscountedProducts = new AdapterDiscountedProducts(DiscountedProductsActivity.this, discountedProductsData);
                        binding.recycleviewMoreDiscountedProducts.setAdapter(adapterDiscountedProducts);
                    } else {
                        Toast.makeText(DiscountedProductsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DiscountedProductsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DiscountedProducts> call, Throwable t) {
                Toast.makeText(DiscountedProductsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

    }

    @Override
    public void dicountedProductsClicked(DiscountedProductsData data) {
        Intent intent = new Intent(DiscountedProductsActivity.this, DiscountedProductsActivity.class);
        intent.putExtra("cat_name", data.getName());
        intent.putExtra("cat_price", data.getPrice());
        intent.putExtra("cat_image",data.getImage());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    public void categoryClicked(CategoriesData data) {

    }
}