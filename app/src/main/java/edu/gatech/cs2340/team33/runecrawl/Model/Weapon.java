package edu.gatech.cs2340.team33.runecrawl.Model;

/**
 * Represents different weapon types in the game.
 * Each weapon has a unique name, damage rate, and damage range.
 */
public enum Weapon {
    WAND("wand", 10, 5),
    SWORD("sword", 15, 7),
    BOW("bow", 12, 6);

    private final String name;
    private final int damageRate;
    private final int damageRange;

    /**
     * Constructor for Weapon enum.
     *
     * @param name        Name of the weapon.
     * @param damageRate  The average damage the weapon can deal.
     * @param damageRange The damage infliction radius of the weapon.
     */
    Weapon(String name, int damageRate, int damageRange) {
        this.name = name;
        this.damageRate = damageRate;
        this.damageRange = damageRange;
    }

    /**
     * Retrieve the name of this weapon.
     *
     * @return The weapon's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieve the average damage this weapon can deal.
     *
     * @return The weapon's damage rate.
     */
    public int getDamageRate() {
        return this.damageRate;
    }

    /**
     * Retrieve the radius in which this weapon can inflict damage.
     *
     * @return The damage infliction radius of the weapon.
     */
    public int getDamageRange() {
        return this.damageRange;
    }
}
