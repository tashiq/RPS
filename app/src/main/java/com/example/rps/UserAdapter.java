package com.example.rps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    List<User> user;
    Context context;
    public UserAdapter(Context context, List<User> user) {
        this.user = user;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.username.setText(user.get(position).getUsername());
        holder.name.setText(user.get(position).getName());
        holder.email.setText("Email: " + user.get(position).getEmail());
        holder.phone.setText("Phone: " + user.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
    TextView username, name, email, phone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
        }
    }
}
