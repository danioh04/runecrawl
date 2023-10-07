package edu.gatech.cs2340.team33.runecrawl.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.gatech.cs2340.team33.runecrawl.R;

/**
 * The ending screen. Displays the leaderboard and whether the players won the game or not.
 * This will be implemented in the later sprints, right now the screen is empty.
 */
public class EndActivity extends AppCompatActivity {
    /**
     * Initializes the ending screen of the game.
     * Uses a XML layout to show whether the user won or not, and the leaderboard.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_screen);

        // Creates a button that is intended to take you back to the config screen
        Button restartButton = findViewById(R.id.restartButton);

        //// Set up a click listener for the restart button to take you back to config screen
        restartButton.setOnClickListener((View view) -> {
            Intent nextActivity = new Intent(this, ConfigurationActivity.class);
            startActivity(nextActivity);
        });

    }

}