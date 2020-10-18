package com.example.supergrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.SearchView;

import com.example.supergrocery.Adapters.AdapterFragmentCategories;
import com.example.supergrocery.Fragments.BasketFragment;
import com.example.supergrocery.Fragments.DiscoverFragment;
import com.example.supergrocery.Fragments.ProfileFragment;
import com.example.supergrocery.Fragments.ShopFragment;
import com.example.supergrocery.GetModels.CategoriesData;
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.Other.ProductsActivity;
import com.example.supergrocery.Other.SaveData;
import com.example.supergrocery.ROOM.ItemsDB;
import com.example.supergrocery.ROOM.OrderItem;
import com.example.supergrocery.databinding.ActivityMain2Binding;
import com.example.supergrocery.databinding.FragmentProfileBinding;
import com.example.supergrocery.databinding.FragmentShopBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.example.supergrocery.Fragments.ShopFragment.modelCategoriesData_shop_fragment;


public class MainActivity2 extends AppCompatActivity  implements ItemClickInterface {

     public ActivityMain2Binding activityMain2Binding;
    FragmentShopBinding fragmentShopBinding;
    FragmentProfileBinding fragmentProfileBinding;
    List<OrderItem> list = new ArrayList<>();
    SaveData saveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMain2Binding= ActivityMain2Binding.inflate(getLayoutInflater());
        final View view=activityMain2Binding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        saveData=new SaveData(this);
        fragmentShopBinding=FragmentShopBinding.inflate(getLayoutInflater());
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
                searchCategory(newText);
                return true;
            }
        });

        Intent intent=getIntent();
        Boolean basket=intent.getBooleanExtra("goToBasket",false);
        if(basket){
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

    private void searchCategory(String s) {

        List<CategoriesData> categoriesData = new ArrayList<>();


        categoriesData.addAll(modelCategoriesData_shop_fragment);
        for (int i = 0; i < categoriesData.size(); i++) {
            if (!categoriesData.get(i).getName().toUpperCase().contains(s.toUpperCase())) {
                categoriesData.remove(i);
                i--;
            }
        }
        if (!categoriesData.isEmpty()) {
            AdapterFragmentCategories adapter = new AdapterFragmentCategories(MainActivity2.this, categoriesData);
            fragmentShopBinding.recycleviewFragmentCategories.setAdapter(adapter);


        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        getTotalQuantity();

    }

}