package com.example.wildcats;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.camerakit.CameraKitView;

import java.io.File;
import java.io.FileOutputStream;

public class CameraAcitivity extends AppCompatActivity {

    private CameraKitView cameraKitView;
    private Button photoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_acitivity);

        cameraKitView = findViewById(R.id.camera);
        photoButton = findViewById(R.id.photoButton);

        photoButton.setOnClickListener(photoOnClickListener);

    }
    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }
    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }
    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private View.OnClickListener photoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                @Override
                public void onImage(CameraKitView cameraKitView, byte[] capturedImage) {
                    File savedPhoto = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
                    try {
                        FileOutputStream outputStream = new FileOutputStream(savedPhoto.getPath());
                        outputStream.write(capturedImage);
                        outputStream.close();
                        MainActivity.storedfile = savedPhoto.getPath();
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

}
