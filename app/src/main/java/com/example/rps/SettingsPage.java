package com.example.rps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SettingsPage extends AppCompatActivity {
    private static APIinterface apIinterface;
    RecyclerView userRV;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        apIinterface = API.getRetrofit().create(APIinterface.class);
        userRV = findViewById(R.id.users);
        apIinterface.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.body().size() > 0) {
                    List<User> userList = response.body();
                    adapter = new UserAdapter(getApplicationContext(), userList);
                    userRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    try {
                        userRV.setAdapter(adapter);
                    } catch (Exception e) {
                        Log.i("babel", "onResponse: " + e);
                    }
                } else {
                    Log.i("babel", "onResponse: empty");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(SettingsPage.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}