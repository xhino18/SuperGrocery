package com.example.supergrocery.loginregisteractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.supergrocery.MainActivity;
import com.example.supergrocery.api.API;
import com.example.supergrocery.models.ModelMainToken;
import com.example.supergrocery.models.UserRegisterData;
import com.example.supergrocery.other.SaveData;

import com.example.supergrocery.models.ModelSendCode;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.ActivityLoginCodeBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class LoginCodeActivity extends AppCompatActivity {
    ActivityLoginCodeBinding binding;

    Bundle bundle;
    Gson gson;
    private int userId;
    SaveData saveData;
    Boolean is_login;
    String user_name, user_email, user_nuis, user_phone, token;
    @Inject
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginCodeBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
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
            } else {
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

    }

    private void startPhoneNumberVerification(String user_phone) {
        /*Call<ModelSendCode> call = api.sendCode(user_phone);
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
        });*/

    }

    private void verifyPhoneNumberWithCode(String code, int userId) {
     /*   Call<ModelMainToken<UserRegisterData>> call = api.verifyCode(code, userId);
        call.enqueue(new Callback<ModelMainToken<UserRegisterData>>() {
            @Override
            public void onResponse(Call<ModelMainToken<UserRegisterData>> call, Response<ModelMainToken<UserRegisterData>> response) {

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
            public void onFailure(Call<ModelMainToken<UserRegisterData>> call, Throwable t) {


            }
        });*/
    }


    public void gotomenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        Toast.makeText(LoginCodeActivity.this, "Wellcome back " + saveData.get_name() + " !", Toast.LENGTH_SHORT).show();

        finish();

    }
}
