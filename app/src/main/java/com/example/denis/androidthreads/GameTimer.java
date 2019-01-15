package com.example.denis.androidthreads;

import android.widget.TextView;

public class GameTimer extends Thread {

    private Ball game;
    private TextView backTimer;
    private int currentTime;

    GameTimer(TextView backTimer, Ball game, int currentTime) {
        this.game = game;
        this.backTimer = backTimer;
        this.currentTime = currentTime;
    }

    @Override
    public void run() {
        for (; currentTime > 0; currentTime--) {
            setTextOnView(backTimer, "" + currentTime);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setTextOnView(backTimer, "End game!");
        game.endGame();
        System.exit(0);

    }

    int getCurrentTime() {
        return currentTime;
    }

    void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    private void setTextOnView(final TextView view, final String value){
        view.post(new Runnable() {
            @Override
            public void run() {
                view.setText(value);
            }
        });
    }

}
