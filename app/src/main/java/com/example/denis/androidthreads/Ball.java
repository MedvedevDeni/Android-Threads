package com.example.denis.androidthreads;

import android.media.SoundPool;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Ball extends Thread {

    private ConstraintLayout gameField;
    private ImageView movableElement;
    private TextView countOfHit;
    private SoundPool hitSound;
    private int soundID;
    private float accelerationX = 0;
    private float accelerationY = 0;
    private int scoreCounter = 0;
    private boolean endGame = false;
    private float xPos;
    private float yPos;

    Ball (ConstraintLayout gameField, ImageView movableElement, TextView countOfHit, SoundPool hitSound, int soundID) {
        this.movableElement = movableElement;
        this.gameField = gameField;
        this.countOfHit = countOfHit;
        this.hitSound = hitSound;
        this.soundID = soundID;
    }

    @Override
    public void run() {
        while (!endGame) {
            xPos = movableElement.getX();
            yPos = movableElement.getY();
            xPos += accelerationX;
            yPos += accelerationY;

            //Сопротивление воздуха
            accelerationY *= 0.98;
            accelerationX *= 0.98;

            if (xPos >= gameField.getWidth() - movableElement.getWidth() || xPos <= 0) {
                accelerationX *= -0.7;
                scoreCounter++;
                playSound(hitSound, soundID);
                if (xPos <= 0) {
                    xPos = 0;
                } else {
                    xPos = gameField.getWidth() - movableElement.getWidth();
                }
            }
            if (yPos >= gameField.getHeight() - movableElement.getHeight() || yPos <= 0) {
                accelerationY *= -0.7;
                scoreCounter++;
                playSound(hitSound, soundID);
                if (yPos <= 0) {
                    yPos = 0;
                } else {
                    yPos = gameField.getHeight() - movableElement.getHeight();
                }
            }
            setCoordinatesOnView(movableElement, xPos, yPos);
            setTextOnView(countOfHit, "" + scoreCounter);
            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    float getxPos() {
        return xPos;
    }

    float getyPos() {
        return yPos;
    }

    float getAccelerationX() {
        return accelerationX;
    }

    float getAccelerationY() {
        return accelerationY;
    }

    int getScoreCounter() {
        return scoreCounter;
    }

    void setxPos(float xPos) {
        movableElement.setX(xPos);
    }

    void setyPos(float yPos) {
        movableElement.setY(yPos);
    }

    void setScoreCounter(int scoreCounter) {
        this.scoreCounter = scoreCounter;
    }

    void setAccelerationX(float accelerationX) {
        this.accelerationX += accelerationX;
    }

    void setAccelerationY(float accelerationY) {
        this.accelerationY += accelerationY;
    }

    private void setCoordinatesOnView(final View view, final float x, final float y){
        view.post(new Runnable() {
            @Override
            public void run() {
                view.setX(x);
                view.setY(y);
            }
        });
    }

    private void setTextOnView(final TextView view, final String value){
        view.post(new Runnable() {
            @Override
            public void run() {
                view.setText(value);
            }
        });
    }

    private void playSound(SoundPool sound, int soundID){
        if (soundID > 0) {
            sound.play(soundID, 1, 1, 1, 0, 1);
        }
    }

    void endGame() {
        this.endGame = true;
    }

}