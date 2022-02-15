package fr.unice.polytech.si3.qgl.ajil;

import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import fr.unice.polytech.si3.qgl.ajil.shipentities.Entity;
import fr.unice.polytech.si3.qgl.ajil.shipentities.OarEntity;
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
        ArrayList<Checkpoint> checkpoints = new ArrayList<>();
        ArrayList<Sailor> sailors = new ArrayList<>();
        jeu = new Game(
                new Goal("regatte",checkpoints),
                ship,
                sailors,
                4
        );
        strategie = new Strategie(jeu);
        Checkpoint checkpoint = new Checkpoint(new Position(5,5,0), new Circle("circle", 1));
        checkpoints.add(checkpoint);
        Sailor sailor = new Sailor(1,1,0,"sailor1");
        Sailor sailor2 = new Sailor(1,1,0,"sailor2");
        sailors.add(sailor); sailors.add(sailor2);
    }

    @Test
    void testGetRange1() {
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 0, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(1, 1, 1, "Sailor 1")); // ( 1 , 2 )
        jeu.setSailors(sailors);

        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new OarEntity(0, 0, "oar")); // ( 0 , 3 )
        entities.add(new OarEntity(1, 1, "oar")); // ( 0 , 1 )
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
        sailors.add(new Sailor(1, 0, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(1, 0, 1, "Sailor 1")); // ( 1 , 2 )
        sailors.add(new Sailor(1, 1, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(1, 1, 0, "Sailor 0")); // ( 0 , 0 )
        jeu.setSailors(sailors);

        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new OarEntity(1, 0, "oar")); // ( 0 , 3 )
        entities.add(new OarEntity(1, 0, "oar")); // ( 0 , 1 )
        entities.add(new OarEntity(1, 1, "oar")); // ( 0 , 3 )
        entities.add(new OarEntity(1, 1, "oar")); // ( 0 , 3 )
        ship.setEntities(entities);
        jeu.setShip(ship);
        strategie.getActions();

        Set<Double> expectedAnswers = new HashSet<>();
        expectedAnswers.add(0.0);
        expectedAnswers.add(Math.PI / 4);
        expectedAnswers.add(Math.PI / 2);
        expectedAnswers.add(-Math.PI / 2);
        expectedAnswers.add(-Math.PI / 4);
        assertEquals(expectedAnswers, ship.getTurnRange());
    }

    @Test
    void testGetRange3() {
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 0, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(1, 0, 1, "Sailor 1")); // ( 1 , 2 )
        sailors.add(new Sailor(2, 1, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(3, 1, 1, "Sailor 1")); // ( 1 , 2 )
        sailors.add(new Sailor(4, 0, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(5, 0, 1, "Sailor 1")); // ( 1 , 2 )
        sailors.add(new Sailor(6, 1, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(7, 1, 1, "Sailor 1")); // ( 1 , 2 )
        jeu.setSailors(sailors);

        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new OarEntity(0, 0, "oar")); // ( 0 , 3 )
        entities.add(new OarEntity(1, 0, "oar")); // ( 0 , 1 )
        entities.add(new OarEntity(2, 1, "oar")); // ( 0 , 3 )
        entities.add(new OarEntity(3, 1, "oar")); // ( 0 , 1
        entities.add(new OarEntity(4, 0, "oar")); // ( 0 , 3 )
        entities.add(new OarEntity(5, 0, "oar")); // ( 0 , 1 )
        entities.add(new OarEntity(6, 1, "oar")); // ( 0 , 3 )
        entities.add(new OarEntity(7, 1, "oar")); // ( 0 , 1
        ship.setEntities(entities);
        jeu.setShip(ship);
        strategie.getActions();

        Set<Double> expectedAnswers = new HashSet<>();
        expectedAnswers.add(0.0);
        expectedAnswers.add(3*Math.PI / 8);
        expectedAnswers.add(-3*Math.PI / 8);
        expectedAnswers.add(Math.PI / 8);
        expectedAnswers.add(-Math.PI / 8);
        expectedAnswers.add(-Math.PI / 4);
        expectedAnswers.add(Math.PI / 4);
        expectedAnswers.add(Math.PI / 2);
        expectedAnswers.add(-Math.PI / 2);
        assertEquals(expectedAnswers, ship.getTurnRange());
    }
}
