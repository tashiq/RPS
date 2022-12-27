package com.example.rps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.game_menu_item:
                intent = new Intent(getApplicationContext(), GamePage.class);
                startActivity(intent);
                return true;
            case R.id.aboutus_menu_item:
                intent = new Intent(getApplicationContext(), AboutUs.class);
                startActivity(intent);
                return true;
            case R.id.tutorial_menu_item:
                intent = new Intent(getApplicationContext(), Tutorial.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}