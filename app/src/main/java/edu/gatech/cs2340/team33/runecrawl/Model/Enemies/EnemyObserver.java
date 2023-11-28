package edu.gatech.cs2340.team33.runecrawl.Model.Enemies;

/**
 * Represents the method(s) every observer should have.
 */
public interface EnemyObserver {
    /**
     * Currently, every observer should have an implementation of what follows a
     * collision with an enemy.
     *
     * @param enemy The enemy that the player has collided with.
     */
    void playerCollisionOccurred(Enemy enemy);
}
