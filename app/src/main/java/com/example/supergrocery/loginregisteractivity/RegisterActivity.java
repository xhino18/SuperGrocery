package com.example.supergrocery.loginregisteractivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.supergrocery.MainActivity;
import com.example.supergrocery.api.API;
import com.example.supergrocery.models.ModelMainToken;
import com.example.supergrocery.models.UserRegisterData;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.ActivityRegisterBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class RegisterActivity extends AppCompatActivity{

    ActivityRegisterBinding binding;
    int acc_type;
    String name,email,nuis,phone;
    Gson gson;
    @Inject
    API api;
    private ViewModel_Authentication viewModel_authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel_authentication=new ViewModelProvider(this).get(ViewModel_Authentication.class);
        binding =ActivityRegisterBinding.inflate(getLayoutInflater());
        View view= binding.getRoot();
        setContentView(view);

        init();

    }

    private void init() {
        gson= new GsonBuilder().create();
        binding.tvProfilePersonal.setOnClickListener(v -> {
            binding.linearNuis.setVisibility(View.INVISIBLE);
        });
        binding.tvProfileBusiness.setOnClickListener(v -> {
            binding.linearNuis.setVisibility(View.VISIBLE);
        });
        binding.tvLogin.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        binding.buttonRegister.setOnClickListener(view -> {
            name = binding.tvProfileName.getText().toString();
            email = binding.tvProfileEmail.getText().toString();
            nuis = binding.tvProfileNuis.getText().toString();
            phone = binding.tvProfilePhone.getText().toString();
            if(binding.tvProfilePersonal.isChecked()) {
                if (!name.equals("") && !email.equals("") && !phone.equals("")) {
                    if (email.contains("@")) {
                        acc_type=2;
                        viewModel_authentication.registerCall(name,email,phone,acc_type,nuis,API.PLATFORM_ID,API.FIREBASE_TOKEN);
                        getInfo();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Email not valid!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "Please complete all fields!", Toast.LENGTH_SHORT).show();
                }
            }

            if(binding.tvProfileBusiness.isChecked()) {
                if (!name.equals("") && !email.equals("") && !nuis.equals("")&& !phone.equals("")) {
                    if (email.contains("@")) {
                        acc_type=1;
                        viewModel_authentication.registerCall(name,email,phone,acc_type,nuis,API.PLATFORM_ID,API.FIREBASE_TOKEN);
                        getInfo();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Email not valid!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "Please complete all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
private void getInfo(){
        viewModel_authentication.getRegisterLiveData().observe(this, userRegisterDataModelMainToken -> {
            if (!userRegisterDataModelMainToken.getError()){
                Intent intent = new Intent(RegisterActivity.this, LoginCodeActivity.class);
                intent.putExtra("register_type","register");
                intent.putExtra("name",name);
                intent.putExtra("email",email);
                intent.putExtra("phone",phone);
                intent.putExtra("nuis",nuis);
                startActivity(intent);
            }
        });
}
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }
}