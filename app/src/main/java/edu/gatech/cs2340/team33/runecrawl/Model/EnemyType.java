package edu.gatech.cs2340.team33.runecrawl.Model;

import edu.gatech.cs2340.team33.runecrawl.R;

/**
 * Represents the different types of enemies in the game.
 * Each enemy type has a unique sprite resource ID and a damage rate.
 */
public enum EnemyType {
    SLIME(R.drawable.slime, 5, 30),
    ROBOT(R.drawable.robot, 10, 40),
    ORC(R.drawable.orc, 15, 20),
    WEREWOLF(R.drawable.werewolf, 20, 45);

    private final int spriteResId;
    private final int damageRate;
    private final int movementSpeed;

    /**
     * Constructor for the enum.
     *
     * @param spriteResId   The resource ID of the enemy's sprite.
     * @param damageRate    The damage rate of the enemy.
     * @param movementSpeed The movement speed of the enemy.
     */
    EnemyType(int spriteResId, int damageRate, int movementSpeed) {
        this.spriteResId = spriteResId;
        this.damageRate = damageRate;
        this.movementSpeed = movementSpeed;
    }

    /**
     * Gets the sprite resource ID of the enemy.
     *
     * @return The resource ID of the enemy's sprite.
     */
    public int getSpriteResId() {
        return this.spriteResId;
    }

    /**
     * Gets the damage rate of the enemy.
     *
     * @return The damage rate of the enemy.
     */
    public int getDamageRate() {
        return this.damageRate;
    }

    /**
     * Gets the movement speed of the enemy.
     *
     * @return The movement speed of the enemy.
     */
    public int getMovementSpeed() {
        return this.movementSpeed;
    }
}
