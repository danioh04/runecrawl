package edu.gatech.cs2340.team33.runecrawl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.cs2340.team33.runecrawl.Model.GameDifficulty;
import edu.gatech.cs2340.team33.runecrawl.Model.Player;
import edu.gatech.cs2340.team33.runecrawl.Model.PlayerType;

public class PlayerTest {
    @Before
    public void setUp() {
        Player.initialize("testPlayer", GameDifficulty.EASY, PlayerType.MAGE);
    }

    @Test
    public void testReceiveDamage() {
        Player player = Player.getInstance();
        int initialHp = player.getCurrentHp();
        int damage = 10;

        player.receiveDamage(damage);

        assertEquals(initialHp - damage, player.getCurrentHp());
    }

    @Test
    public void testReceiveDamageExceedsCurrentHp() {
        Player player = Player.getInstance();
        int damage = player.getCurrentHp() + 50;

        player.receiveDamage(damage);

        assertEquals(0, player.getCurrentHp());
    }

    @Test
    public void testDecreaseScoreBelowZero() {
        Player player = Player.getInstance();
        int timesToDecreaseScore = player.getScore() + 1;

        assertThrows(IllegalStateException.class, () -> {
            for (int i = 0; i < timesToDecreaseScore; i++) {
                player.decreaseScore();
            }
        });
    }
}