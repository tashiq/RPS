package com.example.rps;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import java.time.LocalDate;

public class AddReview extends AppCompatActivity implements View.OnClickListener {
    EditText addReviewTitle, addReviewDescription;
    Button submit;
    RatingBar addReviewRating;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        sharedPreferences = getSharedPreferences("User Details", MODE_PRIVATE);
        addReviewTitle = findViewById(R.id.add_review_title);
        addReviewDescription = findViewById(R.id.add_review_description);
        addReviewRating = findViewById(R.id.add_review_rating);
        submit = findViewById(R.id.review_add_btn);
        submit.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.review_add_btn){
            Intent addReviewIntent = new Intent();
            addReviewIntent.putExtra("review_title", addReviewTitle.getText().toString());
            addReviewIntent.putExtra("review_description", addReviewDescription.getText().toString());
            addReviewIntent.putExtra("review_rating", addReviewRating.getRating());
            LocalDate date = LocalDate.now();
            addReviewIntent.putExtra("review_date", date.toString());
            String reviewer = sharedPreferences.getString("user_name", "Not Given");
            addReviewIntent.putExtra("reviewer", reviewer);
            setResult(1, addReviewIntent);
            finish();
        }
    }
}