package com.example.supergrocery.ProductModelActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.Adapters.AdapterMoreDiscountedProducts;
import com.example.supergrocery.Models.AllProductsData;
import com.example.supergrocery.Models.CategoriesData;
import com.example.supergrocery.Models.DiscountedProductsData;
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.Models.ModelMain;
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.Other.SaveData;
import com.example.supergrocery.Payment.PaymentActivity;
import com.example.supergrocery.R;
import com.example.supergrocery.ROOM.ItemsDB;
import com.example.supergrocery.ROOM.OrderItem;
import com.example.supergrocery.databinding.ActivityDiscountedProductsBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscountedProductsActivity extends AppCompatActivity implements ItemClickInterface{
    ActivityDiscountedProductsBinding binding;
    SaveData saveData;
    List<DiscountedProductsData> discountedProductsData=new ArrayList<>();
    AdapterMoreDiscountedProducts adapterMoreDiscountedProducts;
    Gson gson;
    int id,price;
    String name,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityDiscountedProductsBinding.inflate(getLayoutInflater());
        final View view= binding.getRoot();
        setContentView(view);

        init();
        getall(saveData.getToken());
    }

    private void init() {
        gson=new GsonBuilder().create();
        saveData=new SaveData(this);
        binding.recycleviewMoreDiscountedProducts.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        id=getIntent().getIntExtra("cat_id",-1);
        name=getIntent().getStringExtra("cat_name");
        price=getIntent().getIntExtra("cat_price",-1);
        image=getIntent().getStringExtra("cat_image");

        binding.bannerItemName.setText(name);
        binding.bannerItemPrice.setText(price+" ALL");
        Glide.with(this).load(Links.categories_images+image).into(binding.imageviewDiscountProduct);

        binding.ivBackimage.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        binding.buttonBuyNow.setOnClickListener(view -> {
            Intent intent=new Intent(DiscountedProductsActivity.this, PaymentActivity.class);
            intent.putExtra("payment_type","buy_now");
            intent.putExtra("item_price",price);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        binding.buttonAddToCart.setOnClickListener(view -> {
            OrderItem orderItemsModel = parseProductToOrderItems(id,name,image,price);
            boolean found = false;
            for (OrderItem basketOrderItem : ItemsDB.getInstance(this).orderItemDao().getAllItems()) {
                if (basketOrderItem.getId()==(orderItemsModel.getId())) {
                    found = true;
                    basketOrderItem.incrementQuantity();
                    ItemsDB.getInstance(this).orderItemDao().update(basketOrderItem);
                    Toast.makeText(this, "Added to basket!", Toast.LENGTH_SHORT).show();
                    break;

                }
            }
            if (!found) {
                ItemsDB.getInstance(this).orderItemDao().insert(orderItemsModel);
                Toast.makeText(this, "Added to basket!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void getall(String token) {
        API apiClient = ClientAPI.createAPI_With_Token(token);
        Call<ModelMain<List<DiscountedProductsData>>> call = apiClient.getDiscountedProducts();
        call.enqueue(new Callback<ModelMain<List<DiscountedProductsData>>>() {
            @Override
            public void onResponse(Call<ModelMain<List<DiscountedProductsData>>> call, Response<ModelMain<List<DiscountedProductsData>>> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        discountedProductsData.addAll(response.body().getData());
                        adapterMoreDiscountedProducts = new AdapterMoreDiscountedProducts(DiscountedProductsActivity.this, discountedProductsData);
                        binding.recycleviewMoreDiscountedProducts.setAdapter(adapterMoreDiscountedProducts);
                    } else {
                        Toast.makeText(DiscountedProductsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DiscountedProductsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelMain<List<DiscountedProductsData>>> call, Throwable t) {
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
    public void freeDeliveryClicked(AllProductsData data) {

    }

    @Override
    public void dicountedProductsClicked(DiscountedProductsData data) {
        Intent intent = new Intent(DiscountedProductsActivity.this, DiscountedProductsActivity.class);
        intent.putExtra("cat_id",data.getId());
        intent.putExtra("cat_name", data.getName());
        intent.putExtra("cat_price", data.getPrice());
        intent.putExtra("cat_image",data.getImage());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    public void categoryClicked(CategoriesData data) {

    }

    public OrderItem parseProductToOrderItems(int id,String name,String image,int price) {
        return new OrderItem(
                new Long(id),
                id,
                name,
                image,
                price,
                1

        );
    }
}
