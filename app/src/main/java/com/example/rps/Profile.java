package com.example.rps;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    Button logoutBtn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editorUpdate;
    TextView name, userName, email, country, phone, dob;
    ImageView profileImage;
    ImageButton nameEdit, emailEdit, phoneEdit, countryEdit, dobEdit, nameCheck, emailCheck, phoneCheck, countryCheck, dobCheck;
    EditText nameET, emailET, phoneET, countryET, dobET;
    Database db;
    LinearLayout nameE, nameC, phoneE, phoneC, dobE, dobC, countryE, countryC, emailE, emailC;
    private static final int GALLERY_REQUEST_CODE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");
        name = findViewById(R.id.profile_name);
        userName = findViewById(R.id.profile_user_name);
        email = findViewById(R.id.profile_email);
        country = findViewById(R.id.profile_country);
        phone = findViewById(R.id.profile_phone);
        dob = findViewById(R.id.profile_dob);
        profileImage = findViewById(R.id.profile_image);
        nameEdit = findViewById(R.id.name_edit);
        emailEdit = findViewById(R.id.email_edit);
        phoneEdit = findViewById(R.id.phone_edit);
        countryEdit = findViewById(R.id.country_edit);
        dobEdit = findViewById(R.id.dob_edit);
        nameET = findViewById(R.id.profile_name_edit_text);
        nameCheck = findViewById(R.id.name_check);
        emailET = findViewById(R.id.profile_email_edit_text);
        emailCheck = findViewById(R.id.email_check);
        phoneET = findViewById(R.id.profile_phone_edit_text);
        phoneCheck = findViewById(R.id.phone_check);
        countryET = findViewById(R.id.profile_country_edit_text);
        countryCheck = findViewById(R.id.country_check);
        dobET = findViewById(R.id.profile_dob_edit_text);
        dobCheck = findViewById(R.id.dob_check);
        db = new Database(getApplicationContext());
        nameEdit.setOnClickListener(this);
        emailEdit.setOnClickListener(this);
        countryEdit.setOnClickListener(this);
        dobEdit.setOnClickListener(this);
        phoneEdit.setOnClickListener(this);
        profileImage.setOnClickListener(this);
        countryCheck.setOnClickListener(this);
        phoneCheck.setOnClickListener(this);
        nameCheck.setOnClickListener(this);
        emailCheck.setOnClickListener(this);
        dobCheck.setOnClickListener(this);
        nameE = findViewById(R.id.name_edit_container);
        nameC = findViewById(R.id.name_check_container);
        emailE = findViewById(R.id.email_edit_container);
        emailC = findViewById(R.id.email_check_container);
        phoneE = findViewById(R.id.phone_edit_container);
        phoneC = findViewById(R.id.phone_check_container);
        countryE = findViewById(R.id.country_edit_container);
        countryC = findViewById(R.id.country_check_container);
        dobE = findViewById(R.id.dob_edit_container);
        dobC = findViewById(R.id.dob_check_container);
        sharedPreferences = getSharedPreferences("User Details", MODE_PRIVATE);
        editorUpdate = sharedPreferences.edit();
//        go login if not logged in.
        if (!sharedPreferences.contains("user_name")) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            Toast.makeText(this, "Please login first.", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
            return;
        }
//        working on profile.
        name.setText(sharedPreferences.getString("name", "Not provided"));
        userName.setText(sharedPreferences.getString("user_name", "Not provided"));
        email.setText(sharedPreferences.getString("user_email", "Not provided"));
        phone.setText(sharedPreferences.getString("user_phone", "Not provided"));
        dob.setText(sharedPreferences.getString("user_DoB", "Not provided"));
        country.setText(sharedPreferences.getString("location", "Not provided"));

        logoutBtn = findViewById(R.id.profile_logout_btn);
        logoutBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.profile_logout_btn) {
            editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            return;
        } else if (v.getId() == R.id.profile_image) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intent, GALLERY_REQUEST_CODE);
            return;
        }
        if (v.getId() == R.id.name_check) {
            String nameValue = nameET.getText().toString();
            db.updateUser("name", nameValue, sharedPreferences.getString("user_name", "Not provided"));
            name.setText(nameValue);
            editorUpdate.putString("name", nameValue);
            nameC.setVisibility(View.GONE);
            nameE.setVisibility(View.VISIBLE);
            editorUpdate.commit();
        } else if (v.getId() == R.id.email_check) {
            String nameValue = emailET.getText().toString();
            db.updateUser("user_email", nameValue, sharedPreferences.getString("user_name", "Not provided"));
            email.setText(nameValue);
            editorUpdate.putString("user_email", nameValue);
            emailE.setVisibility(View.VISIBLE);
            emailC.setVisibility(View.GONE);
            editorUpdate.commit();
        } else if (v.getId() == R.id.phone_check) {
            String nameValue = phoneET.getText().toString();
            db.updateUser("user_phone", nameValue, sharedPreferences.getString("user_name", "Not provided"));
            phone.setText(nameValue);
            phoneE.setVisibility(View.VISIBLE);
            phoneC.setVisibility(View.GONE);
            editorUpdate.putString("user_phone", nameValue);
            editorUpdate.commit();
        } else if (v.getId() == R.id.country_check) {
            String nameValue = countryET.getText().toString();
            db.updateUser("location", nameValue, sharedPreferences.getString("user_name", "Not provided"));
            country.setText(nameValue);
            editorUpdate.putString("location", nameValue);
            countryE.setVisibility(View.VISIBLE);
            countryC.setVisibility(View.GONE);
            editorUpdate.commit();
        } else if (v.getId() == R.id.dob_check) {
            String nameValue = dobET.getText().toString();
            db.updateUser("user_DoB", nameValue, sharedPreferences.getString("user_name", "Not provided"));
            dob.setText(nameValue);
            dobE.setVisibility(View.VISIBLE);
            dobC.setVisibility(View.GONE);
            editorUpdate.putString("user_DoB", nameValue);
            editorUpdate.commit();
        }
        if(v.getId() == R.id.country_edit){
            countryE.setVisibility(View.GONE);
            countryC.setVisibility(View.VISIBLE);
            countryET.setText(country.getText().toString());
        }
        if(v.getId() == R.id.name_edit){
            nameE.setVisibility(View.GONE);
            nameC.setVisibility(View.VISIBLE);
            nameET.setText(name.getText().toString());
        }
        if(v.getId() == R.id.email_edit){
            emailE.setVisibility(View.GONE);
            emailC.setVisibility(View.VISIBLE);
            emailET.setText(email.getText().toString());
        }
        if(v.getId() == R.id.dob_edit){
            dobE.setVisibility(View.GONE);
            dobC.setVisibility(View.VISIBLE);
            dobET.setText(dob.getText().toString());
        }
        if(v.getId() == R.id.phone_edit){
            phoneE.setVisibility(View.GONE);
            phoneC.setVisibility(View.VISIBLE);
            phoneET.setText(phone.getText().toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri selected = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selected);
                bitmap = Bitmap.createScaledBitmap(bitmap, 80, 80, true);
                profileImage.setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bmp = stream.toByteArray();
                bitmap.recycle();
                if(db.UploadImage(bmp, userName.getText().toString())){
                    Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}