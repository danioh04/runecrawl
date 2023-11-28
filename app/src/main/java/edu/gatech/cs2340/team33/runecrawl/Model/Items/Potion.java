package edu.gatech.cs2340.team33.runecrawl.Model.Items;

/**
 * This interface represents the base component for potions in the game.
 * It defines the essential behavior of a potion, which is to provide a health boost.
 */
public interface Potion {
    /**
     * Retrieves the health boost amount provided by the potion.
     *
     * @return The amount of health points (HP) to be increased.
     */
    int getHealthBoost();

    /**
     * Gets the drawable resource ID for the potion's sprite.
     *
     * @return The resource ID of the drawable for this potion.
     */
    int getDrawableResourceId();
}
