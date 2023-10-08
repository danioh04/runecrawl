package edu.gatech.cs2340.team33.runecrawl.Model;

/**
 * Represents the different difficulty levels in the game.
 * Each difficulty level has a specific starting HP and enemy damage rate.
 */
public enum GameDifficulty {
    EASY(100, 5),
    MEDIUM(80, 10),
    HARD(60, 15);

    private final int startingHp;
    private final int enemyDamageRate;

    /**
     * Constructor for GameDifficulty enum.
     *
     * @param startingHp      Initial health points assigned to the player for this difficulty.
     * @param enemyDamageRate Damage rate of the enemy for this difficulty.
     * @throws IllegalArgumentException If the provided starting HP is <= 0.
     */
    GameDifficulty(int startingHp, int enemyDamageRate) {
        if (startingHp <= 0) {
            throw new IllegalArgumentException("Starting HP cannot be <= 0");
        }

        this.startingHp = startingHp;
        this.enemyDamageRate = enemyDamageRate;
    }

    /**
     * Retrieve the starting HP for this difficulty level.
     *
     * @return The starting hit points for the player.
     */
    public int getStartingHp() {
        return startingHp;
    }

    /**
     * Retrieve the enemy damage rate for this difficulty level.
     *
     * @return The rate at which enemies damage the player.
     */
    public int getEnemyDamageRate() {
        return enemyDamageRate;
    }
}
