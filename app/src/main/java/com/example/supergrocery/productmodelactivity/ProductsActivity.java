package com.example.supergrocery.productmodelactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.room.FtsOptions;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.supergrocery.MainViewModel;
import com.example.supergrocery.adapters.AdapterShopProducts;
import com.example.supergrocery.api.API;
import com.example.supergrocery.models.ModelMain;
import com.example.supergrocery.models.ShopProductsData;
import com.example.supergrocery.interfaces.AddItemInBasket;
import com.example.supergrocery.interfaces.ProductClickedInterface;
import com.example.supergrocery.MainActivity;
import com.example.supergrocery.other.SaveData;
import com.example.supergrocery.R;
import com.example.supergrocery.room.ItemsDB;
import com.example.supergrocery.room.OrderItem;
import com.example.supergrocery.databinding.ActivityProductsBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class ProductsActivity extends AppCompatActivity implements AddItemInBasket, ProductClickedInterface {
    ActivityProductsBinding binding;
    SaveData saveData;
    Gson gson;
    OrderItem orderItemsModel;
    boolean found = false;
    //    List<ShopProductsData> modelShopProductsDataList = new ArrayList<>();
//    List<OrderItem> orderItemsModels = new ArrayList<>();
    AdapterShopProducts adapterShopProducts;
    int catId;
    @Inject
    API api;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding = ActivityProductsBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        init();
        getAll();
        getProducts();
    }

    private void init() {
        adapterShopProducts = new AdapterShopProducts(ProductsActivity.this);
        binding.recycleviewShopProducts.setAdapter(adapterShopProducts);
        gson = new GsonBuilder().create();
        saveData = new SaveData(this);
        binding.ivBackimage.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        binding.cardviewBasketItems.setOnClickListener(v -> {
            Intent intent = new Intent(ProductsActivity.this, MainActivity.class);
            intent.putExtra("goToBasket", true);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        });

        catId = getIntent().getIntExtra("cat_id", -1);
        binding.tvProductCategory.setText(getIntent().getStringExtra("cat_name"));
        System.out.println("Id contoller " + catId);
        getTotalQuantity();
        mainViewModel.getShopProducts(catId);

//        binding.searchviewMain2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                searchCategory(newText);
//                return true;
//            }
//        });
        mainViewModel.getBasketItems();

    }

    private void getProducts() {
        mainViewModel.getShopProductsLiveData().observe(this, listModelMain -> {
            if (!listModelMain.getError()) {
                adapterShopProducts.submitList(listModelMain.getData());
            } else {
                Toast.makeText(this, listModelMain.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAll() {
        mainViewModel.getOrderItemLiveData().observe(this, orderItems -> {

        });
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public OrderItem parseProductToOrderItems(ShopProductsData data) {
        return new OrderItem(
                data.getId(),
                data.getName(),
                data.getImage(),
                data.getPrice(),
                1

        );
    }
//    @Override
//    public void addtoBasket(ShopProductsData data) {
//
//        orderItemsModel= parseProductToOrderItems(data);
//        boolean found = false;
//
//
//        for (OrderItem basketOrderItem : ItemsDB.getInstance(this).orderItemDao().getAllItems()) {
//                if (basketOrderItem.getId() == (orderItemsModel.getId())) {
//                    found = true;
//                    basketOrderItem.incrementQuantity();
//                    mainViewModel.updateBasket(basketOrderItem);
//                    getTotalQuantity();
//                    Toast.makeText(this, "Added to basket!", Toast.LENGTH_SHORT).show();
//                    break;
//
//                }
//            }
//            if (!found) {
//                mainViewModel.insertInBasket(orderItemsModel);
//                getTotalQuantity();
//                Toast.makeText(this, "Added to basket!", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }

    @Override
    public void addtoBasket(ShopProductsData data) {
        orderItemsModel = parseProductToOrderItems(data);
        mainViewModel.getOrderItemLiveData().observe(this, orderItems -> {
            mainViewModel.incrementQuantity(orderItemsModel);
            getTotalQuantity();
            Toast.makeText(this, "Added to basket!", Toast.LENGTH_SHORT).show();

        });
    }

    private void getTotalQuantity() {
        int totalquantity = 0;
//        orderItemsModels = ItemsDB.getInstance(this).orderItemDao().allItems;
//        for (int i = 0; i < orderItemsModels.size(); i++) {
//            totalquantity = totalquantity + orderItemsModels.get(i).getQuantity();
//        }
//        if (totalquantity == 0) {
//            binding.tvBasketQuantity.setVisibility(View.GONE);
//        } else {
//            binding.tvBasketQuantity.setVisibility(View.VISIBLE);
//        }
//        binding.tvBasketQuantity.setText(totalquantity + "");
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
//    private void searchCategory(String s) {
//        List<ShopProductsData> data = new ArrayList<>();
//        data.addAll(modelShopProductsDataList);
//        for (int i = 0; i < data.size(); i++) {
//            if (!data.get(i).getName().toUpperCase().contains(s.toUpperCase())) {
//                data.remove(i);
//                i--;
//            }
//        }
//        adapterShopProducts = new AdapterShopProducts(ProductsActivity.this,data);
//        binding.recycleviewShopProducts.setAdapter(adapterShopProducts);
//    }

}