package com.example.rps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SingleReviewPage extends AppCompatActivity {
    Intent intent;
    String user, date, rating, like, dislike, title, description, username;
    TextView userTv, dateTv, titleTv, descriptionTv, likeTv;
    int post_id;
    EditText commentEt;
    Button submit;
    RatingBar ratingBar;
    Database database;
    RecyclerView commentsRecycler;
    ArrayList<Comments> allComments;
    CommentAdapter commentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_review_page);

        intent = getIntent();
        title = intent.getStringExtra("title");
        user = intent.getStringExtra("name");
        description = intent.getStringExtra("description");
        like = intent.getStringExtra("like");
        dislike = intent.getStringExtra("dislike");
        date = intent.getStringExtra("date");
        rating = intent.getStringExtra("rating");
        username = intent.getStringExtra("user");
        post_id = intent.getIntExtra("id", 0);

// check
//        Log.i("babel", "onCreate: "+ rating);
// getting tags
        userTv = findViewById(R.id.single_review_reviewer);
        dateTv = findViewById(R.id.single_review_date);
        titleTv = findViewById(R.id.single_review_title);
        descriptionTv = findViewById(R.id.single_review_description);
        likeTv = findViewById(R.id.single_review_like);
        commentEt = findViewById(R.id.single_review_add_comment);
        submit = findViewById(R.id.single_review_add_comment_btn);
        ratingBar = findViewById(R.id.single_review_rating);
        commentsRecycler = findViewById(R.id.commnet_recycler);

        //get values from db and shared pref.
        database = new Database(getApplicationContext());

        int ratingInt = Integer.parseInt(rating);

        // setting values
        userTv.setText(user);
        dateTv.setText(date);
        titleTv.setText(title);
        likeTv.setText(like);
        descriptionTv.setText(description);
        ratingBar.setRating(ratingInt);
        setAllComment();
        // on click
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentEt.getText().toString();
                commentEt.setText("");
                boolean res = database.addComment(username, post_id, comment);
                if(!res){
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    return;
                }
                setAllComment();
            }
        });

    }
    public void setAllComment(){
        allComments = database.getComments(post_id);
        commentAdapter = new CommentAdapter(getApplicationContext(), allComments);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        commentsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Log.i("babel", "onCreate: " + allComments.size());
        commentsRecycler.setAdapter(commentAdapter);
    }
}