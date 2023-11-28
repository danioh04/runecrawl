package edu.gatech.cs2340.team33.runecrawl.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * This class sets up the canvas for a room. The character is initially
 * drawn onto the screen, and its position is continuously updated.
 */
@SuppressLint("ViewConstructor")
public class CanvasView extends View {
    private final Bitmap character;
    private final List<Bitmap> enemySprites;
    private final List<Float> enemyX;
    private final List<Float> enemyY;
    private final List<Bitmap> potionSprites;
    private final List<Float> potionX;
    private final List<Float> potionY;

    private float playerX;
    private float playerY;

    /**
     * The main constructor for the class.
     *
     * @param currentClass The current state of the application.
     * @param character    The image of the character.
     * @param playerX      The player's X-coordinate.
     * @param playerY      The player's Y-coordinate.
     */
    public CanvasView(Context currentClass, Bitmap character, float playerX, float playerY) {
        super(currentClass);
        this.character = character;
        this.playerX = playerX;
        this.playerY = playerY;

        this.enemySprites = new ArrayList<>();
        this.enemyX = new ArrayList<>();
        this.enemyY = new ArrayList<>();

        this.potionSprites = new ArrayList<>();
        this.potionX = new ArrayList<>();
        this.potionY = new ArrayList<>();
    }

    /**
     * The character is drawn onto the screen.
     *
     * @param canvas The canvas for the screen.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(character, playerX, playerY, null);

        for (int i = 0; i < enemySprites.size(); i++) {
            canvas.drawBitmap(enemySprites.get(i), enemyX.get(i), enemyY.get(i), null);
        }
        
        for (int i = 0; i < potionSprites.size(); i++) {
            canvas.drawBitmap(potionSprites.get(i), potionX.get(i), potionY.get(i), null);
        }
    }

    /**
     * Adds a new enemy to the game with its sprite and position.
     *
     * @param sprite The Bitmap representing the enemy's sprite.
     * @param x      The X coordinate of the enemy's position.
     * @param y      The Y coordinate of the enemy's position.
     */
    public void addEnemy(Bitmap sprite, float x, float y) {
        enemySprites.add(sprite);
        enemyX.add(x);
        enemyY.add(y);
    }

    /**
     * The player's new coordinates are retrieved and updated.
     *
     * @param newPlayerX The player's updated X-coordinate.
     * @param newPlayerY The player's updated Y-coordinate.
     */
    public void updatePlayerPosition(float newPlayerX, float newPlayerY) {
        playerX = newPlayerX;
        playerY = newPlayerY;
        invalidate();
    }

    /**
     * Updates the position of a specific enemy.
     *
     * @param enemyIndex The index of the enemy in the enemySprites list.
     * @param newX       The new X coordinate for the enemy.
     * @param newY       The new Y coordinate for the enemy.
     */
    public void updateEnemyPosition(int enemyIndex, float newX, float newY) {
        enemyX.set(enemyIndex, newX);
        enemyY.set(enemyIndex, newY);
        invalidate();
    }

    /**
     * Adds a new potion to the game with its sprite and position.
     *
     * @param sprite The Bitmap representing the potion's sprite.
     * @param x      The X coordinate of the potion's position.
     * @param y      The Y coordinate of the potion's position.
     */
    public void addPotion(Bitmap sprite, float x, float y) {
        potionSprites.add(sprite);
        potionX.add(x);
        potionY.add(y);
    }

    /**
     * Removes a potion from the canvas.
     *
     * @param x The X coordinate of the potion to be removed.
     * @param y The Y coordinate of the potion to be removed.
     */
    public void removePotion(float x, float y) {
        for (int i = 0; i < potionX.size(); i++) {
            if (potionX.get(i) == x && potionY.get(i) == y) {
                potionSprites.remove(i);
                potionX.remove(i);
                potionY.remove(i);
                invalidate();
                break;
            }
        }
    }
}