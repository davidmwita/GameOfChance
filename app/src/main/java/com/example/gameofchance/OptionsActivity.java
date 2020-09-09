package com.example.gameofchance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OptionsActivity extends AppCompatActivity {

    //views
    Player player;
    TextView nameDisplayText, newsText, instructionText;

    //buttons
    Button findTheAceButton, pickANumberButton, noMatchDealerButton, creditsButton, resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        //get player instance
        player = Player.getInstance();


        //init views
        newsText = findViewById(R.id.newsText);

        nameDisplayText = findViewById(R.id.nameDisplayText);
        String nameText = "Hello " + player.getName() + "! Shall we begin?";
        nameDisplayText.setText(nameText);

        instructionText = findViewById(R.id.instructionText);
        String text = "Select a game to play, or an action to take. \n" +
                "Each game costs " + player.getCost() + " credits to play.";
        instructionText.setText(text);

        findTheAceButton    = findViewById(R.id.findTheAceButton);
        pickANumberButton   = findViewById(R.id.pickANumberButton);
        noMatchDealerButton = findViewById(R.id.noMatchDealerButton);
        creditsButton       = findViewById(R.id.creditsButton);
        resetButton         = findViewById(R.id.resetButton);

        findTheAceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OptionsActivity.this, FindTheAceActivity.class));
            }
        });

        pickANumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OptionsActivity.this, PickANumberActivity.class));
            }
        });

        noMatchDealerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OptionsActivity.this, NoMatchDealerActivity.class));
            }
        });

        creditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newsString = "Credits: " + player.getCredits() + " credits";
                newsText.setText(newsString);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setCredits(player.getDefaultCredits());
                String newsString = "You have reset your account! You now have 100 credits.";
                newsText.setText(newsString); //TODO: integer literal above to be removed
            }
        });

    }
}