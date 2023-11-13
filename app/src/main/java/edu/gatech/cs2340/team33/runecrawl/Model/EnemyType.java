package edu.gatech.cs2340.team33.runecrawl.Model;

import edu.gatech.cs2340.team33.runecrawl.R;

/**
 * Represents the different types of enemies in the game.
 * Each enemy type has a unique sprite resource ID and a damage rate.
 */
public enum EnemyType {
    SLIME(R.drawable.slime, calculateDamage("SLIME", Player.getInstance().getDifficulty()), 30),
    ROBOT(R.drawable.robot, calculateDamage("ROBOT", Player.getInstance().getDifficulty()), 40),
    ORC(R.drawable.orc, calculateDamage("ORC", Player.getInstance().getDifficulty()), 20),
    WEREWOLF(R.drawable.werewolf, calculateDamage("WEREWOLF",
            Player.getInstance().getDifficulty()), 45);

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
     * Calculates the damage of an enemy based on its type and difficulty.
     * This method uses a switch case to determine the damage corresponding to each enemy type
     * and game difficulty.
     *
     * @param enemyType      The type of the enemy for which the initial HP is to be calculated.
     * @param gameDifficulty The difficulty of the game.
     * @return The calculated damage of the enemy.
     * @throws IllegalArgumentException If the enemy type or game difficulty is unknown.
     */
    public static int calculateDamage(String enemyType, GameDifficulty gameDifficulty) {
        switch (gameDifficulty) {
        case EASY:
            switch (enemyType) {
            case "SLIME":
                return 1;
            case "ROBOT":
                return 5;
            case "ORC":
                return 10;
            case "WEREWOLF":
                return 15;
            default:
                throw new IllegalArgumentException("Unknown enemy type: " + enemyType);
            }
        case MEDIUM:
            switch (enemyType) {
            case "SLIME":
                return 5;
            case "ROBOT":
                return 10;
            case "ORC":
                return 15;
            case "WEREWOLF":
                return 20;
            default:
                throw new IllegalArgumentException("Unknown enemy type: " + enemyType);
            }
        case HARD:
            switch (enemyType) {
            case "SLIME":
                return 10;
            case "ROBOT":
                return 15;
            case "ORC":
                return 20;
            case "WEREWOLF":
                return 25;
            default:
                throw new IllegalArgumentException("Unknown enemy type: " + enemyType);
            }
        default:
            throw new IllegalArgumentException("Unknown difficulty type: " + gameDifficulty);
        }
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
