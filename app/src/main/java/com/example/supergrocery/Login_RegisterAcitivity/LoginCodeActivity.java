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
import com.example.supergrocery.MainActivity;
import com.example.supergrocery.Other.SaveData;
import com.example.supergrocery.PostModels.ModelSendCode;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.ActivityLoginBinding;
import com.example.supergrocery.databinding.ActivityLoginCodeBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginCodeActivity extends AppCompatActivity {
    ActivityLoginCodeBinding activityLoginCodeBinding;

    Bundle bundle;
    Gson gson;
    SaveData saveData;
    Boolean is_login;
    String user_name,user_email,user_nuis,user_phone,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginCodeBinding=ActivityLoginCodeBinding.inflate(getLayoutInflater());
        final View view=activityLoginCodeBinding.getRoot();
        setContentView(view);

        init();

    }

    private void init() {
        gson=new GsonBuilder().create();
        bundle=getIntent().getExtras();
        activityLoginCodeBinding.rightArrow.setOnClickListener(v -> {
            Intent intent= new Intent(LoginCodeActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        if(bundle!=null){
            is_login=false;
            user_name=bundle.getString("name");
            user_email=bundle.getString("email");
            user_nuis=bundle.getString("nuis");
            user_phone=bundle.getString("phone");
            token=bundle.getString("token");

            if (!user_phone.equalsIgnoreCase("")) {
                startPhoneNumberVerification(user_phone, token);
            }
//        } else {
//            is_login = true;
//            token = bundle.getString("token");
//            user_phone = bundle.getString("phone_number");
//            if (!user_phone.equalsIgnoreCase("")) {
//                startPhoneNumberVerification(user_phone, token);
//            }

        }
        activityLoginCodeBinding.buttonResendCode.setOnClickListener(v -> {
            user_phone = bundle.getString("phone_number");

            if (!user_phone.equalsIgnoreCase("")) {
                startPhoneNumberVerification(user_phone, token);
            }

        });
        activityLoginCodeBinding.buttonVerify.setOnClickListener(v -> {
            if (activityLoginCodeBinding.buttonVerify.getText().toString().length() < 6) {
                Toast.makeText(LoginCodeActivity.this, "Kodi i verifikimit i pasakte!", Toast.LENGTH_SHORT).show();
            } else {
                verifyPhoneNumberWithCode(activityLoginCodeBinding.buttonVerify.getText().toString(), bundle.getString("token"));
            }

        });

    }

    private void startPhoneNumberVerification(String user_phone, String token) {

        API apiClient = ClientAPI.createAPI_With_Token(token);
        Call<ModelSendCode> call = apiClient.sendCode(user_phone);
        call.enqueue(new Callback<ModelSendCode>() {
            @Override
            public void onResponse(Call<ModelSendCode> call, Response<ModelSendCode> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        Toast.makeText(LoginCodeActivity.this, "Code sent sucesfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginCodeActivity.this, "Unexpected error :(", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginCodeActivity.this, "Unexpected error :(", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelSendCode> call, Throwable t) {

            }
        });

    }
    private void verifyPhoneNumberWithCode(String code, String token) {

        API apiClient = ClientAPI.createAPI_With_Token(token);
        Call<ModelSendCode> call = apiClient.verifyCode(code);
        call.enqueue(new Callback<ModelSendCode>() {
            @Override
            public void onResponse(Call<ModelSendCode> call, Response<ModelSendCode> response) {

                if (!gson.toJson(response.body()).equalsIgnoreCase("")) {
                    if (!response.body().getError()) {
                        signInWithPhoneAuthCredential();
                    } else {
                        Toast.makeText(LoginCodeActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ModelSendCode> call, Throwable t) {


            }
        });
    }

    private void signInWithPhoneAuthCredential() {

            saveData.saveUserToken(token);
            saveData.save_user_info(user_name,user_email,user_nuis,user_phone);
            startActivity(new Intent(this,MainActivity.class));
        }
    }
