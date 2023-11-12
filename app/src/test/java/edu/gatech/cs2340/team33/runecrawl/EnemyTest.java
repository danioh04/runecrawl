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

public class EnemyTest {
    private static final int MAX_HEALTH = 100;
    private static final int ROOM_BOUNDARY = 200;
    private static final int DAMAGE = 20;
    private Enemy enemy;

    @Before
    public void setUp() {
        enemy = new Enemy(EnemyType.SLIME, MAX_HEALTH);
        Player.initialize("testPlayer", GameDifficulty.EASY, PlayerType.MAGE);
    }

    @Test
    public void enemyShouldMoveWithinRoomBoundaries() {
        RoomViewModel room = new RoomViewModel(0, ROOM_BOUNDARY, 0, ROOM_BOUNDARY);
        enemy.moveRandomly(room);
        boolean inBounds = enemy.getX() >= room.getLowerXCoordinateLimit() &&
                enemy.getX() <= room.getUpperXCoordinateLimit() &&
                enemy.getY() <= room.getUpperYCoordinateLimit() &&
                enemy.getY() >= room.getLowerYCoordinateLimit();
        assertTrue(inBounds);
    }

    @Test
    public void enemyShouldChangePositionAfterMove() {
        float initialX = enemy.getX();
        float initialY = enemy.getY();
        RoomViewModel room = new RoomViewModel(0, ROOM_BOUNDARY, 0, ROOM_BOUNDARY);
        enemy.moveRandomly(room);
        boolean hasMoved = (initialX != enemy.getX()) || (initialY != enemy.getY());
        assertTrue(hasMoved);
    }

    @Test
    public void enemyShouldBeDeadAfterLethalDamage() {
        enemy.receiveDamage(MAX_HEALTH);
        assertFalse(enemy.isAlive());
    }

    @Test
    public void enemyHealthShouldDecreaseAfterDamage() {
        enemy.receiveDamage(DAMAGE);
        assertEquals(MAX_HEALTH - DAMAGE, enemy.getCurrentHp());
    }
}
