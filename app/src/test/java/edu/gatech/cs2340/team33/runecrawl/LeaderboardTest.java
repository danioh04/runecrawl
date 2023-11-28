package edu.gatech.cs2340.team33.runecrawl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import edu.gatech.cs2340.team33.runecrawl.Model.GameAttempt;
import edu.gatech.cs2340.team33.runecrawl.Model.GameDifficulty;
import edu.gatech.cs2340.team33.runecrawl.Model.Leaderboard;
import edu.gatech.cs2340.team33.runecrawl.Model.Player;
import edu.gatech.cs2340.team33.runecrawl.Model.PlayerType;

/**
 * This class is designed to test the functionality of the leaderboard with test cases.
 */
public class LeaderboardTest {
    private Leaderboard leaderboard;

    @Before
    public void setUp() {
        leaderboard = Leaderboard.getInstance();
    }

    /**
     * This test makes sure that an exception will be created when a null attempts to be added.
     */
    @Test
    public void testAddNullAttempt() {
        assertThrows("Did not throw Exception.", IllegalArgumentException.class, () -> leaderboard.addAttempt(null));
    }

    /**
     * This test adds attempts to leaderboard and determines if the leaderboard contains the
     * attempts that were added.
     */
    @Test
    public void testAddAttempts() {
        Player.initialize("player1", GameDifficulty.EASY, PlayerType.MAGE);
        Player player1 = Player.getInstance();
        player1.decreaseScore();
        GameAttempt attempt1 = new GameAttempt(player1);

        Player.initialize("player2", GameDifficulty.EASY, PlayerType.MAGE);
        Player player2 = Player.getInstance();
        GameAttempt attempt2 = new GameAttempt(player2);

        leaderboard.addAttempt(attempt1);
        leaderboard.addAttempt(attempt2);

        List<GameAttempt> topAttempts = leaderboard.getTopAttempts();

        assertEquals(2, topAttempts.size());
    }

    /**
     * This test is to make sure that the leaderboard is in the correct oder based on the score.
     */
    @Test
    public void testLeaderboardInOrder() {
        List<GameAttempt> topAttempts = leaderboard.getTopAttempts();
        assertEquals("player2", topAttempts.get(0).getUsername());
        assertEquals("player1", topAttempts.get(1).getUsername());
    }

    /**
     * This test is to test when we have a full leaderboard and a new player tries to be added and
     * unless it has a higher score than anything on the leaderboard it should not be on the
     * leaderboard.
     */
    @Test
    public void testAddAttemptToFullLeaderboard() {
        for (int i = 0; i < 5; i++) {
            Player.initialize("player" + i, GameDifficulty.EASY, PlayerType.MAGE);
            Player player = Player.getInstance();
            GameAttempt attempt = new GameAttempt(player);
            leaderboard.addAttempt(attempt);
        }

        Player.initialize("newPlayer", GameDifficulty.EASY, PlayerType.MAGE);
        Player newPlayer = Player.getInstance();
        newPlayer.decreaseScore();
        GameAttempt newAttempt = new GameAttempt(newPlayer);
        leaderboard.addAttempt(newAttempt);

        List<GameAttempt> topAttempts = leaderboard.getTopAttempts();

        assertEquals(5, topAttempts.size());
        assertEquals("player0", topAttempts.get(0).getUsername());
        assertTrue(topAttempts.stream().noneMatch(attempt -> "newPlayer".equals(attempt.getUsername())));
    }
}
