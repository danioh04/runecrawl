package edu.gatech.cs2340.team33.runecrawl.Objects.General;

import edu.gatech.cs2340.team33.runecrawl.R;

/**
 * Represents different player character types in the game.
 * Each player type has an associated default weapon and a sprite resource ID for representation.
 */
public enum PlayerType {
    // TODO: Create actual drawable assets for each sprite
    MAGE(Weapon.WAND, R.drawable.mageSprite),
    WARRIOR(Weapon.SWORD, R.drawable.warriorSprite),
    ARCHER(Weapon.BOW, R.drawable.archerSprite);

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
        this.spriteResId = spriteResId; // TODO: Use actual drawable assets for each sprite
    }

    /**
     * Retrieve the default weapon associated with this player type.
     *
     * @return The weapon linked to this player type.
     */
    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * Retrieve the sprite image resource ID for this player type.
     *
     * @return The resource ID of the sprite.
     */
    public int getSpriteResId() {
        return spriteResId; // TODO: Use actual drawable assets for each sprite
    }
}
