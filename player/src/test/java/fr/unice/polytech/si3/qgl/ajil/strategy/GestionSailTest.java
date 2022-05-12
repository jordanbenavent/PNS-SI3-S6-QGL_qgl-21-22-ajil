package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;

class GestionSailTest {
    StratData stratData;
    Game jeu;
    Wind wind;
    Ship ship;
    GestionSail gestionSail;

    @BeforeEach
    void setUp() {
        ship = new Ship("ship", 100, new Position(0.0, 0.0, 0.0), "name", new Deck(2, 5), new ArrayList<>(), new Rectangle("rectangle", 5, 5, 5));
        jeu = new Game(new Goal("regatte", null), ship, null, 4, new Wind(0, 50));
        stratData = new StratData(jeu);
        List<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor());
        stratData.setSailorsManager(sailors);
        wind = stratData.jeu.getWind();
        gestionSail = new GestionSail(stratData);
    }

    /**
     * Simplify angle calculation
     **/
    @Test
    void simplifyAngleTestNoSimplification() {
        final double shipOrientation = 0.0;
        final double windOrientation = 0.0;
        double result = gestionSail.simplifyAngle(shipOrientation, windOrientation);
        assertEquals(0.0, result);
    }

    @Test
    void simplifyAngleTestRangeLimits() {
        double res1 = gestionSail.simplifyAngle(Math.PI, 0.0);
        double res2 = gestionSail.simplifyAngle(0.0, Math.PI);
        assertEquals(Math.PI, res1);
        assertEquals(-Math.PI, res2);
    }

    @Test
    void simplifyAngleTestRange() {
        double res1 = gestionSail.simplifyAngle(Math.PI, Math.PI / 2);
        double res2 = gestionSail.simplifyAngle(-Math.PI / 2, 0);
        assertEquals(Math.PI / 2, res1);
        assertEquals(-Math.PI / 2, res2);
    }

    @Test
    void simplifyAngleTestOutOfRange() {
        final double COEFF = 501;
        double res1 = gestionSail.simplifyAngle(COEFF * Math.PI, Math.PI / 2);
        double res2 = gestionSail.simplifyAngle(-COEFF * Math.PI / 2, 0);
        double res3 = gestionSail.simplifyAngle(Math.PI, COEFF * Math.PI / 2);
        assertEquals(Math.PI / 2, res1, 0.0000001);
        assertEquals(-Math.PI / 2, res2, 0.0000001);
        assertEquals(Math.PI / 2, res3, 0.0000001);
    }

    @Test
    void simplifyAngleTestOutOfRangeAbuse() {
        final double COEFF = 5000001;
        double res1 = gestionSail.simplifyAngle(COEFF * Math.PI, Math.PI / 2);
        double res2 = gestionSail.simplifyAngle(-COEFF * Math.PI / 2, 0);
        assertEquals(Math.PI / 2, res1, 0.001);
        assertEquals(-Math.PI / 2, res2, 0.001);
    }

    /**
     * Is wind straight
     */
    @Test
    void isWindStraightTest() {
        wind.setOrientation(0);
        ship.getPosition().setOrientation(0.0);
        boolean res1 = gestionSail.isWindStraight(ship, wind);

        wind.setOrientation(Math.PI / 2);
        ship.getPosition().setOrientation(-Math.PI / 2);
        boolean res2 = gestionSail.isWindStraight(ship, wind);

        wind.setOrientation(0);
        ship.getPosition().setOrientation(-(Math.PI / 2 + 0.00000001));
        boolean res3 = gestionSail.isWindStraight(ship, wind);

        wind.setOrientation(0);
        ship.getPosition().setOrientation(-Math.PI / 2 - 0.00000001);
        boolean res4 = gestionSail.isWindStraight(ship, wind);

        assertTrue(res1);
        assertFalse(res2);
        assertFalse(res3);
        assertFalse(res4);
    }

    @Test
    void isWindStraightTestOk() {
        GestionSail temp = new GestionSail(stratData);
        GestionSail spyTemp = Mockito.spy(temp);

        Mockito.doReturn(-Math.PI / 2).when(spyTemp).simplifyAngle(anyDouble(), anyDouble());
        boolean status = spyTemp.isWindStraight(spyTemp.stratData.jeu.getShip(), spyTemp.stratData.jeu.getWind());
        assertTrue(status);

        Mockito.doReturn(Math.PI / 2).when(spyTemp).simplifyAngle(anyDouble(), anyDouble());
        boolean status2 = spyTemp.isWindStraight(spyTemp.stratData.jeu.getShip(), spyTemp.stratData.jeu.getWind());
        assertTrue(status2);

        Mockito.doReturn(Math.PI / 2 - 0.00).when(spyTemp).simplifyAngle(anyDouble(), anyDouble());
        boolean status3 = spyTemp.isWindStraight(spyTemp.stratData.jeu.getShip(), spyTemp.stratData.jeu.getWind());
        assertTrue(status3);
    }

    @Test
    void isWindStraightTestWrong() {
        GestionSail temp = new GestionSail(stratData);
        GestionSail spyTemp = Mockito.spy(temp);

        Mockito.doReturn(-Math.PI).when(spyTemp).simplifyAngle(anyDouble(), anyDouble());
        boolean status = spyTemp.isWindStraight(spyTemp.stratData.jeu.getShip(), spyTemp.stratData.jeu.getWind());
        assertFalse(status);

        Mockito.doReturn(Math.PI / 2 + 0.00001).when(spyTemp).simplifyAngle(anyDouble(), anyDouble());
        boolean status2 = spyTemp.isWindStraight(spyTemp.stratData.jeu.getShip(), spyTemp.stratData.jeu.getWind());
        assertFalse(status2);

        Mockito.doReturn(-Math.PI / 2 - 0.0000001).when(spyTemp).simplifyAngle(anyDouble(), anyDouble());
        boolean status3 = spyTemp.isWindStraight(spyTemp.stratData.jeu.getShip(), spyTemp.stratData.jeu.getWind());
        assertFalse(status3);
    }

    /**
     * Put sail when needed
     */
    @Test
    void putSailTestPerfectAlignment() {
        wind.setOrientation(0);
        wind.setStrength(50);
        ship.getPosition().setOrientation(0.0);
        gestionSail.putSail(ship, wind);
        assertTrue(gestionSail.isSailLifted());
    }

    @Test
    void putSailTestPerfectOppositeAlignment() {
        wind.setOrientation(Math.PI);
        wind.setStrength(50);
        ship.getPosition().setOrientation(0.0);
        gestionSail.putSail(ship, wind);
        assertFalse(gestionSail.isSailLifted());
    }

    @Test
    void putSailTestPi2Alignment() {
        wind.setOrientation(-Math.PI / 2);
        wind.setStrength(100);
        ship.getPosition().setOrientation(-4);
        gestionSail.putSail(ship, wind);
        assertFalse(gestionSail.isSailLifted());
    }

    @Test
    void putSailTest2PiShipAlignment() {
        wind.setOrientation(3 * Math.PI / 4); // 3 PI /4
        wind.setStrength(100);
        ship.getPosition().setOrientation(3 * Math.PI); // pi
        gestionSail.putSail(ship, wind);
        assertTrue(gestionSail.isSailLifted());
    }

    @Test
    void putSailTestBugFix() {
        wind.setOrientation(0.00); // 3 PI /4
        wind.setStrength(100);
        ship.getPosition().setOrientation(1.99 * Math.PI); // pi
        gestionSail.putSail(ship, wind);
        assertTrue(gestionSail.isSailLifted());
    }

    @Test
    void putSailHugeCase() {
        wind.setOrientation(0.00); // 3 PI /4
        wind.setStrength(0.1);
        ship.getPosition().setOrientation(10000 * 1.99 * Math.PI); // pi
        gestionSail.putSail(ship, wind);
        assertTrue(gestionSail.isSailLifted());
    }
}
