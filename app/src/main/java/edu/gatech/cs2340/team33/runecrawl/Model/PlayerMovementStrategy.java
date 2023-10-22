package edu.gatech.cs2340.team33.runecrawl.Model;

/**
 * Represents the method(s) every movement strategy should have.
 */
public interface PlayerMovementStrategy {
    /**
     * Currently, every movement strategy should have
     * an implementation of how fast the character should move.
     *
     * @return The speed at which the character should move.
     */
    int movementSpeed();
}
