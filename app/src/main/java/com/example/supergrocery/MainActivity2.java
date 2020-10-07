package com.example.supergrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.supergrocery.Adapters.AdapterFragmentCategories;
import com.example.supergrocery.Fragments.BasketFragment;
import com.example.supergrocery.Fragments.DiscoverFragment;
import com.example.supergrocery.Fragments.HomeFragment;
import com.example.supergrocery.Fragments.ProfileFragment;
import com.example.supergrocery.Fragments.ShopFragment;
import com.example.supergrocery.Interfaces.ItemClickInterface;
import com.example.supergrocery.GetModels.ModelCategoriesData;
import com.example.supergrocery.Other.ProductsActivity;
import com.example.supergrocery.ROOM.ItemsDB;
import com.example.supergrocery.ROOM.OrderItemsModel;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.example.supergrocery.Fragments.ShopFragment.modelCategoriesData_shop_fragment;
import static com.example.supergrocery.Fragments.ShopFragment.recycleview_fragment_categories_shop_fragment;

public class MainActivity2 extends AppCompatActivity  implements ItemClickInterface {
    CardView cardview_basket_quantity;
    SearchView searchview_main_2;
    public static TextView tv_basket_quantity;

    List<OrderItemsModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv_basket_quantity = findViewById(R.id.tv_basket_quantity);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        cardview_basket_quantity = findViewById(R.id.cardview_basket_quantity);


        //Which fragment to show first

        bottomNavigationView.getMenu().getItem(3).setChecked(true);
        bottomNavigationView.getMenu().performIdentifierAction(R.id.nav_shop, 0);
        getTotalQuantity();
        cardview_basket_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasketFragment fragment = new BasketFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        searchview_main_2 = findViewById(R.id.searchview_main_2);
        searchview_main_2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

                }
            };


    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void categoryClicked(ModelCategoriesData data) {
        Intent intent = new Intent(MainActivity2.this, ProductsActivity.class);
        intent.putExtra("cat_id", data.getId());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    private void getTotalQuantity() {
        int totalquantity = 0;
        list = ItemsDB.getInstance(MainActivity2.this).orderItemDao().getAllItems();
        for (int i = 0; i < list.size(); i++) {
            totalquantity = totalquantity + list.get(i).getQuantity();
        }
        if (totalquantity == 0) {
            tv_basket_quantity.setVisibility(View.GONE);
        } else {
            tv_basket_quantity.setVisibility(View.VISIBLE);
        }
        tv_basket_quantity.setText(totalquantity + "");
    }

    private void searchCategory(String s) {

        List<ModelCategoriesData> categoriesData = new ArrayList<>();


        categoriesData.addAll(modelCategoriesData_shop_fragment);
        for (int i = 0; i < categoriesData.size(); i++) {
            if (!categoriesData.get(i).getName().toUpperCase().contains(s.toUpperCase())) {
                categoriesData.remove(i);
                i--;
            }
        }
        if (!categoriesData.isEmpty()) {
            AdapterFragmentCategories adapter = new AdapterFragmentCategories(MainActivity2.this, categoriesData);
            recycleview_fragment_categories_shop_fragment.setAdapter(adapter);


        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getTotalQuantity();
    }
}