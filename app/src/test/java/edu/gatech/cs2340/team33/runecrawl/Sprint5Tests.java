package edu.gatech.cs2340.team33.runecrawl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import edu.gatech.cs2340.team33.runecrawl.Model.Enemies.Enemy;
import edu.gatech.cs2340.team33.runecrawl.Model.Enemies.EnemyType;
import edu.gatech.cs2340.team33.runecrawl.Model.Game.Attempt;
import edu.gatech.cs2340.team33.runecrawl.Model.Game.Difficulty;
import edu.gatech.cs2340.team33.runecrawl.Model.Game.Leaderboard;
import edu.gatech.cs2340.team33.runecrawl.Model.Items.BasicPotion;
import edu.gatech.cs2340.team33.runecrawl.Model.Items.JumboPotion;
import edu.gatech.cs2340.team33.runecrawl.Model.Items.MediumPotion;
import edu.gatech.cs2340.team33.runecrawl.Model.Items.PotionDecorator;
import edu.gatech.cs2340.team33.runecrawl.Model.Items.SmallPotion;
import edu.gatech.cs2340.team33.runecrawl.Model.Player.Player;
import edu.gatech.cs2340.team33.runecrawl.Model.Player.PlayerType;
import edu.gatech.cs2340.team33.runecrawl.ViewModel.RoomViewModel;

/**
 * This class is designed to test the functionality of the enemy's movement test.
 */
public class Sprint5Tests {
    private static final int ROOM_BOUNDARY = 200;
    private Enemy enemyTest;
    private Leaderboard leaderboard;
    private BasicPotion basicPotion;
    private Enemy enemy;
    private static final int MAX_HEALTH = 100;

    @Before
    public void setUp() {
        Player.initialize("testPlayer", Difficulty.EASY, PlayerType.MAGE);
        enemyTest = new Enemy(EnemyType.ORC, 100, 20, 20);
        leaderboard = Leaderboard.getInstance();
        basicPotion = new BasicPotion();
        enemy = new Enemy(EnemyType.SLIME, MAX_HEALTH, 20, 20);
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
                + enemyTest.getX() + "\n", inbounds);
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
                + enemyTest.getX() + "\n", inbounds);
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
                + enemyTest.getY() + "\n", inbounds);
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
                + enemyTest.getY() + "\n", inbounds);
    }

    /**
     * This test makes sure that an exception will be created when a null attempts to be added.
     */
    @Test
    public void testAddNullAttempt() {
        assertThrows("Did not throw Exception.", IllegalArgumentException.class,
                () -> leaderboard.addAttempt(null));
    }

    /**
     * This test adds attempts to leaderboard and determines if the leaderboard contains the
     * attempts that were added.
     */
    @Test
    public void testAddAttempts() {
        Player.initialize("player1", Difficulty.EASY, PlayerType.MAGE);
        Player player1 = Player.getInstance();
        player1.decreaseScore(1);
        Attempt attempt1 = new Attempt(player1);

        Player.initialize("player2", Difficulty.EASY, PlayerType.MAGE);
        Player player2 = Player.getInstance();
        Attempt attempt2 = new Attempt(player2);

        leaderboard.addAttempt(attempt1);
        leaderboard.addAttempt(attempt2);

        List<Attempt> topAttempts = leaderboard.getTopAttempts();

        assertEquals(2, topAttempts.size());
    }

    /**
     * This test is to make sure that the leaderboard is in the correct oder based on the score.
     */
    @Test
    public void testLeaderboardInOrder() {
        List<Attempt> topAttempts = leaderboard.getTopAttempts();
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
            Player.initialize("player" + i, Difficulty.EASY, PlayerType.MAGE);
            Player player = Player.getInstance();
            Attempt attempt = new Attempt(player);
            leaderboard.addAttempt(attempt);
        }

        Player.initialize("newPlayer", Difficulty.EASY, PlayerType.MAGE);
        Player newPlayer = Player.getInstance();
        newPlayer.decreaseScore(1);
        Attempt newAttempt = new Attempt(newPlayer);
        leaderboard.addAttempt(newAttempt);

        List<Attempt> topAttempts = leaderboard.getTopAttempts();

        assertEquals(5, topAttempts.size());
        assertEquals("player0", topAttempts.get(0).getUsername());
        assertTrue(topAttempts.stream().noneMatch(attempt ->
                "newPlayer".equals(attempt.getUsername())));
    }

    /**
     * This is a test to see if applying the jumbo potion effect boosts the
     * healing effect by 20 points.
     */
    @Test
    public void testJumboPotionEffect() {
        basicPotion = new BasicPotion();
        JumboPotion jP = new JumboPotion(basicPotion);
        PotionDecorator decoratorJP = jP;
        assertEquals(basicPotion.getHealthBoost() + 20, decoratorJP.getHealthBoost());
    }

    /**
     * This is a test to see if applying the Medium potion effect boosts the
     * healing effect by 10 points.
     */
    @Test
    public void testMediumPotionEffect() {
        basicPotion = new BasicPotion();
        MediumPotion mP = new MediumPotion(basicPotion);
        PotionDecorator decoratorMP = mP;
        assertEquals(basicPotion.getHealthBoost() + 10, decoratorMP.getHealthBoost());
    }

    /**
     * This is a test to see if applying the Small potion effect boosts the
     * healing effect by 5 points.
     */
    @Test
    public void testSmallPotionEffect() {
        basicPotion = new BasicPotion();
        SmallPotion sP = new SmallPotion(basicPotion);
        PotionDecorator decoratorSP = sP;
        assertEquals(basicPotion.getHealthBoost() + 5, decoratorSP.getHealthBoost());
    }

    /**
     * tests if enemy's health decreases when receiving damage
     */
    @Test
    public void testSlimeTakingDamage() {
        Enemy enemyTest = new Enemy(EnemyType.SLIME, 50, 20, 20);
        int damage = enemyTest.getCurrentHp() - 1;
        enemyTest.receiveDamage(damage);
        assertEquals(1, enemyTest.getCurrentHp());
    }

    /**
     * checks that player's health increases after drinking potion
     */
    @Test
    public void testPlayerDrinkPotion() {
        Player.initialize("testPlayer", Difficulty.HARD, PlayerType.MAGE);
        Player player = Player.getInstance();
        BasicPotion potion = new BasicPotion();
        JumboPotion potion1 = new JumboPotion(potion);
        int health = player.getCurrentHp() + potion1.getHealthBoost();
        player.drinkPotion(potion1);
        assertEquals(health, player.getCurrentHp());
    }
}
