package edu.gatech.cs2340.team33.runecrawl.Model.RoomStrategies;

import edu.gatech.cs2340.team33.runecrawl.Model.Player.MovementStrategy;

/**
 * Represents the second room's specific implementation of a movement strategy.
 */
public class SecondRoomStrategy implements MovementStrategy {
    /**
     * The specific implementation of a movement strategy's movement speed.
     */
    @Override
    public int getMovementSpeed() {
        return 40;
    }
}
