package edu.gatech.cs2340.team33.runecrawl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.view.KeyEvent;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.cs2340.team33.runecrawl.Model.GameDifficulty;
import edu.gatech.cs2340.team33.runecrawl.Model.Player;
import edu.gatech.cs2340.team33.runecrawl.Model.PlayerMovementStrategy;
import edu.gatech.cs2340.team33.runecrawl.Model.PlayerType;
import edu.gatech.cs2340.team33.runecrawl.Model.Strategies.InitialRoomStrategy;
import edu.gatech.cs2340.team33.runecrawl.Model.Strategies.SecondRoomStrategy;
import edu.gatech.cs2340.team33.runecrawl.Model.Strategies.ThirdRoomStrategy;
import edu.gatech.cs2340.team33.runecrawl.ViewModel.RoomViewModel;


public class PlayerMovementTest {
    @Before
    public void setUp() {
        Player.initialize("testPlayer", GameDifficulty.EASY, PlayerType.MAGE);
    }

    /**
     * This tests the left movement command of the player.
     */
    @Test
    public void testLeft() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        PlayerMovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_LEFT);
        assertTrue(coordinates[0] < x);
        assertEquals(coordinates[1], y, 0.0);
    }
    /**
     * This tests the right movement command of the player.
     */
    @Test
    public void testRight() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        PlayerMovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_RIGHT);
        assertTrue(coordinates[0] > x);
        assertEquals(coordinates[1], y, 0.0);
    }
    /**
     * This tests the up movement command of the player
     */
    @Test
    public void testUp() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        PlayerMovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_UP);
        assertEquals(coordinates[0], x, 0.0);
        assertTrue(coordinates[1] < y);
    }
    /**
     * This tests the down movement command of the player
     */
    @Test
    public void testDown() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        PlayerMovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_DOWN);
        assertEquals(coordinates[0], x, 0.0);
        assertTrue(coordinates[1] > y);
    }
    /**
     * This tests the left wall to make sure the player cannot pass that point.
     */
    @Test
    public void testLeftWall() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        PlayerMovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float y = 50;
        float[] coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_LEFT);
        for (int i = 0; i < 10; i++) {
            coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_LEFT);
        }
        assertTrue(coordinates[0] >= 0);
        assertEquals(coordinates[1], y, 0.0);
    }
    /**
     * This tests the right wall to make sure the player cannot pass that point.
     */
    @Test
    public void testRightWall() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        PlayerMovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float y = 50;
        float[] coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_RIGHT);
        for (int i = 0; i < 10; i++) {
            coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_RIGHT);
        }
        assertTrue(coordinates[0] <= 200);
        assertEquals(coordinates[1], y, 0.0);
    }
    /**
     * This tests the upper wall to make sure the player cannot pass that point.
     */
    @Test
    public void testUpperWall() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        PlayerMovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float[] coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_UP);
        for (int i = 0; i < 10; i++) {
            coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_UP);
        }
        assertEquals(coordinates[0], x, 0.0);
        assertTrue(coordinates[1] >= 0);
    }
    /**
     * This tests the lower wall to make sure the player cannot pass that point.
     */
    @Test
    public void testLowerWall() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        PlayerMovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float[] coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_DOWN);
        for (int i = 0; i < 10; i++) {
            coordinates = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_DOWN);
        }
        assertEquals(coordinates[0], x, 0.0);
        assertTrue(coordinates[1] <= 200);
    }

    /**
     * This tests the player movement for the initial room.
     */
    @Test
    public void testInitialMovementStrategy() {
        RoomViewModel room = new RoomViewModel(0, 200, 0, 200);
        PlayerMovementStrategy initialRoomStrategy = new InitialRoomStrategy();
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
        PlayerMovementStrategy secondRoomStrategy = new SecondRoomStrategy();
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
        PlayerMovementStrategy thirdRoomStrategy = new ThirdRoomStrategy();
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
        PlayerMovementStrategy initialRoomStrategy = new InitialRoomStrategy();
        PlayerMovementStrategy secondRoomStrategy = new SecondRoomStrategy();
        PlayerMovementStrategy thirdRoomStrategy = new ThirdRoomStrategy();
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
