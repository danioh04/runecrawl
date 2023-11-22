package edu.gatech.cs2340.team33.runecrawl;

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
 * This class is deisgned to test the functionality of the enemy's movement test.
 */
public class EnemyMovementTest {
    private static final int ROOM_BOUNDARY = 200;
    private Enemy enemyTest;

    @Before
    public void setUp() {
        Player.initialize("testPlayer", GameDifficulty.EASY, PlayerType.MAGE);
        enemyTest = new Enemy(EnemyType.ORC, 100, 20, 20);
    }

    /**
     * This tests to make sure that the enemy stays within the left boundary.
     */
    @Test
    public void testLeftBoundary() {
        RoomViewModel room = new RoomViewModel(0, ROOM_BOUNDARY,
                0, ROOM_BOUNDARY);
        enemyTest.moveRandomly(room);
        boolean inbounds = enemyTest.getX() >= room.getLowerXCoordinateLimit();
        assertTrue(inbounds);
    }

    /**
     * This tests to make sure that the enemy stays within the right boundary.
     */
    @Test
    public void testRightBoundary() {
        RoomViewModel room = new RoomViewModel(0, ROOM_BOUNDARY,
                0, ROOM_BOUNDARY);
        enemyTest.moveRandomly(room);
        boolean inbounds = enemyTest.getX() <= room.getUpperXCoordinateLimit();
        assertTrue(inbounds);
    }

    /**
     * This tests to make sure that the enemy stays within the upper boundary.
     */
    @Test
    public void testUpperBoundary() {
        RoomViewModel room = new RoomViewModel(0, ROOM_BOUNDARY,
                0, ROOM_BOUNDARY);
        enemyTest.moveRandomly(room);
        boolean inbounds = enemyTest.getY() <= room.getUpperYCoordinateLimit();
        assertTrue(inbounds);
    }

    /**
     * This tests to make sure that the enemy stays within the lower boundary.
     */
    @Test
    public void testLowerBoundary() {
        RoomViewModel room = new RoomViewModel(0, ROOM_BOUNDARY,
                0, ROOM_BOUNDARY);
        enemyTest.moveRandomly(room);
        boolean inbounds = enemyTest.getY() >= room.getLowerYCoordinateLimit();
        assertTrue(inbounds);
    }
}
