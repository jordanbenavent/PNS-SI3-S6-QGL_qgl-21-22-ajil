package fr.unice.polytech.si3.qgl.ajil;

import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipTest {
    Ship ship;
    Game jeu;
    Strategie strategie;

    @BeforeEach
    void setUp() {
        ship = new Ship("ship", 100,
                new Position(0.0, 0.0, 0.0), "name",
                new Deck(2, 5),
                new ArrayList<>(),
                new Rectangle("rectangle", 5, 5, 5));
        jeu = new Game();
        strategie = new Strategie(jeu);
    }

    @Test
    void testGetRange1() {
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 0, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(1, 1, 1, "Sailor 1")); // ( 1 , 2 )
        jeu.setSailors(sailors);

        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new Entity(0, 0, "oar", false)); // ( 0 , 3 )
        entities.add(new Entity(1, 1, "oar", false)); // ( 0 , 1 )
        ship.setEntities(entities);
        jeu.setShip(ship);
        strategie.getActions();

        Set<Double> expectedAnswers = new HashSet<>();
        expectedAnswers.add(0.0);
        expectedAnswers.add(Math.PI / 2);
        expectedAnswers.add(-Math.PI / 2);
        assertEquals(expectedAnswers, ship.getTurnRange());
    }

    @Test
    void testGetRange2() {
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(1, 1, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(1, 1, 1, "Sailor 1")); // ( 1 , 2 )
        jeu.setSailors(sailors);

        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new Entity(1, 1, "oar", false)); // ( 0 , 3 )
        entities.add(new Entity(1, 1, "oar", false)); // ( 0 , 1 )
        ship.setEntities(entities);
        jeu.setShip(ship);
        strategie.getActions();

        Set<Double> expectedAnswers = new HashSet<>();
        expectedAnswers.add(0.0);
        expectedAnswers.add(Math.PI / 4);
        expectedAnswers.add(Math.PI / 2);
        assertEquals(expectedAnswers, ship.getTurnRange());
    }

    @Test
    void testGetRange3() {
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 0, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(1, 0, 1, "Sailor 1")); // ( 1 , 2 )
        sailors.add(new Sailor(2, 1, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(3, 1, 1, "Sailor 1")); // ( 1 , 2 )
        jeu.setSailors(sailors);

        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new Entity(0, 0, "oar", false)); // ( 0 , 3 )
        entities.add(new Entity(1, 0, "oar", false)); // ( 0 , 1 )
        entities.add(new Entity(2, 1, "oar", false)); // ( 0 , 3 )
        entities.add(new Entity(3, 1, "oar", false)); // ( 0 , 1
        ship.setEntities(entities);
        jeu.setShip(ship);
        strategie.getActions();

        Set<Double> expectedAnswers = new HashSet<>();
        expectedAnswers.add(0.0);
        expectedAnswers.add(-Math.PI / 4);
        expectedAnswers.add(Math.PI / 4);
        expectedAnswers.add(Math.PI / 2);
        expectedAnswers.add(-Math.PI / 2);
        assertEquals(expectedAnswers, ship.getTurnRange());
    }
}
