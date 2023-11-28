package edu.gatech.cs2340.team33.runecrawl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.cs2340.team33.runecrawl.Model.Enemies.Enemy;
import edu.gatech.cs2340.team33.runecrawl.Model.Enemies.EnemyType;
import edu.gatech.cs2340.team33.runecrawl.Model.Game.Difficulty;
import edu.gatech.cs2340.team33.runecrawl.Model.Player.Player;
import edu.gatech.cs2340.team33.runecrawl.Model.Player.PlayerType;

/**
 * This class is designed to test the functionality of the player for specifically whether they take
 * damage and die properly
 */
public class PlayerTest {
    private EnemyType enemySlime;
    private EnemyType enemyOrc;
    private EnemyType enemyRobot;

    @Before
    public void setUp() {
        Player.initialize("testPlayer", Difficulty.EASY, PlayerType.MAGE);
        enemySlime = EnemyType.SLIME;
        enemyOrc = EnemyType.ORC;
        enemyRobot = EnemyType.ROBOT;
    }

    /**
     * This is the first test to see if the damage is taken and subtracted properly.
     */
    @Test
    public void testReceiveDamage() {
        Player player = Player.getInstance();
        int initialHp = player.getCurrentHp();
        int damage = 10;

        player.receiveDamage(damage);

        assertEquals(initialHp - damage, player.getCurrentHp());
    }

    /**
     * This is a test to see when the damage taken is greater than currentHP which means the
     * character dies so hp should be zero rather than a negative number.
     */
    @Test
    public void testReceiveDamageExceedsCurrentHp() {
        Player player = Player.getInstance();
        int damage = player.getCurrentHp() + 50;

        player.receiveDamage(damage);

        assertEquals(0, player.getCurrentHp());
    }

    /**
     * This checks to see that if when the player gets a negative score, it is an illegal state
     * as it should never happen.
     */
    @Test
    public void testDecreaseScoreBelowZero() {
        Player player = Player.getInstance();
        int timesToDecreaseScore = player.getScore() + 1;
        player.decreaseScore(timesToDecreaseScore);
        assertEquals(player.getScore(), 0);
    }

    /**
     * This is a test to make sure that an invalid username gives an error and doesn't work.
     */
    @Test
    public void testIllegalUsername() {
        assertThrows(IllegalArgumentException.class, () ->
                Player.initialize(" ", Difficulty.EASY, PlayerType.MAGE));
    }

    /**
     * This is a test to make sure that an invalid difficulty gives an error and doesn't work.
     */
    @Test
    public void testIllegalDifficulty() {
        assertThrows(IllegalArgumentException.class, () ->
                Player.initialize("testPlayer", null, PlayerType.MAGE));
    }

    /**
     * This is a test to make sure that an invalid player type gives an error and doesn't work.
     */
    @Test
    public void testIllegalPlayerType() {
        assertThrows(IllegalArgumentException.class, () ->
                Player.initialize("testPlayer", Difficulty.EASY, null));
    }

    /**
     * This tests to see whether the player instance is not null.
     */
    @Test
    public void testNullPlayerInstance() {
        Player instance = Player.getInstance();
        assertNotNull(instance);
    }

    /**
     * This is a test to see how the damage from the slime enemy works.
     */
    @Test
    public void testReceiveDamageFromSlime() {
        Player player = Player.getInstance();
        int initialHp = player.getCurrentHp();
        int damage = (int) (player.getDifficulty().getEnemyDamageMultiplier()
                * enemySlime.getBaseDamageRate());
        player.receiveDamage(damage);
        assertEquals(player.getCurrentHp(), initialHp - damage);

    }

    /**
     * This is a test to see how the damage from the orc enemy works.
     */
    @Test
    public void testReceiveDamageFromOrc() {
        Player player = Player.getInstance();
        int initialHp = player.getCurrentHp();
        int damage = (int) (player.getDifficulty().getEnemyDamageMultiplier()
                * enemyOrc.getBaseDamageRate());
        player.receiveDamage(damage);
        assertEquals(player.getCurrentHp(), initialHp - damage);
    }

    /**
     * This is a test to see how the damage from the robot enemy works.
     */
    @Test
    public void testReceiveDamageFromRobot() {
        Player player = Player.getInstance();
        int initialHp = player.getCurrentHp();
        int damage = (int) (player.getDifficulty().getEnemyDamageMultiplier()
                * enemyRobot.getBaseDamageRate());
        player.receiveDamage(damage);
        assertEquals(player.getCurrentHp(), initialHp - damage);

    }

    /**
     * This is a test to see how the damage from the werewolf enemy works.
     */
    @Test
    public void testReceiveDamageFromWerewolf() {
        Player player = Player.getInstance();
        int initialHp = player.getCurrentHp();
        int damage = (int) (player.getDifficulty().getEnemyDamageMultiplier()
                * enemyOrc.getBaseDamageRate());
        player.receiveDamage(damage);
        assertEquals(player.getCurrentHp(), initialHp - damage);
    }

    @Test
    public void testDecreaseScore() {
        Player player = Player.getInstance();
        int score = player.getScore();
        int damage = (int) (player.getDifficulty().getEnemyDamageMultiplier()
                * EnemyType.ORC.getBaseDamageRate());
        player.decreaseScore(damage);
        int newScore = player.getScore();
        assertEquals(score - damage, newScore);
    }
    

}
