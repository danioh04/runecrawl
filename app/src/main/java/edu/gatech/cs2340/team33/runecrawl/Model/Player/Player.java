package edu.gatech.cs2340.team33.runecrawl.Model.Player;

import edu.gatech.cs2340.team33.runecrawl.Model.Game.Difficulty;
import edu.gatech.cs2340.team33.runecrawl.Model.Items.Potion;

/**
 * Represents a player in the RuneCrawl game.
 * Each player has a unique username, chosen difficulty, type (mage, warrior, archer),
 * and a current health points (HP).
 */
public class Player {
    private static Player instance;
    private final String username;
    private final Difficulty difficulty;
    private final PlayerType type;
    private int currentHp;
    private int score;
    private float x;
    private float y;

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
    private Player(String username, Difficulty difficulty, PlayerType type) {
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
        this.score = 100;
    }

    /**
     * Provides the singleton instance of the Player. Creates the instance if it does not
     * already exist.
     *
     * @param username   Unique username of the player.
     * @param difficulty Chosen difficulty level for the game.
     * @param type       Player's chosen type or character.
     */
    public static void initialize(String username, Difficulty difficulty, PlayerType type) {
        instance = new Player(username, difficulty, type);
    }

    /**
     * Provides the singleton instance of the Player. Throws an exception if the instance
     * is not yet created.
     *
     * @return The single instance of the Player.
     * @throws IllegalStateException If the Player instance has not been initialized yet.
     */
    public static Player getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Player instance has not been initialized."
                    + " Call getInstance with parameters first.");
        }

        return instance;
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
    public Difficulty getDifficulty() {
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
     * Retrieves the player's X-coordinate.
     *
     * @return Player's X-coordinate.
     */
    public float getX() {
        return this.x;
    }

    /**
     * Sets the player's X-coordinate.
     *
     * @param x Player's new X-coordinate.
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Retrieves the player's Y-coordinate.
     *
     * @return Player's Y-coordinate.
     */
    public float getY() {
        return this.y;
    }

    /**
     * Sets the player's Y-coordinate.
     *
     * @param y Player's new Y-coordinate.
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Checks if the player is dead.
     * A player is considered dead if their HP is less than 0.
     *
     * @return true if player is dead, false otherwise.
     */
    public boolean isDead() {
        return this.currentHp <= 0;
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
     * Increases the score of the character when an enemy is killed.
     */
    public void killEnemy() {
        this.score += 10;
    }

    /**
     * Increases the current hit points (HP) of the character by the health boost provided by the
     * potion.
     *
     * @param potion The potion to be consumed. It must not be null and should provide a health
     *               boost value.
     */
    public void drinkPotion(Potion potion) {
        this.currentHp += potion.getHealthBoost();
    }

    /**
     * Decreases the player's score as they take more time to complete the game.
     *
     * @param amount Amount to subtract from the player's score.
     */
    public void decreaseScore(int amount) {
        this.score -= amount;

        if (this.score < 0) {
            this.score = 0;
        }
    }
}
