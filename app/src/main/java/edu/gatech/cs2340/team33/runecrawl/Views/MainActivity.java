package edu.gatech.cs2340.team33.runecrawl.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.gatech.cs2340.team33.runecrawl.R;

/**
 * MainActivity serves as the entry point for the application.
 * This activity displays the game title and provides options
 * for starting or exiting the game.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Initializes the activity's user interface when it's created.
     * This method binds the XML layout to the activity and sets up click listeners
     * for the buttons.
     *
     * @param savedInstanceState Contains the activity's previously saved state if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the Start and Exit Game buttons from the XML layout
        Button startGameButton = findViewById(R.id.startGameButton);
        Button exitGameButton = findViewById(R.id.exitGameButton);

        // Set up a click listener for the Start Game button
        startGameButton.setOnClickListener((View v) -> {
            Intent nextActivity = new Intent(this, ConfigurationActivity.class);
            startActivity(nextActivity);
        });

        // Set up a click listener for the Exit Game button
        // When clicked, this button will terminate the app
        exitGameButton.setOnClickListener((View v) -> finish());
    }
}
