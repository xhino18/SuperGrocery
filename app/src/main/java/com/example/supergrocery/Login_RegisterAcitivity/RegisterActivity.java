package com.example.supergrocery.Login_RegisterAcitivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.PostModels.UserRegister;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.ActivityRegisterBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity{

    ActivityRegisterBinding binding;
    int acc_type;
    String name, email, nuis, phone;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityRegisterBinding.inflate(getLayoutInflater());
        View view= binding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
                        registercall();
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
                        registercall();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Email not valid!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "Please complete all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void registercall(){
        API apiClient = ClientAPI.createApiNoToken();
        retrofit2.Call<UserRegister> call = apiClient.register(name,email,phone,acc_type,nuis,API.PLATFORM_ID,API.FIREBASE_TOKEN);
        call.enqueue(new Callback<UserRegister>() {
            @Override
            public void onResponse(retrofit2.Call<UserRegister> call, Response<UserRegister> response) {

                 System.out.println("respoonseeee "+ gson.toJson(response.body()));
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")){
                    if (!response.body().getError()){
                        Intent intent = new Intent(RegisterActivity.this,LoginCodeActivity.class);
                        intent.putExtra("register_type","register");
                        intent.putExtra("name",name);
                        intent.putExtra("email",email);
                        intent.putExtra("phone",phone);
                        intent.putExtra("nuis",nuis);
                        startActivity(intent);

                    } else {
                        Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(RegisterActivity.this, "oppssss", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<UserRegister> call, Throwable t) {
                Log.e("test", t.toString());

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }


}