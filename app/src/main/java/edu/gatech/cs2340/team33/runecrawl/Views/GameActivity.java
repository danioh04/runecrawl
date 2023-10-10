package edu.gatech.cs2340.team33.runecrawl.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import edu.gatech.cs2340.team33.runecrawl.Model.GameAttempt;
import edu.gatech.cs2340.team33.runecrawl.Model.Leaderboard;
import edu.gatech.cs2340.team33.runecrawl.Model.Player;
import edu.gatech.cs2340.team33.runecrawl.R;

/**
 * This is the Game Activity Class that has the main screen that the user will play on.
 * Currently the goal is to display username, HP, difficulty and the sprite picked.
 */
public class GameActivity extends AppCompatActivity {
    private final Player player = Player.getInstance();

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
        setContentView(R.layout.game_screen);

        // Obtain references to UI components
        TextView playerName = findViewById(R.id.playerName);
        TextView difficulty = findViewById(R.id.difficulty);
        TextView hp = findViewById(R.id.hitpoints);
        TextView score = findViewById(R.id.score);
        ImageView spriteImage = findViewById(R.id.playerSprite);
        Button endButton = findViewById(R.id.endGameButton);

        // Populate UI components with player details
        playerName.setText(String.format("Name: %s", player.getUsername()));
        difficulty.setText(String.format("Difficulty: %s", player.getDifficulty()));
        hp.setText(String.format("HP: %s", player.getCurrentHp()));

        // Display the sprite image based on the player's type
        spriteImage.setImageResource(player.getType().getSpriteResId());

        // Decrement the player's score by 1 for every half a second
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                player.decreaseScore();
                runOnUiThread(() -> score.setText(String.format("Score: %s", player.getScore())));
            }
        }, 0, 500);

        // Set up a click listener for the end game button to transition to the end activity
        endButton.setOnClickListener((View view) -> {
            // Stop the score timer when the button is pressed
            timer.cancel();

            // Add current game attempt to the leaderboard before moving to the end screen
            GameAttempt currentAttempt = new GameAttempt(player);
            Leaderboard.getInstance().addAttempt(currentAttempt);

            // Move on to the end screen
            Intent nextActivity = new Intent(this, EndActivity.class);
            startActivity(nextActivity);
        });
    }
}