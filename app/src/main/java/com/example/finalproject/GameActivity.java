package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    TextView game_TXT_score;
    TextView game_TXT_time;
    TextView game_TXT_exercise;
    TextView game_TXT_lives;
    Button game_BTN_one;
    Button game_BTN_two;
    Button game_BTN_three;
    Button game_BTN_four;

    private GameManager gameManager;
    Timer timer;
    private int countTimer;
    int score;
    Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        findViews();
        gameManager= new GameManager();
        initViews();
    }

    private void findViews() {
        game_TXT_score= findViewById(R.id.game_TXT_score);
        game_TXT_time= findViewById(R.id.game_TXT_time);
        game_TXT_exercise= findViewById(R.id.game_TXT_exercise);
        game_BTN_one= findViewById(R.id.game_BTN_one);
        game_BTN_two= findViewById(R.id.game_BTN_two);
        game_BTN_three= findViewById(R.id.game_BTN_three);
        game_BTN_four= findViewById(R.id.game_BTN_four);
        game_TXT_lives= findViewById(R.id.game_TXT_lives);


    }

    private void initViews() {
        score=0;
        game_BTN_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkAnswer(0, game_BTN_one)){
                    game_BTN_one.setEnabled(false);
                }
            }
        });

        game_BTN_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkAnswer(1, game_BTN_two)){
                    game_BTN_two.setEnabled(false);
                }
            }
        });

        game_BTN_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkAnswer(2, game_BTN_three)){
                    game_BTN_three.setEnabled(false);
                }
            }
        });


        game_BTN_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkAnswer(3, game_BTN_four)){

                    game_BTN_four.setEnabled(false);
                }
            }
        });

        startTimer();
        startGame();
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int minutes = countTimer / 60;
                        int seconds = countTimer % 60;
                        game_TXT_time.setText(String.format("%02d:%02d", minutes, seconds));
                        countTimer++;
                    }
                });
            }
        }, 1000, 1000);
    }


    private void startGame() {
        updateUI();
    }


    private boolean checkAnswer(int clickedButton, Button b) {
        if(clickedButton == gameManager.getRandLoc()) {
            score++;
            updateUI();
            return true;
        }
        else {
            b.setBackgroundColor(Color.parseColor("#B00020"));
            wrongAnswer(clickedButton);
        }
        return false;
    }

    private void wrongAnswer(int clickedButton) {
        gameManager.setLives(gameManager.getLives()-1);
        if(gameManager.getLives() > 0) {
            updateLives();
        }
        else {
            intent= new Intent(GameActivity.this, gameoverActivity.class);
            intent.putExtra("score", gameManager.getScore());
            startActivity(intent);

        }
    }


    private void updateUI() {
        gameManager.updateGame();
        updateExercise();
        updateOptions();
        updateScore();
        updateLives();
    }

    private void updateLives() {
        game_TXT_lives.setText(gameManager.getLives() + "");
    }

    private void updateExercise() {

        game_TXT_exercise.setText(gameManager.getFirstNum() + "  X  " + gameManager.getSecondNum() + "  = ");
    }

    private void updateOptions() {
        game_BTN_one.setEnabled(true);
        game_BTN_two.setEnabled(true);
        game_BTN_three.setEnabled(true);
        game_BTN_four.setEnabled(true);
        game_BTN_one.setText(String.valueOf(gameManager.getOptions(0)));
        game_BTN_two.setText(String.valueOf(gameManager.getOptions(1)));
        game_BTN_three.setText(String.valueOf(gameManager.getOptions(2)));
        game_BTN_four.setText(String.valueOf(gameManager.getOptions(3)));
    }

    private void updateScore() {
        gameManager.setScore(score);
        score=gameManager.getScore();
        game_TXT_score.setText(String.valueOf(score));
    }




}