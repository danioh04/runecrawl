package edu.gatech.cs2340.team33.runecrawl.Model;

import edu.gatech.cs2340.team33.runecrawl.R;

/**
 * Represents different player character types in the game.
 * Each player type has an associated default weapon and a sprite resource ID for representation.
 */
public enum PlayerType {
    MAGE(Weapon.WAND, R.drawable.mage),
    WARRIOR(Weapon.SWORD, R.drawable.warrior),
    ARCHER(Weapon.BOW, R.drawable.archer);

    private final Weapon weapon;
    private final int spriteResId;

    /**
     * Constructor for PlayerType enum.
     *
     * @param weapon      The default weapon associated with this player type.
     * @param spriteResId The resource ID for the sprite image representing this player type.
     */
    PlayerType(Weapon weapon, int spriteResId) {
        this.weapon = weapon;
        this.spriteResId = spriteResId;
    }

    /**
     * Retrieve the default weapon associated with this player type.
     *
     * @return The weapon linked to this player type.
     */
    public Weapon getWeapon() {
        return this.weapon;
    }

    /**
     * Retrieve the sprite image resource ID for this player type.
     *
     * @return The resource ID of the sprite.
     */
    public int getSpriteResId() {
        return this.spriteResId;
    }
}
