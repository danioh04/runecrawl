package edu.gatech.cs2340.team33.runecrawl.Model;

/**
 * Represents an enemy in the game.
 * Each enemy has a type and a current hit point (HP) value.
 */
public class Enemy {
    private final EnemyType type;
    private int currentHp;

    /**
     * Constructs an Enemy with a specified type and current HP.
     *
     * @param type      The type of the enemy, as defined in the EnemyType enum.
     * @param currentHp The initial HP of the enemy.
     */
    public Enemy(EnemyType type, int currentHp) {
        this.type = type;
        this.currentHp = currentHp;
    }

    /**
     * Gets the type of the enemy.
     *
     * @return The EnemyType of this enemy.
     */
    public EnemyType getType() {
        return this.type;
    }

    /**
     * Gets the current HP of the enemy.
     *
     * @return The current HP of this enemy.
     */
    public int getCurrentHp() {
        return this.currentHp;
    }

    /**
     * Applies damage to the enemy, reducing its HP.
     * If the damage reduces the HP below zero, it sets the HP to zero.
     *
     * @param damage The amount of damage to be applied to the enemy.
     */
    public void receiveDamage(int damage) {
        this.currentHp -= damage;
        if (this.currentHp < 0) {
            this.currentHp = 0;
        }
    }

    /**
     * Checks if the enemy is still alive.
     *
     * @return True if the enemy's current HP is greater than zero, false otherwise.
     */
    public boolean isAlive() {
        return this.currentHp > 0;
    }
}
