package com.example.rps;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.Date;


public class AddReviewFragment extends Fragment implements View.OnClickListener {

    EditText addReviewTitle, addReviewDescription;
    Button submit;
    RatingBar addReviewRating;
    Context context;
    Database database;
    SharedPreferences sharedPreferences;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        database = new Database(context);
        sharedPreferences = context.getSharedPreferences("User Details", MODE_PRIVATE);
        if (!sharedPreferences.contains("user_name")) {
            startActivity(new Intent(context, Login.class));
            return null;
        }

        View view = inflater.inflate(R.layout.fragment_add_review, container, false);
        addReviewTitle = view.findViewById(R.id.add_review_title);
        addReviewDescription = view.findViewById(R.id.add_review_description);
        submit = view.findViewById(R.id.review_add_btn);
        addReviewRating = view.findViewById(R.id.add_review_rating);
        submit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.review_add_btn) {
            String title, description;

            title = addReviewTitle.getText().toString();
            if (TextUtils.isEmpty(title)) {
                Toast.makeText(context, "Title Field Empty", Toast.LENGTH_SHORT).show();
                return;
            }


            description = addReviewDescription.getText().toString();
            if (TextUtils.isEmpty(description)) {
                Toast.makeText(context, "Description Field Empty", Toast.LENGTH_SHORT).show();
                return;
            }

            submit.setEnabled(false);
            submit.setBackgroundColor(Color.GRAY);

            Date date = new Date();
            String strDate = date.toString();
            String userName = sharedPreferences.getString("user_name", null);

            int rating = (int) addReviewRating.getRating();

            database.addNewReview(userName, strDate, title, description, rating);
            database.close();

            addReviewRating.setRating(0.0f);
            addReviewTitle.setText("");
            addReviewDescription.setText("");

        }
    }
}