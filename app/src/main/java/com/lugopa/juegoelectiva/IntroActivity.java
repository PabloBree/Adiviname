package com.lugopa.juegoelectiva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SyncStatusObserver;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import java.io.IOException;

public class IntroActivity extends AppCompatActivity {

    String TAG = "IntroActivity";
    Boolean flagIntro = true; // es para indicar si el video esta o no reproduciendose
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // CODIGO PARA REPRODUCIR VIDEO EN LA APP
        String videoPath = "android.resource://com.lugopa.juegoelectiva/"+ R.raw.video_intro;
        Uri uri = Uri.parse(videoPath);
        videoView = findViewById(R.id.videoView_intro);
        videoView.setVideoURI(uri);
        flagIntro = true;
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                // Video Playing is completed
                abrir_main();
            }
        });
    }

    private void abrir_main(){
        // abre activity main
        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra( lo que queramos pasar) // para pasar cosas a la activity
        startActivity(intent);
        finish();
    }

}
