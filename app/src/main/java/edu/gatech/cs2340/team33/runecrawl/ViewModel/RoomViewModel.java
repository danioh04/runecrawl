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
public class RoomViewModel extends Activity {
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
    private float lowerXCoordinateLimit;
    private float upperXCoordinateLimit;
    private float lowerYCoordinateLimit;
    private float upperYCoordinateLimit;

    /**
     * Constructs a new RoomViewModel with specified upper and lower limits for
     * x and y coordinates.
     *
     * @param lowerXCoordinateLimit The minimum x-coordinate the player can reach within the room.
     * @param upperXCoordinateLimit The maximum x-coordinate the player can reach within the room.
     * @param lowerYCoordinateLimit The minimum y-coordinate the player can reach within the room.
     * @param upperYCoordinateLimit The maximum y-coordinate the player can reach within the room.
     */
    public RoomViewModel(float lowerXCoordinateLimit, float upperXCoordinateLimit,
                         float lowerYCoordinateLimit, float upperYCoordinateLimit) {
        this.lowerXCoordinateLimit = lowerXCoordinateLimit;
        this.upperXCoordinateLimit = upperXCoordinateLimit;
        this.lowerYCoordinateLimit = lowerYCoordinateLimit;
        this.upperYCoordinateLimit = upperYCoordinateLimit;
    }

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
        // Decode the character sprite from the resources based on the player's type
        Bitmap character = BitmapFactory.decodeResource(currentClass.getResources(),
                Player.getInstance().getType().getSpriteResId());

        // Get the width and height of the character sprite
        characterWidth = character.getWidth();
        characterHeight = character.getHeight();

        // Calculate the starting X and Y position of the player on the screen (centered)
        playerX = (currentClass.getResources().getDisplayMetrics().widthPixels
                - characterWidth) / 2;
        playerY = (currentClass.getResources().getDisplayMetrics().heightPixels
                - characterHeight) / 2;

        // Calculate the center position of the screen for player's hitbox
        playerHitboxX = (float) currentClass.getResources().getDisplayMetrics().widthPixels / 2;
        playerHitboxY = (float) currentClass.getResources().getDisplayMetrics().heightPixels / 2;

        // Create a new canvas view with the character sprite and starting position
        canvas = new CanvasView(currentClass, character, playerX, playerY);

        // Add the canvas view to the parent layout to render it on the screen
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
        int movementSpeed = movementStrategy.getMovementSpeed();

        switch (keyCode) {
        // When the LEFT arrow key is pressed
        case android.view.KeyEvent.KEYCODE_DPAD_LEFT:
            // Check if the player is within the left boundary and above the lower X limit
            if (playerX - movementSpeed >= 0 && playerX - movementSpeed >= lowerXCoordinateLimit) {
                // Move the player and the hitbox to the left
                playerX -= movementSpeed;
                playerHitboxX -= movementSpeed;
            }
            break;

        // When the RIGHT arrow key is pressed
        case android.view.KeyEvent.KEYCODE_DPAD_RIGHT:
            // Check if the player is within the right boundary and below the upper X limit
            if (playerX + movementSpeed <= canvas.getWidth() && playerX
                    + movementSpeed <= upperXCoordinateLimit) {
                // Move the player and the hitbox to the right
                playerX += movementSpeed;
                playerHitboxX += movementSpeed;
            }
            break;

        // When the UP arrow key is pressed
        case android.view.KeyEvent.KEYCODE_DPAD_UP:
            // Check if the player is within the top boundary and above the lower Y limit
            if (playerY - movementSpeed >= 0 && playerY - movementSpeed >= lowerYCoordinateLimit) {
                // Move the player and the hitbox upwards
                playerY -= movementSpeed;
                playerHitboxY -= movementSpeed;
            }
            break;

        // When the DOWN arrow key is pressed
        case android.view.KeyEvent.KEYCODE_DPAD_DOWN:
            // Check if the player is within the bottom boundary and below the upper Y limit
            if (playerY + movementSpeed <= canvas.getHeight() && playerY
                    + movementSpeed <= upperYCoordinateLimit) {
                // Move the player and the hitbox downwards
                playerY += movementSpeed;
                playerHitboxY += movementSpeed;
            }
            break;

        default:
            break;
        }

        System.out.println(playerX);
        System.out.println(playerY);

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
        timer.cancel();

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
    public void moveToNextScreen(Context currentClass, Class<?> nextClass) {
        timer.cancel();

        // Move on to the next screen
        Intent nextActivity = new Intent(currentClass, nextClass);
        currentClass.startActivity(nextActivity);
    }
}
