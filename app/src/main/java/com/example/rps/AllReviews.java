package com.example.rps;

import android.content.Context;
import android.os.Bundle;

import android.app.Fragment;;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AllReviews extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Reviews> reviews;
    public Database database;
    public Context context;
    private RecyclerAdapter recyclerAdapter;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_all_reviews, container, false);;
        recyclerView = rootView.findViewById(R.id.reviewRecycler);
        database = new Database(context);
        reviews = database.getAllReviews(1);
        recyclerAdapter = new RecyclerAdapter(context, reviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos) {
            }
        });
        return rootView;
    }
   public void onUpdateContent(ArrayList<Reviews> allReviews){
        reviews.clear();
        reviews.addAll(allReviews);
        recyclerAdapter.notifyDataSetChanged();
   }


}