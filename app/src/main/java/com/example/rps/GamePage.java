package com.example.rps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rps.ml.Model;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;

import java.io.IOException;
import java.util.List;

public class GamePage extends AppCompatActivity {
    Button capture, predict;
    ImageView predictImage;
    TextView result;
    Bitmap img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
        capture = findViewById(R.id.capture);
        predictImage = findViewById(R.id.CapturedImage);
        predict = findViewById(R.id.predict);
        result = findViewById(R.id.result);

        predict.setVisibility(View.GONE);

        getPermission();
        // Check whether app has camera permission
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, 101);
            }
        });
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Model model = Model.newInstance(getApplicationContext());

                    // Creates inputs for reference.
                    TensorImage image = TensorImage.fromBitmap(img);

                    // Runs model inference and gets result.
                    Model.Outputs outputs = model.process(image);
                    List<Category> probability = outputs.getProbabilityAsCategoryList();

                    int index = getMax(probability);
                    String winner = probability.get(index).getLabel();
//                    Log.i("winner", probability.get(index).getLabel()+"");
                    float guess = probability.get(index).getScore();
                    result.setText("Result is "+winner+" with probability "+guess);
                    // Releases model resources if no longer used.
                    model.close();
                } catch (IOException e) {
                    // TODO Handle the exception
                }
            }
        });
    }
    public  int getMax(List<Category> prob){
        float max = 0;
        int index = 0;
        for(int i=0; i<prob.size(); i++){
            if(max <= prob.get(i).getScore()){
                max = prob.get(i).getScore();
                index = i;
            }
        }
        return index;
    }
    public void getPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 10);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 10) {
            if (grantResults.length>0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                this.getPermission();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 101 && resultCode == Activity.RESULT_OK)
        {
            img = (Bitmap) data.getExtras().get("data");
            predict.setVisibility(View.VISIBLE);
            predictImage.setImageBitmap(img);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}