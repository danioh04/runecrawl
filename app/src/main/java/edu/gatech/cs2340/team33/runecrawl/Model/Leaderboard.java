package edu.gatech.cs2340.team33.runecrawl.Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Represents a leaderboard that keeps track of players' game attempts.
 * The leaderboard stores a limited number of top scoring attempts.
 */
public class Leaderboard {
    private static final int MAX_ATTEMPTS = 7;
    private static Leaderboard instance;
    private final PriorityQueue<GameAttempt> attempts;

    /**
     * Private constructor to create a Leaderboard instance.
     * Initializes a priority queue to store game attempts.
     */
    private Leaderboard() {
        // Using a PriorityQueue to efficiently keep top scores
        this.attempts = new PriorityQueue<>(
                MAX_ATTEMPTS, Comparator.comparing(GameAttempt::getScore));
    }

    /**
     * Provides the singleton instance of the Leaderboard.
     *
     * @return The single instance of the Leaderboard.
     */
    public static Leaderboard getInstance() {
        if (instance == null) {
            instance = new Leaderboard();
        }

        return instance;
    }

    /**
     * Retrieves the top game attempts currently stored in the leaderboard.
     *
     * @return A list of top game attempts.
     */
    public List<GameAttempt> getTopAttempts() {
        // Converting to a List to return attempts
        return new ArrayList<>(this.attempts);
    }

    /**
     * Adds a new game attempt to the leaderboard. If the leaderboard already contains the
     * maximum number of attempts, the new attempt will replace the lowest scoring attempt
     * if its score is higher.
     *
     * @param attempt The game attempt to be added to the leaderboard.
     * @throws IllegalArgumentException If the provided attempt is null.
     */
    public void addAttempt(GameAttempt attempt) {
        if (attempt == null) {
            throw new IllegalArgumentException("Attempt cannot be null");
        }

        if (this.attempts.size() < MAX_ATTEMPTS) {
            // If less than MAX_ATTEMPTS, add the attempt
            this.attempts.offer(attempt);
        } else if (this.attempts.peek() != null && attempt.getScore()
                > this.attempts.peek().getScore()) {
            // If the new attempt's score is higher than the lowest in the queue, replace
            this.attempts.poll();
            this.attempts.offer(attempt);
        }
    }
}
