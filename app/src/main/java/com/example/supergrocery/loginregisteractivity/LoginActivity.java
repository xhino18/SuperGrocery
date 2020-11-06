package com.example.supergrocery.loginregisteractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.supergrocery.api.API;
import com.example.supergrocery.other.SaveData;
import com.example.supergrocery.models.ModelSendCode;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.ActivityLoginBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    SaveData saveData;
    Gson gson;
    boolean doubleBackToExitPressedOnce = false;
    String phone;
    @Inject
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityLoginBinding.inflate(getLayoutInflater());
        final View view= binding.getRoot();
        setContentView(view);

        init();

    }

    private void init() {
        gson=new GsonBuilder().create();
        saveData=new SaveData(this);
       binding.buttonRegister.setOnClickListener(v -> {
           Intent intent= new Intent(LoginActivity.this,RegisterActivity.class);
           startActivity(intent);
           overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
       });
       binding.buttonLogin.setOnClickListener(v -> {
           phone= binding.etLoginPhone.getText().toString();
           login_phoneNumber(phone);
           
       });
    }

    public void login_phoneNumber(String phoneNumber){
       /* Call<ModelSendCode> call = api.sendCode(phoneNumber);
        call.enqueue(new Callback<ModelSendCode>() {
                         @Override
                         public void onResponse(Call<ModelSendCode> call, Response<ModelSendCode> response) {

                             if (!gson.toJson(response.body()).equalsIgnoreCase("null")){
                                 if (!response.body().getError()){
                                     Intent intent  = new Intent(LoginActivity.this, LoginCodeActivity.class);
                                     intent.putExtra("register_type", "login");
                                     intent.putExtra("phone",phone);
                                     startActivity(intent);
                                     finish();
                                 }
                                 else{
                                     Toast.makeText(LoginActivity.this, "Incorrect number!", Toast.LENGTH_SHORT).show();
                                 }

                             }else
                                 Toast.makeText(LoginActivity.this, "Ndodhi një gabim1!", Toast.LENGTH_SHORT).show();
                         }

                         @Override
                         public void onFailure(Call<ModelSendCode> call, Throwable t) {

                             Toast.makeText(LoginActivity.this, "Ndodhi një gabim2!", Toast.LENGTH_SHORT).show();
                         }
                     }
        );*/
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