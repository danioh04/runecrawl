package edu.gatech.cs2340.team33.runecrawl.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.gatech.cs2340.team33.runecrawl.Objects.Player;
import edu.gatech.cs2340.team33.runecrawl.R;

/**
 * This is the Game Activity Class that has the main screen that the user will play on.
 * Currently the goal is to display username, HP, difficulty and the sprite picked.
 */
public class GameActivity extends AppCompatActivity {
    private final Player player = ConfigurationActivity.getPlayer();

    /**
     * Initializes the game activity screen.
     * This method binds the XML layout to the game activity and sets up the base screen
     * with the few descriptors.
     *
     * @param savedInstanceState Contains the activity's previously saved state if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for the game screen from XML
        setContentView(R.layout.game_screen);

        // Obtain references to UI components
        TextView playerName = findViewById(R.id.playerName);
        TextView difficulty = findViewById(R.id.difficulty);
        TextView hp = findViewById(R.id.hitpoints);
        ImageView spriteImage = findViewById(R.id.playerSprite);
        Button endButton = findViewById(R.id.endGameButton);

        // Set up a click listener for the end game button to transition to the end activity
        endButton.setOnClickListener((View view) -> {
            Intent nextActivity = new Intent(GameActivity.this, EndActivity.class);
            startActivity(nextActivity);
        });

        // Populate UI components with player details
        playerName.setText(player.getUsername());
        difficulty.setText(player.getDifficulty().toString());
        hp.setText(String.valueOf(player.getCurrentHp()));

        // Display the sprite image based on the player's type
        spriteImage.setImageResource(player.getType().getSpriteResId());
    }
}