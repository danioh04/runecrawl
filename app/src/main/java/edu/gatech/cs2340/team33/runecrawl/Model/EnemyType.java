package edu.gatech.cs2340.team33.runecrawl.Model;

import edu.gatech.cs2340.team33.runecrawl.R;

/**
 * Represents the different types of enemies in the game.
 * Each enemy type has a unique sprite resource ID and a damage rate.
 */
public enum EnemyType {
    SLIME(R.drawable.slime, 5, 30),
    SKELETON(R.drawable.skeleton, 10, 40),
    ORC(R.drawable.orc, 15, 20),
    WEREWOLF(R.drawable.werewolf, 20, 45);

    private final int spriteResId;
    private final int damageRate;
    private final int speed;

    /**
     * Constructor for the enum.
     *
     * @param spriteResId The resource ID of the enemy's sprite.
     * @param damageRate  The damage rate of the enemy.
     * @param speed       The movement speed of the enemy.
     */
    EnemyType(int spriteResId, int damageRate, int speed) {
        this.spriteResId = spriteResId;
        this.damageRate = damageRate;
        this.speed = speed;
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
    public int getSpeed() {
        return speed;
    }
}
