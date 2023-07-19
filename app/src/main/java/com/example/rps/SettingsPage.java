package com.example.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        APIinterface apiInterface = API.getRetrofit();
        Call<List<User>> call= apiInterface.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> userList = response.body();
                    // Process the list of users
                } else {
                    // Handle API error
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.i("babel", "onFailure: " + t.getMessage());
            }
        });
    }
}