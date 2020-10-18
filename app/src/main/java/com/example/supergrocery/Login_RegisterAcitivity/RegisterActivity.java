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
import com.example.supergrocery.MainActivity;
import com.example.supergrocery.PostModels.UserRegister;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.ActivityRegisterBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity{

    ActivityRegisterBinding activityRegisterBinding;
    int acc_type;
    String name, email, nuis, phone;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding=ActivityRegisterBinding.inflate(getLayoutInflater());
        View view=activityRegisterBinding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        init();

    }

    private void init() {

        gson= new GsonBuilder().create();
        activityRegisterBinding.tvProfilePersonal.setOnClickListener(v -> {
            activityRegisterBinding.linearNuis.setVisibility(View.INVISIBLE);
        });
        activityRegisterBinding.tvProfileBusiness.setOnClickListener(v -> {
            activityRegisterBinding.linearNuis.setVisibility(View.VISIBLE);
        });
        activityRegisterBinding.rightArrow.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        activityRegisterBinding.buttonRegister.setOnClickListener(view -> {
            name = activityRegisterBinding.tvProfileName.getText().toString();
            email = activityRegisterBinding.tvProfileEmail.getText().toString();
            nuis = activityRegisterBinding.tvProfileNuis.getText().toString();
            phone = activityRegisterBinding.tvProfilePhone.getText().toString();
            if(activityRegisterBinding.tvProfilePersonal.isChecked()) {
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

            if(activityRegisterBinding.tvProfileBusiness.isChecked()) {
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