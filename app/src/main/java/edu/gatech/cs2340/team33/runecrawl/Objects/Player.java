package edu.gatech.cs2340.team33.runecrawl.Objects;

import edu.gatech.cs2340.team33.runecrawl.Objects.General.GameDifficulty;
import edu.gatech.cs2340.team33.runecrawl.Objects.General.PlayerType;

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

    /**
     * Constructs a new Player with specified username, difficulty, and type.
     *
     * @param username   Unique username of the player.
     * @param difficulty Chosen difficulty level for the game.
     * @param type       Player's chosen type or character.
     * @throws IllegalArgumentException If the username is null, whitespace, or empty
     */
    public Player(String username, GameDifficulty difficulty, PlayerType type) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null, "
                    + "empty, or whitespace");
        }

        this.username = username;
        this.difficulty = difficulty;
        this.type = type;
        this.currentHp = difficulty.getStartingHp();
    }

    /**
     * Retrieves the player's username.
     *
     * @return Player's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the chosen difficulty level for the game.
     *
     * @return Chosen game difficulty.
     */
    public GameDifficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Retrieves the player's type or character.
     *
     * @return Player's type.
     */
    public PlayerType getType() {
        return type;
    }

    /**
     * Retrieves the current health points (HP) of the player.
     *
     * @return Current HP of the player.
     */
    public int getCurrentHp() {
        return currentHp;
    }

    /**
     * Checks if the player is alive.
     * A player is considered alive if their HP is greater than 0.
     *
     * @return true if player is alive, false otherwise.
     */
    public boolean isAlive() {
        return currentHp > 0;
    }

    /**
     * Decreases the player's HP by the specified damage amount.
     * If the damage taken exceeds the current HP, the HP is set to 0.
     *
     * @param damage Amount of damage to subtract from the player's HP.
     */
    public void receiveDamage(int damage) {
        this.currentHp -= damage;

        if (currentHp < 0) {
            currentHp = 0;
        }
    }
}