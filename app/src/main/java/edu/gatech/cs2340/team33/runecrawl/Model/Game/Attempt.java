package edu.gatech.cs2340.team33.runecrawl.Model.Game;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import edu.gatech.cs2340.team33.runecrawl.Model.Player.Player;

/**
 * Represents a single game attempt by a player, including the username, score, and
 * date/time when the attempt was made.
 */
public class Attempt {
    private final String username;
    private final int score;
    private final Date dateTime;

    /**
     * Constructs a new GameAttempt using the provided Player object.
     *
     * @param player The player whose game attempt is being recorded.
     * @throws IllegalArgumentException If the provided player object is null.
     */
    public Attempt(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }

        this.username = player.getUsername();
        this.score = player.getScore();
        this.dateTime = new Date();
    }

    /**
     * Retrieves the username of the player who made this game attempt.
     *
     * @return The username of the player.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Retrieves the score achieved by the player in this game attempt.
     *
     * @return The score of the game attempt.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Retrieves the date and time when this game attempt was made.
     *
     * @return The date and time of the game attempt.
     */
    public String getDateTime() {
        DateFormat df = DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.US);
        return df.format(this.dateTime);
    }

    /**
     * Returns a string representation of the game attempt, including the username,
     * score, and date/time, formatted for the US locale.
     *
     * @return A string representation of the game attempt.
     */
    @NonNull
    @Override
    public String toString() {
        return "Attempt: " + this.username
                + ", " + this.score
                + ", " + this.getDateTime();
    }
}
