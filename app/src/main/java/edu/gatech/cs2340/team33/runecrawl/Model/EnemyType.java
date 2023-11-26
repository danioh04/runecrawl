package edu.gatech.cs2340.team33.runecrawl.Model;

import edu.gatech.cs2340.team33.runecrawl.R;

/**
 * Represents the different types of enemies in the game.
 * Each enemy type has a unique sprite resource ID and a damage rate.
 */
public enum EnemyType {
    SLIME(R.drawable.slime, 2, 30),
    ROBOT(R.drawable.robot, 5, 40),
    ORC(R.drawable.orc, 8, 20),
    WEREWOLF(R.drawable.werewolf, 10, 45);

    private final int spriteResId;
    private final int baseDamageRate;
    private final int movementSpeed;

    /**
     * Constructor for the enum.
     *
     * @param spriteResId    The resource ID of the enemy's sprite.
     * @param baseDamageRate The base damage rate of the enemy.
     * @param movementSpeed  The movement speed of the enemy.
     */
    EnemyType(int spriteResId, int baseDamageRate, int movementSpeed) {
        this.spriteResId = spriteResId;
        this.baseDamageRate = baseDamageRate;
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
     * Gets the base damage rate of the enemy.
     *
     * @return The base damage rate of the enemy.
     */
    public int getBaseDamageRate() {
        return this.baseDamageRate;
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
