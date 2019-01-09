package com.example.demouser.mydice;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    private TextView yourScoreView;
    private TextView turnScoreView;
    private TextView computerScoreView;
    private TextView userWin;

    private ImageView dice;

    private int userScore;
    private int turnScore;
    private int computerScore;

    private boolean userTurn;
    private Handler handler;

    private Button rollButton;
    private Button holdButton;
    private Button resetButton;


    private Animation zoom;

    private Random random = new Random();

    private int[] images = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);

        yourScoreView = findViewById(R.id.yourScore);
        turnScoreView = findViewById(R.id.turnScore);
        computerScoreView = findViewById(R.id.computerScore);
        userWin = findViewById(R.id.userWin);
        userWin.setVisibility(View.INVISIBLE);

        dice = findViewById(R.id.dice);


        rollButton = findViewById(R.id.rollButton);
        holdButton = findViewById(R.id.holdButton);
        resetButton = findViewById(R.id.newGame);

        handler = new Handler();

        rollButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    roll();
                    updateView();

                }
            });

        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                turnScore=0;
                userScore=0;
                computerScore=0;

                dice.setImageResource(images[0]);
                userTurn=true;
                updateView();

                holdButton.setEnabled(true);
                resetButton.setEnabled(true);
                rollButton.setEnabled(true);

                userWin.setVisibility(View.INVISIBLE);

            }
        });

        holdButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                userScore = userScore+turnScore;

                turnScore=0;
                switchUpdate();
            }
        });



    }

    private Runnable computerRoll = new Runnable() {
        @Override
        public void run() {
            if(turnScore<20 && !userTurn){
                roll();
                handler.postDelayed(this, 500);
            }
            else {
                computerScore += turnScore;
                turnScore = 0;
                updateView();

                if(!gameOver()) {
                    userTurn = true;

                    //enable all buttons for user turn
                    rollButton.setEnabled(true);
                    resetButton.setEnabled(true);
                    holdButton.setEnabled(true);
                }

                resetButton.setEnabled(true);
            }
            updateView();
        }
    };

    private void roll(){
        int i = random.nextInt(6);
        dice.setImageResource(images[i]);

        if(i !=0){
            turnScore += i + 1;
            gameOver();
        }
        else{
            turnScore=0;
            if(userTurn) {
                userTurn = false;
                computerTurn();
            }
            else{
                userTurn=true;
            }
        }

        dice.startAnimation(zoom);


    }

    private void updateView(){
        turnScoreView.setText("Turn Score: " + turnScore);
        yourScoreView.setText("Your Score: " + userScore);
        computerScoreView.setText("Computer Score: "+ computerScore);

    }

    private void switchUpdate(){
        yourScoreView.setText("Your Score: " + userScore);
        turnScoreView.setText("Turn Score: " +turnScore);
        userTurn=false;
        if(!gameOver())
            computerTurn();
    }

    private void computerTurn(){
        holdButton.setEnabled(false);
        resetButton.setEnabled(false);
        rollButton.setEnabled(false);
        handler.postDelayed(computerRoll,500);

    }
    private boolean gameOver(){

        if(userScore >= 100){
            rollButton.setEnabled(false);
            holdButton.setEnabled(false);
            userWin.setText("YOU WIN");
            userWin.setVisibility(View.VISIBLE);

            return true;
        }
        else if(computerScore>= 100){
            rollButton.setEnabled(false);
            holdButton.setEnabled(false);

            userWin.setText("COMPUTER WINS");
            userWin.setVisibility(View.VISIBLE);

            return true;
        }

        return false;
    }


}

