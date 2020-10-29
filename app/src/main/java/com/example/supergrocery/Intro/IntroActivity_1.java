package com.example.supergrocery.Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.example.supergrocery.Login_RegisterAcitivity.LoginActivity;
import com.example.supergrocery.MainActivity;
import com.example.supergrocery.Other.SaveData;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.ActivityIntro1Binding;

public class IntroActivity_1 extends AppCompatActivity {
    ActivityIntro1Binding binding;
    SaveData saveData;


    private static int HAPJE_ACTIVITY= 6000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityIntro1Binding.inflate(getLayoutInflater());
        final View view= binding.getRoot();
        setContentView(view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        saveData=new SaveData(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (saveData.getToken().equals("")) {
                    Intent intent = new Intent(IntroActivity_1.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.intro_login, R.anim.slide_out_right);
                    finish();
                }else{
                    Intent intent1 = new Intent(IntroActivity_1.this, MainActivity.class);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.intro_login, R.anim.slide_out_right);
                }
            }
        },HAPJE_ACTIVITY);
    }
}