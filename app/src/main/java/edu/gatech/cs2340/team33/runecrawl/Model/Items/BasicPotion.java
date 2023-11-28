package edu.gatech.cs2340.team33.runecrawl.Model.Items;

/**
 * Basic implementation of a potion.
 */
public class BasicPotion implements Potion {
    /**
     * Get the health boost provided by the base potion.
     *
     * @return the HP increase value
     */
    @Override
    public int getHealthBoost() {
        return 0;
    }

    /**
     * Returns 0 as the basic potion has no sprite.
     *
     * @return The resource ID of the drawable for this potion.
     */
    @Override
    public int getDrawableResourceId() {
        return 0;
    }
}
