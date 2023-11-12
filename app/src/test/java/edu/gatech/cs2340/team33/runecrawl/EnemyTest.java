package edu.gatech.cs2340.team33.runecrawl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.cs2340.team33.runecrawl.Model.Enemy;
import edu.gatech.cs2340.team33.runecrawl.Model.EnemyType;
import edu.gatech.cs2340.team33.runecrawl.Model.GameDifficulty;
import edu.gatech.cs2340.team33.runecrawl.Model.Player;
import edu.gatech.cs2340.team33.runecrawl.Model.PlayerType;
import edu.gatech.cs2340.team33.runecrawl.View.Rooms.InitialRoomActivity;
import edu.gatech.cs2340.team33.runecrawl.ViewModel.RoomViewModel;

public class EnemyTest {
    private Enemy enemy;
    @Before
    public void setUp() {
        enemy = new Enemy(EnemyType.SLIME, 100);
        Player.initialize("testPlayer", GameDifficulty.EASY, PlayerType.MAGE);
    }

    @Test
    public void testMoveInBoundry() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        enemy.moveRandomly(room);
        boolean result = enemy.getX() >= room.getLowerXCoordinateLimit() && enemy.getX() <= room.getUpperXCoordinateLimit()
                && enemy.getY() <= room.getUpperYCoordinateLimit() && enemy.getY() >= room.getLowerYCoordinateLimit();
        assertTrue(result);
    }

    @Test
    public void testMove() {
        float x = enemy.getX();
        float y = enemy.getY();
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        enemy.moveRandomly(room);
        boolean result = x != enemy.getX() || y != enemy.getY();
        assertTrue(result);

    }
    @Test
    public void testEnemyAlive() {
        int damage = 100;
        enemy.receiveDamage(damage);
        assertEquals(false, enemy.isAlive());
    }
    @Test
    public void testRecieveDamage() {
        enemy = new Enemy(EnemyType.SLIME, 100);
        int damage = 20;
        enemy.receiveDamage(20);
        assertEquals(80, enemy.getCurrentHp());
    }
}