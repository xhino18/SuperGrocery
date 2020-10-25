package com.example.supergrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.supergrocery.Fragments.BasketFragment;
import com.example.supergrocery.Fragments.DiscoverFragment;
import com.example.supergrocery.Fragments.HomeFragment;
import com.example.supergrocery.Fragments.ProfileFragment;
import com.example.supergrocery.Fragments.ShopFragment;
import com.example.supergrocery.GetModels.AllProducts;
import com.example.supergrocery.GetModels.AllProductsData;
import com.example.supergrocery.GetModels.CategoriesData;
import com.example.supergrocery.GetModels.DiscountedProductsData;
import com.example.supergrocery.GetModels.FreeDeliveryProductsData;
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.Other.DiscountedProductsActivity;
import com.example.supergrocery.Other.FreeDeliveryActivity;
import com.example.supergrocery.Other.ProductsActivity;
import com.example.supergrocery.Other.SaveData;
import com.example.supergrocery.ROOM.ItemsDB;
import com.example.supergrocery.ROOM.OrderItem;
import com.example.supergrocery.databinding.ActivityMain2Binding;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


public class MainActivity2 extends AppCompatActivity implements ItemClickInterface {

    ActivityMain2Binding binding;
    List<OrderItem> list = new ArrayList<>();
    boolean doubleBackToExitPressedOnce = false;
    SaveData saveData;
    Gson gson;
    BadgeDrawable badgeDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        init();


    }

    private void init() {

        gson = new GsonBuilder().create();
        saveData = new SaveData(this);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(navListener);
        //Which fragment to show first
        binding.bottomNavigation.setSelectedItemId(R.id.nav_home);
        getTotalQuantity();


        Boolean basket = getIntent().getBooleanExtra("goToBasket", false);
        if (basket) {
            binding.bottomNavigation.setSelectedItemId(R.id.nav_basket);
        }
        Boolean shop = getIntent().getBooleanExtra("goToShop", false);
        if (shop) {
            binding.bottomNavigation.setSelectedItemId(R.id.nav_shop);
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.nav_discover:
                        selectedFragment = new DiscoverFragment();
                        break;
                    case R.id.nav_basket:
                        selectedFragment = new BasketFragment();
                        break;
                    case R.id.nav_shop:
                        selectedFragment = new ShopFragment();
                        break;
                    case R.id.nav_profile:
                        selectedFragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;

            };


    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void freeDeliveryClicked(AllProductsData data) {
        Intent intent = new Intent(MainActivity2.this, FreeDeliveryActivity.class);
        intent.putExtra("cat_id",data.getId());
        intent.putExtra("cat_name", data.getName());
        intent.putExtra("cat_price", data.getPrice());
        intent.putExtra("cat_image",data.getImage());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    public void dicountedProductsClicked(DiscountedProductsData data) {
        Intent intent = new Intent(MainActivity2.this, DiscountedProductsActivity.class);
        intent.putExtra("cat_id",data.getId());
        intent.putExtra("cat_name", data.getName());
        intent.putExtra("cat_price", data.getPrice());
        intent.putExtra("cat_image",data.getImage());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void categoryClicked(CategoriesData data) {
        Intent intent = new Intent(MainActivity2.this, ProductsActivity.class);
        intent.putExtra("cat_id", data.getId());
        intent.putExtra("cat_name", data.getName());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public void getTotalQuantity() {
        int totalquantity = 0;
        list = ItemsDB.getInstance(MainActivity2.this).orderItemDao().getAllItems();
        badgeDrawable = binding.bottomNavigation.getOrCreateBadge(R.id.nav_basket);
        badgeDrawable.setBackgroundColor(Color.RED);
        badgeDrawable.setBadgeTextColor(Color.WHITE);
        badgeDrawable.setMaxCharacterCount(2);
        for (int i = 0; i < list.size(); i++) {
            totalquantity = totalquantity + list.get(i).getQuantity();
        }
        if (totalquantity == 0) {
            badgeDrawable.setVisible(false);
        } else {
            badgeDrawable.setVisible(true);
        }
        badgeDrawable.setNumber(totalquantity);

    }

//    private void searchCategory(String s) {
//        List<AllProductsData> productsData = new ArrayList<>();
//
//
//        productsData.addAll(fragmentProfileBinding.allProductsData);
//        for (int i = 0; i < productsData.size(); i++) {
//            if (!productsData.get(i).getName().toUpperCase().contains(s.toUpperCase())) {
//                productsData.remove(i);
//                i--;
//            }
//        }
//        if (!productsData.isEmpty()) {
//            activityMain2Binding.recycleviewAllProducts.setVisibility(View.GONE);
//
//
//        }
//
//    }


    @Override
    protected void onResume() {
        super.onResume();
        getTotalQuantity();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


}