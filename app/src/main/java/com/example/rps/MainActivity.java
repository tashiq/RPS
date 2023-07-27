package com.example.rps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    LayoutInflater game, settings, computer, tutorial;
    LinearLayout firstRow, secondRow;
    ImageButton loginBtn, profileBtn;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        drawerLayout = findViewById(R.id.main_activity_drawerlayout);
        navigationView = findViewById(R.id.navigation_view_drawer);
        toolbar = findViewById(R.id.toolbar);
        firstRow = findViewById(R.id.home_page_item_first);
        secondRow = findViewById(R.id.home_page_item_second);
        profileBtn = findViewById(R.id.profile_main_btn);

        profileBtn.setOnClickListener(this);
        loginBtn = findViewById(R.id.login_main_activity);
        sharedPreferences = getSharedPreferences("User Details", MODE_PRIVATE);
        if (sharedPreferences.contains("user_name")) {
            loginBtn.setVisibility(View.GONE);
            profileBtn.setVisibility(View.VISIBLE);
        } else {
            profileBtn.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
        }
        loginBtn.setOnClickListener(this);
//        toolbar
        setSupportActionBar(toolbar);
//        navigation drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
//        navigation view
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
//        home page items
        game = getLayoutInflater();
        View gameItem = game.inflate(R.layout.home_page_items, firstRow, false);
        ImageView gameIcon = gameItem.findViewById(R.id.home_page_item_icon);
        TextView gameText = gameItem.findViewById(R.id.home_page_item_title);
        gameIcon.setImageResource(R.drawable.icon_game_white);
        gameText.setText("Play");

        computer = getLayoutInflater();
        View computerItem = computer.inflate(R.layout.home_page_items, firstRow, false);
        ImageView computerIcon = computerItem.findViewById(R.id.home_page_item_icon);
        TextView computerText = computerItem.findViewById(R.id.home_page_item_title);
        computerIcon.setImageResource(R.drawable.icon_computer_white);
        computerText.setText("Computer");

        settings = getLayoutInflater();
        View settingsItem = settings.inflate(R.layout.home_page_items, secondRow, false);
        ImageView settingsIcon = settingsItem.findViewById(R.id.home_page_item_icon);
        TextView settingsText = settingsItem.findViewById(R.id.home_page_item_title);
        settingsIcon.setImageResource(R.drawable.review);
        settingsText.setText("Reviews");

        tutorial = getLayoutInflater();
        View tutorialItem = tutorial.inflate(R.layout.home_page_items, secondRow, false);
        ImageView tutorialIcon = tutorialItem.findViewById(R.id.home_page_item_icon);
        TextView tutorialText = tutorialItem.findViewById(R.id.home_page_item_title);
        tutorialIcon.setImageResource(R.drawable.icon_tutorial_white);
        tutorialText.setText("Tutorial");


        gameItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GamePage.class);
                startActivity(i);
            }
        });
        tutorialItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Tutorial.class);
                startActivity(i);
            }
        });
        settingsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ReviewsPage.class);
                startActivity(i);
            }
        });
        computerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GamePage.class);
                startActivity(i);
            }
        });

        firstRow.addView(gameItem);
        firstRow.addView(computerItem);
        secondRow.addView(tutorialItem);
        secondRow.addView(settingsItem);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.profile_menu_item:
                intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
                break;

            case R.id.game_menu_item:
                intent = new Intent(getApplicationContext(), GamePage.class);
                startActivity(intent);
                break;
            case R.id.aboutus_menu_item:
                intent = new Intent(getApplicationContext(), AboutUs.class);
                startActivity(intent);
                break;
            case R.id.tutorial_menu_item:
                intent = new Intent(getApplicationContext(), Tutorial.class);
                startActivity(intent);
                break;
            case R.id.review_menu_item:
                intent = new Intent(getApplicationContext(), ReviewsPage.class);
                startActivity(intent);
                break;
            case R.id.api_menu_item:
                intent = new Intent(getApplicationContext(), SettingsPage.class);
                startActivity(intent);
                break;
            default:
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert!");
            builder.setIcon(R.drawable.icon_warning);
            builder.setMessage("Would you like to exit?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
//                    Toast.makeText(MainActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.login_main_activity) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            return;
        }
        if (v.getId() == R.id.profile_main_btn) {
            Intent intent = new Intent(getApplicationContext(), Profile.class);
            startActivity(intent);
            return;
        }

    }


}