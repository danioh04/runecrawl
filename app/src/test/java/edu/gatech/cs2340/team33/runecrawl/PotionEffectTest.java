package edu.gatech.cs2340.team33.runecrawl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.cs2340.team33.runecrawl.Model.Game.Difficulty;
import edu.gatech.cs2340.team33.runecrawl.Model.Items.BasicPotion;
import edu.gatech.cs2340.team33.runecrawl.Model.Items.JumboPotion;
import edu.gatech.cs2340.team33.runecrawl.Model.Items.MediumPotion;
import edu.gatech.cs2340.team33.runecrawl.Model.Items.PotionDecorator;
import edu.gatech.cs2340.team33.runecrawl.Model.Items.SmallPotion;
import edu.gatech.cs2340.team33.runecrawl.Model.Player.Player;
import edu.gatech.cs2340.team33.runecrawl.Model.Player.PlayerType;
//package edu.gatech.cs2340.team33.runecrawl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class is designed to test the functionality of mutating the score.
 */
public class PotionEffectTest {
    private BasicPotion basicPotion;
    @Before
    public void setUp() {
        basicPotion = new BasicPotion();
        Player.initialize("testPlayer", Difficulty.EASY, PlayerType.MAGE);
    }

    /**
     * This is a test to see if applying the jumbo potion effect boosts the
     * healing effect by 20 points.
     */
    @Test
    public void testJumboPotionEffect() {
        basicPotion = new BasicPotion();
        JumboPotion jP = new JumboPotion(basicPotion);
        PotionDecorator decoratorJP = JP;

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
        PotionDecorator decoratorMP = MP;

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
        PotionDecorator decoratorSP = SP;

        assertEquals(basicPotion.getHealthBoost() + 5, decoratorSP.getHealthBoost());
    }


}