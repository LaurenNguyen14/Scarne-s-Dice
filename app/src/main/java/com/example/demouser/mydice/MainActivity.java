package com.example.demouser.mydice;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    private ImageView dice;

    private int userScore;
    private int turnScore;
    private int computerScore;

    private boolean userTurn;


    private Button rollButton;
    private Button holdButton;
    private Button resetButton;

    private Random random = new Random();

    private int[] images = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        yourScoreView = findViewById(R.id.yourScore);
        turnScoreView = findViewById(R.id.turnScore);
        computerScoreView = findViewById(R.id.computerScore);

        dice = findViewById(R.id.dice);


        rollButton = findViewById(R.id.rollButton);
        holdButton = findViewById(R.id.holdButton);
        resetButton = findViewById(R.id.newGame);




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


    private void roll(){
        int i = random.nextInt(6);
        dice.setImageResource(images[i]);

        if(i !=0){
            turnScore += i + 1;
        }
        else{
            turnScore=0;
            if(userTurn) {
                userTurn = false;
            }
            else{
                userTurn=true;
            }
        }
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
    }

}
