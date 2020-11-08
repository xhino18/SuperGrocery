package com.example.supergrocery.loginregisteractivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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

    private ViewModel_Authentication viewModel_authentication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel_authentication=new ViewModelProvider(this).get(ViewModel_Authentication.class);
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
           viewModel_authentication.login_phoneNumber(phone);
           getInfo();
       });
    }

    private void getInfo(){
        viewModel_authentication.getLoginPhoneLiveData().observe(this, modelSendCode -> {
            if(!modelSendCode.getError()){
                Intent intent  = new Intent(LoginActivity.this, LoginCodeActivity.class);
                intent.putExtra("register_type", "login");
                intent.putExtra("phone",phone);
                startActivity(intent);
                finish();
            }

        });
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