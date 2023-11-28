package edu.gatech.cs2340.team33.runecrawl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.view.KeyEvent;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.cs2340.team33.runecrawl.Model.Game.Difficulty;
import edu.gatech.cs2340.team33.runecrawl.Model.Player.MovementStrategy;
import edu.gatech.cs2340.team33.runecrawl.Model.Player.Player;
import edu.gatech.cs2340.team33.runecrawl.Model.Player.PlayerType;
import edu.gatech.cs2340.team33.runecrawl.Model.RoomStrategies.InitialRoomStrategy;
import edu.gatech.cs2340.team33.runecrawl.Model.RoomStrategies.SecondRoomStrategy;
import edu.gatech.cs2340.team33.runecrawl.Model.RoomStrategies.ThirdRoomStrategy;
import edu.gatech.cs2340.team33.runecrawl.ViewModel.RoomViewModel;


public class Sprint5Tests {
    @Before
    public void setUp() {
        Player.initialize("testPlayer", Difficulty.EASY, PlayerType.MAGE);
    }

    /**
     * This tests the left movement command of the player.
     */
    @Test
    public void testLeft() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        MovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_LEFT);
        assertTrue("Player did not Move Left.",coordinates[0] < x);
        assertEquals(coordinates[1], y, 0.0);
    }

    /**
     * This tests the right movement command of the player.
     */
    @Test
    public void testRight() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        MovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_RIGHT);
        assertTrue("Player did not move Right.",coordinates[0] > x);
        assertEquals(coordinates[1], y, 0.0);
    }

    /**
     * This tests the up movement command of the player
     */
    @Test
    public void testUp() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        MovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_UP);
        assertEquals(coordinates[0], x, 0.0);
        assertTrue("Player did not move Up.", coordinates[1] < y);
    }

    /**
     * This tests the down movement command of the player
     */
    @Test
    public void testDown() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        MovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_DOWN);
        assertEquals(coordinates[0], x, 0.0);
        assertTrue("Player did not move down.", coordinates[1] > y);
    }

    /**
     * This tests the left wall to make sure the player cannot pass that point.
     */
    @Test
    public void testLeftWall() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        MovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float y = 50;
        float[] coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_LEFT);
        for (int i = 0; i < 10; i++) {
            coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_LEFT);
        }
        assertTrue("Player is out of bounds",coordinates[0] >= 0);
        assertEquals(coordinates[1], y, 0.0);
    }

    /**
     * This tests the right wall to make sure the player cannot pass that point.
     */
    @Test
    public void testRightWall() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        MovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float y = 50;
        float[] coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_RIGHT);
        for (int i = 0; i < 10; i++) {
            coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_RIGHT);
        }
        assertTrue("Player is out of bounds",coordinates[0] <= 200);
        assertEquals(coordinates[1], y, 0.0);
    }

    /**
     * This tests the upper wall to make sure the player cannot pass that point.
     */
    @Test
    public void testUpperWall() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        MovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float[] coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_UP);
        for (int i = 0; i < 10; i++) {
            coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_UP);
        }
        assertEquals(coordinates[0], x, 0.0);
        assertTrue("Player is out of bounds",coordinates[1] >= 0);
    }

    /**
     * This tests the lower wall to make sure the player cannot pass that point.
     */
    @Test
    public void testLowerWall() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        MovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float[] coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_DOWN);
        for (int i = 0; i < 10; i++) {
            coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_DOWN);
        }
        assertEquals(coordinates[0], x, 0.0);
        assertTrue("Player is out of bounds",coordinates[1] <= 200);
    }

    /**
     * This tests the player movement for the initial room.
     */
    @Test
    public void testInitialMovementStrategy() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        MovementStrategy initialRoomStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coordinates = room.testKeyPress(initialRoomStrategy, KeyEvent.KEYCODE_DPAD_DOWN);
        assertEquals(y + 50, coordinates[1], 0.0);
        coordinates = room.testKeyPress(initialRoomStrategy, KeyEvent.KEYCODE_DPAD_RIGHT);
        assertEquals(x + 50, coordinates[0], 0.0);
    }

    /**
     * This tests the player movement for the second room.
     */
    @Test
    public void testSecondMovementStrategy() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        MovementStrategy secondRoomStrategy = new SecondRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coordinates = room.testKeyPress(secondRoomStrategy, KeyEvent.KEYCODE_DPAD_DOWN);
        assertEquals(y + 40, coordinates[1], 0.0);
        coordinates = room.testKeyPress(secondRoomStrategy, KeyEvent.KEYCODE_DPAD_RIGHT);
        assertEquals(x + 40, coordinates[0], 0.0);
    }

    /**
     * This tests the player movement for the third room.
     */
    @Test
    public void testThirdMovementStrategy() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        MovementStrategy thirdRoomStrategy = new ThirdRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coordinates = room.testKeyPress(thirdRoomStrategy, KeyEvent.KEYCODE_DPAD_DOWN);
        assertEquals(y + 30, coordinates[1], 0.0);
        coordinates = room.testKeyPress(thirdRoomStrategy, KeyEvent.KEYCODE_DPAD_RIGHT);
        assertEquals(x + 30, coordinates[0], 0.0);
    }

    /**
     * This test is for all the different movement strategies across all the rooms.
     */
    @Test
    public void testMovementStrategies() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        MovementStrategy initialRoomStrategy = new InitialRoomStrategy();
        MovementStrategy secondRoomStrategy = new SecondRoomStrategy();
        MovementStrategy thirdRoomStrategy = new ThirdRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coordinates = room.testKeyPress(initialRoomStrategy, KeyEvent.KEYCODE_DPAD_DOWN);
        assertEquals(y + 50, coordinates[1], 0.0);
        coordinates = room.testKeyPress(secondRoomStrategy, KeyEvent.KEYCODE_DPAD_RIGHT);
        assertEquals(x + 40, coordinates[0], 0.0);
        y = coordinates[1];
        coordinates = room.testKeyPress(thirdRoomStrategy, KeyEvent.KEYCODE_DPAD_UP);
        assertEquals(y - 30, coordinates[1], 0);
    }
}
