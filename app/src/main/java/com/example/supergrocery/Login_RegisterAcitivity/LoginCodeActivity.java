package com.example.supergrocery.Login_RegisterAcitivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.MainActivity;
import com.example.supergrocery.Other.SaveData;
import com.example.supergrocery.PostModels.ModelRegister;
import com.example.supergrocery.PostModels.ModelSendCode;

import com.example.supergrocery.R;
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
    private int userId;
    SaveData saveData;
    Boolean is_login;
    String user_name, user_email, user_nuis, user_phone,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginCodeBinding = ActivityLoginCodeBinding.inflate(getLayoutInflater());
        final View view = activityLoginCodeBinding.getRoot();
        setContentView(view);

        init();

    }

    private void init() {
        gson = new GsonBuilder().create();
        bundle = getIntent().getExtras();
        saveData=new SaveData(this);
        activityLoginCodeBinding.rightArrow.setOnClickListener(v -> {
            Intent intent = new Intent(LoginCodeActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        if (bundle != null) {
            is_login = false;
            user_name = bundle.getString("name");
            user_email = bundle.getString("email");
            user_nuis = bundle.getString("nuis");
            user_phone = bundle.getString("phone");

            if (!user_phone.equalsIgnoreCase("")) {
                startPhoneNumberVerification(user_phone);
            }
        } else {
            is_login = true;
            user_phone = bundle.getString("phone");
            if (!user_phone.equalsIgnoreCase("")) {
                startPhoneNumberVerification(user_phone);
            }

        }
        activityLoginCodeBinding.buttonResendCode.setOnClickListener(v -> {
            user_phone = bundle.getString("phone");
            if (!user_phone.equalsIgnoreCase("")) {
                startPhoneNumberVerification(user_phone);
            }

        });
            activityLoginCodeBinding.buttonVerify.setOnClickListener(v -> {
                if (activityLoginCodeBinding.buttonVerify.getText().toString().length() < 6) {
                    Toast.makeText(LoginCodeActivity.this, "Kodi i verifikimit i pasakte!", Toast.LENGTH_SHORT).show();
                } else {
                    verifyPhoneNumberWithCode(activityLoginCodeBinding.code.getText().toString(), userId);
                }

            });

        }

        private void startPhoneNumberVerification (String user_phone){

            API apiClient = ClientAPI.createApiNoToken();
            Call<ModelSendCode> call = apiClient.sendCode(user_phone);
            call.enqueue(new Callback<ModelSendCode>() {
                @Override
                public void onResponse(Call<ModelSendCode> call, Response<ModelSendCode> response) {
                    if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                        if (!response.body().getError()) {
                            userId=response.body().getUserId();
                            activityLoginCodeBinding.buttonVerify.setEnabled(true);
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
        private void verifyPhoneNumberWithCode (String code,int userId){

            API apiClient = ClientAPI.createApiNoToken();
            Call<ModelRegister> call = apiClient.verifyCode(code, userId);
            call.enqueue(new Callback<ModelRegister>() {
                @Override
                public void onResponse(Call<ModelRegister> call, Response<ModelRegister> response) {

                    if (!gson.toJson(response.body()).equalsIgnoreCase("")) {
                        if (!response.body().getError()) {
                            System.out.println("tokentoken"+token);
                            saveData.saveUserToken(response.body().getToken());
                            saveData.save_user_info(response.body().getData().getName(),
                                    response.body().getData().getEmail(),
                                    response.body().getData().getNuis(),
                                    response.body().getData().getPhoneNumber());
                            gotomenu();
                        } else {
                            Toast.makeText(LoginCodeActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<ModelRegister> call, Throwable t) {


                }
            });
        }

//        private void signInWithPhoneAuthCredential () {
//
//            saveData.saveUserToken(token);
//            saveData.save_user_info(user_name, user_email, user_nuis, user_phone);
//            gotomenu();
//        }

        public void gotomenu () {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
