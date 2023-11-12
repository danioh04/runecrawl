package edu.gatech.cs2340.team33.runecrawl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.cs2340.team33.runecrawl.Model.Enemy;
import edu.gatech.cs2340.team33.runecrawl.Model.EnemyType;

public class EnemyTest {
    private Enemy enemy;
    @Before
    public void setUp() {
        enemy = new Enemy(EnemyType.SLIME, 100);
    }
    @Test
    public void testEnemyAlive() {
        int damage = 100;
        enemy.receiveDamage(damage);
        assertEquals(false, enemy.isAlive());
    }

    @Test
    public void testIllegalEnemyType() {
        assertThrows(IllegalArgumentException.class, () ->
                new Enemy(null, 100));
    }

    @Test
    public void testZeroHp() {
        assertThrows(IllegalArgumentException.class, () ->
                new Enemy(EnemyType.ORC, 0));
    }

    @Test
    public void test() {

    }


}