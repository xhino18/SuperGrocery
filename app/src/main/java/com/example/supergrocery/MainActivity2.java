package com.example.supergrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.Adapters.AdapterAllProducts;
import com.example.supergrocery.Adapters.AdapterCategories;
import com.example.supergrocery.Adapters.AdapterFragmentCategories;
import com.example.supergrocery.Fragments.BasketFragment;
import com.example.supergrocery.Fragments.DiscoverFragment;
import com.example.supergrocery.Fragments.ProfileFragment;
import com.example.supergrocery.Fragments.ShopFragment;
import com.example.supergrocery.GetModels.AllProducts;
import com.example.supergrocery.GetModels.AllProductsData;
import com.example.supergrocery.GetModels.Banner;
import com.example.supergrocery.GetModels.Categories;
import com.example.supergrocery.GetModels.CategoriesData;
import com.example.supergrocery.GetModels.DiscountedProducts;
import com.example.supergrocery.GetModels.DiscountedProductsData;
import com.example.supergrocery.GetModels.FreeDeliveryProducts;
import com.example.supergrocery.GetModels.FreeDeliveryProductsData;
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.Other.ProductsActivity;
import com.example.supergrocery.Other.SaveData;
import com.example.supergrocery.ROOM.ItemsDB;
import com.example.supergrocery.ROOM.OrderItem;
import com.example.supergrocery.databinding.ActivityMain2Binding;
import com.example.supergrocery.databinding.FragmentBasketBinding;
import com.example.supergrocery.databinding.FragmentProfileBinding;
import com.example.supergrocery.databinding.FragmentShopBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.supergrocery.Fragments.ShopFragment.modelCategoriesData_shop_fragment;


public class MainActivity2 extends AppCompatActivity  implements ItemClickInterface {

    public ActivityMain2Binding activityMain2Binding;
    FragmentBasketBinding fragmentBasketBinding;
    List<OrderItem> list = new ArrayList<>();
    List<AllProductsData> allProductsData = new ArrayList<>();
    AdapterAllProducts adapterAllProducts;
    SaveData saveData;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMain2Binding = ActivityMain2Binding.inflate(getLayoutInflater());
        final View view = activityMain2Binding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        gson = new GsonBuilder().create();
        saveData = new SaveData(this);
        activityMain2Binding.bottomNavigation.setOnNavigationItemSelectedListener(navListener);
        //Which fragment to show first
        activityMain2Binding.bottomNavigation.getMenu().getItem(3).setChecked(true);
        activityMain2Binding.bottomNavigation.getMenu().performIdentifierAction(R.id.nav_shop, 0);
        getTotalQuantity();
        activityMain2Binding.cardviewBasketQuantity.setOnClickListener(v -> {
            BasketFragment fragment = new BasketFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        activityMain2Binding.searchviewMain2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                searchCategory(newText);
//                activityMain2Binding.recycleviewAllProducts.setVisibility(View.VISIBLE);

                return true;
            }
        });

        Intent intent = getIntent();
        Boolean basket = intent.getBooleanExtra("goToBasket", false);
        if (basket) {
            activityMain2Binding.bottomNavigation.setSelectedItemId(R.id.nav_basket);
        }
//        Intent intent1=getIntent();
//        Boolean profile=intent1.getBooleanExtra("goToProfile",false);
//        if(profile){
//            activityMain2Binding.bottomNavigation.setSelectedItemId(R.id.nav_profile);
//        }


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        finish();
                        return true;
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
    public void freeDeliveryClicked(FreeDeliveryProductsData data) {

    }

    @Override
    public void dicountedProductsClicked(DiscountedProductsData data) {

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
        for (int i = 0; i < list.size(); i++) {
            totalquantity = totalquantity + list.get(i).getQuantity();
        }
        if (totalquantity == 0) {
            activityMain2Binding.tvBasketQuantity.setVisibility(View.GONE);
        } else {
            activityMain2Binding.tvBasketQuantity.setVisibility(View.VISIBLE);

        }
        activityMain2Binding.tvBasketQuantity.setText(totalquantity + "");

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

}