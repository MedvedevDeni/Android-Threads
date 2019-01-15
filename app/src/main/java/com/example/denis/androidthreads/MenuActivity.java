package com.example.denis.androidthreads;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    private MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        backgroundMusic = MediaPlayer.create(this, R.raw.credits);
        backgroundMusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        backgroundMusic.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        backgroundMusic.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        backgroundMusic.stop();
        backgroundMusic.release();
    }

    /*
     * Метод запускает игру
     */

    public void startButton(View view) {
        Intent intent = new Intent(MenuActivity.this, GameActivity.class);
        startActivity(intent);
        System.exit(0);
    }

    /*
     * Метод завершает игру
     */

    public void exitButton(View view) {
        System.exit(0);
    }
}
