package com.example.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText cred, password;
    Button loginBtn;
    Database db;
    View remember;
    CheckBox remember_check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = getSharedPreferences("User Details", MODE_PRIVATE);
        db = new Database(getApplicationContext());
        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
        remember = findViewById(R.id.rememberme);
        remember_check = findViewById(R.id.remember_check);
        remember.setOnClickListener(this);
        findViewById(R.id.to_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        cred = findViewById(R.id.login_user_name);
        password = findViewById(R.id.login_password);
        if (v.getId() == R.id.login_btn) {
            boolean result = db.loginValidation(cred.getText().toString(), password.getText().toString(), remember_check.isChecked());
            if (result) {
                navigate();
            } else {
                Toast.makeText(this, "Failed To Login.", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.to_register) {
            Intent toRegister = new Intent(getApplicationContext(), Register.class);
            startActivity(toRegister);
            finish();
        }
        else if(v.getId() == R.id.rememberme){
            remember_check.setChecked(!remember_check.isChecked());
        }
    }

    public void navigate() {
        Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}