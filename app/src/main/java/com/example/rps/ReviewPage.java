package com.example.rps;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReviewPage extends AppCompatActivity implements View.OnClickListener {
    private LayoutInflater inflater;
    TextView name, title, description, review_date;
    RatingBar rating;
    LinearLayout reviewContainer;
    ImageButton addReviewBtn;
    ArrayList<Reviews> reviews = new ArrayList<>();
    Database database;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);
        sharedPreferences = getSharedPreferences("User Details", MODE_PRIVATE);
//        getting all reviews
        database = new Database(this);
        getAllCourses();
//        adding new review
        addReviewBtn = findViewById(R.id.addReviewBtn);
        addReviewBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addReviewBtn) {
            Intent reviewIntent;
            if(!sharedPreferences.contains("user_name")){
                reviewIntent = new Intent(getApplicationContext(), Login.class);
                startActivity(reviewIntent);
                return;
            }
            reviewIntent = new Intent(getApplicationContext(), AddReview.class);
            startActivityForResult(reviewIntent, 1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.i("label", "onActivityResult: " + resultCode);
            String title = data.getStringExtra("review_title");
            String date = data.getStringExtra("review_date");
            String description = data.getStringExtra("review_description");
            String reviewer = data.getStringExtra("reviewer");
            float rating = data.getFloatExtra("review_rating", 0f);
            database.addNewReview(reviewer, date, title, description, Math.round(rating));
            Toast.makeText(this, "Review Added", Toast.LENGTH_SHORT).show();
            getAllCourses();
        }
    }

    protected void getAllCourses() {
//        Calling database function.
        reviews = database.getAllCourses();
//        getting container
        reviewContainer = findViewById(R.id.reviewContainer);
        reviewContainer.removeAllViews();

//        adding to the layout reviews.
        Log.i("label", "getAllCourses: " + reviews.size());
        try {
            for (int i = 0; i < reviews.size(); i++) {
                ImageButton likebtn, dislikebtn;
                TextView like;
                boolean liked = false, disliked = false;
                inflater = getLayoutInflater();
                View singleReview = inflater.inflate(R.layout.review, reviewContainer, false);
//                getting views to show
                name = singleReview.findViewById(R.id.reviewer);
                title = singleReview.findViewById(R.id.review_title);
                description = singleReview.findViewById(R.id.review_description);
                review_date = singleReview.findViewById(R.id.review_date);
                like = singleReview.findViewById(R.id.likecount);
                rating = singleReview.findViewById(R.id.reviewRating);
                likebtn = singleReview.findViewById(R.id.likebtn);
                dislikebtn = singleReview.findViewById(R.id.dislikebtn);

//                getting all values from database
                String Reviewer = reviews.get(i).getReviewer();
                String ReviewDate = reviews.get(i).getReview_date();
                String ReviewTitle = reviews.get(i).getReview_title();
                Log.i("label", "getAllCourses: "+ i+" " + ReviewTitle);
                String ReviewDescription = reviews.get(i).getReview_description();
                int ReviewLikes = reviews.get(i).getReview_like();
                int ReviewRating = reviews.get(i).getReview_rating();
                likebtn.setOnClickListener(this);
                dislikebtn.setOnClickListener(this);
                name.setText(Reviewer);
                review_date.setText(ReviewDate);
                title.setText(ReviewTitle);
                description.setText(ReviewDescription);
//                getting error when i want to insert integer value in textview.
                like.setText(String.valueOf(ReviewLikes));
                rating.setRating(ReviewRating);
                reviewContainer.addView(singleReview);
                likebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!liked){
                            int value = Integer.parseInt(like.getText().toString()) + 1;
                            like.setText(value  + "");
                        }
                        if(disliked){
                            int value = Integer.parseInt(like.getText().toString()) - 1;
                            like.setText(value  + "");
                        }
                    }
                });
                dislikebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int value = Integer.parseInt(like.getText().toString()) - 1;
                        like.setText(value  + "");
                    }
                });
            }
        } catch (Exception e) {
            Log.i("error", "Reading values: " + e);
        }
    }
}