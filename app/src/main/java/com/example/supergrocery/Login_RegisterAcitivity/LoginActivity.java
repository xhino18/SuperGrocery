package com.example.supergrocery.Login_RegisterAcitivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.GetModels.Login;
import com.example.supergrocery.Other.SaveData;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.ActivityLoginBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding activityLoginBinding;
    SaveData saveData;
    Gson gson;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding=ActivityLoginBinding.inflate(getLayoutInflater());
        final View view=activityLoginBinding.getRoot();
        setContentView(view);
        gson=new GsonBuilder().create();
        init();


    }

    private void init() {

       activityLoginBinding.buttonRegister.setOnClickListener(v -> {
           Intent intent= new Intent(LoginActivity.this,RegisterActivity.class);
           startActivity(intent);
           overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
       });
       activityLoginBinding.buttonLogin.setOnClickListener(v -> {
           phone=activityLoginBinding.etLoginPhone.getText().toString();
           login_phoneNumber(phone);
           
       });
    }

    public void login_phoneNumber(final String PhoneNumber){

        API ApiClinet =  ClientAPI.createApiNoToken();
        saveData = new SaveData(getApplicationContext());
        Call<Login> call = ApiClinet.login(PhoneNumber);
        call.enqueue(new Callback<Login>() {
                         @Override
                         public void onResponse(Call<Login> call, Response<Login> response) {

                             if (!gson.toJson(response.body()).equalsIgnoreCase("null")){
                                 if (!response.body().getError()){
                                     Intent intent  = new Intent(LoginActivity.this,LoginCodeActivity.class);
                                     intent.putExtra("phone_number",phone);
                                     intent.putExtra("token",response.body().getToken());
                                     startActivity(intent);
                                 }
                                 else{
                                     Toast.makeText(LoginActivity.this, "Ndodhi një gabim!", Toast.LENGTH_SHORT).show();
                                 }

                             }else
                                 Toast.makeText(LoginActivity.this, "Ndodhi një gabim!", Toast.LENGTH_SHORT).show();
                         }

                         @Override
                         public void onFailure(Call<Login> call, Throwable t) {

                             Toast.makeText(LoginActivity.this, "Ndodhi një gabim!", Toast.LENGTH_SHORT).show();
                         }
                     }
        );




    }

}