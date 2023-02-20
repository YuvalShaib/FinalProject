package com.example.finalproject;

import java.util.Random;

public class GameManager {
    int lives=3;
    int score=0;
    Random r= new Random();
    int firstNum=0;
    int secondNum=0;
    int result=0;
    int randLoc=0;

    int options[]= new int[4];

    public GameManager() {

    }

    public GameManager(int firstNum, int secondNum, int result, int[] options) {
        this.firstNum = firstNum;
        this.secondNum = secondNum;
        this.result = result;
        this.options = options;
    }

    private void newExercise() {
        firstNum= r.nextInt(9) +1;
        secondNum=r.nextInt(9) +1;
        result=firstNum*secondNum;

        randLoc= r.nextInt(4);
        for (int i = 0; i < options.length; i++) {
            if(i == randLoc) {
                options[i]=result;
            }
            else if(i==0) {
                if(result==4) {
                    options[i]=firstNum+secondNum+4;
                }
                else {
                    options[i]=firstNum+secondNum;
                }
            }
            else if(i==1){
                options[i]=Math.abs(firstNum-secondNum);
            }
            else if(i==2){
                if(secondNum==1 || firstNum==0){
                    options[i]= (secondNum-1)*firstNum+5;
                }
                else {
                    options[i]= (secondNum-1)*firstNum+13;
                }
            }
            else if(i==3){
                options[i]= (firstNum+2)*secondNum+3;
            }

        }

    }




    public void updateGame() {
        newExercise();
    }

    public int getScore() {
        return score;
    }


    public int getFirstNum() {
        return firstNum;
    }

    public int getSecondNum() {
        return secondNum;
    }

    public int getResult() {
        return result;
    }

    public int getOptions(int i) {
        return options[i];
    }

    public int getRandLoc() {
        return randLoc;
    }

    public int getLives() {
        return lives;
    }

    public GameManager setLives(int lives) {
        this.lives = lives;
        return this;
    }

    public GameManager setScore(int score) {
        this.score = score;
        return this;
    }
}