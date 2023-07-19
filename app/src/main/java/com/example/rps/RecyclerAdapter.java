package com.example.rps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    ArrayList<Reviews> allReview;
    Context context;
    private OnItemClickListener onItemClickListener;
    SharedPreferences sharedPreferences;
    String username = "";
    public RecyclerAdapter(Context context, ArrayList<Reviews> allReview) {
        this.allReview = allReview;
        this.context = context;

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review, parent, false);
        sharedPreferences = context.getSharedPreferences("User Details", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("user_name", "");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Database db = new Database(context);
        int postId = allReview.get(holder.getAdapterPosition()).getReview_id();
//        Log.i("babel", "onBindViewHolder: " +username);
        Cursor cursor = db.getReact(postId, username);
        int react = cursor.getInt(1);
//        Log.i("babel", "onBindViewHolder: " + cursor.getColumnName(1));
        int reactCount = cursor.getInt(0);

        holder.reviewer.setText(allReview.get(position).getReviewer());
        holder.review_date.setText(allReview.get(position).getReview_date());
        holder.review_title.setText(allReview.get(position).getReview_title());
        holder.rating.setRating(allReview.get(position).getReview_rating());
        holder.review_description.setText(allReview.get(position).getReview_description());
        holder.like_count.setText(reactCount + "");
        int likes = reactCount;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    Intent singleReview = new Intent(context, SingleReviewPage.class);
                    singleReview.putExtra("id", allReview.get(holder.getAdapterPosition()).getReview_id());
                    singleReview.putExtra("name", allReview.get(holder.getAdapterPosition()).getReviewer());
                    singleReview.putExtra("title", allReview.get(holder.getAdapterPosition()).getReview_title());
                    singleReview.putExtra("description", allReview.get(holder.getAdapterPosition()).getReview_description());
                    singleReview.putExtra("like", holder.like_count.getText().toString());
                    singleReview.putExtra("user", username);
                    singleReview.putExtra("date", allReview.get(holder.getAdapterPosition()).getReview_date());
                    singleReview.putExtra("rating", allReview.get(holder.getAdapterPosition()).getReview_rating() + "");
                    context.startActivity(singleReview);
                }
            }
        });


        if (react == 0) {
            holder.like_btn.setButtonDrawable(R.drawable.icon_like);
            holder.dislike_btn.setButtonDrawable(R.drawable.icon_dislike);
            holder.like_btn.setChecked(false);
            holder.dislike_btn.setChecked(false);
        } else if (react == -1) {
            holder.like_btn.setButtonDrawable(R.drawable.icon_like);
            holder.dislike_btn.setButtonDrawable(R.drawable.icon_dislike_active);
            holder.dislike_btn.setChecked(true);
            holder.like_btn.setChecked(false);
        } else if (react == 1) {
            holder.like_btn.setButtonDrawable(R.drawable.icon_like_active);
            holder.like_btn.setChecked(true);
            holder.dislike_btn.setChecked(false);
            holder.dislike_btn.setButtonDrawable(R.drawable.icon_dislike);
        }
        holder.like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.like_btn.isChecked()) {
                    int current = Integer.valueOf(holder.like_count.getText().toString());
                    if (holder.dislike_btn.isChecked()) {
                        current += 1;
                    }
                    current += 1;
                    holder.like_count.setText(String.valueOf(current));
                    holder.dislike_btn.setButtonDrawable(R.drawable.dislike);
                    holder.like_btn.setButtonDrawable(R.drawable.like_blue);
                    holder.dislike_btn.setChecked(false);
                    db.addReact(Integer.valueOf(allReview.get(position).getReview_id()),username, 1);
                } else {
                    int current = Integer.valueOf(holder.like_count.getText().toString()) - 1;
                    holder.like_count.setText(current + "");
                    holder.like_btn.setButtonDrawable(R.drawable.like);
                    db.addReact(Integer.valueOf(allReview.get(position).getReview_id()), username, 0);
                }
            }
        });
        holder.dislike_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.dislike_btn.isChecked()) {
                    int current = Integer.valueOf(holder.like_count.getText().toString());
                    if (holder.like_btn.isChecked())
                        current -= 2;
                    else {
                        current -= 1;
                    }
                    holder.like_count.setText(current + "");
                    holder.dislike_btn.setButtonDrawable(R.drawable.dislike_blue);
                    holder.like_btn.setChecked(false);
                    holder.like_btn.setButtonDrawable(R.drawable.like);
                    db.addReact(Integer.valueOf(allReview.get(position).getReview_id()), username, -1);
                } else {

                    int current = Integer.valueOf(holder.like_count.getText().toString()) + 1;
                    holder.like_count.setText(current + "");
                    holder.dislike_btn.setButtonDrawable(R.drawable.dislike);
                    db.addReact(Integer.valueOf(allReview.get(position).getReview_id()),username, 0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allReview.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView reviewer, review_date, review_title, review_description, like_count;
        RatingBar rating;
        ConstraintLayout review_container;
        ImageButton review_message_btn;
        ToggleButton like_btn, dislike_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reviewer = itemView.findViewById(R.id.reviewer);
            review_date = itemView.findViewById(R.id.review_date);
            review_description = itemView.findViewById(R.id.review_description);
            review_title = itemView.findViewById(R.id.review_title);
            like_count = itemView.findViewById(R.id.likecount);
            rating = itemView.findViewById(R.id.reviewRating);
            like_btn = itemView.findViewById(R.id.likebtn);
            like_btn.setTextOn("");
            like_btn.setTextOff("");
            dislike_btn = itemView.findViewById(R.id.dislikebtn);
            dislike_btn.setTextOn("");
            dislike_btn.setTextOff("");
            review_message_btn = itemView.findViewById(R.id.review_message_btn);
            review_container = itemView.findViewById(R.id.single_review_container);
            Animation translate_anim;
            translate_anim = AnimationUtils.loadAnimation(review_container.getContext(), R.anim.review_animation);
            review_container.setAnimation(translate_anim);

        }
    }
}
