package com.example.supergrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.example.supergrocery.fragments.BasketFragment;
import com.example.supergrocery.fragments.DiscoverFragment;
import com.example.supergrocery.fragments.HomeFragment;
import com.example.supergrocery.fragments.profile.ProfileFragment;
import com.example.supergrocery.fragments.ShopFragment;
import com.example.supergrocery.models.AllProductsData;
import com.example.supergrocery.models.CategoriesData;
import com.example.supergrocery.models.DiscountedProductsData;
import com.example.supergrocery.interfaces.ItemClickInterface;
import com.example.supergrocery.productmodelactivity.DiscountedProductsActivity;
import com.example.supergrocery.productmodelactivity.FreeDeliveryActivity;
import com.example.supergrocery.productmodelactivity.ProductsActivity;
import com.example.supergrocery.other.SaveData;
import com.example.supergrocery.room.ItemsDB;
import com.example.supergrocery.room.OrderItem;
import com.example.supergrocery.databinding.ActivityMainBinding;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.hilt.android.AndroidEntryPoint;

import java.util.ArrayList;
import java.util.List;


@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements ItemClickInterface {

    ActivityMainBinding binding;
    List<OrderItem> list = new ArrayList<>();
    boolean doubleBackToExitPressedOnce = false;
    SaveData saveData;
    Gson gson;
    BadgeDrawable badgeDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        init();

    }

    private void init() {

        gson = new GsonBuilder().create();
        saveData = new SaveData(this);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(navListener);
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
        Intent intent = new Intent(MainActivity.this, FreeDeliveryActivity.class);
        intent.putExtra("cat_id",data.getId());
        intent.putExtra("cat_name", data.getName());
        intent.putExtra("cat_price", data.getPrice());
        intent.putExtra("cat_image",data.getImage());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    public void dicountedProductsClicked(DiscountedProductsData data) {
        Intent intent = new Intent(MainActivity.this, DiscountedProductsActivity.class);
        intent.putExtra("cat_id",data.getId());
        intent.putExtra("cat_name", data.getName());
        intent.putExtra("cat_price", data.getPrice());
        intent.putExtra("cat_image",data.getImage());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void categoryClicked(CategoriesData data) {
        Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
        intent.putExtra("cat_id", data.getId());
        intent.putExtra("cat_name", data.getName());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public void getTotalQuantity() {
        int totalquantity = 0;
//        list = ItemsDB.getInstance(MainActivity.this).orderItemDao().allItems;
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

        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

}