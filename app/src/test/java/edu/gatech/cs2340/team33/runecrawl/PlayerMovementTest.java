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

    @Test
    public void testLeft() {
        RoomViewModel room = new RoomViewModel(0, 0, 0, 0);
        PlayerMovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coords = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_LEFT);
        assertTrue(coords[0] < x);
        assertEquals(coords[1], y, 0.0);
    }

    @Test
    public void testRight() {
        RoomViewModel room = new RoomViewModel(0, 0, 0, 0);
        PlayerMovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coords = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_RIGHT);
        assertTrue(coords[0] > x);
        assertEquals(coords[1], y, 0.0);
    }

    @Test
    public void testUp() {
        RoomViewModel room = new RoomViewModel(0, 0, 0, 0);
        PlayerMovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coords = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_UP);
        assertEquals(coords[0], x, 0.0);
        assertTrue(coords[1] < y);
    }

    @Test
    public void testDown() {
        RoomViewModel room = new RoomViewModel(0, 0, 0, 0);
        PlayerMovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coords = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_DOWN);
        assertEquals(coords[0], x, 0.0);
        assertTrue(coords[1] > y);
    }

    @Test
    public void testLeftWall() {
        RoomViewModel room = new RoomViewModel(0, 0, 0, 0);
        PlayerMovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float y = 50;
        float[] coords = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_LEFT);
        for (int i = 0; i < 10; i++) {
            coords = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_LEFT);
        }
        assertTrue(coords[0] >= 0);
        assertEquals(coords[1], y, 0.0);
    }

    @Test
    public void testRightWall() {
        RoomViewModel room = new RoomViewModel(0, 0, 0, 0);
        PlayerMovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float y = 50;
        float[] coords = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_RIGHT);
        for (int i = 0; i < 10; i++) {
            coords = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_RIGHT);
        }
        assertTrue(coords[0] <= 100);
        assertEquals(coords[1], y, 0.0);
    }

    @Test
    public void testUpperWall() {
        RoomViewModel room = new RoomViewModel(0, 0, 0, 0);
        PlayerMovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float[] coords = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_UP);
        for (int i = 0; i < 10; i++) {
            coords = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_UP);
        }
        assertEquals(coords[0], x, 0.0);
        assertTrue(coords[1] >= 0);
    }

    @Test
    public void testLowerWall() {
        RoomViewModel room = new RoomViewModel(0, 0, 0, 0);
        PlayerMovementStrategy playerMovementStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float[] coords = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_DOWN);
        for (int i = 0; i < 10; i++) {
            coords = room.testKeyPress(playerMovementStrategy, KeyEvent.KEYCODE_DPAD_DOWN);
        }
        assertEquals(coords[0], x, 0.0);
        assertTrue(coords[1] <= 100);
    }

    @Test
    public void testInitialMovementStrategy() {
        RoomViewModel room = new RoomViewModel(0, 0, 0, 0);
        PlayerMovementStrategy initialRoomStrategy = new InitialRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coords = room.testKeyPress(initialRoomStrategy, KeyEvent.KEYCODE_DPAD_DOWN);
        assertEquals(y + 30, coords[1], 0.0);
        coords = room.testKeyPress(initialRoomStrategy, KeyEvent.KEYCODE_DPAD_RIGHT);
        assertEquals(x + 30, coords[0], 0.0);
    }

    @Test
    public void testSecondMovementStrategy() {
        RoomViewModel room = new RoomViewModel(0, 0, 0, 0);
        PlayerMovementStrategy secondRoomStrategy = new SecondRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coords = room.testKeyPress(secondRoomStrategy, KeyEvent.KEYCODE_DPAD_DOWN);
        assertEquals(y + 20, coords[1], 0.0);
        coords = room.testKeyPress(secondRoomStrategy, KeyEvent.KEYCODE_DPAD_RIGHT);
        assertEquals(x + 20, coords[0], 0.0);
    }

    @Test
    public void testThirdMovementStrategy() {
        RoomViewModel room = new RoomViewModel(0, 0, 0, 0);
        PlayerMovementStrategy thirdRoomStrategy = new ThirdRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coords = room.testKeyPress(thirdRoomStrategy, KeyEvent.KEYCODE_DPAD_DOWN);
        assertEquals(y + 40, coords[1], 0.0);
        coords = room.testKeyPress(thirdRoomStrategy, KeyEvent.KEYCODE_DPAD_RIGHT);
        assertEquals(x + 40, coords[0], 0.0);
    }

    @Test
    public void testMovementStrategies() {
        RoomViewModel room = new RoomViewModel(0, 0, 0, 0);
        PlayerMovementStrategy initialRoomStrategy = new InitialRoomStrategy();
        PlayerMovementStrategy secondRoomStrategy = new SecondRoomStrategy();
        PlayerMovementStrategy thirdRoomStrategy = new ThirdRoomStrategy();
        room.setXY(50, 50);
        float x = 50;
        float y = 50;
        float[] coords = room.testKeyPress(initialRoomStrategy, KeyEvent.KEYCODE_DPAD_DOWN);
        assertEquals(y + 30, coords[1], 0.0);
        coords = room.testKeyPress(secondRoomStrategy, KeyEvent.KEYCODE_DPAD_RIGHT);
        assertEquals(x + 20, coords[0], 0.0);
        y = coords[1];
        coords = room.testKeyPress(thirdRoomStrategy, KeyEvent.KEYCODE_DPAD_UP);
        assertEquals(y - 40, coords[1], 0);
    }
}
