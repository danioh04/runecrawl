package edu.gatech.cs2340.team33.runecrawl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.cs2340.team33.runecrawl.Model.Enemy;
import edu.gatech.cs2340.team33.runecrawl.Model.EnemyType;
import edu.gatech.cs2340.team33.runecrawl.ViewModel.RoomViewModel;
public class EnemyTest {
    @Before
    public void setUp() {
        Enemy enemyTest = new Enemy(EnemyType.SLIME, 50);
    }

    /*
    This test damages the enemy by more Hp than it currenlty has, and then checks if the enemy is dead
    or that is has 0 Hp.
    */
    @Test
    public void testEnemyDying() throws Exception {
        Enemy enemyTest = new Enemy(EnemyType.SLIME, 50);
        int damage = enemyTest.getCurrentHp() - 1;
        enemyTest.receiveDamage(damage);
        assertTrue(enemyTest.getCurrentHp() == 0);
    }

    /*
    This test will test if every "enemy type" differs by at least 2 attributes.
    */
    @Test
    public void testEnemyDiffAttributes() throws Exception {
        EnemyType enemyType1 = EnemyType.SLIME;
        EnemyType enemyType2 = EnemyType.ORC;
        EnemyType enemyType3 = EnemyType.ROBOT;
        EnemyType enemyType4 = EnemyType.WEREWOLF;

        int enemyType1Res = enemyType1.getSpriteResId();
        int enemyType2Res = enemyType2.getSpriteResId();
        int enemyType3Res = enemyType3.getSpriteResId();
        int enemyType4Res = enemyType4.getSpriteResId();

        int enemyType1Damage = enemyType1.getDamageRate();
        int enemyType2Damage = enemyType2.getDamageRate();
        int enemyType3Damage = enemyType3.getDamageRate();
        int enemyType4Damage = enemyType4.getDamageRate();

        /*
        checks to see if enemytype1 is different than enemytype2 in 3 attributes.
         */
        assertTrue(enemyType1Res != enemyType2Res && enemyType1Damage != enemyType2Damage;
        /*
        checks to see if enemytype1 is different than enemytype3 in 3 attributes.
         */
        assertTrue(enemyType1Res != enemyType3Res && enemyType1Damage != enemyType3Damage);
        /*
        checks to see if enemytype1 is different than enemytype4 in 3 attributes.
         */
        assertTrue(enemyType1Res != enemyType4Res && enemyType1Damage != enemyType4Damage);

        /*
        checks to see if enemytype2 is different than enemytype3 in 3 attributes.
         */
        assertTrue(enemyType2Res != enemyType3Res && enemyType2Damage != enemyType3Damage);
        /*
        checks to see if enemytype2 is different than enemytype4 in 3 attributes.
         */
        assertTrue(enemyType2Res != enemyType4Res && enemyType2Damage != enemyType4Damage);

        /*
        checks to see if enemytype3 is different than enemytype4 in 3 attributes.
         */
        assertTrue(enemyType3Res != enemyType4Res && enemyType3Damage != enemyType4Damage);
    }

    /*
    This test will test if every "enemy type" has different movement patterns.
    */
    @Test
    public void testEnemyMovementAttribute() throws Exception {
        EnemyType enemyType1 = EnemyType.SLIME;
        EnemyType enemyType2 = EnemyType.ORC;
        EnemyType enemyType3 = EnemyType.ROBOT;
        EnemyType enemyType4 = EnemyType.WEREWOLF;

        int enemyType1Speed = enemyType1.getMovementSpeed();
        int enemyType2Speed = enemyType2.getMovementSpeed();
        int enemyType3Speed = enemyType3.getMovementSpeed();
        int enemyType4Speed = enemyType4.getMovementSpeed();

        assertTrue(enemyType1Speed != enemyType2Speed);
        assertTrue(enemyType1Speed != enemyType3Speed);
        assertTrue(enemyType1Speed != enemyType4Speed);
        assertTrue(enemyType2Speed != enemyType3Speed));
        assertTrue(enemyType2Speed != enemyType4Speed);
        assertTrue(enemyType3Speed != enemyType4Speed);
    }

    /*
    This test  randomly moves the enemy and then checks to see if the coordinates or the enemy are different.
    */
    @Test
    public void testEnemyRandomMovement() throws Exception {
        Enemy enemyTest = new Enemy(EnemyType.SLIME, 50);
        RoomViewModel roomTest = new RoomViewModel(50, 50, 50, 50);
        int x = enemyTest.getX();
        int y = enemyTest.getY();
        enemyTest.moveRandomly(roomTest);
        assertTrue(x != enemyTest.getX() || y != enemyTest.getY());
    }
}
