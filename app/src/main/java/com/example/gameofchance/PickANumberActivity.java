package com.example.gameofchance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

public class PickANumberActivity extends AppCompatActivity {
    //local vars
    final int reward = 100;

    //views
    SeekBar seekBar;
    TextView progressText, resultText, gameInfoText;
    Button rollButton;

    //min and max for progress
    int min = 0, max = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_a_number);

        //get player instance
        final Player player = Player.getInstance();

        //init views
        seekBar      = findViewById(R.id.seekBar);
        progressText = findViewById(R.id.progressText);
        resultText   = findViewById(R.id.resultsTextPAN);
        gameInfoText = findViewById(R.id.gameInfoTextPAN);
        rollButton   = findViewById(R.id.rollButton);

        String text = "Simply pick a number between " + min + " and " + max + ", and if you pick " +
                "the winning number, you will win the jackpot of " + reward + " credits!";
        gameInfoText.setText(text);

        seekBar.setMax(max);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                String text = "" + progress + "";
                progressText.setText(text);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play(player, seekBar.getProgress());
            }
        });

    }


    private void play(Player player, int guess) {
        if (!player.deductCost()) {
            String text = "Sorry! You are out of credits.";
            resultText.setText(text);
            return;
        }

        Random rand = new Random();
        int winningNum = rand.nextInt(max) + 1;

        if (guess != winningNum) {
            String string = "Sorry! The winning number is " + winningNum;
            resultText.setText(string);
        } else {
            player.addPoints(reward);
            String string = "Congratulations! You have won " + reward + " credits!";
            resultText.setText(string);
        }
    }
}