package com.example.supergrocery.Login_RegisterAcitivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.MainActivity;
import com.example.supergrocery.Other.SaveData;
import com.example.supergrocery.PostModels.ModelSendCode;
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);
        gson=new GsonBuilder().create();
        saveData=new SaveData(this);
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

    public void login_phoneNumber(String phoneNumber){

        API ApiClinet =  ClientAPI.createApiNoToken();
        Call<ModelSendCode> call = ApiClinet.sendCode(phoneNumber);
        call.enqueue(new Callback<ModelSendCode>() {
                         @Override
                         public void onResponse(Call<ModelSendCode> call, Response<ModelSendCode> response) {

                             if (!gson.toJson(response.body()).equalsIgnoreCase("null")){
                                 if (!response.body().getError()){
                                     Intent intent  = new Intent(LoginActivity.this, LoginCodeActivity.class);
                                     intent.putExtra("register_type", "login");
                                     intent.putExtra("phone",phone);
                                     startActivity(intent);
                                 }
                                 else{
                                     Toast.makeText(LoginActivity.this, "Ndodhi një gabim!", Toast.LENGTH_SHORT).show();
                                 }

                             }else
                                 Toast.makeText(LoginActivity.this, "Ndodhi një gabim1!", Toast.LENGTH_SHORT).show();
                         }

                         @Override
                         public void onFailure(Call<ModelSendCode> call, Throwable t) {

                             Toast.makeText(LoginActivity.this, "Ndodhi një gabim2!", Toast.LENGTH_SHORT).show();
                         }
                     }
        );




    }

}