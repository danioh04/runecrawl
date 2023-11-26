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
 * This class is designed to test the functionality of the enemy's movement test.
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
     * This tests is to determine whether the enemies' random movement will not make it go out of
     * the left boundary determined by its x position.
     */
    @Test
    public void testLeftBoundary() {
        RoomViewModel room = new RoomViewModel(0, ROOM_BOUNDARY,
                0, ROOM_BOUNDARY);
        enemyTest.moveRandomly(room);
        boolean inbounds = enemyTest.getX() >= room.getLowerXCoordinateLimit();
        assertTrue("Left Boundary: " + room.getLowerXCoordinateLimit() + " Enemy X: "
                + enemyTest.getX() + "\n",inbounds);
    }

    /**
     * This tests is to determine whether the enemies' random movement will not make it go out of
     * the right boundary determined by its x position.
     */
    @Test
    public void testRightBoundary() {
        RoomViewModel room = new RoomViewModel(0, ROOM_BOUNDARY,
                0, ROOM_BOUNDARY);
        enemyTest.moveRandomly(room);
        boolean inbounds = enemyTest.getX() <= room.getUpperXCoordinateLimit();
        assertTrue("Right Boundary: " + room.getUpperXCoordinateLimit() + " Enemy X: "
                + enemyTest.getX() + "\n",inbounds);
    }

    /**
     * This tests is to determine whether the enemies' random movement will not make it go out of
     * the upper boundary determined by its y position.
     */
    @Test
    public void testUpperBoundary() {
        RoomViewModel room = new RoomViewModel(0, ROOM_BOUNDARY,
                0, ROOM_BOUNDARY);
        enemyTest.moveRandomly(room);
        boolean inbounds = enemyTest.getY() <= room.getUpperYCoordinateLimit();
        assertTrue("Upper Boundary: " + room.getUpperYCoordinateLimit() + " Enemy Y: "
                + enemyTest.getY() + "\n",inbounds);
    }

    /**
     * This tests is to determine whether the enemies' random movement will not make it go out of
     * the lower boundary determined by its y position.
     */
    @Test
    public void testLowerBoundary() {
        RoomViewModel room = new RoomViewModel(0, ROOM_BOUNDARY,
                0, ROOM_BOUNDARY);
        enemyTest.moveRandomly(room);
        boolean inbounds = enemyTest.getY() >= room.getLowerYCoordinateLimit();
        assertTrue("Lower Boundary: " + room.getLowerYCoordinateLimit() + " Enemy Y: "
                + enemyTest.getY() + "\n",inbounds);
    }
}
