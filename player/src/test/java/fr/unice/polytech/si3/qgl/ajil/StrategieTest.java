package fr.unice.polytech.si3.qgl.ajil;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StrategieTest {
    Strategie strategie;
    Game jeu;
    Ship ship;

    @BeforeEach
    void setUp() {
        ship = new Ship("ship", 100,
                new Position(0.0,0.0,0.0), "name",
                new Deck(2,5),
                new ArrayList<>(),
                new Rectangle("rectangle", 5,5,5));
        jeu = new Game();
        strategie = new Strategie(jeu);
    }

    @Test
    void placerSurRamesTest() {
        ship.setDeck(new Deck(2, 5));
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 0, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(1, 2, 1, "Sailor 1")); // ( 1 , 2 )
        jeu.setSailors(sailors);
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new Entity(0,3,"oar",false)); // ( 0 , 3 )
        entities.add(new Entity(0,1,"oar",false)); // ( 0 , 1 )
        ship.setEntities(entities);
        jeu.setShip(ship);
        strategie.getActions();
        Assertions.assertEquals(0, strategie.getListActions().get(0).getSailorId());
        Assertions.assertEquals(1, strategie.getListActions().get(0).getYdistance());
        Assertions.assertEquals(-1, strategie.getListActions().get(1).getXdistance());
    }

    @Test
    void whereAreSailors() {
        ship.setDeck(new Deck(2, 5));
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 0, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(1, 1, 1, "Sailor 1")); // ( 1 , 2 )
        jeu.setSailors(sailors);
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new Entity(0,0,"oar",false)); // ( 0 , 3 )
        entities.add(new Entity(0,1,"oar",false)); // ( 0 , 1 )
        ship.setEntities(entities);
        jeu.setShip(ship);
        strategie.getActions();
        assertEquals(1, strategie.getLeftSailors().size());
        assertEquals(1, strategie.getRightSailors().size());
    }

    @Test
    void whereAreSailors2() {
        ship.setDeck(new Deck(2, 5));
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 1, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(1, 1, 1, "Sailor 1")); // ( 1 , 2 )
        sailors.add(new Sailor(1, 1, 1, "Sailor 1")); // ( 1 , 2 )
        jeu.setSailors(sailors);
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new Entity(0,1,"oar",false)); // ( 0 , 3 )
        entities.add(new Entity(0,1,"oar",false)); // ( 0 , 1 )
        entities.add(new Entity(0,1,"oar",false)); // ( 0 , 1 )
        ship.setEntities(entities);
        jeu.setShip(ship);
        strategie.getActions();
        assertEquals(0, strategie.getLeftSailors().size());
        assertEquals(3, strategie.getRightSailors().size());
    }
}