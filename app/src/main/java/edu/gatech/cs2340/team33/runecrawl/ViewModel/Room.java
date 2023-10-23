package edu.gatech.cs2340.team33.runecrawl.ViewModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import edu.gatech.cs2340.team33.runecrawl.Model.GameAttempt;
import edu.gatech.cs2340.team33.runecrawl.Model.Leaderboard;
import edu.gatech.cs2340.team33.runecrawl.Model.Player;
import edu.gatech.cs2340.team33.runecrawl.Model.PlayerMovementStrategy;
import edu.gatech.cs2340.team33.runecrawl.Model.PlayerObserver;
import edu.gatech.cs2340.team33.runecrawl.View.CanvasView;
import edu.gatech.cs2340.team33.runecrawl.View.EndActivity;

/**
 * Room is a ViewModel class that is the skeleton for all room activity classes.
 * It handles the logic related to properly setting all UI components,
 * decrementing the timer, going to the next screen or the end screen,
 * coordinate changes when a key is pressed, and collision logic.
 */
public class Room extends Activity {
    private final Timer timer = new Timer();
    private final Player player = Player.getInstance();
    private final List<PlayerObserver> observers = new ArrayList<>();
    private CanvasView canvas;
    private float characterWidth;
    private float characterHeight;
    private float playerX;
    private float playerY;
    private float playerHitboxX;
    private float playerHitboxY;
    private RectF playerRectangle;

    /**
     * Displays the player's attributes on the screen.
     *
     * @param playerName The player's name.
     * @param difficulty The game difficulty.
     * @param hp         The player's HP.
     */
    public void populateUIComponents(TextView playerName, TextView difficulty, TextView hp) {
        // Populate UI components with player details
        playerName.setText(String.format("Name: %s", player.getUsername()));
        difficulty.setText(String.format("Difficulty: %s", player.getDifficulty()));
        hp.setText(String.format("HP: %s", player.getCurrentHp()));
    }

    /**
     * Decrements the score every half a second.
     *
     * @param currentClass The screen the game is currently on.
     * @param score        The current score.
     */
    public void startScoreDecrementTimer(Context currentClass, TextView score) {
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
     * Set up the initial coordinates of the player and its hitbox
     * and creates a new CanvasView object.
     *
     * @param currentClass The current state of the application.
     * @param screenLayout the XML layout of the current screen.
     */
    public void addToCanvas(Context currentClass, ConstraintLayout screenLayout) {
        Bitmap character = BitmapFactory.decodeResource(currentClass.getResources(),
                Player.getInstance().getType().getSpriteResId());
        characterWidth = character.getWidth();
        characterHeight = character.getHeight();
        playerX = (currentClass.getResources().getDisplayMetrics().widthPixels
                - characterWidth) / 2;
        playerY = (currentClass.getResources().getDisplayMetrics().heightPixels
                - characterHeight) / 2;
        playerHitboxX = currentClass.getResources().getDisplayMetrics().widthPixels / 2;
        playerHitboxY = currentClass.getResources().getDisplayMetrics().heightPixels / 2;

        canvas = new CanvasView(currentClass, character, playerX, playerY);
        screenLayout.addView(canvas);
    }

    /**
     * Handles what happens when a key is pressed to move the character.
     * Implements bounds checking and updates coordinates of the player and its hitbox.
     *
     * @param movementStrategy The movement strategy specific to a screen's implementation.
     * @param keyCode          The code of the key that was pressed.
     */
    public void onKeyDown(PlayerMovementStrategy movementStrategy, int keyCode) {
        // Retrieve the class-specific movement strategy's speed
        int movementSpeed = movementStrategy.movementSpeed();
        System.out.println(canvas.getHeight());
        switch (keyCode) {
            case android.view.KeyEvent.KEYCODE_DPAD_LEFT:
                if (playerX - movementSpeed >= 0) {
                    playerX -= movementSpeed;
                    playerHitboxX -= movementSpeed;
                }
                break;
            case android.view.KeyEvent.KEYCODE_DPAD_RIGHT:
                if (playerX + movementSpeed + characterWidth <= canvas.getWidth()) {
                    playerX += movementSpeed;
                    playerHitboxX += movementSpeed;
                }
                break;
            case android.view.KeyEvent.KEYCODE_DPAD_UP:
                if (playerY - movementSpeed >= 0) {
                    playerY -= movementSpeed;
                    playerHitboxY -= movementSpeed;
                }
                break;
            case android.view.KeyEvent.KEYCODE_DPAD_DOWN:
                if (playerY + movementSpeed + characterHeight <= canvas.getHeight()) {
                    playerY += movementSpeed;
                    playerHitboxY += movementSpeed;
                }
                break;
            default:
                break;
        }
        canvas.updatePosition(playerX, playerY);
        playerRectangle = new RectF(playerHitboxX - characterWidth / 2,
                playerHitboxY - characterHeight / 2, playerHitboxX + characterWidth / 2,
                playerHitboxY + characterHeight / 2);
    }

    /**
     * Handles what happens when a collision has occurred
     * between the character and a door.
     * If a collision has occurred, the observers are notified.
     *
     * @param doorX The door's X-coordinate.
     * @param doorY The door's Y-coordinate.
     * @return If a collision has occurred.
     */
    public boolean isCollision(float doorX, float doorY) {
        RectF doorRectangle = new RectF(doorX - 50, doorY - 50, doorX + 50, doorY + 50);
        if (playerRectangle.intersect(doorRectangle)) {
            notifyObservers();
            return true;
        }
        return false;
    }

    /**
     * Adds a class to a list of observers.
     *
     * @param observer The class to be added to the list.
     */
    public void addObserver(PlayerObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes a class from a list of observers.
     *
     * @param observer The class to be removed from the list.
     */
    public void removeObserver(PlayerObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies every observer in the list if a collision has occurred.
     */
    public void notifyObservers() {
        for (PlayerObserver observer : observers) {
            observer.collisionOccurred();
        }
    }

    /**
     * Transitions to the end game screen, stops the score decrement timer,
     * and adds the current game attempt to the leaderboard.
     *
     * @param currentClass The current state of the application.
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
     * @param currentClass The current state of the application.
     * @param nextClass    The next screen the game will be on.
     */
    public void moveToNextScreen(Context currentClass, Class nextClass) {
        if (timer != null) {
            timer.cancel();
        }
        // Move on to the next screen
        Intent nextActivity = new Intent(currentClass, nextClass);
        currentClass.startActivity(nextActivity);
    }

    /**
     * method to emulate and test functionality of keyPresses.
     * @param movementStrategy the movement strategy for a given room which dictates movement speed.
     * @param keyCode The code of the key that was pressed.
     * @return an array of floats that give the coordinates of the player in [x,y] format.
     */
    public float[] testKeyPress(PlayerMovementStrategy movementStrategy, int keyCode) {
        // Retrieve the class-specific movement strategy's speed
        int movementSpeed = movementStrategy.movementSpeed();
        switch (keyCode) {
            case android.view.KeyEvent.KEYCODE_DPAD_LEFT:
                if (playerX - movementSpeed >= 0) {
                    playerX -= movementSpeed;

                }
                break;
            case android.view.KeyEvent.KEYCODE_DPAD_RIGHT:
                if (playerX + movementSpeed + 10 <= 100) {
                    playerX += movementSpeed;

                }
                break;
            case android.view.KeyEvent.KEYCODE_DPAD_UP:
                if (playerY - movementSpeed >= 0) {
                    playerY -= movementSpeed;

                }
                break;
            case android.view.KeyEvent.KEYCODE_DPAD_DOWN:
                if (playerY + movementSpeed + 10 <= 100) {
                    playerY += movementSpeed;

                }
                break;
            default:
                break;
        }
        return new float[]{playerX, playerY};
    }

    /**
     * method to set initial location of player for testing purposes.
     * @param x the horizontal location of player.
     * @param y the vertical location of player.
     */
    public void setXY(float x, float y) {
        playerX = x;
        playerY = y;
    }
}
