package com.example.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class Tutorial extends AppCompatActivity {
    WebView webView;
    TextView title, desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        webView = findViewById(R.id.tutorial_video);
        title = findViewById(R.id.tutorial_title);
        desc = findViewById(R.id.tutorial_desc);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/nerko.ttf");
        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf");
        title.setTypeface(typeface);
        desc.setTypeface(typeface2);
        webView.getSettings().setJavaScriptEnabled(true);
        String tutorial = "<html><body><iframe src=\"https://www.youtube.com/embed/ND4fd6yScBM\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe></body></html>";
        webView.loadData(tutorial, "text/html", "utf-8");
    }
}