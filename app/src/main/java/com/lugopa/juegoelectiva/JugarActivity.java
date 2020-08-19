package com.lugopa.juegoelectiva;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class JugarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);

        // CODIGO PARA REPRODUCIR VIDEO EN LA APP - No funciona
        VideoView videoView = findViewById(R.id.videoView_intro);
        String videoPath = "android.resource://com.lugopa.juegoelectiva/"+ R.raw.video_intro;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.start();
//        MediaController mediaController = new MediaController(this);
//        videoView.setMediaController(mediaController);
//        mediaController.setAnchorView(videoView);
    }


}