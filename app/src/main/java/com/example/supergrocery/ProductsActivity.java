package com.example.supergrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.Adapters.AdapterCategories;
import com.example.supergrocery.Adapters.AdapterShopProducts;
import com.example.supergrocery.Models.ModelCategories;
import com.example.supergrocery.Models.ModelDiscountedProducts;
import com.example.supergrocery.Models.ModelFreeDeliveryProducts;
import com.example.supergrocery.Models.ModelShopProducts;
import com.example.supergrocery.Models.ModelShopProductsData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.supergrocery.MainActivity.token_login;

public class ProductsActivity extends AppCompatActivity {
    ImageView iv_backimage;
Gson gson;
List<ModelShopProductsData> modelShopProductsDataList=new ArrayList<>();
RecyclerView recycleview_shop_products;
AdapterShopProducts adapterShopProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);


        recycleview_shop_products=findViewById(R.id.recycleview_shop_products);
        recycleview_shop_products.setLayoutManager(new GridLayoutManager(ProductsActivity.this, 2));
        gson=new GsonBuilder().create();
        iv_backimage=findViewById(R.id.iv_backimage);
        iv_backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        int catId = getIntent().getIntExtra("cat_id", -1);
        System.out.println("Id contoller " + catId);
        getall(token_login,catId);
    }
        public void getall (String token,int catId){
            API apiClient = ClientAPI.createAPI_With_Token(token);
            Call<ModelShopProducts> call = apiClient.getProducts(catId);
            call.enqueue(new Callback<ModelShopProducts>() {
                @Override
                public void onResponse(Call<ModelShopProducts> call, Response<ModelShopProducts> response) {
                    if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                        if (!response.body().getError()) {
                            modelShopProductsDataList.addAll(response.body().getData());
                            adapterShopProducts = new AdapterShopProducts(ProductsActivity.this, modelShopProductsDataList);
                            recycleview_shop_products.setAdapter(adapterShopProducts);
                        } else {
                            Toast.makeText(ProductsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ProductsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelShopProducts> call, Throwable t) {
                    Toast.makeText(ProductsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}