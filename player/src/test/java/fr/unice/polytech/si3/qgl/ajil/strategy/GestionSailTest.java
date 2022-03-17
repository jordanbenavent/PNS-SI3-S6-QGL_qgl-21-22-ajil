package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GestionSailTest {
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
        stratData.setSailorsManager(new Sailor());
        wind = stratData.jeu.getWind();
        gestionSail = new GestionSail(stratData);
    }

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
        ship.getPosition().setOrientation(1.99*Math.PI); // pi
        gestionSail.putSail(ship, wind);
        assertTrue(gestionSail.isSailLifted());
    }
}
