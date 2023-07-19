package com.example.rps;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReviewsPage extends AppCompatActivity implements View.OnClickListener {
    RecyclerView reviewRecycler;
    Database db;
    Button addReview, viewReview, nextBtn, prevBtn, one;
    Fragment fragment;
    TextView avgRating;
    public Context mainContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_page);
        avgRating = findViewById(R.id.avg_rating);
        addReview = findViewById(R.id.write_review_btn);
        viewReview = findViewById(R.id.view_review_btn);
        nextBtn = findViewById(R.id.next_btn);
        one = findViewById(R.id.one);
        prevBtn = findViewById(R.id.prev_btn);
        prevBtn.setEnabled(false);
        nextBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);
        addReview.setOnClickListener(this);
        viewReview.setOnClickListener(this);
        db = new Database(getApplicationContext());
//        Toast.makeText(this, "" + db.avgRating(), Toast.LENGTH_SHORT).show();
        double avg_rt = db.avgRating();
        avgRating.setText("Average Rating: " + avg_rt + " (5)");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.write_review_btn) {
            fragment = new AddReviewFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.review_fragment, fragment);
            fragmentTransaction.commit();

            // change background color
            addReview.setBackgroundResource(R.drawable.solid_btn_reverse);
            addReview.setTextColor(Color.WHITE);
            viewReview.setBackgroundResource(R.drawable.solid_btn);
            viewReview.setTextColor(Color.BLACK);
            return;
        }
        if (v.getId() == R.id.view_review_btn) {
            fragment = new AllReviews();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.review_fragment, fragment);
            fragmentTransaction.commit();
            // change background color
            viewReview.setBackgroundResource(R.drawable.solid_btn_reverse);
            viewReview.setTextColor(Color.WHITE);
            addReview.setBackgroundResource(R.drawable.solid_btn);
            addReview.setTextColor(Color.BLACK);
            return;
        }
        if (v.getId() == R.id.next_btn) {
            ArrayList<Reviews> allReviews;
            int pageNo = Integer.parseInt(one.getText().toString()) + 1;
            int totalReview = db.reviewCount();
            Log.i("babel", "onClick: " + totalReview);

            prevBtn.setEnabled(true);
            one.setText(pageNo + "");
            allReviews = db.getAllReviews(pageNo);
            AllReviews ar = (AllReviews) getFragmentManager().findFragmentById(R.id.review_fragment);
            ar.onUpdateContent(allReviews);
            if ((pageNo) * 10 > totalReview) {
                nextBtn.setEnabled(false);
            }
        }
        if (v.getId() == R.id.prev_btn) {
            ArrayList<Reviews> allReviews;
            int pageNo = Integer.parseInt(one.getText().toString()) - 1;
            nextBtn.setEnabled(true);
            if (pageNo == 1) {
                prevBtn.setEnabled(false);
            }
            one.setText(pageNo + "");
            allReviews = db.getAllReviews(pageNo);
            AllReviews ar = (AllReviews) getFragmentManager().findFragmentById(R.id.review_fragment);
            ar.onUpdateContent(allReviews);
            return;
        }
    }
}