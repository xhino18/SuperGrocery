package com.example.supergrocery.Login_RegisterAcitivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.supergrocery.R;
import com.example.supergrocery.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding=ActivityLoginBinding.inflate(getLayoutInflater());
        final View view=activityLoginBinding.getRoot();
        setContentView(view);
        init();


    }

    private void init() {
        activityLoginBinding.rightArrow.setOnClickListener(v -> {
            Intent intent= new Intent(LoginActivity.this,LoginCodeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
       activityLoginBinding.buttonRegister.setOnClickListener(v -> {
           Intent intent= new Intent(LoginActivity.this,RegisterActivity.class);
           startActivity(intent);
           overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
       });
    }
}