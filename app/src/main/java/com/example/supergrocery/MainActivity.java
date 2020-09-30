package com.example.supergrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.Adapters.AdapterCategories;
import com.example.supergrocery.Models.ModelCategories;
import com.example.supergrocery.Models.ModelCategoriesData;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.supergrocery.API.API.BASE_URL;

public class MainActivity extends AppCompatActivity {
    ImageView iv_menuicon;
    Gson gson;
    List<ModelCategoriesData> categoriesDataList;
    AdapterCategories adapterCategories;
    RecyclerView recyclerView_categories;
    final static String Token= "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiZTEwMGUwZjBlMmM1ZmIzOWU3ZDNmZGVjNzY1ODFiZDkyZWIzMGZjMDQ1NjE2MDY3MDRjNDg4OTlmNmZjZTc3NjJlZjRjZjA1NjFkYmU4MzIiLCJpYXQiOjE2MDE0NTgwMjUsIm5iZiI6MTYwMTQ1ODAyNSwiZXhwIjoxNjMyOTk0MDI1LCJzdWIiOiIxMDYiLCJzY29wZXMiOltdfQ.kWX7XWn6cNGHBroLv0YWE_FMx671kYlx-SXCt-Ej7Q43GlBq9Rs7AaZLypcrsM_EfWLWjUfmzlZTKYFR4w5LzeCtwrh2p6nUGGu2nVMejikDXevgvFXHJBBYUB87L4xfRW7992FK14M-Ajze0CzZRiC28JuPmkmF6jwwcWM87T-wxM2brYZ6rCNK4hNcRAhmOUSKfMQRGgKIh0hVMB4s8LvBRm3NaRESJIREoti7FHDiRYmMfrzwARV9C2YUf-8i_aLb8vEGzcPXDAyByrRpEzNo5MWmgxSqExPTjQKzHsRX4UWBlwg-E0FkI5RHm6jM90RDqbylrEzB3fZAE9h36U9MngoEglzjTBF66KekjvheqmmeVGb50N7ha2aUEq2Ye02d4o_au0RW85pqroZhimJ9JKSsujCuvs8XDUTZECrXNyoy8XmKM2zZykoKagkMVPiTSQtkVcAW2yBS1mMN-BbtXp0Vs_5hnuUBFHUoHF0A3NWfEMC3t9MMdXFHN1y-YPFS9DrqgE7HB41zS-3OQ185j0o9GyDNjLCew9XK7_86ysLA14lYyzhvB2Qdlm313C-Bp4NCsZiklQEO-8N-w_dxmQIdMHcELU85y6O7EitVIbkG_2ZfCcuHPWoWhAULbgfkDPJwsRr5mOz-LWf7rElC-yBfShY3_W445kMZt5w";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getCategories(Token);

    }

    public void init() {
        recyclerView_categories = findViewById(R.id.recycleview_food_categories);
        recyclerView_categories.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        iv_menuicon = findViewById(R.id.iv_menuicon);
        iv_menuicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


    }

    public void getCategories(String token) {
        API apiClient = ClientAPI.createAPI_With_Token(token);
        Call<ModelCategories> call = apiClient.getCategories();
        call.enqueue(new Callback<ModelCategories>() {
            @Override
            public void onResponse(Call<ModelCategories> call, Response<ModelCategories> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        categoriesDataList.addAll(response.body().getData());
                        adapterCategories = new AdapterCategories(MainActivity.this, categoriesDataList);
                        recyclerView_categories.setAdapter(adapterCategories);
                    } else {
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelCategories> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}