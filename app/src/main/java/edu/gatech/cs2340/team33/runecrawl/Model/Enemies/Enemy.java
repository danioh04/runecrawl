package edu.gatech.cs2340.team33.runecrawl.Model.Enemies;

import android.view.KeyEvent;

import edu.gatech.cs2340.team33.runecrawl.ViewModel.RoomViewModel;

/**
 * Represents an enemy in the game.
 * Each enemy has a type and a current hit point (HP) value.
 */
public class Enemy {
    private final EnemyType type;
    private final float width;
    private final float height;
    private float x;
    private float y;
    private int currentHp;

    /**
     * Constructs an Enemy with a specified type and current HP.
     *
     * @param type      The type of the enemy, as defined in the EnemyType enum.
     * @param currentHp The initial HP of the enemy.
     * @param width     The width of the enemy.
     * @param height    The height of the enemy.
     */
    public Enemy(EnemyType type, int currentHp, int width, int height) {
        this.type = type;
        this.currentHp = currentHp;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the width of the enemy.
     *
     * @return The width of the enemy.
     */
    public float getWidth() {
        return this.width;
    }

    /**
     * Gets the height of the enemy.
     *
     * @return The height of the enemy.
     */
    public float getHeight() {
        return this.height;
    }

    /**
     * Gets the type of the enemy.
     *
     * @return The EnemyType of this enemy.
     */
    public EnemyType getType() {
        return this.type;
    }

    /**
     * Gets the current HP of the enemy.
     *
     * @return The current HP of this enemy.
     */
    public int getCurrentHp() {
        return this.currentHp;
    }

    /**
     * Gets the damage rate of the enemy based on its type.
     *
     * @return The damage rate of this enemy.
     */
    public int getBaseDamageRate() {
        return this.type.getBaseDamageRate();
    }

    /**
     * Gets the movement speed of the enemy based on its type.
     *
     * @return The movement speed of this enemy.
     */
    public int getMovementSpeed() {
        return this.type.getMovementSpeed();
    }

    /**
     * Gets the x-coordinate value.
     *
     * @return The x-coordinate value of this enemy.
     */
    public float getX() {
        return this.x;
    }

    /**
     * Sets the x-coordinate value.
     *
     * @param x The new x-coordinate to move to.
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate value.
     *
     * @return The y-coordinate value of this enemy.
     */
    public float getY() {
        return this.y;
    }

    /**
     * Sets the y-coordinate value.
     *
     * @param y The new y-coordinate to move to.
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Applies damage to the enemy, reducing its HP.
     * If the damage reduces the HP below zero, it sets the HP to zero.
     *
     * @param damage The amount of damage to be applied to the enemy.
     */
    public void receiveDamage(int damage) {
        this.currentHp -= damage;
        if (this.currentHp < 0) {
            this.currentHp = 0;
        }
    }

    /**
     * Checks if the enemy is still alive.
     *
     * @return True if the enemy's current HP is greater than zero, false otherwise.
     */
    public boolean isAlive() {
        return this.currentHp > 0;
    }

    /**
     * Moves the enemy randomly within the room's boundaries.
     * This method generates a random direction and moves the enemy
     * according to its movement speed. It ensures the enemy does not move
     * outside the room's boundaries as defined in the RoomViewModel.
     *
     * @param room The RoomViewModel instance containing the room's boundary details.
     * @throws IllegalStateException if an invalid direction is generated.
     */
    public void moveRandomly(RoomViewModel room) {
        // Generate a random direction code between [19, 22]
        int direction = 19 + (int) (Math.random() * 4);

        // Adjust the x or y coordinate depending on the generated direction code
        switch (direction) {
        case KeyEvent.KEYCODE_DPAD_UP:
            this.y -= this.getMovementSpeed();
            break;
        case KeyEvent.KEYCODE_DPAD_DOWN:
            this.y += this.getMovementSpeed();
            break;
        case KeyEvent.KEYCODE_DPAD_LEFT:
            this.x -= this.getMovementSpeed();
            break;
        case KeyEvent.KEYCODE_DPAD_RIGHT:
            this.x += this.getMovementSpeed();
            break;
        default:
            throw new IllegalStateException("Invalid direction: " + direction);
        }

        // Clamp the x and y coordinates within the range
        this.x = Math.max(Math.min(this.x, room.getUpperXCoordinateLimit()),
                room.getLowerXCoordinateLimit());
        this.y = Math.max(Math.min(this.y, room.getUpperYCoordinateLimit()),
                room.getLowerYCoordinateLimit());
    }
}
