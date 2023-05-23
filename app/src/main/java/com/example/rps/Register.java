package com.example.rps;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity implements View.OnClickListener{

//    initialization
    Button registerDobBtn;
    TextView  login_link;
    DatePickerDialog datePickerDialog;
    DatePicker datePicker;
    EditText userName, name, email, password, phone;
    Button register;
    AutoCompleteTextView country;
    Database db;
    String selected_country = "World";
    SharedPreferences sharedPreferences;
    boolean userAuth = false, emailAuth = false, phoneAuth = false;
    String[] country_list;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        check whether if he is logged in before.
        sharedPreferences = getSharedPreferences("User Details", MODE_PRIVATE);
        if(sharedPreferences.contains("user_name")){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
//        jhamela
        db = new Database(getApplicationContext());
        datePicker = new DatePicker(this);
        register = findViewById(R.id.register_btn);
        register.setOnClickListener(this);
        userName = findViewById(R.id.register_user_name);
        login_link = findViewById(R.id.login_page_link);
        login_link.setOnClickListener(this);
        email = findViewById(R.id.register_email);
        /*

        name = findViewById(R.id.register_name);
        registerDobBtn = findViewById(R.id.register_dob_dialog);
        registerDobBtn.setOnClickListener(this);
        dob = findViewById(R.id.register_dob_tv);
        phone = findViewById(R.id.register_phone);
        country = findViewById(R.id.register_country);
        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && (phone.getText().toString().trim().length() != 11)){
                    phoneAuth = false;
                    phone.setError("Phone number must have 11 digits");
                }
                else{
                    phoneAuth = true;
                }
            }
        });

// onclick

        if(v.getId() == R.id.register_dob_dialog){
            int currentDate = datePicker.getDayOfMonth();
            int currentMonth = datePicker.getMonth();
            int currentYear = datePicker.getYear();
            datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month+=1;
                    String datePicked = dayOfMonth + "/" + month + "/" + year;
                    dob.setText(datePicked);
                }
            }, currentYear, currentMonth, currentDate);
            datePickerDialog.show();
        }
        String DoB = dob.getText().toString().trim();

        */
        password = findViewById(R.id.register_password);
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    Boolean b = db.userNameVerification(s.toString());
                    if(b){
                        userAuth = false;
                        userName.setError("User name must be unique");
                    }
                    else{
                        userAuth = true;
                    }
//                    Log.i("babel", "afterTextChanged: " + userAuth);
                }
                catch (Exception e){
                    Log.i("listener", "afterTextChanged: " + e);
                }
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                emailAuth = db.emailVerification(s.toString());
                if(!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()){
                    emailAuth = false;
                    email.setError("Invalid password format.");
                }
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
//                String pass = v.toString();
//                Log.i("listener", "onFocusChange: " + pass.matches(pattern));
//                if(!pass.matches(pattern)){
//                    password.setError("Password must have \n * at least 6 letter. \n * contains an alphabet and a digit.");
//                }
//                String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{6,}";
            }
        });


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.register_btn){
            String UserName = userName.getText().toString().trim();
            Log.i("babel", "onClick: " + UserName);
            if(UserName == "" || UserName == null){
                userName.setError("Empty field");
                return;
            }
            if(userAuth == false){
                Toast.makeText(this, "Invalid username", Toast.LENGTH_SHORT).show();
                return;
            }
            if(emailAuth == false){
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            }
            String Email = email.getText().toString().trim();
            String Password = password.getText().toString();
            try {
                boolean result = false;
                result = db.addNewUser(UserName, "", Email, "", "",  Password, "");
                if(result){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }catch (Exception e){

            }
        }
        else if(v.getId() == R.id.login_page_link){
            Intent toLogin = new Intent(getApplicationContext(), Login.class);
            startActivity(toLogin);
            finish();
        }
    }
}