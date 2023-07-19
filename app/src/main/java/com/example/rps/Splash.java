package com.example.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    TextView splashText;
    Animation anim;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences("User Details", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        if(sharedPreferences.getString("remember", "").equals("false")){
            edit.clear();
            edit.apply();
        }


        splashText = findViewById(R.id.splashText);
        anim = AnimationUtils.loadAnimation(this, R.anim.splashanimation);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/nerko.ttf");
        splashText.setTypeface(typeface);
        splashText.setAnimation(anim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1600);
    }
}