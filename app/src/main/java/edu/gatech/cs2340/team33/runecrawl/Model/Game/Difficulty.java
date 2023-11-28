package edu.gatech.cs2340.team33.runecrawl.Model.Game;

import androidx.annotation.NonNull;

/**
 * Represents the different difficulty levels in the game.
 * Each difficulty level has a specific starting HP and enemy damage rate.
 */
public enum Difficulty {
    EASY(100, 1),
    MEDIUM(80, 1.5),
    HARD(60, 1.75);

    private final int startingHp;
    private final double enemyDamageMultiplier;

    /**
     * Constructor for GameDifficulty enum.
     *
     * @param startingHp            Initial HP assigned to the player for this difficulty.
     * @param enemyDamageMultiplier Damage multiplier of the enemy for this difficulty.
     * @throws IllegalArgumentException If the provided starting HP is <= 0.
     */
    Difficulty(int startingHp, double enemyDamageMultiplier) {
        if (startingHp <= 0) {
            throw new IllegalArgumentException("Starting HP cannot be <= 0");
        }

        this.startingHp = startingHp;
        this.enemyDamageMultiplier = enemyDamageMultiplier;
    }

    /**
     * Retrieve the starting HP for this difficulty level.
     *
     * @return The starting hit points for the player.
     */
    public int getStartingHp() {
        return this.startingHp;
    }

    /**
     * Retrieve the enemy damage rate for this difficulty level.
     *
     * @return The rate at which enemies damage the player.
     */
    public double getEnemyDamageMultiplier() {
        return this.enemyDamageMultiplier;
    }

    /**
     * Returns a string representation of the game difficulty.
     * This method overrides the default toString method to provide
     * a human-readable name for each difficulty level.
     *
     * @return A string representing the difficulty level. Possible values are:
     * "Easy" for EASY,
     * "Med" for MEDIUM,
     * "Hard" for HARD.
     * @throws IllegalArgumentException if the difficulty level is not recognized.
     */
    @Override
    @NonNull
    public String toString() {
        switch (this) {
        case EASY:
            return "Easy";
        case MEDIUM:
            return "Med";
        case HARD:
            return "Hard";
        default:
            throw new IllegalArgumentException("Unexpected value: " + this);
        }
    }
}
