package com.example.supergrocery.Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.supergrocery.MainActivity;
import com.example.supergrocery.R;

public class IntroActivity_1 extends AppCompatActivity {
    TextView tv_intro;

    private static int HAPJE_ACTIVITY= 10700;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tv_intro=findViewById(R.id.tv_intro);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(IntroActivity_1.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },HAPJE_ACTIVITY);
    }
}