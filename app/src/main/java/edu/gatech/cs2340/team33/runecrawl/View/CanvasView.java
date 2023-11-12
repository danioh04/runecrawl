package edu.gatech.cs2340.team33.runecrawl.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

/**
 * This class sets up the canvas for a room. The character is initially
 * drawn onto the screen, and its position is continuously updated.
 */
@SuppressLint("ViewConstructor")
public class CanvasView extends View {
    private final Bitmap character;
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
    }

    /**
     * The player's new coordinates are retrieved and updated.
     *
     * @param newPlayerX The player's updated X-coordinate.
     * @param newPlayerY The player's updated Y-coordinate.
     */
    public void updatePosition(float newPlayerX, float newPlayerY) {
        playerX = newPlayerX;
        playerY = newPlayerY;
        invalidate();
    }
}
