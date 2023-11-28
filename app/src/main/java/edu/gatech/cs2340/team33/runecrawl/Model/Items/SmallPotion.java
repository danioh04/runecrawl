package edu.gatech.cs2340.team33.runecrawl.Model.Items;

import edu.gatech.cs2340.team33.runecrawl.R;

/**
 * Decorator for a small potion that increases HP by 5.
 * This class adds a small health boost to the existing potion effects.
 */
public class SmallPotion extends PotionDecorator {
    /**
     * Constructor for the SmallPotion decorator.
     *
     * @param decoratedPotion The potion object that is being decorated with the small potion effect.
     */
    public SmallPotion(Potion decoratedPotion) {
        super(decoratedPotion);
    }

    /**
     * Enhances the potion's health boost by adding 5 HP to the existing boost.
     *
     * @return The total HP increase after adding the small potion effect.
     */
    @Override
    public int getHealthBoost() {
        return super.getHealthBoost() + 5;
    }

    /**
     * Gets the drawable resource ID for the small potion's sprite.
     *
     * @return The resource ID of the drawable for this potion.
     */
    @Override
    public int getDrawableResourceId() {
        return R.drawable.small_potion;
    }
}
