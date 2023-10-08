package edu.gatech.cs2340.team33.runecrawl.Model;

/**
 * Represents a player in the RuneCrawl game.
 * Each player has a unique username, chosen difficulty, type (mage, warrior, archer),
 * and a current health points (HP).
 */
public class Player {
    private final String username;
    private final GameDifficulty difficulty;
    private final PlayerType type;
    private int currentHp;
    private int score;

    /**
     * Constructs a new Player with specified username, difficulty, and type.
     *
     * @param username   Unique username of the player.
     * @param difficulty Chosen difficulty level for the game.
     * @param type       Player's chosen type or character.
     * @throws IllegalArgumentException If the username is null, whitespace, or empty
     * @throws IllegalArgumentException If the difficulty is null
     * @throws IllegalArgumentException If the player type is null
     */
    public Player(String username, GameDifficulty difficulty, PlayerType type) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null, "
                    + "empty, or whitespace");
        }

        if (difficulty == null) {
            throw new IllegalArgumentException("Difficulty cannot be null");
        }

        if (type == null) {
            throw new IllegalArgumentException("Player type cannot be null");
        }

        this.username = username;
        this.difficulty = difficulty;
        this.type = type;
        this.currentHp = difficulty.getStartingHp();
        this.score = 100; // Temporarily start the player's score at 100
    }

    /**
     * Retrieves the player's username.
     *
     * @return Player's username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Retrieves the chosen difficulty level for the game.
     *
     * @return Chosen game difficulty.
     */
    public GameDifficulty getDifficulty() {
        return this.difficulty;
    }

    /**
     * Retrieves the player's type or character.
     *
     * @return Player's type.
     */
    public PlayerType getType() {
        return this.type;
    }

    /**
     * Retrieves the current health points (HP) of the player.
     *
     * @return Current HP of the player.
     */
    public int getCurrentHp() {
        return this.currentHp;
    }

    /**
     * Retrieves the current score of the player. Currently, the score is
     * correlated with the time the player takes to complete the game.
     *
     * @return Current score of the player.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Checks if the player is alive.
     * A player is considered alive if their HP is greater than 0.
     *
     * @return true if player is alive, false otherwise.
     */
    public boolean isAlive() {
        return this.currentHp > 0;
    }

    /**
     * Decreases the player's HP by the specified damage amount.
     * If the damage taken exceeds the current HP, the HP is set to 0.
     *
     * @param damage Amount of damage to subtract from the player's HP.
     */
    public void receiveDamage(int damage) {
        this.currentHp -= damage;

        if (this.currentHp < 0) {
            this.currentHp = 0;
        }
    }

    /**
     * Decrease the player's score as they take more time to complete the game.
     *
     * @throws IllegalArgumentException If the current score is already at 0
     */
    public void decreaseScore() {
        if (this.score <= 0) {
            throw new IllegalArgumentException("Score is already 0 and cannot be decreased");
        }

        this.score -= 1;
    }
}