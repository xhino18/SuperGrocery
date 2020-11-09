package com.example.supergrocery.productmodelactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.supergrocery.MainViewModel;
import com.example.supergrocery.adapters.AdapterMoreFreeDeliveryProducts;
import com.example.supergrocery.api.API;
import com.example.supergrocery.models.AllProductsData;
import com.example.supergrocery.models.CategoriesData;
import com.example.supergrocery.models.DiscountedProductsData;
import com.example.supergrocery.interfaces.ItemClickInterface;
import com.example.supergrocery.models.ModelMain;
import com.example.supergrocery.other.Links;
import com.example.supergrocery.other.SaveData;
import com.example.supergrocery.payment.PaymentActivity;
import com.example.supergrocery.R;
import com.example.supergrocery.room.ItemsDB;
import com.example.supergrocery.room.OrderItem;
import com.example.supergrocery.databinding.ActivityFreeDeliveryBinding;
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
public class FreeDeliveryActivity extends AppCompatActivity implements ItemClickInterface {
    ActivityFreeDeliveryBinding binding;
    SaveData saveData;
    List<AllProductsData> allProductsData=new ArrayList<>();
    AdapterMoreFreeDeliveryProducts adapterMoreFreeDeliveryProducts;
    Gson gson;
    int id,price;
    String name,image;
    @Inject
    API api;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel=new ViewModelProvider(this).get(MainViewModel.class);
        binding =ActivityFreeDeliveryBinding.inflate(getLayoutInflater());
        final View view= binding.getRoot();
        setContentView(view);

        init();
        getFreeDeliveryProducts();

    }

    private void init() {
        gson=new GsonBuilder().create();
        saveData=new SaveData(this);
        adapterMoreFreeDeliveryProducts = new AdapterMoreFreeDeliveryProducts(FreeDeliveryActivity.this);
        binding.recycleviewMoreFreeProducts.setAdapter(adapterMoreFreeDeliveryProducts);
        binding.recycleviewMoreFreeProducts.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        id=getIntent().getIntExtra("cat_id",-1);
        name=getIntent().getStringExtra("cat_name");
        price=getIntent().getIntExtra("cat_price",-1);
        image=getIntent().getStringExtra("cat_image");

        binding.bannerItemName.setText(name);
        binding.bannerItemPrice.setText((price)+" ALL");
        Glide.with(this).load(Links.categories_images+image).into(binding.imageviewDiscountProduct);

        binding.ivBackimage.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        binding.buttonBuyNow.setOnClickListener(view -> {
            Intent intent=new Intent(FreeDeliveryActivity.this, PaymentActivity.class);
            intent.putExtra("payment_type","buy_now");
            intent.putExtra("item_price",price);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        binding.buttonAddToCart.setOnClickListener(v -> {
            OrderItem orderItemsModel = parseProductToOrderItems(id,name,image,price);
            boolean found = false;
//            for (OrderItem basketOrderItem : ItemsDB.getInstance(this).orderItemDao().allItems) {
//                if (basketOrderItem.getId()==(orderItemsModel.getId())) {
//                    found = true;
//                    basketOrderItem.incrementQuantity();
//                    ItemsDB.getInstance(this).orderItemDao().update(basketOrderItem);
//                    Toast.makeText(this, "Added to basket!", Toast.LENGTH_SHORT).show();
//                    break;
//
//                }
//            }
//            if (!found) {
//                ItemsDB.getInstance(this).orderItemDao().insert(orderItemsModel);
//                Toast.makeText(this, "Added to basket!", Toast.LENGTH_SHORT).show();
//
//            }
        });
        mainViewModel.getFreeDeliveryProducts();

    }
    private void getFreeDeliveryProducts(){
        mainViewModel.getFreeDeliveryLiveData().observe(this, listModelMain -> {
            if (!listModelMain.getError()) {
                adapterMoreFreeDeliveryProducts.submitList(listModelMain.getData());
            }else {
                Toast.makeText(this, listModelMain.getMessage(), Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(FreeDeliveryActivity.this, FreeDeliveryActivity.class);
        intent.putExtra("cat_id",data.getId());
        intent.putExtra("cat_name", data.getName());
        intent.putExtra("cat_price", data.getPrice());
        intent.putExtra("cat_image",data.getImage());
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

      @Override
    public void dicountedProductsClicked(DiscountedProductsData data) {

    }
    @Override
    public void categoryClicked(CategoriesData data) {

    }
}