package com.example.gameofchance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FindTheAceActivity extends AppCompatActivity {

    //local vars
    Player player;
    final private int size = 3;
    private int wagerAmount, APosition, revealPos, choice; //choice is [1,3] so must be used after decrement.
    Random rand;

    //views
    TextView    gameInfoText, wagerText, dealText, selectText, revealText, changeText, resultsText, choiceText;
    EditText    wagerAmountText, selectionText;
    Button      dealButton, goButton, goButton2;
    RadioGroup  radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_the_ace);

        //get player instance
        player = Player.getInstance();

        //init random num generator
        rand  = new Random();

        //init views
        gameInfoText = (TextView) findViewById(R.id.gameInfoTextFTA);
        wagerText    = (TextView) findViewById(R.id.wagerTextFTA);
        dealText     = (TextView) findViewById(R.id.dealTextFTA);
        selectText   = (TextView) findViewById(R.id.selectText);
        changeText   = (TextView) findViewById(R.id.changeText);
        resultsText  = (TextView) findViewById(R.id.resultsTextFTA);
        choiceText   = (TextView) findViewById(R.id.choiceText);

        wagerAmountText = (EditText) findViewById(R.id.wagerAmountTextFTA);
        selectionText   = (EditText) findViewById(R.id.selectionText);

        dealButton  = (Button) findViewById(R.id.dealButtonFTA);
        goButton    = (Button) findViewById(R.id.goButtonFTA);
        goButton2   = (Button) findViewById(R.id.goButtonFTA2);

        radioGroup  = (RadioGroup) findViewById(R.id.radioGroup);

        //setting views that come later invisible
        dealText.setVisibility(View.INVISIBLE);
        selectText.setVisibility(View.INVISIBLE);
        changeText.setVisibility(View.INVISIBLE);
        choiceText.setVisibility(View.INVISIBLE);

        selectionText.setVisibility(View.INVISIBLE);

        goButton.setVisibility(View.INVISIBLE);
        goButton2.setVisibility(View.INVISIBLE);

        radioGroup.setVisibility(View.INVISIBLE);


        //setting texts
        String gameinfotext = "In this game you can wager up to all of your credits. \n" +
                "Three cards will be dealt: two queens and one ace.\n" +
                "If you find the ace, you will win your wager.\n" +
                "After choosing a card, one of the queens will be revealed.\n" +
                "At this point, you may either select a different card or remain with your choice.";
        gameInfoText.setText(gameinfotext);

        String wagertext = "How many of your " + player.getCredits() + " credits would you " +
                "like to wager?";
        wagerText.setText(wagertext);




        dealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wagerAmount = Integer.parseInt(wagerAmountText.getText().toString().trim());
                deal();
                //TODO: have a dramatic pause between actions.
            }
        });


    }


    private void deal() {
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

        APosition = rand.nextInt(size);

        String dealtext = "*** Dealing cards *** \n" +
                "Cards: |X|    |X|    |X| \n" +
                "               1      2      3";
        dealText.setVisibility(View.VISIBLE);
        dealText.setText(dealtext);

        select();
    }


    private void select() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String selecttext = "Select a card: 1, 2, or 3:";
        selectText.setVisibility(View.VISIBLE);
        selectText.setText(selecttext);

        selectionText.setVisibility(View.VISIBLE);
        goButton.setVisibility(View.VISIBLE);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice = Integer.parseInt(selectionText.getText().toString().trim());

                String choicetext = "choice: " + choice;
                choiceText.setVisibility(View.VISIBLE);
                choiceText.setText(choicetext);

                revealCard();
            }
        });
    }


    private void revealCard() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        revealPos = rand.nextInt(size);

        while (revealPos == APosition || revealPos == choice--) revealPos = (revealPos + 1) % size;

        dealText.setVisibility(View.VISIBLE);

        switch (revealPos) {
            case 0:
                String dealtext = "*** Revealing a QUEEN *** \n" + "Cards: |Q|    |X|    |X|";
                dealText.setText(dealtext);
                break;
            case 1:
                String dealtext1 = "*** Revealing a QUEEN *** \n" + "Cards: |X|    |Q|    |X|";
                dealText.setText(dealtext1);
                break;
            default:
                String dealtext2 = "*** Revealing a QUEEN *** \n" + "Cards: |X|    |Q|    |X|";
                dealText.setText(dealtext2);
        }

        changeCard();
    }


    private void changeCard() {
        String changetext = "Would you like to change your pick?";
        changeText.setVisibility(View.VISIBLE);
        changeText.setText(changetext);

        radioGroup.setVisibility(View.VISIBLE);
        goButton2.setVisibility(View.VISIBLE);

        goButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(radioId);

                String answer = radioButton.getText().toString().trim().toLowerCase();
                if (answer.equals("yes")) {
                    while (choice-- != revealPos) choice = (choice + 1) % size;
                    String choicetext = "choice: " + choice;
                    choiceText.setText(choicetext);
                }

                endResult();

            }
        });
    }


    private void endResult() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        switch (revealPos) {
            case 0:
                String finaltext;
                if (APosition == 1) {
                    finaltext = "*** End Result *** \n" + "Cards: |Q|    |A|    |Q|";
                    dealText.setText(finaltext);
                } else {
                    finaltext = "*** End Result *** \n" + "Cards: |Q|    |Q|    |A|";
                    dealText.setText(finaltext);
                }
                break;
            case 1:
                String finaltext1;
                if (APosition == 2) {
                    finaltext1 = "*** End Result *** \n" + "Cards: |Q|    |Q|    |A|";
                    dealText.setText(finaltext1);
                } else {
                    finaltext1 = "*** End Result *** \n" + "Cards: |A|    |Q|    |Q|";
                    dealText.setText(finaltext1);
                }
                break;
            default:
                String finaltext2;
                if (APosition == 0) {
                    finaltext2 = "*** End Result *** \n" + "Cards: |A|    |Q|    |Q|";
                    dealText.setText(finaltext2);
                } else {
                    finaltext2 = "*** End Result *** \n" + "Cards: |Q|    |A|    |Q|";
                    dealText.setText(finaltext2);
                }
                break;
        }

        String resulttext;
        if (choice-- == APosition) {
            player.addPoints(wagerAmount);
            resulttext = "Congratulations! You have won " + wagerAmount + " credits!";
            resultsText.setText(resulttext);
        } else {
            resulttext = "Sorry! You picked the wrong card.";
            resultsText.setText(resulttext);
        }
    }
}