package com.example.supergrocery.other;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.supergrocery.adapters.AdapterSearchedProduct;
import com.example.supergrocery.api.API;
import com.example.supergrocery.models.AllProductsData;
import com.example.supergrocery.models.CategoriesData;
import com.example.supergrocery.models.DiscountedProductsData;
import com.example.supergrocery.interfaces.ItemClickInterface;
import com.example.supergrocery.models.ModelMain;
import com.example.supergrocery.productmodelactivity.ProductsActivity;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.ActivityMainSearchBinding;
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
public class MainSearchActivity extends AppCompatActivity implements ItemClickInterface {
    ActivityMainSearchBinding binding;
    Gson gson;
    SaveData saveData;
    List<CategoriesData> categoriesDataList = new ArrayList<>();
    AdapterSearchedProduct adapterSearchedProduct;
    @Inject
    API api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainSearchBinding.inflate(getLayoutInflater());
        final View view=binding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        gson=new GsonBuilder().create();
        saveData=new SaveData(this);
        getall();
        binding.searchviewMain.onActionViewExpanded();
        binding.searchviewMain.requestFocus();
        binding.ivBack.setOnClickListener(view1 -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
//        binding.searchviewMain.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    }
    public void getall() {
     /*   Call<ModelMain<List<CategoriesData>>> call = api.getCategories();
        call.enqueue(new Callback<ModelMain<List<CategoriesData>>>() {
            @Override
            public void onResponse(Call<ModelMain<List<CategoriesData>>> call, Response<ModelMain<List<CategoriesData>>> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        categoriesDataList.addAll(response.body().getData());
                    } else {
                        Toast.makeText(MainSearchActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainSearchActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelMain<List<CategoriesData>>> call, Throwable t) {
                Toast.makeText(MainSearchActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

//    private void searchCategory(String s) {
//        List<CategoriesData> categoriesData = new ArrayList<>();
//        categoriesData.addAll(categoriesDataList);
//        for (int i = 0; i < categoriesData.size(); i++) {
//            if (!categoriesData.get(i).getName().toUpperCase().contains(s.toUpperCase())) {
//                categoriesData.remove(i);
//                i--;
//            }
//        }
//        adapterSearchedProduct=new AdapterSearchedProduct(MainSearchActivity.this,categoriesData);
//        binding.recycleviewSearchedItems.setAdapter(adapterSearchedProduct);
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void freeDeliveryClicked(AllProductsData data) {

    }

    @Override
    public void dicountedProductsClicked(DiscountedProductsData data) {

    }

    @Override
    public void categoryClicked(CategoriesData data) {
        Intent intent = new Intent(MainSearchActivity.this, ProductsActivity.class);
        intent.putExtra("cat_id", data.getId());
        intent.putExtra("cat_name", data.getName());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}