package edu.gatech.cs2340.team33.runecrawl.Model.Items;

import edu.gatech.cs2340.team33.runecrawl.R;

/**
 * Decorator for a big potion that increases HP by 10.
 * This class adds a significant health boost to the base potion effect.
 */
public class MediumPotion extends PotionDecorator {
    /**
     * Constructor for the MediumPotion decorator.
     *
     * @param decoratedPotion The potion object to be enhanced with the big potion effect.
     */
    public MediumPotion(Potion decoratedPotion) {
        super(decoratedPotion);
    }

    /**
     * Enhances the potion's health boost by adding 10 HP to the existing boost.
     *
     * @return The total HP increase after adding the big potion effect.
     */
    @Override
    public int getHealthBoost() {
        return super.getHealthBoost() + 10;
    }

    /**
     * Gets the drawable resource ID for the medium potion's sprite.
     *
     * @return The resource ID of the drawable for this potion.
     */
    @Override
    public int getSpriteResId() {
        return R.drawable.medium_potion;
    }
}
