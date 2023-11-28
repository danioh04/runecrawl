package edu.gatech.cs2340.team33.runecrawl.ViewModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import edu.gatech.cs2340.team33.runecrawl.Model.Enemies.Enemy;
import edu.gatech.cs2340.team33.runecrawl.Model.Enemies.EnemyFactory;
import edu.gatech.cs2340.team33.runecrawl.Model.Enemies.EnemyObserver;
import edu.gatech.cs2340.team33.runecrawl.Model.Enemies.EnemyType;
import edu.gatech.cs2340.team33.runecrawl.Model.Game.Attempt;
import edu.gatech.cs2340.team33.runecrawl.Model.Game.Leaderboard;
import edu.gatech.cs2340.team33.runecrawl.Model.Player.MovementStrategy;
import edu.gatech.cs2340.team33.runecrawl.Model.Player.Player;
import edu.gatech.cs2340.team33.runecrawl.Model.Player.PlayerObserver;
import edu.gatech.cs2340.team33.runecrawl.R;
import edu.gatech.cs2340.team33.runecrawl.View.CanvasView;
import edu.gatech.cs2340.team33.runecrawl.View.EndActivity;

/**
 * Room is a ViewModel class that is the skeleton for all room activity classes.
 * It handles the logic related to properly setting all UI components,
 * generating enemies, going to the next screen or the end screen,
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
    private final Handler handler = new Handler();
    private Bitmap character;
    private CanvasView canvas;
    private float characterWidth;
    private float characterHeight;
    private RectF playerRectangle;
    private Enemy collidedEnemy;
    private boolean facingRight = true;

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
     * @param playerName The text view for the player's name.
     * @param difficulty The text view for the game difficulty.
     * @param hp         The text view for the player's HP.
     * @param score      The text view for the player's score.
     */
    public void populateUIComponents(TextView playerName, TextView difficulty, TextView hp,
                                     TextView score) {
        // Populate UI components with player details
        playerName.setText(String.format("Name: %s", player.getUsername()));
        difficulty.setText(String.format("Difficulty: %s", player.getDifficulty()));
        hp.setText(String.format("HP: %s", player.getCurrentHp()));
        score.setText(String.format("Score: %s", player.getScore()));
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
     * This method iterates through different enemy types, generates each enemy with a random
     * position, and adds them to the game environment.
     *
     * @param currentClass The context of the current activity for accessing resources.
     */
    private void generateEnemies(Context currentClass) {
        EnemyType[] types = {EnemyType.SLIME, EnemyType.ROBOT, EnemyType.ORC, EnemyType.WEREWOLF};

        for (EnemyType type : types) {
            PointF randomPosition = generateRandomPosition();
            Enemy randomEnemy = EnemyFactory.createEnemy(type);
            Bitmap enemySprite = loadEnemySprite(currentClass, randomEnemy);

            addEnemyToGame(randomEnemy, randomPosition, enemySprite);
        }
    }

    /**
     * Generates a random position within defined coordinate limits.
     *
     * @return PointF object containing X and Y coordinates.
     */
    private PointF generateRandomPosition() {
        float randomX = this.lowerXCoordinateLimit + (float) (Math.random()
                * (this.upperXCoordinateLimit - this.lowerXCoordinateLimit));
        float randomY = this.lowerYCoordinateLimit + (float) (Math.random()
                * (this.upperYCoordinateLimit - this.lowerYCoordinateLimit));

        return new PointF(randomX, randomY);
    }

    /**
     * Loads the sprite for a given enemy.
     *
     * @param currentClass The context used to access resources.
     * @param enemy        The enemy for which the sprite needs to be loaded.
     * @return Bitmap of the loaded sprite.
     */
    private Bitmap loadEnemySprite(Context currentClass, Enemy enemy) {
        return BitmapFactory.decodeResource(
                currentClass.getResources(), enemy.getType().getSpriteResId());
    }

    /**
     * Adds an enemy to the game environment.
     * This method sets the enemy's position, creates its hitbox, and adds the enemy and its sprite
     * to the canvas and game lists.
     *
     * @param enemy    The enemy to add.
     * @param position The position of the enemy.
     * @param sprite   The sprite of the enemy.
     */
    private void addEnemyToGame(Enemy enemy, PointF position, Bitmap sprite) {
        float enemyWidth = sprite.getWidth();
        float enemyHeight = sprite.getHeight();

        enemy.setX(position.x);
        enemy.setY(position.y);

        RectF enemyRectangle = new RectF(position.x - enemyWidth / 2,
                position.y - enemyHeight / 2, position.x + enemyWidth / 2,
                position.y + enemyHeight / 2);

        canvas.addEnemy(sprite, position.x, position.y);
        enemies.add(enemy);
        enemyRectangles.add(enemyRectangle);
        enemyMap.put(enemy, enemyRectangle);
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
        character = BitmapFactory.decodeResource(currentClass.getResources(),
                player.getType().getSpriteResId());

        // Get the width and height of the character sprite
        characterWidth = character.getWidth();
        characterHeight = character.getHeight();

        // Calculate the starting X and Y position of the player on the screen (centered)
        player.setX((currentClass.getResources().getDisplayMetrics().widthPixels
                - characterWidth) / 2);
        player.setY((currentClass.getResources().getDisplayMetrics().
                heightPixels - characterHeight) / 2);

        // Create a new canvas view with the character sprite and starting position
        canvas = new CanvasView(currentClass, character, player.getX(), player.getY());

        // Call the method to generate and add enemies to the canvas
        generateEnemies(currentClass);

        // Add the canvas view to the parent layout to render it on the screen
        screenLayout.addView(canvas);
    }

    /**
     * Handles key presses to move the character. Checks boundaries and updates player and
     * hitbox coordinates. This method reacts to key presses and determines the direction of
     * movement based on the key code. It uses the provided movement strategy to calculate the speed
     * and updates the player's position accordingly.
     *
     * @param currentClass     The context of the current activity for resource access.
     * @param movementStrategy The movement strategy for the screen.
     * @param keyCode          The key code of the pressed key.
     */
    public void onKeyDown(Context currentClass, MovementStrategy movementStrategy, int keyCode) {
        int movementSpeed = movementStrategy.getMovementSpeed();

        switch (keyCode) {
        case android.view.KeyEvent.KEYCODE_SPACE:
            switch (player.getType()) {
            case MAGE:
                if (facingRight) {
                    character = BitmapFactory.decodeResource(currentClass.getResources(),
                            R.drawable.right_attacking_mage);
                } else {
                    character = BitmapFactory.decodeResource(currentClass.getResources(),
                            R.drawable.left_attacking_mage);
                }

                handler.postDelayed(() -> runOnUiThread(() -> {
                    if (facingRight) {
                        character = BitmapFactory.decodeResource(currentClass.getResources(),
                                R.drawable.right_still_mage);
                    } else {
                        character = BitmapFactory.decodeResource(currentClass.getResources(),
                                R.drawable.left_still_mage);
                    }
                    canvas.updateSprite(character);
                }), 500);

                break;
            case WARRIOR:
                if (facingRight) {
                    character = BitmapFactory.decodeResource(currentClass.getResources(),
                            R.drawable.right_attacking_warrior);
                } else {
                    character = BitmapFactory.decodeResource(currentClass.getResources(),
                            R.drawable.left_attacking_warrior);
                }

                handler.postDelayed(() -> runOnUiThread(() -> {
                    if (facingRight) {
                        character = BitmapFactory.decodeResource(currentClass.getResources(),
                                R.drawable.right_still_warrior);
                    } else {
                        character = BitmapFactory.decodeResource(currentClass.getResources(),
                                R.drawable.left_still_warrior);
                    }
                    canvas.updateSprite(character);
                }), 500);

                break;
            case ARCHER:
                if (facingRight) {
                    character = BitmapFactory.decodeResource(currentClass.getResources(),
                            R.drawable.right_attacking_archer);
                } else {
                    character = BitmapFactory.decodeResource(currentClass.getResources(),
                            R.drawable.left_attacking_archer);
                }

                handler.postDelayed(() -> runOnUiThread(() -> {
                    if (facingRight) {
                        character = BitmapFactory.decodeResource(currentClass.getResources(),
                                R.drawable.right_still_archer);
                    } else {
                        character = BitmapFactory.decodeResource(currentClass.getResources(),
                                R.drawable.left_still_archer);
                    }
                    canvas.updateSprite(character);
                }), 500);

                break;
            default:
                break;
            }
            break;
        case android.view.KeyEvent.KEYCODE_DPAD_LEFT:
            switch (player.getType()) {
            case MAGE:
                character = BitmapFactory.decodeResource(currentClass.getResources(),
                        R.drawable.left_still_mage);
                break;
            case WARRIOR:
                character = BitmapFactory.decodeResource(currentClass.getResources(),
                        R.drawable.left_still_warrior);
                break;
            case ARCHER:
                character = BitmapFactory.decodeResource(currentClass.getResources(),
                        R.drawable.left_still_archer);
                break;
            default:
                break;
            }
            facingRight = false;
            updatePosition(-movementSpeed, 0);
            break;
        case android.view.KeyEvent.KEYCODE_DPAD_RIGHT:
            switch (player.getType()) {
            case MAGE:
                character = BitmapFactory.decodeResource(currentClass.getResources(),
                        R.drawable.right_still_mage);
                break;
            case WARRIOR:
                character = BitmapFactory.decodeResource(currentClass.getResources(),
                        R.drawable.right_still_warrior);
                break;
            case ARCHER:
                character = BitmapFactory.decodeResource(currentClass.getResources(),
                        R.drawable.right_still_archer);
                break;
            default:
                break;
            }
            facingRight = true;
            updatePosition(movementSpeed, 0);
            break;
        case android.view.KeyEvent.KEYCODE_DPAD_UP:
            updatePosition(0, -movementSpeed);
            break;
        case android.view.KeyEvent.KEYCODE_DPAD_DOWN:
            updatePosition(0, movementSpeed);
            break;
        default:
            break;
        }

        canvas.updateSprite(character);
        updatePlayerAndHitbox();
    }

    /**
     * Updates the player's position based on the delta values.
     * This method calculates the new position of the player by adding delta values to the current
     * coordinates. It checks for boundary conditions before updating to ensure the player remains
     * within allowed limits.
     *
     * @param deltaX The change in the X coordinate.
     * @param deltaY The change in the Y coordinate.
     */
    private void updatePosition(int deltaX, int deltaY) {
        if (canMove(deltaX, deltaY)) {
            player.setX(player.getX() + deltaX);
            player.setY(player.getY() + deltaY);
        }
    }

    /**
     * Checks if the player can move to a new position.
     * This method determines if the new position calculated from delta values is within the game's
     * boundary limits.
     *
     * @param deltaX The proposed change in the X coordinate.
     * @param deltaY The proposed change in the Y coordinate.
     * @return boolean indicating whether the movement is within boundaries.
     */
    private boolean canMove(int deltaX, int deltaY) {
        return (player.getX() + deltaX >= lowerXCoordinateLimit && player.getX()
                + deltaX <= upperXCoordinateLimit)
                && (player.getY() + deltaY >= lowerYCoordinateLimit
                && player.getY() + deltaY <= upperYCoordinateLimit);
    }

    /**
     * Updates the player's position and hitbox on the canvas.This method updates the canvas with
     * the new position of the player and recalculates the hitbox based on the new position. It
     * ensures that the graphical representation of the player and its hitbox are synchronized.
     */
    private void updatePlayerAndHitbox() {
        canvas.updatePlayerPosition(player.getX(), player.getY());
        playerRectangle = new RectF(player.getX() - characterWidth / 2,
                player.getY() - characterHeight / 2,
                player.getX() + characterWidth / 2,
                player.getY() + characterHeight / 2);
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

        // Add current game attempt to the leaderboard if they finished
        if (player.getCurrentHp() > 0) {
            Attempt currentAttempt = new Attempt(player);
            Leaderboard.getInstance().addAttempt(currentAttempt);
        }

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
    public float[] testKeyPress(MovementStrategy movementStrategy, int keyCode) {
        // Retrieve the class-specific movement strategy's speed
        int movementSpeed = movementStrategy.getMovementSpeed();

        switch (keyCode) {
        case android.view.KeyEvent.KEYCODE_DPAD_LEFT:
            if (player.getX() - movementSpeed >= lowerXCoordinateLimit) {
                player.setX(player.getX() - movementSpeed);
            }
            break;
        case android.view.KeyEvent.KEYCODE_DPAD_RIGHT:
            if (player.getX() + movementSpeed + 10 <= upperXCoordinateLimit) {
                player.setX(player.getX() + movementSpeed);
            }
            break;
        case android.view.KeyEvent.KEYCODE_DPAD_UP:
            if (player.getY() - movementSpeed >= lowerYCoordinateLimit) {
                player.setY(player.getY() - movementSpeed);
            }
            break;
        case android.view.KeyEvent.KEYCODE_DPAD_DOWN:
            if (player.getY() + movementSpeed + 10 <= upperYCoordinateLimit) {
                player.setY(player.getY() + movementSpeed);
            }
            break;
        default:
            break;
        }

        return new float[]{player.getX(), player.getY()};
    }

    /**
     * Sets the initial location of the player for testing purposes.
     *
     * @param x The horizontal location of the player.
     * @param y The vertical location of the player.
     */
    public void setXY(float x, float y) {
        player.setX(x);
        player.setY(y);
    }
}
