package com.example.denis.androidthreads;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;



public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private ConstraintLayout gameField;
    private ImageView imageBall;
    private Ball ball;
    private TextView backTimer;
    private GameTimer timer;
    private MediaPlayer backgroundMusic;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView countOfHit;
    private int soundID;
    private SoundPool hitSound;
    private int currentPosition;

    private float xPos;
    private float yPos;
    private float accelerationX;
    private float accelerationY;
    private int currentScore;

    private int currentGameTime = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameField = findViewById(R.id.gameField);
        imageBall = findViewById(R.id.ball);
        backTimer = findViewById(R.id.backTimer);
        countOfHit = findViewById(R.id.countOfHit);
        backgroundMusic = MediaPlayer.create(this, R.raw.credits);

        //Инициализация и назначение слушателя для датчика

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        hitSound = getSoundPoolLink();
        try {
            soundID = hitSound.load(getAssets().openFd("sounds/hit.ogg"), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ball = new Ball(gameField, imageBall, countOfHit, hitSound, soundID);
        timer = new GameTimer(backTimer, ball, currentGameTime);
        backgroundMusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
        ball.start();
        timer.start();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentPosition", backgroundMusic.getCurrentPosition());
        outState.putFloat("xPos", ball.getxPos());
        outState.putFloat("yPos", ball.getyPos());
        outState.putFloat("accelerationX", ball.getAccelerationX());
        outState.putFloat("accelerationY", ball.getAccelerationY());
        outState.putInt("currentScore", ball.getScoreCounter());
        outState.putInt("currentGameTime", timer.getCurrentTime());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null){
            currentPosition = savedInstanceState.getInt("currentPosition");
            xPos = savedInstanceState.getFloat("xPos");
            yPos = savedInstanceState.getFloat("yPos");
            accelerationX = savedInstanceState.getFloat("accelerationX");
            accelerationY = savedInstanceState.getFloat("accelerationY");
            currentScore = savedInstanceState.getInt("currentScore");
            currentGameTime = savedInstanceState.getInt("currentGameTime");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            int coordinateX = (int) event.values[0];
            int coordinateY = (int) event.values[1];
            accelerationChange(-coordinateX, coordinateY);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        backgroundMusic.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        resetUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        backgroundMusic.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        backgroundMusic.stop();
        backgroundMusic.release();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void resetUI() {
        backgroundMusic.seekTo(currentPosition);
        ball.setScoreCounter(currentScore);
        ball.setxPos(xPos);
        ball.setyPos(yPos);
        ball.setAccelerationX(accelerationX);
        ball.setAccelerationY(accelerationY);
        timer.setCurrentTime(currentGameTime);
    }


    public void accelerationChange(float coordinateX, float coordinateY) {
        ball.setAccelerationX(coordinateX);
        ball.setAccelerationY(coordinateY);
    }

    private SoundPool getSoundPoolLink() {
        return Build.VERSION.SDK_INT <= 20 ? new SoundPool(5, AudioManager.STREAM_MUSIC, 0)
                : new SoundPool.Builder().setAudioAttributes(
                  new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
                 .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()).build();
    }
}
