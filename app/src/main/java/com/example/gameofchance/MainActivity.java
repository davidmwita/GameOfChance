package com.example.gameofchance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    //view declarations
    EditText nameText;
    Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init views
        nameText   = findViewById(R.id.nameText);
        playButton = findViewById(R.id.playButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString().trim();
                if (!name.isEmpty()) {
                    Player player = Player.getInstance();
                    player.setName(name);
                    player.setCredits(player.getDefaultCredits());

                    startActivity(new Intent(MainActivity.this, OptionsActivity.class));
                }
                else {
                    playButton.setError("Please enter a name");
                    playButton.setFocusable(true); //TODO: work on error display
                }
            }
        });
    }

}