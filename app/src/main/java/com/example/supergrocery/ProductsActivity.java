package com.example.supergrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.Adapters.AdapterCategories;
import com.example.supergrocery.Adapters.AdapterShopProducts;
import com.example.supergrocery.Fragments.BasketFragment;
import com.example.supergrocery.Interfaces.AddItemInBasket;
import com.example.supergrocery.Models.ModelCategories;
import com.example.supergrocery.Models.ModelDiscountedProducts;
import com.example.supergrocery.Models.ModelFreeDeliveryProducts;
import com.example.supergrocery.Models.ModelShopProducts;
import com.example.supergrocery.Models.ModelShopProductsData;
import com.example.supergrocery.ROOM.ItemsDB;
import com.example.supergrocery.ROOM.OrderItemsModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.supergrocery.MainActivity.token_login;

public class ProductsActivity extends AppCompatActivity implements AddItemInBasket {
    CardView cardview_basket_items;
    TextView tv_basket_quantity;
    ImageView iv_backimage;
Gson gson;
List<ModelShopProductsData> modelShopProductsDataList=new ArrayList<>();
List<OrderItemsModel> orderItemsModels= new ArrayList<>();
RecyclerView recycleview_shop_products;
AdapterShopProducts adapterShopProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        tv_basket_quantity=findViewById(R.id.tv_basket_quantity);
        recycleview_shop_products=findViewById(R.id.recycleview_shop_products);
        recycleview_shop_products.setLayoutManager(new GridLayoutManager(ProductsActivity.this, 2));
        gson=new GsonBuilder().create();
        iv_backimage=findViewById(R.id.iv_backimage);

        iv_backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        int catId = getIntent().getIntExtra("cat_id", -1);
        System.out.println("Id contoller " + catId);
        getall(token_login,catId);
        getTotalQuantity();

    }
        public void getall (String token,int catId){
            API apiClient = ClientAPI.createAPI_With_Token(token);
            Call<ModelShopProducts> call = apiClient.getProducts(catId);
            call.enqueue(new Callback<ModelShopProducts>() {
                @Override
                public void onResponse(Call<ModelShopProducts> call, Response<ModelShopProducts> response) {
                    if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                        if (!response.body().getError()) {
                            modelShopProductsDataList.addAll(response.body().getData());
                            adapterShopProducts = new AdapterShopProducts(ProductsActivity.this, modelShopProductsDataList);
                            recycleview_shop_products.setAdapter(adapterShopProducts);
                        } else {
                            Toast.makeText(ProductsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ProductsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelShopProducts> call, Throwable t) {
                    Toast.makeText(ProductsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public OrderItemsModel parseProductToOrderItems(ModelShopProductsData data){
        return new OrderItemsModel(
                new Long(data.getId()),
                data.getId(),
                data.getName(),
                data.getImage(),
                data.getPrice(),
                1

        );
    }

    @Override
    public void addtoBasket(ModelShopProductsData data) {
         OrderItemsModel orderItemsModel = parseProductToOrderItems(data);
        boolean found = false;
        for (OrderItemsModel basketOrderItem : ItemsDB.getInstance(this).orderItemDao().getAllItems()) {
            if (basketOrderItem.getId().equals(orderItemsModel.getId())) {
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
    private void getTotalQuantity(){
        int totalquantity=0;
        orderItemsModels = ItemsDB.getInstance(this).orderItemDao().getAllItems();
        for (int i =0;i<orderItemsModels.size();i++){
            totalquantity=totalquantity+ orderItemsModels.get(i).getQuantity();
        }
        if(totalquantity==0){
            tv_basket_quantity.setVisibility(View.GONE);
        }else{
            tv_basket_quantity.setVisibility(View.VISIBLE);
        }
        tv_basket_quantity.setText(totalquantity+"");
        System.out.println("Quantity controler "+totalquantity);
    }
}