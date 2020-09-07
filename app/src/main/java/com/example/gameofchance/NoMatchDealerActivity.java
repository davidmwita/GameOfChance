package com.example.gameofchance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class NoMatchDealerActivity extends AppCompatActivity {

    // local vars
    int size = 16, min = 0, max = 99; //size = number of randInts; min & max are range of ints.
    int[] array;
    //views
    TextView gameInfoText, wagerText, dealingText, numbersText, resultsText;
    EditText wagerAmountText;
    Button   dealButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_match_dealer);

        //get player instance
        final Player player = Player.getInstance();

        //init array
        array = new int[size];

        //init views
        gameInfoText    = (TextView) findViewById(R.id.gameInfoTextNMD);
        wagerText       = (TextView) findViewById(R.id.wagerText);
        dealingText     = (TextView) findViewById(R.id.dealingText);
        numbersText     = (TextView) findViewById(R.id.numbersText);
        resultsText     = (TextView) findViewById(R.id.resultsTextNMD);
        wagerAmountText = (EditText) findViewById(R.id.wagerAmountText);
        dealButton      = (Button) findViewById(R.id.dealButton);

        String gameText = "In this game, you can wager up to all of your credits. \n" +
                "The dealer will deal out " + size + " random numbers between " + min + " and " +
                max + ". \n" + "If there are no matches among them, you double your money! \n" +
                "Each deal costs " + player.getCost() + " credits.";
        gameInfoText.setText(gameText);

        String wagertext = "How many of your " + player.getCredits() +
                " credits would you like to wager?";
        wagerText.setText(wagertext);



        dealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int wagerAmount = Integer.parseInt(wagerAmountText.getText().toString().trim());
                play(player, wagerAmount);
            }
        });


    }

    private void play(Player player, int wagerAmount) {
        if (wagerAmount > player.getCredits()) {
            String text = "Sorry! You do not have enough credits to make this wager.\n" +
                    "Check your credits and try again.";
            resultsText.setText(text);
            return;
        }

        if (!player.deductCost()) {
            String text = "Sorry! You are out of credits.";
            resultsText.setText(text);
            return;
        }

        Random rand = new Random();

        String dealingtext = "Dealing out " + size + " random numbers";
        dealingText.setText(dealingtext);

        //populate array
        for (int i = 0; i < size; i++)
            array[i] = rand.nextInt(max + 1); //nextInt gives [0, max)

        //display array
        StringBuilder numberstext = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (i % 8 == 0 && i != 0) numberstext.append("\n");
            numberstext.append(" ").append(array[i]);
        }
        numbersText.setText(numberstext);

        //find matches
        boolean matches = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (array[i] == array[j] && i != j) {
                    matches = true;
                    break;
                }
            }
        }

        //display result
        String resultstext;
        if (matches) {
            resultstext = "Sorry! There were matches.";
            resultsText.setText(resultstext);
        } else {
            player.addPoints(wagerAmount);
            resultstext = "There were no matches! You win " + wagerAmount + " credits!";
            resultsText.setText(resultstext);
        }

        //update wager prompt
        String updatedWagerText = "How many of your " + player.getCredits() +
                " credits would you like to wager?";
        wagerText.setText(updatedWagerText);

    }
}