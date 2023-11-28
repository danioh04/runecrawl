package edu.gatech.cs2340.team33.runecrawl.Model.Player;

/**
 * Represents the method(s) every movement strategy should have.
 */
public interface MovementStrategy {
    /**
     * Currently, every movement strategy should have
     * an implementation of how fast the character should move.
     *
     * @return The speed at which the character should move.
     */
    int getMovementSpeed();
}
