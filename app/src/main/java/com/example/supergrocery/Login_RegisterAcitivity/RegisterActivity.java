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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import javax.xml.validation.Validator;

import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity{
    ImageView right_Arrow;
    Button button_register;
    EditText tv_profile_name, tv_profile_email, tv_profile_nuis, tv_profile_phone;
    String name, email, nuis, phone;
    Gson gson;
    Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
//        validator = new Validator(this);
//        validator.setValidationListener(this);
    }

    private void init() {

        gson= new GsonBuilder().create();
        right_Arrow = findViewById(R.id.right_Arrow);
        right_Arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_register = findViewById(R.id.button_register);
        tv_profile_name = findViewById(R.id.tv_profile_name);
        tv_profile_email = findViewById(R.id.tv_profile_email);
        tv_profile_nuis = findViewById(R.id.tv_profile_nuis);
        tv_profile_phone = findViewById(R.id.tv_profile_phone);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = tv_profile_name.getText().toString();
                email = tv_profile_email.getText().toString();
                nuis = tv_profile_nuis.getText().toString();
                phone = tv_profile_phone.getText().toString();
                if (!tv_profile_name.equals(null) && !tv_profile_email.equals(null) && !tv_profile_nuis.equals(null) && !tv_profile_phone.equals(null)) {
                    Toast.makeText(RegisterActivity.this, "Registered Succesfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Please complete all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void registercall(){
        API apiClient = ClientAPI.createApiNoToken();
        retrofit2.Call<ModelRegister> call = apiClient.register(name,email,nuis,phone);
        call.enqueue(new Callback<ModelRegister>() {
            @Override
            public void onResponse(retrofit2.Call<ModelRegister> call, Response<ModelRegister> response) {

                // System.out.println("respoonseeee "+ gson.toJson(response.body()));
                if (!gson.toJson(response.body()).equalsIgnoreCase("null")){
                    if (!response.body().getError()){
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("email",email);
                        intent.putExtra("nuis",nuis);
                        intent.putExtra("phone",phone);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

//    @Override
//    public void onValidationSucceeded() {
//        //  Toast.makeText(this, "Yay! we got it right!", Toast.LENGTH_SHORT).show();
//
//        registercall();
//    }
//
//    @Override
//    public void onValidationFailed(List<ValidationError> errors) {
//        for (ValidationError error : errors) {
//            View view = error.getView();
//            String message = error.getCollatedErrorMessage(this);
//
//            // Display error messages ;)
//            if (view instanceof EditText) {
//                ((EditText) view).setError(message);
//            } else {
//                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}