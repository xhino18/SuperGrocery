package com.example.supergrocery.ProductModelActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.Adapters.AdapterGetProductByID;
import com.example.supergrocery.Adapters.AdapterMoreShopProducts;
import com.example.supergrocery.ModelsGet.GetProductByID;
import com.example.supergrocery.ModelsGet.GetProductByIDData;
import com.example.supergrocery.ModelsGet.ShopProducts;
import com.example.supergrocery.ModelsGet.ShopProductsData;
import com.example.supergrocery.Interfaces.ProductClickedInterface;
import com.example.supergrocery.Other.Links;
import com.example.supergrocery.Other.SaveData;
import com.example.supergrocery.Payment.PaymentActivity;
import com.example.supergrocery.R;
import com.example.supergrocery.ROOM.ItemsDB;
import com.example.supergrocery.ROOM.OrderItem;
import com.example.supergrocery.databinding.ActivitySelectedProductBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectedProductActivity extends AppCompatActivity implements ProductClickedInterface{
    ActivitySelectedProductBinding binding;
    SaveData saveData;
    List<ShopProductsData> shopProductsData=new ArrayList<>();
    AdapterGetProductByID adapterGetProductByID;
    GetProductByIDData getProductByIDData;
    AdapterMoreShopProducts adapterMoreShopProducts;
    Gson gson;
    String name,image,description;
    int cat_id,prod_id,price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySelectedProductBinding.inflate(getLayoutInflater());
        final View view=binding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        init();
        getall(saveData.getToken(),cat_id,prod_id);

    }

    private void init() {
        gson=new GsonBuilder().create();
        saveData=new SaveData(this);
        binding.recycleviewMoreProducts.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        cat_id=getIntent().getIntExtra("cat_id",-1);
        prod_id=getIntent().getIntExtra("prod_id",-1);
        name=getIntent().getStringExtra("prod_name");
        price=(getIntent().getIntExtra("prod_price",-1));
        description=getIntent().getStringExtra("prod_description");
        image=getIntent().getStringExtra("prod_image");

        binding.productsItemName.setText(name);
        binding.productsItemPrice.setText(price+" ALL");
        binding.productsItemDescription.setText(Html.fromHtml(description));
        Glide.with(SelectedProductActivity.this).load(Links.categories_images+image).into(binding.imageviewProducts);

        binding.ivBackimage.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        binding.buttonBuyNow.setOnClickListener(view -> {
            Intent intent=new Intent(SelectedProductActivity.this,PaymentActivity.class);
            intent.putExtra("payment_type","buy_now");
            intent.putExtra("item_price",price);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        binding.buttonAddToCart.setOnClickListener(view -> {
            OrderItem orderItemsModel = parseProductToOrderItems(prod_id,name,image,price);
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
    public void getall(String token,int cat_id,int prod_id) {
        API apiClient = ClientAPI.createAPI_With_Token(token);
        Call<ShopProducts> call = apiClient.getProducts(cat_id);
        Call<GetProductByID> call1= apiClient.getProductByID(prod_id);
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
//        call1.enqueue(new Callback<GetProductByID>() {
//            @Override
//            public void onResponse(Call<GetProductByID> call, Response<GetProductByID> response) {
//                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
//                    if (!response.body().getError()) {
//                        getProductByIDData=new GetProductByIDData(
//                                response.body().getData().getId(),
//                                response.body().getData().getName(),
//                                response.body().getData().getImage(),
//                                response.body().getData().getDescription(),
//                                response.body().getData().getPrice(),
//                                response.body().getData().getStock(),
//                                response.body().getData().getUnit(),
//                                response.body().getData().getSizes(),
//                                response.body().getData().getMinimum_order(),
//                                response.body().getData().getMaximum_order(),
//                                response.body().getData().getDiscount(),
//                                response.body().getData().getDiscounted_price(),
//                                response.body().getData().getPrices(),
//                                response.body().getData().getMore_images());
//                        binding.productsItemName.setText(response.body().getData().getName());
//                        binding.productsItemPrice.setText(Integer.toString(response.body().getData().getPrice()));
//                        binding.productsItemDescription.setText(response.body().getData().getDescription());
//                        Glide.with(SelectedProductActivity.this).load(Links.categories_images+response.body().getData().getImage()).into(binding.imageviewProducts);
//
//                    } else {
//                        Toast.makeText(SelectedProductActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(SelectedProductActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetProductByID> call, Throwable t) {
//                Toast.makeText(SelectedProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(SelectedProductActivity.this, "ops", Toast.LENGTH_SHORT).show();
//            }
//        });

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
        intent.putExtra("cat_id", cat_id);
        intent.putExtra("prod_id", data.getId());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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