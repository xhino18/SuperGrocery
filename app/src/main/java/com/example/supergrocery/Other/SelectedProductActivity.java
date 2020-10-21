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
import com.example.supergrocery.Adapters.AdapterAllProducts;
import com.example.supergrocery.Adapters.AdapterDiscountedProducts;
import com.example.supergrocery.Adapters.AdapterMoreShopProducts;
import com.example.supergrocery.Adapters.AdapterShopProducts;
import com.example.supergrocery.GetModels.AllProducts;
import com.example.supergrocery.GetModels.AllProductsData;
import com.example.supergrocery.GetModels.DiscountedProducts;
import com.example.supergrocery.GetModels.DiscountedProductsData;
import com.example.supergrocery.GetModels.ShopProducts;
import com.example.supergrocery.GetModels.ShopProductsData;
import com.example.supergrocery.Interfaces.ProductClickedInterface;
import com.example.supergrocery.MainActivity2;
import com.example.supergrocery.Payment.PaymentActivity;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.ActivitySelectedProductBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectedProductActivity extends AppCompatActivity implements ProductClickedInterface {
    ActivitySelectedProductBinding binding;
    SaveData saveData;
    List<ShopProductsData> shopProductsData=new ArrayList<>();
    AdapterMoreShopProducts adapterMoreShopProducts;
    Gson gson;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySelectedProductBinding.inflate(getLayoutInflater());
        final View view=binding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        init();
        getall(saveData.getToken(),id);
    }

    private void init() {
        gson=new GsonBuilder().create();
        saveData=new SaveData(this);
        binding.recycleviewMoreProducts.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        id=getIntent().getIntExtra("cat_id",-1);
        String name=getIntent().getStringExtra("cat_name");
        int price=getIntent().getIntExtra("cat_price",-1);
        String image=getIntent().getStringExtra("cat_image");
        String description=getIntent().getStringExtra("cat_description");

        binding.productsItemName.setText(name);
        binding.productsItemPrice.setText(price+" ALL");
        binding.productsItemDescription.setText(description);
        Glide.with(this).load(Links.categories_images+image).into(binding.imageviewProducts);

        binding.ivBackimage.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        binding.buttonBuyNow.setOnClickListener(view -> {
            Intent intent=new Intent(SelectedProductActivity.this, PaymentActivity.class);
            String total=String.valueOf(price);
            intent.putExtra("payment_type","buy_now");
            intent.putExtra("item_price",total);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        });
    }

    public void getall(String token,int id) {
        API apiClient = ClientAPI.createAPI_With_Token(token);
        Call<ShopProducts> call = apiClient.getProducts(id);
        call.enqueue(new Callback<ShopProducts>() {
            @Override
            public void onResponse(Call<ShopProducts> call, Response<ShopProducts> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        shopProductsData.addAll(response.body().getData());
                        adapterMoreShopProducts = new AdapterMoreShopProducts(SelectedProductActivity.this,shopProductsData);
                        binding.recycleviewMoreProducts.setAdapter(adapterMoreShopProducts);
                    } else {
                        Toast.makeText(SelectedProductActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SelectedProductActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShopProducts> call, Throwable t) {
                Toast.makeText(SelectedProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void productClicked(ShopProductsData data) {
        Intent intent = new Intent(SelectedProductActivity.this, SelectedProductActivity.class);
        intent.putExtra("cat_id", id);
        intent.putExtra("cat_name", data.getName());
        intent.putExtra("cat_description", data.getDescription());
        intent.putExtra("cat_price", data.getPrice());
        intent.putExtra("cat_image", data.getImage());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}