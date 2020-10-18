package com.example.supergrocery.Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.supergrocery.Login_RegisterAcitivity.LoginActivity;
import com.example.supergrocery.MainActivity;
import com.example.supergrocery.R;
import com.example.supergrocery.databinding.ActivityIntro1Binding;

public class IntroActivity_1 extends AppCompatActivity {
    ActivityIntro1Binding activityIntro1Binding;


    private static int HAPJE_ACTIVITY= 6000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityIntro1Binding=ActivityIntro1Binding.inflate(getLayoutInflater());
        final View view=activityIntro1Binding.getRoot();
        setContentView(view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(IntroActivity_1.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.intro_login, R.anim.slide_out_right);
                finish();
            }
        },HAPJE_ACTIVITY);
    }
}