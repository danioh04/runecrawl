package edu.gatech.cs2340.team33.runecrawl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.cs2340.team33.runecrawl.Model.Enemy;
import edu.gatech.cs2340.team33.runecrawl.Model.EnemyType;
import edu.gatech.cs2340.team33.runecrawl.Model.GameDifficulty;
import edu.gatech.cs2340.team33.runecrawl.Model.Player;
import edu.gatech.cs2340.team33.runecrawl.Model.PlayerType;
import edu.gatech.cs2340.team33.runecrawl.ViewModel.RoomViewModel;

/**
 * This class is designed to test the functionality of the enemy.
 */

public class EnemyTest {
    private static final int MAX_HEALTH = 100;
    private static final int ROOM_BOUNDARY = 200;
    private static final int DAMAGE = 20;
    private Enemy enemy;

    @Before
    public void setUp() {
        Player.initialize("testPlayer", GameDifficulty.EASY, PlayerType.MAGE);
        enemy = new Enemy(EnemyType.SLIME, MAX_HEALTH, 20, 20);
    }

    /**
     * A test to make sure that the enemy stays within the boundaries of the room.
     */
    @Test
    public void enemyShouldMoveWithinRoomBoundaries() {
        RoomViewModel room = new RoomViewModel(0, ROOM_BOUNDARY, 0, ROOM_BOUNDARY);
        enemy.moveRandomly(room);
        boolean inBounds = enemy.getX() >= room.getLowerXCoordinateLimit() &&
                enemy.getX() <= room.getUpperXCoordinateLimit() &&
                enemy.getY() <= room.getUpperYCoordinateLimit() &&
                enemy.getY() >= room.getLowerYCoordinateLimit();
        String failureString = String.format("Enemy is out of bounds \nUpper Bound: (%d, %d) " +
                "\nLower Bound (0,0)\n Enemy Coordinate: (%f,%f)",
                ROOM_BOUNDARY, ROOM_BOUNDARY, enemy.getX(), enemy.getY());
        assertTrue(failureString , inBounds);
    }

    /**
     * A test that makes sure that the enemy position should change after they move.
     */
    @Test
    public void enemyShouldChangePositionAfterMove() {
        enemy.setY(100);
        enemy.setX(100);
        float initialX = enemy.getX();
        float initialY = enemy.getY();
        RoomViewModel room = new RoomViewModel(0, ROOM_BOUNDARY, 0, ROOM_BOUNDARY);
        enemy.moveRandomly(room);
        boolean hasMoved = (initialX != enemy.getX()) || (initialY != enemy.getY());
        assertTrue("Enemy has not Moved.", hasMoved);
    }

    /**
     * A test to make sure an enemy dies if they take damage equivalent to their max health.
     */
    @Test
    public void enemyShouldBeDeadAfterLethalDamage() {
        enemy.receiveDamage(MAX_HEALTH);
        assertFalse("Enemy is still alive.", enemy.isAlive());
    }

    /**
     * A test that looks at the health of the enemy after taking non-lethal damage.
     */
    @Test
    public void enemyHealthShouldDecreaseAfterDamage() {
        enemy.receiveDamage(DAMAGE);
        assertEquals(MAX_HEALTH - DAMAGE, enemy.getCurrentHp());
    }

    /**
     * This tests to see if the slime enemy will have 1 health after taking as much damage as
     * possible minus one.
     */
    @Test
    public void testEnemyTakingDamage() {
        Enemy enemyTest = new Enemy(EnemyType.SLIME, 50, 20, 20);
        int damage = enemyTest.getCurrentHp() - 1;
        enemyTest.receiveDamage(damage);
        assertEquals(enemyTest.getCurrentHp(), 1);
    }

    /**
     * Tests if the slime enemy actually dies.
     */
    @Test
    public void testEnemyDyingSlime() {
        Enemy enemyTest = new Enemy(EnemyType.SLIME, 50, 20, 20);
        int damage = enemyTest.getCurrentHp() + 1;
        enemyTest.receiveDamage(damage);
        assertFalse("Slime is still Alive.", enemyTest.isAlive());
    }
    /**
     * Tests if the orc enemy actually dies.
     */
    @Test
    public void testEnemyDyingOrc() {
        Enemy enemyTest = new Enemy(EnemyType.ORC, 50, 20, 20);
        int damage = enemyTest.getCurrentHp() + 1;
        enemyTest.receiveDamage(damage);
        assertFalse("Orc is Still Alive.", enemyTest.isAlive());
    }
    /**
     * Tests if the werewolf enemy actually dies.
     */
    @Test
    public void testEnemyDyingWerewolf() {
        Enemy enemyTest = new Enemy(EnemyType.WEREWOLF, 50, 20, 20);
        int damage = enemyTest.getCurrentHp() + 1;
        enemyTest.receiveDamage(damage);
        assertFalse("Werewolf is still alive.", enemyTest.isAlive());
    }
    /**
     * Tests if the robot enemy actually dies.
     */
    @Test
    public void testEnemyDyingRobot() {
        Enemy enemyTest = new Enemy(EnemyType.ROBOT, 50, 20, 20);
        int damage = enemyTest.getCurrentHp() + 1;
        enemyTest.receiveDamage(damage);
        assertFalse( "Robot is still alive.", enemyTest.isAlive());
    }

    /**
     * A test to make sure the attributes of the different enemies are different.
     */
    @Test
    public void testEnemyDiffAttributes() {
        EnemyType enemyType1 = EnemyType.SLIME;
        EnemyType enemyType2 = EnemyType.ORC;
        EnemyType enemyType3 = EnemyType.ROBOT;
        EnemyType enemyType4 = EnemyType.WEREWOLF;

        int enemyType1Res = enemyType1.getSpriteResId();
        int enemyType2Res = enemyType2.getSpriteResId();
        int enemyType3Res = enemyType3.getSpriteResId();
        int enemyType4Res = enemyType4.getSpriteResId();

        int enemyType1Damage = enemyType1.getBaseDamageRate();
        int enemyType2Damage = enemyType2.getBaseDamageRate();
        int enemyType3Damage = enemyType3.getBaseDamageRate();
        int enemyType4Damage = enemyType4.getBaseDamageRate();

        assertTrue(enemyType1Res != enemyType2Res && enemyType1Damage != enemyType2Damage);
        assertTrue(enemyType1Res != enemyType3Res && enemyType1Damage != enemyType3Damage);
        assertTrue(enemyType1Res != enemyType4Res && enemyType1Damage != enemyType4Damage);
        assertTrue(enemyType2Res != enemyType3Res && enemyType2Damage != enemyType3Damage);
        assertTrue(enemyType2Res != enemyType4Res && enemyType2Damage != enemyType4Damage);
        assertTrue(enemyType3Res != enemyType4Res && enemyType3Damage != enemyType4Damage);
    }

    /**
     * This tests all four types of enemies and makes sure no speed is equal.
     */
    @Test
    public void testEnemyMovementAttribute() {
        EnemyType enemyType1 = EnemyType.SLIME;
        EnemyType enemyType2 = EnemyType.ORC;
        EnemyType enemyType3 = EnemyType.ROBOT;
        EnemyType enemyType4 = EnemyType.WEREWOLF;

        int enemyType1Speed = enemyType1.getMovementSpeed();
        int enemyType2Speed = enemyType2.getMovementSpeed();
        int enemyType3Speed = enemyType3.getMovementSpeed();
        int enemyType4Speed = enemyType4.getMovementSpeed();

        assertTrue(enemyType1Speed != enemyType2Speed);
        assertTrue(enemyType1Speed != enemyType3Speed);
        assertTrue(enemyType1Speed != enemyType4Speed);
        assertTrue(enemyType2Speed != enemyType3Speed);
        assertTrue(enemyType2Speed != enemyType4Speed);
        assertTrue(enemyType3Speed != enemyType4Speed);
    }

    /**
     * A test to see whether the enemy does move randomly.
     */
    @Test
    public void testEnemyRandomMovement() {
        Enemy enemyTest = new Enemy(EnemyType.SLIME, 50, 20, 20);
        RoomViewModel roomTest = new RoomViewModel(50, 50, 50, 50);
        float x = enemyTest.getX();
        float y = enemyTest.getY();
        enemyTest.moveRandomly(roomTest);
        assertTrue("Enemy has not moved.",x != enemyTest.getX() || y != enemyTest.getY());
    }
}
                   