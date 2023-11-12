package edu.gatech.cs2340.team33.runecrawl.Model.Strategies;

import edu.gatech.cs2340.team33.runecrawl.Model.PlayerMovementStrategy;

/**
 * Represents the second room's specific implementation of a movement strategy.
 */
public class SecondRoomStrategy implements PlayerMovementStrategy {
    /**
     * The specific implementation of a movement strategy's movement speed.
     */
    @Override
    public int getMovementSpeed() {
        return 40;
    }
}
