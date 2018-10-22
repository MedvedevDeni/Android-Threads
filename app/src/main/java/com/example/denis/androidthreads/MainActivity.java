package com.example.denis.androidthreads;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout gameField;
    ImageView movableElement;
    Button upButton;
    Button downButton;
    Button leftButton;
    Button rightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameField = findViewById(R.id.gameField);
        movableElement = findViewById(R.id.movableElement);
        upButton = findViewById(R.id.upElementPosition);
        downButton = findViewById(R.id.downElementPosition);
        leftButton = findViewById(R.id.leftElementPosition);
        rightButton = findViewById(R.id.rightElementPosition);
    }

    public void move(View view) {
        float xPos = movableElement.getX();
        float yPos = movableElement.getY();
        switch (view.getId()) {
            case R.id.upElementPosition:
                if (yPos >= 10)
                    movableElement.setY(yPos - 10);
                break;
            case R.id.downElementPosition:
                if (yPos < (gameField.getHeight() - movableElement.getHeight()))
                    movableElement.setY(yPos + 10);
                break;
            case R.id.leftElementPosition:
                if (xPos >= 10)
                    movableElement.setX(xPos - 10);
                break;
            case R.id.rightElementPosition:
                if (xPos <= (gameField.getWidth() - movableElement.getWidth()))
                    movableElement.setX(xPos + 10);
                break;
        }
    }

}
