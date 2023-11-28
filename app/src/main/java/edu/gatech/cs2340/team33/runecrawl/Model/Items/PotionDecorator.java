package edu.gatech.cs2340.team33.runecrawl.Model.Items;

/**
 * Abstract decorator class for potions.
 * It follows the decorator pattern to dynamically add responsibilities to potion objects.
 * This class should be extended by specific potion decorators to enhance the potion effects.
 */
public abstract class PotionDecorator implements Potion {
    protected Potion decoratedPotion;

    /**
     * Constructor for the PotionDecorator.
     *
     * @param decoratedPotion The potion object to be decorated.
     */
    public PotionDecorator(Potion decoratedPotion) {
        this.decoratedPotion = decoratedPotion;
    }

    /**
     * Gets the cumulative health boost from the base potion and any additional effects
     * added by this decorator.
     *
     * @return The total health points (HP) increase provided by the potion and its decorators.
     */
    @Override
    public int getHealthBoost() {
        return decoratedPotion.getHealthBoost();
    }
}
