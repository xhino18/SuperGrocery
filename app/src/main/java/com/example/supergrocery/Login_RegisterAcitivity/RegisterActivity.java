package com.example.supergrocery.Login_RegisterAcitivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.supergrocery.API.API;
import com.example.supergrocery.API.ClientAPI;
import com.example.supergrocery.MainActivity;
import com.example.supergrocery.PostModels.ModelRegister;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.ActivityRegisterBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import javax.xml.validation.Validator;

import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity{

    ActivityRegisterBinding activityRegisterBinding;

    String name, email, nuis, phone;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding=ActivityRegisterBinding.inflate(getLayoutInflater());
        View view=activityRegisterBinding.getRoot();
        setContentView(view);

        init();



    }

    private void init() {

        gson= new GsonBuilder().create();
        activityRegisterBinding.tvProfileIndivid.setOnClickListener(v -> {
            activityRegisterBinding.linearNuis.setVisibility(View.INVISIBLE);
        });
        activityRegisterBinding.tvProfileBiznes.setOnClickListener(v -> {
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
            if (!name.equals("") && !email.equals("") && !nuis.equals("") && !phone.equals("")) {
                if(email.contains("@")){
                    Toast.makeText(RegisterActivity.this, "Registered Succesfully!", Toast.LENGTH_SHORT).show();
                    registercall();
                }else{
                    Toast.makeText(RegisterActivity.this, "Email not valid!", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(RegisterActivity.this, "Please complete all fields!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void registercall(){
        API apiClient = ClientAPI.createApiNoToken();
        retrofit2.Call<ModelRegister> call = apiClient.register(name,email,phone,1,nuis,API.PLATFORM_ID,API.FIREBASE_TOKEN);
        call.enqueue(new Callback<ModelRegister>() {
            @Override
            public void onResponse(retrofit2.Call<ModelRegister> call, Response<ModelRegister> response) {

                // System.out.println("respoonseeee "+ gson.toJson(response.body()));
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")){
                    if (!response.body().getError()){
                        Intent intent = new Intent(RegisterActivity.this,LoginCodeActivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("email",email);
                        intent.putExtra("phone",phone);
                        intent.putExtra("account_type",1);
                        intent.putExtra("nuis",nuis);
                        intent.putExtra("platform",API.PLATFORM_ID);
                        intent.putExtra("firebase_token",API.FIREBASE_TOKEN);
                        intent.putExtra("Token",response.body().getToken());
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
            public void onFailure(retrofit2.Call<ModelRegister> call, Throwable t) {
            }
        });
    }


}