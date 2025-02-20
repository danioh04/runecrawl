package edu.gatech.cs2340.team33.runecrawl.Model.Enemies;

/**
 * A factory class for creating Enemy instances.
 * It provides a method to create enemies of different types with their initial HP.
 */
public class EnemyFactory {
    /**
     * Creates an Enemy instance of the specified type.
     * The initial HP of the enemy is determined based on its type.
     *
     * @param type The type of the enemy to be created, as defined in the EnemyType enum.
     * @return An Enemy instance with the specified type and its corresponding initial HP.
     */
    public static Enemy createEnemy(EnemyType type) {
        int initialHp = calculateInitialHp(type);
        int width = getWidth(type);
        int height = getHeight(type);
        return new Enemy(type, initialHp, width, height);
    }

    /**
     * Gets the width of an enemy based on its type.
     * This method uses a switch case to determine the width corresponding to each enemy type.
     *
     * @param type The type of the enemy for which the initial HP is to be calculated.
     * @return The pre-calculated width of the enemy.
     * @throws IllegalArgumentException If the enemy type is unknown.
     */
    private static int getWidth(EnemyType type) {
        switch (type) {
        case SLIME:
            return 129;
        case ROBOT:
            return 94;
        case ORC:
            return 162;
        case WEREWOLF:
            return 204;
        case BOSS:
            return 100;
        default:
            throw new IllegalArgumentException("Unknown enemy type: " + type);
        }
    }

    /**
     * Gets the height of an enemy based on its type.
     * This method uses a switch case to determine the width corresponding to each enemy type.
     *
     * @param type The type of the enemy for which the initial HP is to be calculated.
     * @return The pre-calculated height of the enemy.
     * @throws IllegalArgumentException If the enemy type is unknown.
     */
    private static int getHeight(EnemyType type) {
        switch (type) {
        case SLIME:
            return 88;
        case ROBOT:
            return 204;
        case ORC:
            return 160;
        case WEREWOLF:
            return 228;
        case BOSS:
            return 132;
        default:
            throw new IllegalArgumentException("Unknown enemy type: " + type);
        }
    }

    /**
     * Calculates the initial HP of an enemy based on its type.
     * This method uses a switch case to determine the HP corresponding to each enemy type.
     *
     * @param type The type of the enemy for which the initial HP is to be calculated.
     * @return The calculated initial HP of the enemy.
     * @throws IllegalArgumentException If the enemy type is unknown.
     */
    private static int calculateInitialHp(EnemyType type) {
        switch (type) {
        case SLIME:
            return 20;
        case ROBOT:
            return 30;
        case ORC:
            return 50;
        case WEREWOLF:
            return 70;
        case BOSS:
            return 150;
        default:
            throw new IllegalArgumentException("Unknown enemy type: " + type);
        }
    }
}
