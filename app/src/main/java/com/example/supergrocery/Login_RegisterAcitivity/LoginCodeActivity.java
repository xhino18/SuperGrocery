package com.example.supergrocery.Login_RegisterAcitivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.MainActivity2;
import com.example.supergrocery.Other.SaveData;

import com.example.supergrocery.ModelsPost.ModelSendCode;
import com.example.supergrocery.ModelsPost.UserRegister;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.ActivityLoginCodeBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginCodeActivity extends AppCompatActivity {
    ActivityLoginCodeBinding binding;

    Bundle bundle;
    Gson gson;
    private int userId;
    SaveData saveData;
    Boolean is_login;
    String user_name, user_email, user_nuis, user_phone, token, register_type,register_type_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginCodeBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        init();

    }

    private void init() {
        gson = new GsonBuilder().create();
        bundle = getIntent().getExtras();
        saveData = new SaveData(this);

        if (bundle != null) {
            if (bundle.getString("register_type").equalsIgnoreCase("register")) {
                is_login = false;
                user_name = bundle.getString("name");
                user_email = bundle.getString("email");
                user_nuis = bundle.getString("nuis");
                user_phone = bundle.getString("phone");

                if (!user_phone.equalsIgnoreCase("")) {
                    startPhoneNumberVerification(user_phone);
                }
            } else{
                    is_login = true;
                    user_phone = bundle.getString("phone");
                    if (!user_phone.equalsIgnoreCase("")) {
                        startPhoneNumberVerification(user_phone);
                    }
            }
        }
        binding.buttonResendCode.setOnClickListener(v -> {
            user_phone = bundle.getString("phone");
            if (!user_phone.equalsIgnoreCase("")) {
                startPhoneNumberVerification(user_phone);
            }

        });
        binding.buttonVerify.setOnClickListener(v -> {
            if (binding.buttonVerify.getText().toString().length() < 6) {
                Toast.makeText(LoginCodeActivity.this, "Kodi i verifikimit i pasakte!", Toast.LENGTH_SHORT).show();
            } else {
                verifyPhoneNumberWithCode(binding.code.getText().toString(), userId);
            }

        });

//        if (activityLoginCodeBinding.code!= null) {
//            activityLoginCodeBinding.code.setOnPinEnteredListener(str -> {
//                if (str.toString().equals("111111")) {
//                    Toast.makeText(LoginCodeActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(LoginCodeActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
//                    activityLoginCodeBinding.code.setText(null);
//                }
//            });
//        }

    }

    private void startPhoneNumberVerification(String user_phone) {

        API apiClient = ClientAPI.createApiNoToken();
        Call<ModelSendCode> call = apiClient.sendCode(user_phone);
        call.enqueue(new Callback<ModelSendCode>() {
            @Override
            public void onResponse(Call<ModelSendCode> call, Response<ModelSendCode> response) {
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")) {
                    if (!response.body().getError()) {
                        userId = response.body().getUser_id();
                        binding.buttonVerify.setEnabled(true);
                        Toast.makeText(LoginCodeActivity.this, "Code sent sucessfully!", Toast.LENGTH_SHORT).show();
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

    private void verifyPhoneNumberWithCode(String code, int userId) {

        API apiClient = ClientAPI.createApiNoToken();
        Call<UserRegister> call = apiClient.verifyCode(code, userId);
        call.enqueue(new Callback<UserRegister>() {
            @Override
            public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {

                if (!gson.toJson(response.body()).equalsIgnoreCase("")) {
                    if (!response.body().getError()) {
                        System.out.println("tokentoken" + token);
                        saveData.saveUserToken(response.body().getToken());
                        saveData.save_user_info(response.body().getData().getName(),
                                response.body().getData().getEmail(),
                                response.body().getData().getNuis(),
                                response.body().getData().getPhone_number());
                        gotomenu();
                    } else {
                        Toast.makeText(LoginCodeActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<UserRegister> call, Throwable t) {


            }
        });
    }


    public void gotomenu() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        Toast.makeText(LoginCodeActivity.this, "Wellcome back " + saveData.get_name() + " !", Toast.LENGTH_SHORT).show();

        finish();

    }
}
