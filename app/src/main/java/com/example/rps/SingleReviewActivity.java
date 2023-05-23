package com.example.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

public class SingleReviewActivity extends AppCompatActivity {

    Intent intent;
    String user, date, rating, like, dislike, title, description;
    TextView userTv, dateTv, titleTv, descriptionTv, likeTv;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_review);
        /*intent = getIntent();
        user = intent.getStringExtra("name");
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        like = intent.getStringExtra("like");
        dislike = intent.getStringExtra("dislike");
        date = intent.getStringExtra("date");
        rating = intent.getStringExtra("rating");*/

        // getting tags
       /* userTv = findViewById(R.id.single_review_reviewer);
        dateTv = findViewById(R.id.single_review_date);
        titleTv = findViewById(R.id.single_review_title);
        descriptionTv = findViewById(R.id.single_review_description);
        likeTv = findViewById(R.id.single_review_like);
        ratingBar = findViewById(R.id.single_review_rating);
        int ratingInt = Integer.parseInt(rating);

        // set values
        userTv.setText(user);
        dateTv.setText(date);
        titleTv.setText(title);
        likeTv.setText(like);
        descriptionTv.setText(description);
        ratingBar.setRating(ratingInt);
*/
    }
}