package com.jina.wishbook.Camera;

import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jina.wishbook.R;

import java.io.IOException;

public class CameraActivity extends AppCompatActivity  {
    CameraSurfaceView surfaceView;


    Button sample_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        sample_scan = findViewById(R.id.sample_scan);
        surfaceView= new CameraSurfaceView(this, (SurfaceView) findViewById(R.id.camera_view));

        sample_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}