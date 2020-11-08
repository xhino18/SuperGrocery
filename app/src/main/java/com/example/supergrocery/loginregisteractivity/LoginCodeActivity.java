package com.example.supergrocery.loginregisteractivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
    private ViewModel_Authentication viewModel_authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel_authentication=new ViewModelProvider(this).get(ViewModel_Authentication.class);
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
                    viewModel_authentication.startPhoneNumberVerification(user_phone);
                    getCode();
                }
            } else {
                is_login = true;
                user_phone = bundle.getString("phone");
                if (!user_phone.equalsIgnoreCase("")) {
                    viewModel_authentication.startPhoneNumberVerification(user_phone);
                    getCode();

                }
            }
        }
        binding.buttonResendCode.setOnClickListener(v -> {
            user_phone = bundle.getString("phone");
            if (!user_phone.equalsIgnoreCase("")) {
                viewModel_authentication.startPhoneNumberVerification(user_phone);
                getCode();

            }

        });
        binding.buttonVerify.setOnClickListener(v -> {
            if (binding.buttonVerify.getText().toString().length() < 6) {
                Toast.makeText(LoginCodeActivity.this, "Kodi i verifikimit i pasakte!", Toast.LENGTH_SHORT).show();
            } else {
                viewModel_authentication.verifyPhoneNumberWithCode(binding.code.getText().toString(), userId);
                verifyPhone();
            }

        });

    }

    private void getCode(){
        viewModel_authentication.getSendCodeLiveData().observe(this,modelSendCode -> {
            if (!modelSendCode.getError()){
                userId = modelSendCode.getUser_id();
                binding.buttonVerify.setEnabled(true);
                Toast.makeText(LoginCodeActivity.this, "Code sent sucessfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verifyPhone(){
        viewModel_authentication.getVerifyPhoneLiveData().observe(this,userRegisterDataModelMainToken -> {
            if (!userRegisterDataModelMainToken.getError()){
                saveData.saveUserToken(userRegisterDataModelMainToken.getToken());
                saveData.save_user_info(userRegisterDataModelMainToken.getData().getName(),
                        userRegisterDataModelMainToken.getData().getEmail(),
                        userRegisterDataModelMainToken.getData().getNuis(),
                        userRegisterDataModelMainToken.getData().getPhone_number());
                gotomenu();
            }
        });
    }


    public void gotomenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        Toast.makeText(LoginCodeActivity.this, "Wellcome back " + saveData.get_name() + " !", Toast.LENGTH_SHORT).show();

        finish();

    }
}
