package com.example.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    TextView splashText;
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashText = findViewById(R.id.splashText);
        anim = AnimationUtils.loadAnimation(this, R.anim.splashanimation);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/nerko.ttf");
        splashText.setTypeface(typeface);
        splashText.setAnimation(anim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), GamePage.class);
                startActivity(intent);
                finish();
            }
        }, 1600);
    }
}