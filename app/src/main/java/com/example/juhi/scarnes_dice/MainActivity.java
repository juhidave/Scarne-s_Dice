package com.example.juhi.scarnes_dice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import android.os.Handler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private int yourOverAllScore = 0;
    private int yourTurnScore;
    private int compOverAllScore = 0;
    private int comTurnScore;
    private Random random = new Random();
    ImageView diceView;
    TextView cScoreVal;
    TextView uScoreVal;
    TextView uTurnScoreVal;
    TextView uTurnScorelbl;
    TextView cTurnScoreVal;
    TextView cTurnScorelbl;
    Button RollBtn;
    Button HoldBtn;
    Button ResetBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RollBtn = (Button)findViewById(R.id.RollBtn);
        HoldBtn = (Button) findViewById(R.id.HoldBtn);
        ResetBtn = (Button) findViewById(R.id.ResetBtn);

        cScoreVal = (TextView)findViewById(R.id.cScoreVal);
        uScoreVal = (TextView)findViewById(R.id.uScoreVal);
        uTurnScoreVal = (TextView)findViewById(R.id.uTurnScoreVal);
        uTurnScorelbl = (TextView)findViewById(R.id.uTurnScorelbl);
        cTurnScorelbl = (TextView)findViewById(R.id.cTurnScorelbl);
        cTurnScoreVal = (TextView)findViewById(R.id.cTurnScoreVal);

        diceView = (ImageView)findViewById(R.id.diceView);

        RollBtn.setOnClickListener(this);
        HoldBtn.setOnClickListener(this);
        ResetBtn.setOnClickListener(this);
        rollingDice(6,1);
        yourTurn();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.RollBtn:
                int score = rollingDice(6,1);
                if(score == 1){
                    yourTurnScore = 0;
                    uTurnScoreVal.setText(String.valueOf(yourTurnScore));
                    createToast("You Encounter 1",1000);
                    computerTurn();
                }
                else{
                    yourTurnScore+=score;
                    uTurnScoreVal.setText(String.valueOf(yourTurnScore));
                }
                break;
            case R.id.HoldBtn:
                yourOverAllScore += yourTurnScore;
                uScoreVal.setText((String.valueOf(yourOverAllScore)));
                if(yourOverAllScore >= 100){
                    createToast("You Won!",1000);
                    startNewGame();
                }else {
                    computerTurn();
                }
                break;
            case R.id.ResetBtn:
                resetGame();
                break;
        }
    }

    private void computerTurn() {
        createToast("Computer Turn",1000);
        comTurnScore = 0;
        cTurnScoreVal.setText(String.valueOf(comTurnScore));
        RollBtn.setEnabled(false);
        HoldBtn.setEnabled(false);
        ResetBtn.setEnabled(false);
        uTurnScoreVal.setVisibility(View.INVISIBLE);
        uTurnScorelbl.setVisibility(View.INVISIBLE);
        cTurnScorelbl.setVisibility(View.VISIBLE);
        cTurnScoreVal.setVisibility(View.VISIBLE);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                computerPlays();
            }
        },1000);
    }

    private void computerPlays() {
        int score = rollingDice(6, 1);
        /*if(!mode) {
            score = rollingDice(6, 1);
        }
        else{
            score = rollingDice(5,2);
        }*/
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {}
        },1000);
        if(score == 1){
            comTurnScore = 0;
            cTurnScoreVal.setText(String.valueOf(comTurnScore));
            createToast("Computer Encounter 1",500);
            yourTurn();
        }
        else {
            comTurnScore += score;
            cTurnScoreVal.setText(String.valueOf(comTurnScore));
            if (compOverAllScore + comTurnScore >= 100) {
                computerHold();
            } else {
                if (comTurnScore >= 20) {
                    computerHold();
                } else {
                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            computerPlays();
                        }
                    }, 1000);
                }
            }
        }
    }

    private void computerHold() {
        createToast("Computer Holds",500);
        compOverAllScore += comTurnScore;
        cScoreVal.setText(String.valueOf(compOverAllScore));
        if(compOverAllScore >= 100){
            createToast("Computer Won",1000);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startNewGame();
                }
            },1000);
        }else{
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    yourTurn();
                }
            },1000);
        }
    }

    private void startNewGame() {
        createToast("Starting New Game.....!!!",1000);
        rollingDice(6,1);
        resetGame();
    }

    private void resetGame() {
        compOverAllScore = 0;
        yourOverAllScore = 0;
        cScoreVal.setText(String.valueOf(compOverAllScore));
        uScoreVal.setText(String.valueOf(yourOverAllScore));
        yourTurn();
    }

    private void yourTurn() {
        createToast("Your Turn",500);
        yourTurnScore = 0;
        uTurnScoreVal.setText(String.valueOf(yourTurnScore));
        uTurnScoreVal.setVisibility(View.VISIBLE);
        uTurnScorelbl.setVisibility(View.VISIBLE);
        cTurnScorelbl.setVisibility(View.INVISIBLE);
        cTurnScoreVal.setVisibility(View.INVISIBLE);

        RollBtn.setEnabled(true);
        HoldBtn.setEnabled(true);
        ResetBtn.setEnabled(true);
    }

    private int rollingDice(int x,int y) {
        int diceFront = random.nextInt(x)+y;
        changeDiceImage(diceFront);
        return diceFront;
    }
    private void changeDiceImage(int x){
        switch (x){
            case 1:
                diceView.setImageResource(R.drawable.dice1);
                break;
            case 2:
                diceView.setImageResource(R.drawable.dice2);
                break;
            case 3:
                diceView.setImageResource(R.drawable.dice3);
                break;
            case 4:
                diceView.setImageResource(R.drawable.dice4);
                break;
            case 5:
                diceView.setImageResource(R.drawable.dice5);
                break;
            case 6:
                diceView.setImageResource(R.drawable.dice6);
                break;
        }
    }
    private void createToast(String msg,int time){
        final Toast toast = Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        },time);
    }
}
