package edu.gatech.cs2340.team33.runecrawl.Model.Strategies;

import edu.gatech.cs2340.team33.runecrawl.Model.PlayerMovementStrategy;

/**
 * Represents the initial room's specific implementation of a movement strategy.
 */
public class InitialRoomStrategy implements PlayerMovementStrategy {
    private int moveDistance;

    /**
     * The specific implementation of a movement strategy's movement speed.
     */
    @Override
    public int movementSpeed() {
        moveDistance = 30;
        return moveDistance;
    }
}
