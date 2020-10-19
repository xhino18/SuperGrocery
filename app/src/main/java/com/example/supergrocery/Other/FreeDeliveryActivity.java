package com.example.supergrocery.Other;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.supergrocery.GetModels.FreeDeliveryProducts;
import com.example.supergrocery.GetModels.FreeDeliveryProductsData;
import com.example.supergrocery.GetModels.ShopProductsData;
import com.example.supergrocery.R;
import com.example.supergrocery.ROOM.ItemsDB;
import com.example.supergrocery.ROOM.OrderItem;
import com.example.supergrocery.databinding.ActivityFreeDeliveryBinding;

import java.util.ArrayList;
import java.util.List;

public class FreeDeliveryActivity extends AppCompatActivity {
    ActivityFreeDeliveryBinding activityFreeDeliveryBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFreeDeliveryBinding=ActivityFreeDeliveryBinding.inflate(getLayoutInflater());
        final View view=activityFreeDeliveryBinding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        String name=getIntent().getStringExtra("cat_name");
        int price=getIntent().getIntExtra("cat_price",-1);
        String image=getIntent().getStringExtra("cat_image");

        activityFreeDeliveryBinding.bannerItemName.setText(name);
        activityFreeDeliveryBinding.bannerItemPrice.setText(price+" ALL");
        Glide.with(view).load(Links.categories_images+image).into(activityFreeDeliveryBinding.imageviewDiscountProduct);

        activityFreeDeliveryBinding.ivBackimage.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        activityFreeDeliveryBinding.buttonAddToCard.setOnClickListener(v -> {


        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}