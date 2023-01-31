package com.example.rps;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rps.ml.Model;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

public class GamePage extends AppCompatActivity {
    private static final String[] PERMISSIONS = {Manifest.permission.CAMERA};
    int REQUEST_CODE = 101;
    Camera myCam = null;
    CameraPreview myPreview;
    FrameLayout frameLayout;
    private boolean isCameraInitialized;
    TextView timeValue, compWinCount, playerWinCount;
    ImageButton switchBtn;
    Button gameStartBtn;
    SeekBar timeSeekbar;
    ImageView compHand;
    long value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
        compWinCount = findViewById(R.id.computer_score);
        playerWinCount = findViewById(R.id.player_score);
        timeValue = findViewById(R.id.time_value);
        switchBtn = findViewById(R.id.switch_btn);
        gameStartBtn = findViewById(R.id.game_start);
        timeSeekbar = findViewById(R.id.time_seekbar);
        compHand = findViewById(R.id.comp_hand);
        value = timeSeekbar.getProgress();
        timeValue.setText(value + " ms");

        gameStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myCam!=null){
                    myCam.takePicture(null, null, myCallBack);
                    cameraStart();
                }
//                new java.util.Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        if(myCam!=null){
//                            myCam.takePicture(null, null, myCallBack);
//                            cameraStart();
//                        }
//                    }
//                }, value);
            }
        });
        timeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value = progress;
                timeValue.setText(progress + " ms");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

//                Toast.makeText(GamePage.this, value + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    Camera.PictureCallback myCallBack = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            int[] icons = {R.drawable.icon_rock, R.drawable.icon_paper, R.drawable.icon_scissor};
            Random rand = new Random();
            int index = rand.nextInt(icons.length);
//            Toast.makeText(GamePage.this, "rand " + index, Toast.LENGTH_SHORT).show();
            int icon_index = icons[index];
//            model work

            try {
                Model model = Model.newInstance(getApplicationContext());

                // Creates inputs for reference.
                TensorImage image = TensorImage.fromBitmap(bitmap);

                // Runs model inference and gets result.
                Model.Outputs outputs = model.process(image);
                List<Category> probability = outputs.getProbabilityAsCategoryList();

                int label = getMax(probability);
                compHand.setImageResource(icon_index);
                int result = getWinner(label, index);
                int currentComp = Integer.parseInt((String) compWinCount.getText());
                int currentPlayer = Integer.parseInt((String) playerWinCount.getText());
                int nextComp  = result<0? currentComp + 1: currentComp;
                Log.i("label", "onPictureTaken: " + nextComp);
                int nextPlayer = result>0? currentPlayer + 1: currentPlayer;
                Log.i("label", "onPictureTaken: " + nextPlayer);
                compWinCount.setText(String.valueOf(nextComp));
                playerWinCount.setText(String.valueOf(nextPlayer));
                model.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    private int getWinner(int label, int index) {
        if((label == 2 && index == 1) || (label == 1 && index == 2) || (label == 3 && index == 0)){
            return -1;
        }
        if((label == 2 && index == 2) || (label == 1 && index == 0) || (label == 3 && index == 1)){
            return 1;
        }
        else {
            return 0;
        }

    }

    private int getMax(List<Category> probability) {
        float max = 0;
        int index = 0;

        for(int i=1; i<4; i++){
            Log.i("label", "getMax: " + probability.get(i).getLabel());
//            Toast.makeText(this, item.getLabel()+item.getLabel().length(), Toast.LENGTH_SHORT).show();
            if(probability.get(i).getScore()>max){
                max = probability.get(i).getScore();
                index = i;
            }
        }
        return index;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionDenied()) {
            requestPermissions(PERMISSIONS, REQUEST_CODE);
            return;
        }
        cameraStart();
    }
    //    onResume ends
    public void cameraStart(){
        myCam = Camera.open();
        Camera.Parameters parameters = myCam.getParameters();
        List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
        Camera.Size mSize = null;
//        fix the camera resolution
        for (Camera.Size size : sizes) {
            mSize = size;
        }
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            parameters.set("orientation", "portrait");
            myCam.setDisplayOrientation(90);
            parameters.setRotation(90);
        } else {
            parameters.set("orientation", "landscape");
            myCam.setDisplayOrientation(0);
            parameters.setRotation(0);
        }
        parameters.setPictureSize(mSize.width, mSize.height);
        myPreview = new CameraPreview(this, myCam);
        frameLayout = findViewById(R.id.cameraPreview);
        frameLayout.addView(myPreview);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && requestCode == REQUEST_CODE) {
            if (arePermissionDenied()) {
                ((ActivityManager) (this.getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
                recreate();
            } else {
                onResume();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean arePermissionDenied() {
        for (int i = 0; i < PERMISSIONS.length; i++) {
            if (checkSelfPermission(PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

}