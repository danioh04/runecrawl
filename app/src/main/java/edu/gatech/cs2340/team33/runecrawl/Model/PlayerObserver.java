package edu.gatech.cs2340.team33.runecrawl.Model;

/**
 * Represents the method(s) every observer should have.
 */
public interface PlayerObserver {
    /**
     * Currently, every observer should have
     * an implementation of what follows a collision.
     */
    void collisionOccurred();
}
