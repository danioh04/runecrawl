package edu.gatech.cs2340.team33.runecrawl.View;

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
 * Currently the goal is to display username, HP, difficulty, and the sprite picked.
 */
public class Room2Activity extends AppCompatActivity {
    private final Player player = Player.getInstance();
    private Timer timer;
    private TextView score;


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
        setContentView(R.layout.room2);

        initializeUIComponents();

        startScoreDecrementTimer();
    }

    /**
     * Initializes UI components and sets up the initial state of the game screen.
     */
    private void initializeUIComponents() {
        // Obtain references to UI components
        TextView playerName = findViewById(R.id.playerName);
        TextView difficulty = findViewById(R.id.difficulty);
        TextView hp = findViewById(R.id.hitpoints);
        score = findViewById(R.id.score2);
        ImageView spriteImage = findViewById(R.id.playerSprite);
        Button endButton = findViewById(R.id.endGameButton);
        Button nextButton = findViewById(R.id.nextButton);


        // Populate UI components with player details
        playerName.setText(String.format("Name: %s", player.getUsername()));
        difficulty.setText(String.format("Difficulty: %s", player.getDifficulty()));
        hp.setText(String.format("HP: %s", player.getCurrentHp()));

        // Display the sprite image based on the player's type
        spriteImage.setImageResource(player.getType().getSpriteResId());

        // Set up a click listener for the end game button
        endButton.setOnClickListener(this::moveToEndScreen);

        // Set up a click listener for the next button
        nextButton.setOnClickListener(this::moveToNextScreen);
    }

    /**
     * Starts a timer to decrement the player's score every half a second.
     */
    private void startScoreDecrementTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runOnUiThread(() -> {
                    try {
                        player.decreaseScore();
                        score.setText(String.format("Score: %s", player.getScore()));
                    } catch (IllegalStateException e) {
                        // If an exception is caught, stop the timer and move to the end screen
                        timer.cancel();
                        moveToEndScreen(null);
                    }
                });
            }
        }, 0, 500);
    }

    /**
     * Transitions to the next room.
     *
     * @param view The view that triggered this method (can be null).
     */
    private void moveToNextScreen(View view) {
        if (timer != null) {
            timer.cancel();
        }
        Intent nextActivity = new Intent(this, Room3Activity.class);
        startActivity(nextActivity);
    }

    /**
     * Transitions to the end game screen, stops the score decrement timer,
     * and adds the current game attempt to the leaderboard.
     *
     * @param view The view that triggered this method (can be null).
     */
    private void moveToEndScreen(View view) {
        if (timer != null) {
            timer.cancel();
        }

        // Add current game attempt to the leaderboard
        GameAttempt currentAttempt = new GameAttempt(player);
        Leaderboard.getInstance().addAttempt(currentAttempt);

        // Move on to the end screen
        Intent nextActivity = new Intent(this, EndActivity.class);
        startActivity(nextActivity);
    }
}
