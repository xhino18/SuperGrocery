package com.example.supergrocery.ProductModelActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.Adapters.AdapterShopProducts;
import com.example.supergrocery.ModelsGet.ShopProducts;
import com.example.supergrocery.ModelsGet.ShopProductsData;
import com.example.supergrocery.Interfaces.AddItemInBasket;
import com.example.supergrocery.Interfaces.ProductClickedInterface;
import com.example.supergrocery.MainActivity2;
import com.example.supergrocery.R;
import com.example.supergrocery.ROOM.ItemsDB;
import com.example.supergrocery.ROOM.OrderItem;
import com.example.supergrocery.databinding.ActivityProductsBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.supergrocery.MainActivity.token_login;

public class ProductsActivity extends AppCompatActivity implements AddItemInBasket, ProductClickedInterface {
    ActivityProductsBinding binding;

    Gson gson;
    List<ShopProductsData> modelShopProductsDataList = new ArrayList<>();
    List<OrderItem> orderItemsModels = new ArrayList<>();
    AdapterShopProducts adapterShopProducts;
    int catId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityProductsBinding.inflate(getLayoutInflater());
        final View view= binding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);


        init();

    }

    private void init() {

        binding.recycleviewShopProducts.setLayoutManager(new GridLayoutManager(ProductsActivity.this, 2));
        gson = new GsonBuilder().create();
        binding.ivBackimage.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        binding.cardviewBasketItems.setOnClickListener(v -> {
            Intent intent=new Intent(ProductsActivity.this, MainActivity2.class);
            intent.putExtra("goToBasket",true);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        });

        catId = getIntent().getIntExtra("cat_id", -1);
        binding.tvProductCategory.setText(getIntent().getStringExtra("cat_name"));
        System.out.println("Id contoller " + catId);
        getall(token_login, catId);
        getTotalQuantity();

        binding.searchviewMain2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    public void getall(String token, int catId) {
        API apiClient = ClientAPI.createAPI_With_Token(token);
        Call<ShopProducts> call = apiClient.getProducts(catId);
        call.enqueue(new Callback<ShopProducts>() {
            @Override
            public void onResponse(Call<ShopProducts> call, Response<ShopProducts> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        modelShopProductsDataList.addAll(response.body().getData());
                        adapterShopProducts = new AdapterShopProducts(ProductsActivity.this, modelShopProductsDataList);
                        binding.recycleviewShopProducts.setAdapter(adapterShopProducts);
                    } else {
                        Toast.makeText(ProductsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProductsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShopProducts> call, Throwable t) {
                Toast.makeText(ProductsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public OrderItem parseProductToOrderItems(ShopProductsData data) {
        return new OrderItem(
                new Long(data.getId()),
                data.getId(),
                data.getName(),
                data.getImage(),
                data.getPrice(),
                1

        );
    }

    @Override
    public void addtoBasket(ShopProductsData data) {
        OrderItem orderItemsModel = parseProductToOrderItems(data);
        boolean found = false;
        for (OrderItem basketOrderItem : ItemsDB.getInstance(this).orderItemDao().getAllItems()) {
            if (basketOrderItem.getId()==(orderItemsModel.getId())) {
                found = true;
                basketOrderItem.incrementQuantity();
                ItemsDB.getInstance(this).orderItemDao().update(basketOrderItem);
                getTotalQuantity();
                Toast.makeText(this, "Added to basket!", Toast.LENGTH_SHORT).show();
                break;

            }
        }
        if (!found) {
            ItemsDB.getInstance(this).orderItemDao().insert(orderItemsModel);
            getTotalQuantity();
            Toast.makeText(this, "Added to basket!", Toast.LENGTH_SHORT).show();

        }

    }

    private void getTotalQuantity() {
        int totalquantity = 0;
        orderItemsModels = ItemsDB.getInstance(this).orderItemDao().getAllItems();
        for (int i = 0; i < orderItemsModels.size(); i++) {
            totalquantity = totalquantity + orderItemsModels.get(i).getQuantity();
        }
        if (totalquantity == 0) {
            binding.tvBasketQuantity.setVisibility(View.GONE);
        } else {
            binding.tvBasketQuantity.setVisibility(View.VISIBLE);
        }
        binding.tvBasketQuantity.setText(totalquantity + "");
    }

    @Override
    public void productClicked(ShopProductsData data) {
        Intent intent = new Intent(ProductsActivity.this, SelectedProductActivity.class);
        intent.putExtra("cat_id", catId);
        intent.putExtra("prod_id", data.getId());
        intent.putExtra("prod_name", data.getName());
        intent.putExtra("prod_price", data.getPrice());
        intent.putExtra("prod_description", data.getDescription());
        intent.putExtra("prod_image", data.getImage());


        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
    private void searchCategory(String s) {
        List<ShopProductsData> data = new ArrayList<>();
        data.addAll(modelShopProductsDataList);
        for (int i = 0; i < data.size(); i++) {
            if (!data.get(i).getName().toUpperCase().contains(s.toUpperCase())) {
                data.remove(i);
                i--;
            }
        }
        adapterShopProducts = new AdapterShopProducts(ProductsActivity.this,data);
        binding.recycleviewShopProducts.setAdapter(adapterShopProducts);
    }

}