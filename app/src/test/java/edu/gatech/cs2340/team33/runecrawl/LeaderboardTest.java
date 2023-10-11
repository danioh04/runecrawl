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

public class LeaderboardTest {
    private Leaderboard leaderboard;

    @Before
    public void setUp() {
        leaderboard = Leaderboard.getInstance();
    }

    @Test
    public void testAddNullAttempt() {
        assertThrows(IllegalArgumentException.class, () -> leaderboard.addAttempt(null));
    }

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
        assertEquals(player2.getUsername(), topAttempts.get(0).getUsername());
        assertEquals(player1.getUsername(), topAttempts.get(1).getUsername());
    }

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
