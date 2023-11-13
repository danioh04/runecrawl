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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import edu.gatech.cs2340.team33.runecrawl.Model.Enemy;
import edu.gatech.cs2340.team33.runecrawl.Model.EnemyFactory;
import edu.gatech.cs2340.team33.runecrawl.Model.EnemyObserver;
import edu.gatech.cs2340.team33.runecrawl.Model.EnemyType;
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
    private final List<PlayerObserver> playerObservers;
    private final List<EnemyObserver> enemyObservers;
    private final List<RectF> enemyRectangles;
    private final List<Enemy> enemies;
    private final float lowerXCoordinateLimit;
    private final float upperXCoordinateLimit;
    private final float lowerYCoordinateLimit;
    private final float upperYCoordinateLimit;
    private final Map<Enemy, RectF> enemyMap = new HashMap<>();
    private CanvasView canvas;
    private float characterWidth;
    private float characterHeight;
    private float playerX;
    private float playerY;
    private float playerHitboxX;
    private float playerHitboxY;
    private RectF playerRectangle;
    private Enemy collidedEnemy;

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
        this.playerObservers = new ArrayList<>();
        this.enemyObservers = new ArrayList<>();
        this.enemyRectangles = new ArrayList<>();
        this.enemies = new ArrayList<>();
    }

    /**
     * Gets the lower x-coordinate limit for the room.
     *
     * @return The minimum x-coordinate the player can reach within the room.
     */
    public float getLowerXCoordinateLimit() {
        return this.lowerXCoordinateLimit;
    }

    /**
     * Gets the upper x-coordinate limit for the room.
     *
     * @return The maximum x-coordinate the player can reach within the room.
     */
    public float getUpperXCoordinateLimit() {
        return this.upperXCoordinateLimit;
    }

    /**
     * Gets the lower y-coordinate limit for the room.
     *
     * @return The minimum y-coordinate the player can reach within the room.
     */
    public float getLowerYCoordinateLimit() {
        return this.lowerYCoordinateLimit;
    }

    /**
     * Gets the upper y-coordinate limit for the room.
     *
     * @return The maximum y-coordinate the player can reach within the room.
     */
    public float getUpperYCoordinateLimit() {
        return this.upperYCoordinateLimit;
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
                        if (!player.isAlive()) {
                            throw new IllegalStateException("player is dead");
                        }
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
     * Initiates enemy movement within the room. Enemies move randomly at fixed intervals.
     */
    public void startEnemyMovement() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < enemies.size(); ++i) {
                    Enemy enemy = enemies.get(i);
                    enemy.moveRandomly(RoomViewModel.this);
                    canvas.updateEnemyPosition(i, enemy.getX(), enemy.getY());

                    // Create a new rectangle based off where the enemy has moved
                    RectF newRectangle = new RectF(enemy.getX() - enemy.getWidth() / 2,
                            enemy.getY() - enemy.getHeight() / 2,
                            enemy.getX() + enemy.getWidth() / 2,
                            enemy.getY() + enemy.getHeight() / 2);

                    enemyRectangles.set(i, newRectangle);
                    enemyMap.put(enemy, newRectangle);
                }
            }
        }, 0, 100);
    }

    /**
     * Generates enemies with random positions within the room and adds them to the canvas.
     *
     * @param currentClass The context of the current activity for accessing resources.
     */
    private void generateEnemies(Context currentClass) {
        // List of enemy types to generate
        List<EnemyType> types = Arrays.asList(EnemyType.SLIME,
                EnemyType.ROBOT, EnemyType.ORC, EnemyType.WEREWOLF);

        for (EnemyType type : types) {
            // Generate random coordinates within the defined limits
            float randomX = (this.lowerXCoordinateLimit + (float) (Math.random()
                    * (this.upperXCoordinateLimit - this.lowerXCoordinateLimit)));
            float randomY = (this.lowerYCoordinateLimit + (float) (Math.random()
                    * (this.upperYCoordinateLimit - this.lowerYCoordinateLimit)));

            Enemy randomEnemy = EnemyFactory.createEnemy(type);

            // Load enemy sprite based on type
            Bitmap enemySprite = BitmapFactory.decodeResource(
                    currentClass.getResources(), randomEnemy.getType().getSpriteResId());

            float enemyWidth = enemySprite.getWidth();
            float enemyHeight = enemySprite.getHeight();

            // Set the random coordinates for the enemy
            randomEnemy.setX(randomX);
            randomEnemy.setY(randomY);

            // Create the enemy's initial rectangle hitbox
            RectF enemyRectangle = new RectF(randomX - enemyWidth / 2,
                    randomY - enemyHeight / 2, randomX + enemyWidth / 2,
                    randomY + enemyHeight / 2);

            // Add enemy to the canvas
            canvas.addEnemy(enemySprite, randomX, randomY);

            // Add the enemy to the list
            enemies.add(randomEnemy);
            enemyRectangles.add(enemyRectangle);

            // Add the pairing of the enemy and the rectangle to a hashmap for future use
            enemyMap.put(randomEnemy, enemyRectangle);
        }
    }

    /**
     * Initializes and adds the player's character and enemies to the canvas.
     * It sets up the initial coordinates of the player and its hitbox, and creates
     * a new CanvasView object.
     *
     * @param currentClass The context of the current activity for resource access.
     * @param screenLayout The layout into which the canvas is to be added.
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

        // Call the method to generate and add enemies to the canvas
        generateEnemies(currentClass);

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
            if (playerX - movementSpeed >= 0
                    && playerX - movementSpeed >= lowerXCoordinateLimit) {
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

        canvas.updatePlayerPosition(playerX, playerY);
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
    public boolean isDoorCollision(float doorX, float doorY) {
        RectF doorRectangle = new RectF(doorX - 50, doorY - 50, doorX + 50, doorY + 50);
        if (playerRectangle.intersect(doorRectangle)) {
            notifyPlayerObservers();
            return true;
        }
        return false;
    }

    /**
     * Handles what happens when a collision has occurred
     * between the character and an enemy.
     * If a collision has occurred, the observers are notified.
     */
    public void isEnemyCollision() {
        for (RectF enemyRectangle : enemyRectangles) {
            if (playerRectangle.intersect(enemyRectangle)) {
                for (Map.Entry<Enemy, RectF> entry : enemyMap.entrySet()) {
                    Enemy enemy = entry.getKey();
                    RectF enemyRect = entry.getValue();

                    // Find the corresponding enemy (to the rectangle that was collided with)
                    if (enemyRect == enemyRectangle) {
                        collidedEnemy = enemy;
                    }
                }
                notifyEnemyObservers();
            }
        }
    }

    /**
     * Adds a class to a list of observers for a door collision.
     *
     * @param observer The class to be added to the list.
     */
    public void addPlayerObserver(PlayerObserver observer) {
        playerObservers.add(observer);
    }

    /**
     * Adds a class to a list of observers for an enemy collision.
     *
     * @param observer The class to be added to the list.
     */
    public void addEnemyObserver(EnemyObserver observer) {
        enemyObservers.add(observer);
    }

    /**
     * Removes a class from a list of observers.
     *
     * @param observer The class to be removed from the list.
     */
    public void removePlayerObserver(PlayerObserver observer) {
        playerObservers.remove(observer);
    }

    public void removeEnemyObserver(EnemyObserver observer) {
        enemyObservers.remove(observer);
    }

    /**
     * Notifies every observer in the list if a door collision has occurred.
     */
    public void notifyPlayerObservers() {
        for (PlayerObserver observer : playerObservers) {
            observer.doorCollisionOccurred();
        }
    }

    /**
     * Notifies every observer in the list if an enemy collision has occurred.
     */
    public void notifyEnemyObservers() {
        for (EnemyObserver observer : enemyObservers) {
            observer.playerCollisionOccurred(collidedEnemy);
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

    /**
     * Simulates key presses for testing purposes. This method is used to test the functionality of
     * player movement.
     *
     * @param movementStrategy The movement strategy for a given room, determining the player's
     *                         movement speed.
     * @param keyCode          The code of the key that was pressed.
     * @return An array of floats representing the player's updated coordinates in the format.
     */
    public float[] testKeyPress(PlayerMovementStrategy movementStrategy, int keyCode) {
        // Retrieve the class-specific movement strategy's speed
        int movementSpeed = movementStrategy.getMovementSpeed();

        switch (keyCode) {
        case android.view.KeyEvent.KEYCODE_DPAD_LEFT:
            if (playerX - movementSpeed >= lowerXCoordinateLimit) {
                playerX -= movementSpeed;
            }
            break;
        case android.view.KeyEvent.KEYCODE_DPAD_RIGHT:
            if (playerX + movementSpeed + 10 <= upperXCoordinateLimit) {
                playerX += movementSpeed;
            }
            break;
        case android.view.KeyEvent.KEYCODE_DPAD_UP:
            if (playerY - movementSpeed >= lowerYCoordinateLimit) {
                playerY -= movementSpeed;
            }
            break;
        case android.view.KeyEvent.KEYCODE_DPAD_DOWN:
            if (playerY + movementSpeed + 10 <= upperYCoordinateLimit) {
                playerY += movementSpeed;
            }
            break;
        default:
            break;
        }

        return new float[]{playerX, playerY};
    }

    /**
     * Sets the initial location of the player for testing purposes.
     *
     * @param x The horizontal location of the player.
     * @param y The vertical location of the player.
     */
    public void setXY(float x, float y) {
        playerX = x;
        playerY = y;
    }
}
