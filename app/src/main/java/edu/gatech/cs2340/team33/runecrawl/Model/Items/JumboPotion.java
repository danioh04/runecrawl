package edu.gatech.cs2340.team33.runecrawl.Model.Items;

import edu.gatech.cs2340.team33.runecrawl.R;

/**
 * Decorator for a jumbo potion that increases HP by 20.
 * This class provides a substantial health boost, significantly enhancing the base potion effect.
 */
public class JumboPotion extends PotionDecorator {
    /**
     * Constructor for the JumboPotion decorator.
     *
     * @param decoratedPotion The potion object to be supercharged with the jumbo potion effect.
     */
    public JumboPotion(Potion decoratedPotion) {
        super(decoratedPotion);
    }

    /**
     * Augments the potion's health boost by adding 20 HP to the existing boost.
     *
     * @return The total HP increase after applying the jumbo potion effect.
     */
    @Override
    public int getHealthBoost() {
        return super.getHealthBoost() + 20;
    }

    /**
     * Gets the drawable resource ID for the jumbo potion's sprite.
     *
     * @return The resource ID of the drawable for this potion.
     */
    @Override
    public int getDrawableResourceId() {
        return R.drawable.jumbo_potion;
    }
}

