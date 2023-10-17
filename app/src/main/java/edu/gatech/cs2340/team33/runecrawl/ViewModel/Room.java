package edu.gatech.cs2340.team33.runecrawl.ViewModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import edu.gatech.cs2340.team33.runecrawl.Model.GameAttempt;
import edu.gatech.cs2340.team33.runecrawl.Model.Leaderboard;
import edu.gatech.cs2340.team33.runecrawl.Model.Player;
import edu.gatech.cs2340.team33.runecrawl.View.EndActivity;

/**
 * Room is a ViewModel class that is the skeleton for all room activity classes.
 * It handles the logic related to properly setting all UI components,
 * decrementing the timer, and going to the next screen or the end screen.
 */
public class Room extends Activity {
    private final Player player = Player.getInstance();
    private final Timer timer = new Timer();

    /**
     * Displays the player's attributes on the screen
     *
     * @param playerName  The player's name
     * @param difficulty  The game difficulty
     * @param hp          The player's HP
     * @param spriteImage The player's character image
     * @param player      The player object
     */
    public void populateUIComponents(TextView playerName, TextView difficulty,
                                     TextView hp, ImageView spriteImage, Player player) {

        // Populate UI components with player details
        playerName.setText(String.format("Name: %s", player.getUsername()));
        difficulty.setText(String.format("Difficulty: %s", player.getDifficulty()));
        hp.setText(String.format("HP: %s", player.getCurrentHp()));
        spriteImage.setImageResource(player.getType().getSpriteResId());
    }

    /**
     * Decrements the score every half a second
     *
     * @param currentClass The screen the game is currently on
     * @param score        The current score
     * @param player       The player object
     */
    public void startScoreDecrementTimer(Context currentClass, TextView score, Player player) {
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runOnUiThread(() -> {
                    try {
                        player.decreaseScore();
                        score.setText(String.format("Score: %s", player.getScore()));
                    } catch (IllegalStateException e) {
                        // If an exception is caught, stop the timer and move to the end screen
                        timer.cancel();
                        moveToEndScreen(currentClass);
                    }
                });
            }
        }, 0, 500);
    }

    /**
     * Transitions to the end game screen, stops the score decrement timer,
     * and adds the current game attempt to the leaderboard.
     *
     * @param currentClass The screen the game is currently on
     */
    public void moveToEndScreen(Context currentClass) {
        if (timer != null) {
            timer.cancel();
        }
        // Add current game attempt to the leaderboard
        GameAttempt currentAttempt = new GameAttempt(player);
        Leaderboard.getInstance().addAttempt(currentAttempt);

        // Move on to the end screen
        Intent nextActivity = new Intent(currentClass, EndActivity.class);
        currentClass.startActivity(nextActivity);
    }

    /**
     * Transitions to the next room.
     *
     * @param currentClass The screen the game is currently on
     * @param nextClass    The next screen the game will be on
     */
    public void moveToNextScreen(Context currentClass, Class nextClass) {
        if (timer != null) {
            timer.cancel();
        }
        // Move on to the next screen
        Intent nextActivity = new Intent(currentClass, nextClass);
        currentClass.startActivity(nextActivity);
    }
}
