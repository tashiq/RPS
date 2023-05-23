package com.example.rps;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rps.Comments;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private ArrayList<Comments> allComments;
    private Context context;

    public CommentAdapter(Context context, ArrayList<Comments> itemList) {
        this.context = context;
        this.allComments = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.commentTv.setText(allComments.get(position).getComment());
        holder.commentorTv.setText(allComments.get(position).getCommentor());
    }

    @Override
    public int getItemCount() {
        return allComments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView commentTv, commentorTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentTv = itemView.findViewById(R.id.comment);
            commentorTv = itemView.findViewById(R.id.commentor);
        }
    }
}